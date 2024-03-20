package ru.kode.base.core.routing.coordinator

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ru.kode.base.core.viewmodel.ViewModelProviders
import ru.kode.base.core.viewmodel.ViewModelStore
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

abstract class BaseFlowCoordinator<Event, Result>(
  viewModelProviders: ViewModelProviders,
  flowEvents: MutableSharedFlow<Event>,
  viewModelStore: ViewModelStore,
) : BaseFlowCoordinator1<Event, Unit, Result>(viewModelProviders, flowEvents, viewModelStore),
  FlowCoordinator<Event, Result> {
  final override fun start(
    onFlowFinish: suspend ((Result) -> Unit),
    onError: (Throwable) -> Result,
  ) {
    start(Unit, onFlowFinish, onError)
  }

  final override suspend fun onFlowStart(params: Unit): Unit = onFlowStart()

  open suspend fun onFlowStart() = Unit
}

abstract class BaseFlowCoordinator1<Event, Params, Result>(
  private val viewModelProviders: ViewModelProviders,
  private val flowEvents: MutableSharedFlow<Event>,
  private val viewModelStore: ViewModelStore,
) : FlowCoordinator1<Event, Params, Result> {
  private var onFinish: (suspend (Result) -> Unit)? = null
  private val errorHandler =
    CoroutineExceptionHandler { _, e ->
      Timber.e(e, "coordinator error")
    }
  val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default + errorHandler)
  private val hooks: MutableList<FlowCoordinatorHook<Event, Params, Result>> = mutableListOf()
  private val isFinishing = AtomicBoolean(false)
  private var _params: Params? = null
  override val params: Params
    get() = _params ?: error("params is null. You need to call the start() method first")

  open fun onFlowFinish() = Unit

  final override fun start(
    params: Params,
    onFlowFinish: suspend ((Result) -> Unit),
    onError: (Throwable) -> Result,
  ) {
    val handler =
      CoroutineExceptionHandler { _, error ->
        if (error !is CancellationException) {
          handleFlowError(error, onError)
        }
      }
    coroutineScope.launch(handler) {
      logFlowStart(params)
      hooks.forEach { it.preStart(params) }
      onFinish = onFlowFinish
      _params = params
      viewModelStore.register(viewModelProviders)
      withContext(Dispatchers.Main.immediate) {
        onFlowStart(params)
      }
      flowEvents
        .onEach { event ->
          hooks.forEach { hook -> hook.preHandleEvent(event) }
          withContext(Dispatchers.Main.immediate) {
            handleEvent(event)
          }
          hooks.forEach { hook -> hook.postHandleEvent(event) }
        }
        .launchIn(this)
      hooks.forEach { it.postStart(params) }
    }
  }

  final override fun finish(result: Result) {
    runBlocking {
      finishAsync(result = result).join()
    }
  }

  final override fun finishAsync(result: Result): Job {
    return coroutineScope.launch {
      if (!isFinishing.get()) {
        isFinishing.set(true)
        hooks.forEach { it.preFinish(result) }
        logFlowFinishing(result)
        viewModelStore.unregister(viewModelProviders)
        withContext(Dispatchers.Main.immediate) {
          onFlowFinish()
          onFinish?.invoke(result)
        }

        hooks.forEach { it.postFinish(result) }
      }
    }
      .apply { invokeOnCompletion { coroutineScope.cancel() } }
  }

  private fun handleFlowError(
    error: Throwable,
    onErrorReturn: (Throwable) -> Result,
  ) {
    coroutineScope.launch {
      if (!isFinishing.get()) {
        isFinishing.set(true)
        val result = onErrorReturn(error)
        hooks.forEach { it.preFinish(result) }
        logFlowError(error, result)
        withContext(Dispatchers.Main.immediate) {
          onFlowFinish()
          onFinish?.invoke(result)
        }
        hooks.forEach { it.postFinish(result) }
      }
    }.invokeOnCompletion { coroutineScope.cancel() }
  }

  abstract suspend fun onFlowStart(params: Params)

  abstract override suspend fun handleEvent(event: Event)

  override fun addHook(hook: FlowCoordinatorHook<Event, Params, Result>) {
    hooks.add(hook)
  }

  override fun removeHook(hook: FlowCoordinatorHook<Event, Params, Result>) {
    hooks.remove(hook)
  }

  protected fun runOnMainThread(body: () -> Unit) {
    coroutineScope.launch(Dispatchers.Main.immediate) { body() }
  }

  private fun logFlowStart(params: Params) {
    Timber.d("┌─────────────────────────────────────────────────────────")
    Timber.d("│ Starting [${flowDebugName()}] flow")
    Timber.d("│  params=$params")
    Timber.d("└─────────────────────────────────────────────────────────")
  }

  private fun logFlowFinishing(result: Result) {
    Timber.d("┌─────────────────────────────────────────────────────────")
    Timber.d("│ Finishing [${flowDebugName()}] flow")
    Timber.d("│  result=$result")
    Timber.d("└─────────────────────────────────────────────────────────")
  }

  private fun logFlowError(
    error: Throwable,
    result: Result,
  ) {
    Timber.d("┌─────────────────────────────────────────────────────────")
    Timber.d("│ Interrupting [${flowDebugName()}] flow")
    Timber.d("│  result=$result")
    Timber.d("│  error=$error")
    Timber.d("└─────────────────────────────────────────────────────────")
    Timber.e(error)
  }

  private fun flowDebugName() = this.javaClass.canonicalName!!.split('.').findLast { it.endsWith("Flow") }
}

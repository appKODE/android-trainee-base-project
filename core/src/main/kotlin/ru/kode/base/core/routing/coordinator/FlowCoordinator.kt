package ru.kode.base.core.routing.coordinator

import kotlinx.coroutines.Job

interface FlowCoordinator<Event, Result> : FlowCoordinator1<Event, Unit, Result> {
  fun start(
    onFlowFinish: suspend (Result) -> Unit,
    onError: (Throwable) -> Result
  )
}

interface FlowCoordinator1<Event, Params, Result> {
  val params: Params

  fun start(
    params: Params,
    onFlowFinish: suspend (Result) -> Unit,
    onError: (Throwable) -> Result
  )

  fun finish(result: Result)

  fun finishAsync(result: Result): Job

  suspend fun handleEvent(event: Event)

  fun addHook(hook: FlowCoordinatorHook<Event, Params, Result>)

  fun removeHook(hook: FlowCoordinatorHook<Event, Params, Result>)
}

interface FlowCoordinatorHook<Event, Params, Result> {
  suspend fun preStart(params: Params) = Unit

  suspend fun postStart(params: Params) = Unit

  suspend fun preHandleEvent(event: Event) = Unit

  suspend fun postHandleEvent(event: Event) = Unit

  suspend fun preFinish(result: Result) = Unit

  suspend fun postFinish(result: Result) = Unit
}

package ru.kode.base.internship

import androidx.compose.runtime.Stable
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.kode.base.core.BaseViewModel
import ru.kode.base.core.di.SingleIn
import ru.kode.base.core.viewmodel.ViewModelProviders
import ru.kode.base.core.viewmodel.ViewModelStore
import ru.kode.base.internship.di.ForegroundScope
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Provider

@Stable
@SingleIn(ForegroundScope::class)
@ContributesBinding(ForegroundScope::class)
class AppViewModelStore @Inject constructor() : ViewModelStore {
  private val mutex = Mutex()

  private val viewModels = ConcurrentHashMap<String, BaseViewModel<*, *>>()
  private val providers = ConcurrentHashMap<String, Provider<BaseViewModel<*, *>>>()

  private fun <T : BaseViewModel<*, *>> create(key: String): T {
    val viewModel = providers[key]?.get() ?: error("ViewModel key $key is not registered")
    @Suppress("UNCHECKED_CAST")
    return viewModel as T
  }

  override suspend fun register(providers: ViewModelProviders) {
    mutex.withLock {
      this.providers.putAll(
        providers.mapKeys { (key, _) -> key.canonicalName ?: error("anonymous classes can not be ViewModels") }
      )
    }
  }

  override suspend fun unregister(providers: ViewModelProviders) {
    mutex.withLock {
      val providerKeys = providers.keys.map { it.canonicalName ?: error("anonymous classes can not be ViewModels") }
      viewModels.filterKeys { it in providerKeys }.forEach { (_, viewModel) -> viewModel.destroy() }
      this.providers.keys.removeAll(providerKeys)
    }
  }

  override fun get(modelClass: Class<out BaseViewModel<*, *>>): BaseViewModel<*, *> {
    return runBlocking {
      val className = modelClass.canonicalName ?: error("anonymous classes can not be ViewModels")
      mutex.withLock { viewModels.getOrPut(className) { create(className) } }
    }
  }
}

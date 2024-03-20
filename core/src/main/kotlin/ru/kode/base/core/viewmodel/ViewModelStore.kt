package ru.kode.base.core.viewmodel

import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import ru.kode.base.core.BaseViewModel

val LocalViewModelStore =
  staticCompositionLocalOf<ViewModelStore> {
    error("no LocalViewModelStore provided")
  }

@Stable
interface ViewModelStore {
  suspend fun register(providers: ViewModelProviders)

  suspend fun unregister(providers: ViewModelProviders)

  operator fun get(modelClass: Class<out BaseViewModel<*, *>>): BaseViewModel<*, *>
}

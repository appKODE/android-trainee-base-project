package ru.kode.base.core.viewmodel

import ru.kode.base.core.BaseViewModel
import javax.inject.Provider

typealias ViewModelProviders =
  Map<@JvmSuppressWildcards Class<out BaseViewModel<*, *>>, @JvmSuppressWildcards Provider<BaseViewModel<*, *>>>

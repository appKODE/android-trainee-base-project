package ru.kode.base.core.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import ru.kode.base.core.BaseViewModel

@Composable
inline fun <reified VM : BaseViewModel<*, *>> daggerViewModel(): VM {
  val store = LocalViewModelStore.current
  return remember(store) { store[VM::class.java] as VM }
}

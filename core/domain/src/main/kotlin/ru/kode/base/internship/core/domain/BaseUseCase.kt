package ru.kode.base.internship.core.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Suppress("UnnecessaryAbstractClass")
abstract class BaseUseCase<S : Any>(
  initialState: S,
) {
  private val mutableStateFlow = MutableStateFlow(initialState)

  protected val stateFlow: Flow<S>
    get() = mutableStateFlow

  protected suspend fun setState(reducer: S.() -> S) {
    return suspendCoroutine { cont -> mutableStateFlow.update { reducer(it).also { cont.resume(Unit) } } }
  }

  protected suspend fun <T> withState(action: suspend (state: S) -> T): T {
    val state = suspendCoroutine<S> { cont -> cont.resume(mutableStateFlow.value) }
    return action(state)
  }
}

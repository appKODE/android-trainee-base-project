package ru.kode.base.internship.auth.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.kode.base.core.di.AppScope
import ru.kode.base.core.di.SingleIn
import ru.kode.base.internship.core.domain.BaseUseCase
import ru.kode.base.internship.core.domain.entity.LceState
import javax.inject.Inject

@SingleIn(AppScope::class)
class AuthUseCase @Inject constructor(
  private val repository: AuthRepository,
) : BaseUseCase<AuthUseCase.State>(State()) {
  data class State(
    val userIdentificationState: LceState = LceState.None,
    val loginState: LceState = LceState.None,
  )

  @Suppress("TooGenericExceptionCaught")
  suspend fun identifyUser(phoneNumber: String) {
    setState { copy(userIdentificationState = LceState.Loading) }
    try {
      repository.identifyUser(phoneNumber)
      setState { copy(userIdentificationState = LceState.Content) }
    } catch (e: Exception) {
      setState { copy(userIdentificationState = LceState.Error(e.message)) }
    }
  }

  val userIdentificationState: Flow<LceState>
    get() = stateFlow.map { it.userIdentificationState }.distinctUntilChanged()

  @Suppress("TooGenericExceptionCaught")
  suspend fun login(password: String) {
    setState { copy(loginState = LceState.Loading) }
    try {
      repository.login(password)
      setState { copy(loginState = LceState.Content) }
    } catch (e: Exception) {
      setState { copy(loginState = LceState.Error(e.message)) }
    }
  }

  val loginState: Flow<LceState>
    get() = stateFlow.map { it.loginState }.distinctUntilChanged()
}

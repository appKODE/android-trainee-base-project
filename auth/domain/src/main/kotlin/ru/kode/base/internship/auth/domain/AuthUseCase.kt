package ru.kode.base.internship.auth.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.kode.base.internship.core.domain.BaseUseCase
import ru.kode.base.internship.core.domain.entity.LceState
import javax.inject.Inject

interface AuthUseCase {
  suspend fun identifyUser(phoneNumber: String)
  val userIdentificationState: Flow<LceState>

  suspend fun login(password: String)
  val loginState: Flow<LceState>
}

internal class AuthUseCaseImpl @Inject constructor(
  scope: CoroutineScope,
  private val repository: AuthRepository,
) : BaseUseCase<AuthUseCaseImpl.State>(scope, State()), AuthUseCase {
  data class State(
    val userIdentificationState: LceState = LceState.None,
    val loginState: LceState = LceState.None
  )

  override suspend fun identifyUser(phoneNumber: String) {
    setState { copy(userIdentificationState = LceState.Loading) }
    try {
      repository.identifyUser(phoneNumber)
      setState { copy(userIdentificationState = LceState.Content) }
    } catch (e: Exception) {
      setState { copy(userIdentificationState = LceState.Error(e.message)) }
    }
  }

  override val userIdentificationState: Flow<LceState>
    get() = stateFlow.map { it.userIdentificationState }.distinctUntilChanged()

  override suspend fun login(password: String) {
    setState { copy(loginState = LceState.Loading) }
    try {
      repository.login(password)
      setState { copy(loginState = LceState.Content) }
    } catch (e: Exception) {
      setState { copy(loginState = LceState.Error(e.message)) }
    }
  }

  override val loginState: Flow<LceState>
    get() = stateFlow.map { it.loginState }.distinctUntilChanged()
}

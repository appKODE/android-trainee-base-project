package ru.kode.base.internship.ui.password

import kotlinx.coroutines.flow.MutableSharedFlow
import ru.dimsuz.unicorn2.Machine
import ru.dimsuz.unicorn2.machine
import ru.kode.base.core.BaseViewModel
import ru.kode.base.internship.auth.domain.AuthUseCase
import ru.kode.base.internship.core.domain.entity.LceState
import ru.kode.base.internship.routing.FlowEvent
import ru.kode.base.internship.ui.password.entity.EnterPasswordErrorMessage
import javax.inject.Inject

// Binds in ru.kode.base.internship.routing.di.AppFlowUiModule
class EnterPasswordViewModel @Inject constructor(
  private val authUseCase: AuthUseCase,
  private val flowEvents: MutableSharedFlow<FlowEvent>,
) : BaseViewModel<EnterPasswordViewState, EnterPasswordIntents>() {
  override fun buildMachine(): Machine<EnterPasswordViewState> = machine {
    initial = EnterPasswordViewState() to null

    onEach(intent(EnterPasswordIntents::navigateOnBack)) {
      action { _, _, _ ->
        flowEvents.tryEmit(FlowEvent.EnterPasswordDismissed)
      }
    }

    onEach(intent(EnterPasswordIntents::togglePasswordVisibility)) {
      transitionTo { state, _ ->
        state.copy(
          isPasswordProtected = !state.isPasswordProtected
        )
      }
    }

    onEach(intent(EnterPasswordIntents::changeText)) {
      transitionTo { state, text ->
        state.copy(enteredPassword = text)
      }
    }

    onEach(intent(EnterPasswordIntents::login)) {
      action { _, newState, _ ->
        executeAsync {
          authUseCase.login(newState.enteredPassword)
        }
      }
    }

    onEach(intent(EnterPasswordIntents::dismissError)) {
      transitionTo { state, _ ->
        state.copy(errorMessage = null)
      }
    }

    onEach(authUseCase.loginState) {
      transitionTo { state, lceState ->
        state.copy(
          loginLceState = lceState,
          errorMessage = if (lceState is LceState.Error) EnterPasswordErrorMessage.LoginError else state.errorMessage
        )
      }

      action { _, newState, _ ->
        if (newState.loginLceState == LceState.Content) {
          flowEvents.tryEmit(FlowEvent.UserLoggedIn)
        }
      }
    }
  }
}

package ru.kode.base.internship.ui.identification

import kotlinx.coroutines.flow.MutableSharedFlow
import ru.dimsuz.unicorn2.Machine
import ru.dimsuz.unicorn2.machine
import ru.kode.base.core.BaseViewModel
import ru.kode.base.internship.auth.domain.AuthUseCase
import ru.kode.base.internship.core.domain.entity.LceState
import ru.kode.base.internship.routing.FlowEvent
import ru.kode.base.internship.ui.identification.entity.UserIdentificationErrorMessage
import javax.inject.Inject

// Binds in ru.kode.base.internship.routing.di.AppFlowUiModule
class UserIdentificationViewModel @Inject constructor(
  private val authUseCase: AuthUseCase,
  private val flowEvents: MutableSharedFlow<FlowEvent>,
) : BaseViewModel<UserIdentificationViewState, UserIdentificationIntents>() {
  override fun buildMachine(): Machine<UserIdentificationViewState> = machine {
    initial = UserIdentificationViewState() to null

    onEach(intent(UserIdentificationIntents::navigateOnBack)) {
      action { _, _, _ -> flowEvents.tryEmit(FlowEvent.UserIdentificationDismissed) }
    }

    onEach(intent(UserIdentificationIntents::changeText)) {
      transitionTo { state, text ->
        state.copy(enteredPhoneNumber = text.take(REQUIRED_PHONE_LENGTH))
      }
    }

    onEach(intent(UserIdentificationIntents::dismissError)) {
      transitionTo { state, _ ->
        val identificationLceState = if (state.identificationLceState is LceState.Error) {
          LceState.None
        } else {
          state.identificationLceState
        }
        state.copy(
          identificationLceState = identificationLceState,
          errorMessage = null,
        )
      }
    }

    onEach(intent(UserIdentificationIntents::login)) {
      transitionTo { state, _ ->
        val validationError = if (state.enteredPhoneNumber.length != REQUIRED_PHONE_LENGTH) {
          UserIdentificationErrorMessage.ValidationError.IncorrectPhoneNumber
        } else {
          state.errorMessage
        }
        state.copy(
          errorMessage = validationError,
        )
      }
      action { _, newState, _ ->
        if (newState.errorMessage == null) {
          executeAsync {
            authUseCase.identifyUser(newState.enteredPhoneNumber)
          }
        }
      }
    }

    onEach(authUseCase.userIdentificationState) {
      transitionTo { state, lceState ->
        state.copy(
          identificationLceState = lceState,
          errorMessage = if (lceState is LceState.Error) {
            UserIdentificationErrorMessage.IdentificationError
          } else {
            state.errorMessage
          }
        )
      }
      action { _, newState, _ ->
        if (newState.identificationLceState == LceState.Content) {
          flowEvents.tryEmit(FlowEvent.LoginRequested)
        }
      }
    }
  }
}

private const val REQUIRED_PHONE_LENGTH = 10

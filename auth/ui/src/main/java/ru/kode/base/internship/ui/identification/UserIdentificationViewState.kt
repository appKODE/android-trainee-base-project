package ru.kode.base.internship.ui.identification

import androidx.compose.runtime.Immutable
import ru.kode.base.internship.core.domain.entity.LceState
import ru.kode.base.internship.ui.identification.entity.UserIdentificationErrorMessage

@Immutable
data class UserIdentificationViewState(
  val identificationLceState: LceState = LceState.None,
  val enteredPhoneNumber: String = "",
  val errorMessage: UserIdentificationErrorMessage? = null,
)

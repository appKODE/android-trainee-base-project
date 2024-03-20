package ru.kode.base.internship.ui.password

import androidx.compose.runtime.Immutable
import ru.kode.base.internship.core.domain.entity.LceState
import ru.kode.base.internship.ui.password.entity.EnterPasswordErrorMessage

@Immutable
data class EnterPasswordViewState(
  val loginLceState: LceState = LceState.None,
  val isPasswordProtected: Boolean = true,
  val enteredPassword: String = "",
  val errorMessage: EnterPasswordErrorMessage? = null
)

package ru.kode.base.internship.ui.password

import ru.kode.base.core.BaseViewIntents

class EnterPasswordIntents : BaseViewIntents() {
  val navigateOnBack = intent(name = "navigateOnBack")
  val changeText = intent<String>(name = "changeText")
  val login = intent(name = "login")
  val togglePasswordVisibility = intent(name = "togglePasswordVisibility")
  val dismissError = intent(name = "dismissError")
}

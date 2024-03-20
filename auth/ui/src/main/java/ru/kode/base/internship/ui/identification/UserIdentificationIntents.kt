package ru.kode.base.internship.ui.identification

import ru.kode.base.core.BaseViewIntents

class UserIdentificationIntents : BaseViewIntents() {
  val navigateOnBack = intent(name = "navigateOnBack")
  val changeText = intent<String>(name = "changeText")
  val login = intent(name = "login")
  val dismissError = intent(name = "dismissError")
}

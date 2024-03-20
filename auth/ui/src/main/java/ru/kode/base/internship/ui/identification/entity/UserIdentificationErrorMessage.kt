package ru.kode.base.internship.ui.identification.entity

sealed class UserIdentificationErrorMessage {
  sealed class ValidationError : UserIdentificationErrorMessage() {
    data object IncorrectPhoneNumber : ValidationError()
  }

  data object IdentificationError : UserIdentificationErrorMessage()
}

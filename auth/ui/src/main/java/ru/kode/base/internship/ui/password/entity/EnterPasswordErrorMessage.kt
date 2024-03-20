package ru.kode.base.internship.ui.password.entity

sealed class EnterPasswordErrorMessage {
  data object LoginError : EnterPasswordErrorMessage()
}

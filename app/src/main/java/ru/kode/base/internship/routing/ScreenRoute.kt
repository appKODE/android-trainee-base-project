package ru.kode.base.internship.routing

sealed class ScreenRoute(val path: String) {
  data object EnterPassword : ScreenRoute(path = "enter-password")
  data object UserIdentification : ScreenRoute(path = "user-identification")
}

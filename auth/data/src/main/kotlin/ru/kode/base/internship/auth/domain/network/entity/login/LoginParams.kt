package ru.kode.base.internship.auth.domain.network.entity.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginParams(
  val guestToken: String,
  val password: String,
)

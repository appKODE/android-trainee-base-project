package ru.kode.base.internship.auth.domain.network.entity.login

import kotlinx.serialization.Serializable

@Serializable
internal data class LoginParams(
  val guestToken: String,
  val password: String,
)

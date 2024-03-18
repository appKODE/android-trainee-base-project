package ru.kode.base.internship.auth.domain.network.entity.login

import kotlinx.serialization.Serializable

@Serializable
internal data class LoginResponse(
  val accessToken: String,
  val refreshToken: String,
)
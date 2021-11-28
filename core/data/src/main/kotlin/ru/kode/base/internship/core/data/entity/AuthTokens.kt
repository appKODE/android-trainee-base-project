package ru.kode.base.internship.core.data.entity

data class AuthTokens(
  val accessToken: AccessToken,
  val refreshToken: RefreshToken,
)
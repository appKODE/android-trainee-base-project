package ru.kode.base.internship.auth.domain.network.entity.otp

import kotlinx.serialization.Serializable

@Serializable
data class ConfirmOtpResponse(
  val guestToken: String,
)

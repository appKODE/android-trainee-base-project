package ru.kode.base.internship.auth.domain.network.entity.otp

import kotlinx.serialization.Serializable

@Serializable
internal data class ConfirmOtpResponse(
  val guestToken: String,
)

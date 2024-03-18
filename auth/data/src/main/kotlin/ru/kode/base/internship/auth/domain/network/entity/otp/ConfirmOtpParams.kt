package ru.kode.base.internship.auth.domain.network.entity.otp

import kotlinx.serialization.Serializable

@Serializable
internal data class ConfirmOtpParams(
  val phone: String,
  val otpId: String,
  val otpCode: String,
)

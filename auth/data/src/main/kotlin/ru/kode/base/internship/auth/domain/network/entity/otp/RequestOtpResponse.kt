package ru.kode.base.internship.auth.domain.network.entity.otp

import kotlinx.serialization.Serializable

@Serializable
data class RequestOtpResponse(
  val otpId: String,
  val otpLen: Int,
  val otpCode: String,
)

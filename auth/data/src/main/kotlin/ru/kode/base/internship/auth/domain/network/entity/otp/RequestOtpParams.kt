package ru.kode.base.internship.auth.domain.network.entity.otp

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class RequestOtpParams(
  val phone: String,
)

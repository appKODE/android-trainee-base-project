package ru.kode.base.internship.auth.domain.network.entity.login

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class LoginParams(
  val password: String,
)

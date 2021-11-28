package ru.kode.base.internship.auth.domain

import ru.kode.base.internship.auth.domain.network.AuthApi
import ru.kode.base.internship.auth.domain.network.entity.login.LoginParams
import ru.kode.base.internship.auth.domain.network.entity.otp.ConfirmOtpParams
import ru.kode.base.internship.auth.domain.network.entity.otp.RequestOtpParams
import ru.kode.base.internship.core.data.entity.AccessToken
import ru.kode.base.internship.core.data.entity.AuthTokens
import ru.kode.base.internship.core.data.entity.GuestToken
import ru.kode.base.internship.core.data.entity.RefreshToken
import ru.kode.base.internship.core.data.storage.persistence.TokensPersistence
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
  private val api: AuthApi,
  private val authPersistence: TokensPersistence,
) : AuthRepository {
  override suspend fun identifyUser(phoneNumber: String) {
    val otp = api.requestOtp(params = RequestOtpParams(phoneNumber))
    val token = api.confirmOtp(
      ConfirmOtpParams(
        otpCode = otp.otpCode,
        otpId = otp.otpId,
        phone = phoneNumber,
      )
    )
    authPersistence.saveGuestToken(GuestToken(token.guestToken))
  }

  override suspend fun login(password: String) {
    val tokens = api.login(params = LoginParams(password))
    authPersistence.saveAuthTokens(
      tokens = AuthTokens(
        accessToken = AccessToken(tokens.accessToken),
        refreshToken = RefreshToken(tokens.refreshToken),
      )
    )
  }
}

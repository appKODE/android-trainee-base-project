package ru.kode.base.internship.core.data.storage.persistence

import ru.kode.base.internship.core.data.entity.AccessToken
import ru.kode.base.internship.core.data.entity.AuthTokens
import ru.kode.base.internship.core.data.entity.GuestToken

interface TokensPersistence {
  val guestToken: GuestToken?
  val accessToken: AccessToken?

  fun saveGuestToken(token: GuestToken)
  fun deleteGuestToken()

  fun saveAuthTokens(tokens: AuthTokens)
  fun deleteAuthTokens()
}

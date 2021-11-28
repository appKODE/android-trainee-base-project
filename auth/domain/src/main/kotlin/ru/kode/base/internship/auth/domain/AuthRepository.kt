package ru.kode.base.internship.auth.domain

interface AuthRepository {
  suspend fun identifyUser(phoneNumber: String)
  suspend fun login(password: String)
}

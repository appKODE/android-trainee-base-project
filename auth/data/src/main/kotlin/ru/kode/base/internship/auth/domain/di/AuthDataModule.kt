package ru.kode.base.internship.auth.domain.di

import ru.kode.base.internship.auth.domain.AuthRepository
import ru.kode.base.internship.auth.domain.AuthRepositoryImpl
import ru.kode.base.internship.auth.domain.network.AuthApi
import toothpick.config.Module

class AuthDataModule : Module() {
  init {
    bind(AuthRepository::class.java)
      .to(AuthRepositoryImpl::class.java)
      .singletonInScope()

    bind(AuthApi::class.java)
      .toProvider(AuthApiProvider::class.java)
      .providesSingletonInScope()
  }
}

package ru.kode.base.internship.auth.domain.di

import ru.kode.base.internship.auth.domain.AuthUseCase
import ru.kode.base.internship.auth.domain.AuthUseCaseImpl
import toothpick.config.Module

class AuthDomainModule : Module() {
  init {
    bind(AuthUseCase::class.java)
      .to(AuthUseCaseImpl::class.java)
      .singletonInScope()
  }
}
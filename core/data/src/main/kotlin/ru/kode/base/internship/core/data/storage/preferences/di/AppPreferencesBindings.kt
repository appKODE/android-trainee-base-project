package ru.kode.base.internship.core.data.storage.preferences.di

import ru.kode.base.core.util.ToothpickModuleBindings
import ru.kode.base.internship.core.data.storage.persistence.TokensPersistence
import ru.kode.base.internship.core.data.storage.persistence.TokensPersistenceImpl
import ru.kode.base.internship.core.data.storage.preferences.AndroidAppPreferencesImpl
import ru.kode.base.internship.core.data.storage.preferences.AppPreferences
import toothpick.config.Module

object AppPreferencesBindings : ToothpickModuleBindings {
  override fun bindInto(module: Module) {
    with(module) {
      bind(AppPreferences::class.java)
        .to(AndroidAppPreferencesImpl::class.java)
        .singletonInScope()

      bind(TokensPersistence::class.java)
        .to(TokensPersistenceImpl::class.java)
        .singletonInScope()
    }
  }
}

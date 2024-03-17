package ru.kode.base.internship.core.data.storage.di

import app.cash.sqldelight.db.SqlDriver
import ru.kode.base.core.util.ToothpickModuleBindings
import ru.kode.base.internship.core.data.storage.InMemoryDatabaseDriverProvider
import ru.kode.base.internship.core.data.storage.preferences.di.AppPreferencesBindings
import toothpick.config.Module

internal object StorageBindings : ToothpickModuleBindings {
  override fun bindInto(module: Module) {
    module.bind(SqlDriver::class.java)
      .toProvider(InMemoryDatabaseDriverProvider::class.java)
      .providesSingletonInScope()

    AppPreferencesBindings.bindInto(module)
  }
}
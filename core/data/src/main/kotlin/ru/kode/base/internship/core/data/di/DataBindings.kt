package ru.kode.base.internship.core.data.di

import ru.kode.base.core.util.ToothpickModuleBindings
import ru.kode.base.internship.core.data.network.di.NetworkBindings
import ru.kode.base.internship.core.data.storage.di.StorageBindings
import toothpick.config.Module

object DataBindings : ToothpickModuleBindings {
  override fun bindInto(module: Module) {
    NetworkBindings.bindInto(module)
    StorageBindings.bindInto(module)
  }
}
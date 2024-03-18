package ru.kode.base.core

import ru.kode.base.core.util.ToothpickModuleBindings
import toothpick.config.Module

@Suppress("FunctionName")
inline fun ToothpickScreenBindings(crossinline init: Module.() -> Unit): ToothpickModuleBindings {
  return object : ToothpickModuleBindings {
    override fun bindInto(module: Module) = init(module)
  }
}

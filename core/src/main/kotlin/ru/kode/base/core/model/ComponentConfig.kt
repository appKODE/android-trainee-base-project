package ru.kode.base.core.model

import com.bluelinelabs.conductor.Controller
import ru.kode.base.core.PresenterLifecycle
import ru.kode.base.core.util.ToothpickEmptyModuleBindings
import ru.kode.base.core.util.ToothpickModuleBindings

data class ComponentConfig(
  /**
   * A presenter class to bind into current screen module
   */
  val presenterClass: Class<out PresenterLifecycle<*, *>>,
  /**
   * A controller class to bind into current screen module
   */
  val controllerClass: Class<out Controller>,
  /**
   * A set of additional DI-bindings to associate with screen's module.
   * Will be merged into the same module in which presenter will be created.
   */
  val screenBindings: ToothpickModuleBindings = ToothpickEmptyModuleBindings,
)

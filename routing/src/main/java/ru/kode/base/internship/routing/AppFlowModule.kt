package ru.kode.base.internship.routing

import toothpick.config.Module

/**
 * Содержит "внутренние" зависимости данного flow, для использования только внутри этого gradle-модуля.
 */
internal class AppFlowModule : Module() {
  init {
    bind(AppFlow.Coordinator::class.java).to(AppFlowCoordinator::class.java).singletonInScope()
  }
}

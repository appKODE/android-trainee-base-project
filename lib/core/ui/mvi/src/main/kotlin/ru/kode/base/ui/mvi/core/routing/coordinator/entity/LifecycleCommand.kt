package ru.kode.base.ui.mvi.core.routing.coordinator.entity

data class LifecycleCommand<R>(
  val start: () -> Unit,
  val finish: (result: R) -> Unit,
)

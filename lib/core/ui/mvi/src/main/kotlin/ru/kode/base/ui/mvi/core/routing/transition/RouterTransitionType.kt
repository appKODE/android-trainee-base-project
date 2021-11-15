package ru.kode.base.ui.mvi.core.routing.transition

sealed class RouterTransitionType {
  object Horizontal : RouterTransitionType()
  object Vertical : RouterTransitionType()
  object Fade : RouterTransitionType()
  object None : RouterTransitionType()
}

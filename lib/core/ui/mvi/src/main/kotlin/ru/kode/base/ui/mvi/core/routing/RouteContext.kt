package ru.kode.base.ui.mvi.core.routing

import ru.kode.base.ui.mvi.core.routing.transition.RouterOverlayType
import ru.kode.base.ui.mvi.core.routing.transition.RouterTransitionType

data class RouteContext(
  val parentScopeName: String,
  val transitionType: RouterTransitionType = RouterTransitionType.Horizontal,
  val overlayType: RouterOverlayType = RouterOverlayType.None
)

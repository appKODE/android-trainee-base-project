package ru.kode.base.core.routing

import ru.kode.base.core.routing.transition.RouterOverlayType
import ru.kode.base.core.routing.transition.RouterTransitionType

data class RouteContext(
  val parentScopeName: String,
  val transitionType: RouterTransitionType = RouterTransitionType.Horizontal,
  val overlayType: RouterOverlayType = RouterOverlayType.None
)

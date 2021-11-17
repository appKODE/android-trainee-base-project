package ru.kode.base.internship.routing

import ru.kode.base.core.routing.Route
import ru.kode.base.core.routing.Router
import ru.kode.base.core.routing.coordinator.BaseFlowCoordinator
import ru.kode.base.core.routing.transition.RouterTransitionType
import toothpick.Scope
import javax.inject.Inject

internal class AppFlowCoordinator @Inject constructor(
  scope: Scope,
  private val router: Router,
) : BaseFlowCoordinator<AppFlow.Event, Unit>(scope), AppFlow.Coordinator {

  override fun openInitialRoute(beforePushClearUntil: Route?) {
    return router.push(
      AppRoute.Dummy(title = "Main Screen"),
      createContext(RouterTransitionType.None)
    )
  }

  override fun handleEvent(event: AppFlow.Event) = Unit
}

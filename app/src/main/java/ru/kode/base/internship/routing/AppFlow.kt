package ru.kode.base.internship.routing

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.kode.base.core.di.SingleIn
import ru.kode.base.core.routing.coordinator.BaseFlowCoordinator
import ru.kode.base.core.routing.graph.GraphFlow
import ru.kode.base.core.routing.utils.ScreenTransitionAnimation
import ru.kode.base.core.routing.utils.animatedComposable
import ru.kode.base.core.viewmodel.ViewModelProviders
import ru.kode.base.core.viewmodel.ViewModelStore
import ru.kode.base.internship.routing.di.AppFlowScope
import ru.kode.base.internship.ui.featureinprogress.FeatureInProgressScreen
import ru.kode.base.internship.ui.identification.UserIdentificationScreen
import ru.kode.base.internship.ui.password.EnterPasswordScreen
import javax.inject.Inject

object AppFlow : GraphFlow() {
  @SingleIn(AppFlowScope::class)
  class Coordinator @Inject constructor(
    private val navController: NavHostController,
    flowEvents: MutableSharedFlow<FlowEvent>,
    providers: ViewModelProviders,
    viewModelStore: ViewModelStore,
  ) : BaseFlowCoordinator<FlowEvent, Unit>(providers, flowEvents, viewModelStore) {
    override suspend fun onFlowStart() {
      navController.navigate(ScreenRoute.UserIdentification.route)
    }

    override suspend fun handleEvent(event: FlowEvent) {
      when (event) {
        FlowEvent.UserIdentificationDismissed -> finish(Unit)
        FlowEvent.EnterPasswordDismissed -> navController.popBackStack()
        FlowEvent.LoginRequested -> navController.navigate(ScreenRoute.EnterPassword.route)
        // TODO add navigation to next screen
        FlowEvent.UserLoggedIn -> navController.navigate(ScreenRoute.FeatureInProgress.route)
      }
    }
  }

  override val flowRoute: String = "app-flow"

  override val graph: NavGraphBuilder.() -> Unit = {
    animatedComposable(ScreenRoute.EnterPassword.route, ScreenTransitionAnimation.Horizontal) {
      EnterPasswordScreen()
    }
    animatedComposable(ScreenRoute.UserIdentification.route, ScreenTransitionAnimation.Horizontal) {
      UserIdentificationScreen()
    }
    animatedComposable(ScreenRoute.FeatureInProgress.route, ScreenTransitionAnimation.Horizontal) {
      FeatureInProgressScreen()
    }
  }

  private val ScreenRoute.route: String
    get() = buildRoute(path)
}

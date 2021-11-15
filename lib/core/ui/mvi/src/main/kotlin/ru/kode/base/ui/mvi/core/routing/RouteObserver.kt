package ru.kode.base.ui.mvi.core.routing

interface RouteObserver<R : Route> {
  fun pushStarted(route: R, previousRoute: R?)
  fun popStarted(route: R, previousRoute: R?)
  fun pushCompleted(route: R, previousRoute: R?)
  fun popCompleted(route: R, previousRoute: R?)
}

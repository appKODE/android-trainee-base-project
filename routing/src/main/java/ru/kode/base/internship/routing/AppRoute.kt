package ru.kode.base.internship.routing

import ru.kode.base.core.routing.Route

/**
 * Contains all application routes
 */
sealed class AppRoute(override val path: String) : Route {
  data class Dummy(val title: String) : AppRoute("/dummy/$title")
}

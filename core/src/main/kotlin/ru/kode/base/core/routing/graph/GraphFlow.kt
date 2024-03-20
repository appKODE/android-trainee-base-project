package ru.kode.base.core.routing.graph

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation

abstract class GraphFlow {
  abstract val flowRoute: String

  /**
   * Creates route using the given [builder].
   * If called from nested graph, its route will be added as a prefix.
   */
  protected inline fun route(builder: () -> String): String = buildRoute(builder())

  /** Replaces placeholder of the given [argument] with the given [value]. */
  protected fun String.setArgument(
    argument: NamedNavArgument,
    value: String
  ): String {
    return this.replace(argument.placeholder, value)
  }

  /** Adds query [argument] to string with the given [value] if it is not null. */
  protected fun String.withQueryParameter(
    argument: NamedNavArgument,
    value: Any?
  ): String {
    return withQueryParameter(argument.name, value)
  }

  protected fun buildRoute(childRoute: String): String = "$flowRoute/$childRoute"

  abstract val graph: NavGraphBuilder.() -> Unit

  fun NavGraphBuilder.flowGraph() {
    navigation(
      route = flowRoute,
      startDestination = "$flowRoute/start",
      builder = graph
    )
  }
}

/** Adds query argument with the given [name] associated with the given [value] to string if it is not `null`. */
internal fun String.withQueryParameter(
  name: String,
  value: Any?
): String {
  return when {
    value == null -> this
    '?' in this -> "$this&$name=$value"
    else -> "$this?$name=$value"
  }
}

package ru.kode.base.core.routing.graph

import android.os.Build
import android.os.Bundle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import java.io.Serializable

/** Returns placeholder that should be used in route in place of argument value. */
internal val NamedNavArgument.placeholder: String
  get() = "{$name}"

internal fun stringArgument(name: String) = navArgument(name) { type = NavType.StringType }

internal fun optionalStringArgument(name: String) =
  navArgument(name) {
    type = NavType.StringType
    nullable = true
  }

internal inline fun <reified E : Enum<*>> enumArgument(name: String) =
  navArgument(name) {
    type = NavType.EnumType(E::class.java)
  }

@Suppress("StringLiteralDuplication")
internal fun NavBackStackEntry.requireStringArgument(argument: NamedNavArgument): String {
  return requireArgument(value = getStringArgument(argument), name = argument.name)
}

internal fun NavBackStackEntry.getStringArgument(argument: NamedNavArgument): String? {
  return arguments?.getString(argument.name)
}

internal inline fun <reified E : Enum<E>> NavBackStackEntry.requireEnumArgument(argument: NamedNavArgument): E {
  return requireArgument(value = getEnumArgument<E>(argument), name = argument.name)
}

internal inline fun <reified E : Enum<E>> NavBackStackEntry.getEnumArgument(argument: NamedNavArgument): E? {
  return arguments?.getSerializableCompat(argument.name) as? E
}

internal fun longArgument(name: String) = navArgument(name) { type = NavType.LongType }

internal fun NavBackStackEntry.getLongArgument(argument: NamedNavArgument): Long? {
  return arguments?.getLong(argument.name)
}

internal fun NavBackStackEntry.requireLongArgument(argument: NamedNavArgument): Long {
  return requireArgument(value = getLongArgument(argument), name = argument.name)
}

internal fun intArgument(name: String) = navArgument(name) { type = NavType.IntType }

internal fun NavBackStackEntry.getIntArgument(argument: NamedNavArgument): Int? {
  return arguments?.getInt(argument.name)
}

internal fun NavBackStackEntry.requireIntArgument(argument: NamedNavArgument): Int {
  return requireArgument(value = getIntArgument(argument), name = argument.name)
}

internal fun booleanArgument(name: String) = navArgument(name) { type = NavType.BoolType }

internal fun NavBackStackEntry.getBooleanArgument(argument: NamedNavArgument): Boolean? {
  return arguments?.getBoolean(argument.name)
}

internal fun NavBackStackEntry.requireBooleanArgument(argument: NamedNavArgument): Boolean {
  return requireArgument(value = getBooleanArgument(argument), name = argument.name)
}

internal fun floatArgument(name: String) = navArgument(name) { type = NavType.FloatType }

internal fun NavBackStackEntry.getFloatArgument(argument: NamedNavArgument): Float? {
  return arguments?.getFloat(argument.name)
}

internal fun NavBackStackEntry.requireFloatArgument(argument: NamedNavArgument): Float {
  return requireArgument(value = getFloatArgument(argument), name = argument.name)
}

private fun <T : Any> NavBackStackEntry.requireArgument(
  value: T?,
  name: String
): T {
  return requireNotNull(value) { "Argument '$name' is required for destination $destination." }
}

internal inline fun <reified T : Serializable> Bundle.getSerializableCompat(key: String): T? =
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    getSerializable(key, T::class.java)
  } else {
    @Suppress("DEPRECATION") getSerializable(key) as? T
  }

internal inline fun <reified E> Bundle.getParcelableCompat(key: String): E? =
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    getParcelable(key, E::class.java)
  } else {
    @Suppress("DEPRECATION") get(key) as? E
  }

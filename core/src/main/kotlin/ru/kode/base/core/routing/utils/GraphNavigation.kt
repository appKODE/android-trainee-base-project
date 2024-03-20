package ru.kode.base.core.routing.utils

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.animatedComposable(
  route: String,
  animation: ScreenTransitionAnimation,
  content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
  composable(
    route,
    enterTransition = animation.enterTransition,
    popEnterTransition = animation.popEnterTransition,
    exitTransition = animation.exitTransition,
    popExitTransition = animation.popExitTransition,
    content = content
  )
}

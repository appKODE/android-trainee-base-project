package ru.kode.base.core.routing.utils

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry

sealed interface ScreenTransitionAnimation {
  val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
  val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?
  val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
  val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?

  data object Horizontal : ScreenTransitionAnimation {
    override val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
      slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left)
    }
    override val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
      slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left)
    }
    override val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
      slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right)
    }
    override val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
      slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right)
    }
  }

  data object Vertical : ScreenTransitionAnimation {
    override val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
      slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Down)
    }
    override val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
      slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Down)
    }
    override val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
      slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Up)
    }
    override val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
      slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Up)
    }
  }

  data object Fade : ScreenTransitionAnimation {
    override val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
      fadeIn()
    }
    override val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
      fadeOut()
    }
    override val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
      fadeIn()
    }
    override val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
      fadeOut()
    }
  }
}

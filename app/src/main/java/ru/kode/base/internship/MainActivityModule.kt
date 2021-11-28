package ru.kode.base.internship

import android.app.Activity
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import ru.kode.base.core.annotations.ForegroundActivityContext
import ru.kode.base.core.annotations.RouterScreenFactory
import ru.kode.base.core.routing.ConductorAppRouter
import ru.kode.base.core.routing.Router
import ru.kode.base.internship.routing.ScreenKeyFactory
import toothpick.config.Module

internal class MainActivityModule(activity: Activity, coroutineScope: CoroutineScope) : Module() {
  init {
    bind(Context::class.java).withName(ForegroundActivityContext::class.java).toInstance(activity)
    bind(Router::class.java).to(ConductorAppRouter::class.java).singletonInScope()
    bind(Function1::class.java).withName(RouterScreenFactory::class.java).toInstance(ScreenKeyFactory())
    bind(CoroutineScope::class.java).toInstance(coroutineScope)
  }
}

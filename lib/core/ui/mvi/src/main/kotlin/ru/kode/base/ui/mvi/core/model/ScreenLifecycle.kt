package ru.kode.base.ui.mvi.core.model

import android.content.Context
import android.view.View
import com.bluelinelabs.conductor.Controller
import leakcanary.AppWatcher
import ru.kode.base.ui.mvi.core.MviView
import ru.kode.base.ui.mvi.core.PresenterLifecycle
import toothpick.Toothpick
import toothpick.config.Module

internal class ScreenLifecycle(
  private val componentConfig: ComponentConfig,
  private val parentScopeName: String,
  private val screenScopeName: String,
) : Controller.LifecycleListener() {

  private var presenter: PresenterLifecycle<*, *>? = null

  override fun postContextAvailable(controller: Controller, context: Context) {
    super.postContextAvailable(controller, context)
    val screenScope = Toothpick.openScopes(parentScopeName, screenScopeName)

    val screenModule = ScreenModule(componentConfig)
    screenScope.installModules(screenModule)
  }

  override fun postCreateView(controller: Controller, view: View) {
    super.postCreateView(controller, view)
    if (presenter == null) {
      @Suppress("UNCHECKED_CAST") // the type is known by construction
      presenter = createPresenter()
    }
    @Suppress("UNCHECKED_CAST") // the type is known by construction
    presenter?.attachView(controller as MviView<Any, Nothing>)
  }

  override fun preDestroyView(controller: Controller, view: View) {
    super.preDestroyView(controller, view)
    presenter?.detachView()
  }

  override fun postDestroy(controller: Controller) {
    super.postDestroy(controller)
    presenter?.also {
      AppWatcher.objectWatcher.expectWeaklyReachable(it, "${it.javaClass.simpleName} presenter is destroyed")
    }
    presenter?.destroy()
    presenter = null
    Toothpick.closeScope(screenScopeName)
  }

  private fun createPresenter(): PresenterLifecycle<*, *> {
    if (!Toothpick.isScopeOpen(screenScopeName)) {
      error(
        "lifecycle error: attempt to create ${componentConfig.presenterClass.simpleName} before screen scope is open"
      )
    }
    return Toothpick.openScope(screenScopeName).getInstance(componentConfig.presenterClass)
  }

  private class ScreenModule(
    componentConfig: ComponentConfig,
  ) : Module() {
    init {
      bind(componentConfig.presenterClass)
      componentConfig.screenBindings.bindInto(this)
    }
  }
}

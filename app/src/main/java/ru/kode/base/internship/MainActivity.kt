package ru.kode.base.internship

import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import ru.kode.base.core.routing.ConductorAppRouter
import ru.kode.base.core.routing.Router
import ru.kode.base.core.util.instance
import ru.kode.base.domain.core.di.APP_SCOPE_NAME
import ru.kode.base.domain.core.di.foregroundScopeName
import ru.kode.base.internship.routing.AppFlow
import toothpick.Scope
import toothpick.Toothpick

class MainActivity : AppCompatActivity() {

  private lateinit var foregroundScope: Scope
  private lateinit var router: ConductorAppRouter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    foregroundScope = Toothpick.openScopes(APP_SCOPE_NAME, foregroundScopeName(this))
    foregroundScope.installModules(MainActivityModule(this))

    val rootLayout = createRootLayout()
    setContentView(rootLayout)
    configureEdgeToEdge()

    val appRouter = foregroundScope.instance<Router>() as ConductorAppRouter
    appRouter.attachTo(this, rootLayout)
    router = appRouter
    AppFlow.Builder()
      .withParams(Unit)
      .withFinishAction { finish() }
      .start(foregroundScope.name as String)
  }

  private fun createRootLayout(): ViewGroup {
    return FrameLayout(this)
  }

  override fun onBackPressed() {
    if (!router.handleBack()) {
      super.onBackPressed()
    }
  }

  private fun configureEdgeToEdge() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
  }

  override fun onDestroy() {
    super.onDestroy()
    Toothpick.closeScope(foregroundScope.name)
  }
}

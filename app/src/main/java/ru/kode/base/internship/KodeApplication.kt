package ru.kode.base.internship

import android.app.Application
import ru.kode.base.domain.core.di.APP_SCOPE_NAME
import toothpick.Toothpick

class KodeApplication : Application() {
  override fun onCreate() {
    super.onCreate()

    configureAppScope()
  }

  private fun configureAppScope() {
    val appScope = Toothpick.openScope(APP_SCOPE_NAME)
    appScope.installModules()
  }
}

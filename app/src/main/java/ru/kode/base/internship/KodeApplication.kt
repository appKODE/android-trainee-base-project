package ru.kode.base.internship

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import ru.kode.base.internship.di.AppComponent
import ru.kode.base.internship.di.AppComponentHolder
import ru.kode.base.internship.di.DaggerAppComponent
import timber.log.Timber

class KodeApplication : Application(), ImageLoaderFactory, AppComponentHolder {
  private lateinit var _appComponent: AppComponent
  override fun onCreate() {
    super.onCreate()

    _appComponent = buildAppComponent()
    configureLogging()
  }

  override fun newImageLoader(): ImageLoader {
    return _appComponent.imageLoader()
  }

  override val appComponent: AppComponent get() = _appComponent

  private fun buildAppComponent(): AppComponent {
    return DaggerAppComponent.builder()
      .applicationContext(this)
      .build()
  }

  private fun configureLogging() {
    if (!BuildConfig.RELEASE) Timber.plant(Timber.DebugTree())
  }
}

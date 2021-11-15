package ru.kode.base.intentship

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.os.Build.VERSION.RELEASE
import android.os.Looper
import android.os.StrictMode

class KodeApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    configureLogging()
    // useful to have here as visible app start checkpoint in DebugPanel/Logs.
    // and sometimes to see if app init finishes too late or takes too long
    Timber.d("APP INIT STARTED")
    configureRxAndroid()
    configureStrictMode()
    configureCrashlytics()
    configureAppScope()
    configureAuthSession()
    configurePushDelivery()

    Timber.d("APP INIT FINISHED")
  }

  private fun configureAuthSession() {
    appScope.instance<AuthSessionModel>()
      .start(Unit)
  }

  private fun configureRxAndroid() {
    // see https://medium.com/@sweers/rxandroids-new-async-api-4ab5b3ad3e93
    RxAndroidPlugins.setInitMainThreadSchedulerHandler {
      AndroidSchedulers.from(Looper.getMainLooper(), true)
    }
  }

  private fun configureCrashlytics() {
    FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(
      BuildConfig.EXTERNAL || BuildConfig.INTERNAL || BuildConfig.RELEASE
    )
  }

  private fun configureLogging() {
    // смотри app/build.gradle, раздел buildTypes для информации о том, в каких сборках включен logging и прочее
    if (!BuildConfig.RELEASE) {
      Timber.plant(Timber.DebugTree())
      Timber.plant(LogToFileTree(LogToFileTree.getDefaultDebugLogFileName(this)))
    }
  }

  private fun configureStrictMode() {
    if (!BuildConfig.DEV) {
      return
    }
    // Не используем penaltyDeath(), так как  слишком много ошибочных срабатываний, к тому же Firebase содержит баг,
    // из-за которого StrictMode-проверка срабатывает на старте приложения и "роняет" его.
    StrictMode.setThreadPolicy(
      StrictMode.ThreadPolicy.Builder()
        .detectAll()
        .penaltyLog()
        .penaltyDeathOnNetwork()
        .build()
    )
    StrictMode.setVmPolicy(
      StrictMode.VmPolicy.Builder()
        .detectAll()
        .penaltyLog()
        .build()
    )
  }

  private fun configureAppScope() {
    val appScope = Toothpick.openScope(APP_SCOPE_NAME)
    appScope.installModules(
      KodeApplicationModule(this),
      DomainConfigurationModule(),
      DomainConfigurationModule(),
      DomainAuthSessionModule(),
      AuthSessionDataModule(),
      DomainPushModule()
    )
  }

  @SuppressLint("CheckResult") // Disposable will be automatically disposed on destroy of application
  private fun configurePushDelivery() {
    val pushModel = appScope.instance<PushModel>().also { it.start(Unit) }
    val schedulers = appScope.instance<AppSchedulers>()
    // ВНИМАНИЕ: тут не стоит делать retry и логику авто-переподписывания, это приводит к тому что при ошибке
    // происходит бесконечное переподписывания, потому что receivedMessages() при подписке выдаёт последнее
    // полученное сообщение, если в процессе его обработки ошибка, то и получим бесконечность.
    // По факту, контракт такой: receivedMessages() вообще не должно испускать ошибки, если это происходит - это баг.
    // Оно должно их фильтровать как-то у себя.
    pushModel
      .messageUpdates
      .observeOn(schedulers.io)
      .subscribe(
        { message ->
          checkBackgroundThread()
          val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
          message.toAndroidNotification(this)?.let { notificationManager.notify(message.id, it) }
        },
        { Timber.e(it, "push messages stream must not throw errors, aborting push subscription to push messages") }
      )
  }
}

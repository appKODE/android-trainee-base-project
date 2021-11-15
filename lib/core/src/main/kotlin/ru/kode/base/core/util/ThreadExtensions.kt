package ru.kode.base.core.util

inline fun Thread.doOnUncaughtException(
  crossinline action: (Thread, Throwable) -> Unit
): Thread.UncaughtExceptionHandler? {
  val previousHandler = this.uncaughtExceptionHandler
  this.setUncaughtExceptionHandler { thread, throwable ->
    action(thread, throwable)
    previousHandler?.uncaughtException(thread, throwable)
  }
  return previousHandler
}

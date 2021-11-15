@file:Suppress("unused")

package ru.kode.base.core.util

import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import ru.kode.base.core.MOCK_NOTIFICATION_DEFAULT_DELAY_MS

/**
 * Swaps out an original completable with a new one which will complete after a small delay.
 * Can be used to aid quick testing without removing an original completable from chain.
 *
 * ```
 * myNetworkService.doSomeRequest() // will never get executed
 *   .hotSwapWithSuccess()
 *   .subscribe(...)
 * ```
 */
fun Completable.hotSwapWithSuccess(delayMillis: Long = MOCK_NOTIFICATION_DEFAULT_DELAY_MS): Completable {
  return Completable
    .fromAction {
      Thread.sleep(delayMillis)
    }
    .subscribeOn(Schedulers.io())
}

/**
 * Swaps out an original completable with a new one which will return an error after a small delay.
 * Can be used to aid quick testing without removing an original completable from chain.
 *
 * ```
 * myNetworkService.doSomeRequest() // will never get executed
 *   .hotSwapWithError(fakeError)
 *   .subscribe(...)
 * ```
 */
fun Completable.hotSwapWithError(
  error: Throwable,
  delayMillis: Long = MOCK_NOTIFICATION_DEFAULT_DELAY_MS
): Completable {
  return Completable
    .fromAction {
      Thread.sleep(delayMillis)
      throw error
    }
    .subscribeOn(Schedulers.io())
}

/**
 * Maps an error using provided [transform] function
 */
fun Completable.mapError(
  transform: (Throwable) -> Throwable,
): Completable {
  return this.onErrorResumeNext { e: Throwable -> Completable.error(transform(e)) }
}

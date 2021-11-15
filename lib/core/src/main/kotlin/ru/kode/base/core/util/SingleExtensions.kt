@file:Suppress("unused")

package ru.kode.base.core.util

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.kode.base.core.MOCK_NOTIFICATION_DEFAULT_DELAY_MS

/**
 * Swaps out an original source with a new one which will complete after a small delay.
 * Can be used to aid quick testing without removing an original source from chain.
 *
 * ```
 * myNetworkService.doSomeRequest() // will never get executed, will be substituted with passed mock result
 *   .hotSwapWithSuccess(3)
 *   .subscribe(...)
 * ```
 */
fun <T> Single<T>.hotSwapWithSuccess(result: T, delayMillis: Long = MOCK_NOTIFICATION_DEFAULT_DELAY_MS): Single<T> {
  return Single
    .fromCallable {
      Thread.sleep(delayMillis)
      result
    }
    .subscribeOn(Schedulers.io())
}

/**
 * Swaps out an original source with a new one which will return an error after a small delay.
 * Can be used to aid quick testing without removing an original source from chain.
 *
 * ```
 * myNetworkService.doSomeRequest() // will never get executed, error will be triggered instead
 *   .hotSwapWithError(fakeError)
 *   .subscribe(...)
 * ```
 */
fun <T> Single<T>.hotSwapWithError(
  error: Throwable,
  delayMillis: Long = MOCK_NOTIFICATION_DEFAULT_DELAY_MS
): Single<T> {
  return Single
    .fromCallable<T> {
      Thread.sleep(delayMillis)
      throw error
    }
    .subscribeOn(Schedulers.io())
}

/**
 * Maps an error using provided [transform] function
 */
fun <T> Single<T>.mapError(
  transform: (Throwable) -> Throwable
): Single<T> {
  return this.onErrorResumeNext { Single.error(transform(it)) }
}

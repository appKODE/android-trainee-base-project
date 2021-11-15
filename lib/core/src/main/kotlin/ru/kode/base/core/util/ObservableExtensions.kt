@file:Suppress("unused")

package ru.kode.base.core.util

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import ru.kode.base.core.MOCK_NOTIFICATION_DEFAULT_DELAY_MS

fun <T1 : Any, T2 : Any> Observable<T1>.mapDistinctChanges(transform: (T1) -> T2): Observable<T2> {
  return this.map(transform).distinctUntilChanged()
}

fun <T1 : Any, T2> Observable<T1>.mapDistinctNotNullChanges(
  error: String = "expected non null field",
  transform: (T1) -> T2?,
): Observable<T2> {
  return this.filter { transform(it) != null }.map { transform(it) ?: error(error) }.distinctUntilChanged()
}

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
fun <T> Observable<T>.hotSwapWithSuccess(
  result: T,
  delayMillis: Long = MOCK_NOTIFICATION_DEFAULT_DELAY_MS,
): Observable<T> {
  return Observable
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
fun <T> Observable<T>.hotSwapWithError(
  error: Throwable,
  delayMillis: Long = MOCK_NOTIFICATION_DEFAULT_DELAY_MS,
): Observable<T> {
  return Observable
    .fromCallable<T> {
      Thread.sleep(delayMillis)
      throw error
    }
    .subscribeOn(Schedulers.io())
}

/**
 * @see
 * http://rxmarbles.com/#pairwise
 */
fun <T> Observable<T>.pairwise(): Observable<Pair<T, T>> {
  return publish { source ->
    Observable.zip(
      source,
      source.skip(1),
      BiFunction { v1: T, v2: T -> v1 to v2 }
    )
  }
}

/**
 * Maps an error using provided [transform] function
 */
fun <T> Observable<T>.mapError(
  transform: (Throwable) -> Throwable,
): Observable<T> {
  return this.onErrorResumeNext { e: Throwable -> Observable.error(transform(e)) }
}

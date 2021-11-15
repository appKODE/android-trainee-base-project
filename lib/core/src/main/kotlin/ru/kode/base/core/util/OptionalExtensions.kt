package ru.kode.base.core.util

import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.Some
import com.gojuno.koptional.toOptional
import io.reactivex.Observable

inline fun <T1 : Any, T2 : Any> Observable<Optional<T1>>.switchMapSome(
  crossinline mapper: (T1) -> Observable<T2>,
): Observable<Optional<T2>> {
  return this.switchMap { optional ->
    when (optional) {
      is Some -> mapper(optional.value).map { it.toOptional() }
      is None -> Observable.just(None)
    }
  }
}

inline fun <T1 : Any, T2 : Any> Observable<Optional<T1>>.flatMapSome(
  crossinline mapper: (T1) -> Observable<T2>,
): Observable<Optional<T2>> {
  return this.flatMap { optional ->
    when (optional) {
      is Some -> mapper(optional.value).map { it.toOptional() }
      is None -> Observable.just(None)
    }
  }
}

inline fun <T1 : Any, T2 : Any> Observable<Optional<T1>>.mapSome(
  crossinline mapper: (T1) -> T2,
): Observable<Optional<T2>> {
  return this.map { optional -> optional.map(mapper) }
}

inline fun <T1 : Any, T2 : Any> Observable<Optional<T1>>.concatMapSome(
  crossinline mapper: (T1) -> Observable<T2>,
): Observable<Optional<T2>> {
  return this.concatMap { optional ->
    when (optional) {
      is Some -> mapper(optional.value).map { it.toOptional() }
      is None -> Observable.just(None)
    }
  }
}

inline fun <T : Any, R : Any> Optional<T>.flatMap(transform: (T) -> Optional<R>): Optional<R> {
  return when (this) {
    is Some -> transform(this.value)
    is None -> None
  }
}

inline fun <T : Any, R : Any> Optional<T>.map(transform: (T) -> R): Optional<R> {
  return when (this) {
    is Some -> Some(transform(this.value))
    is None -> None
  }
}

fun <T : Any> Optional<T>.orElse(result: () -> Optional<T>): Optional<T> {
  return when (this) {
    is Some -> this
    is None -> result()
  }
}

fun <T1 : Any, T2 : Any, R : Any> bind(
  optional1: Optional<T1>,
  optional2: Optional<T2>,
  transform: (T1, T2) -> R,
): Optional<R> {
  return optional1.flatMap { t1: T1 ->
    optional2.map { t2: T2 ->
      transform.invoke(t1, t2)
    }
  }
}

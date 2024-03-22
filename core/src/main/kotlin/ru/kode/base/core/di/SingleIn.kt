package ru.kode.base.core.di

import javax.inject.Scope
import kotlin.reflect.KClass

/**
 * Indicates that this provided type (via [Provides], [Binds], [Inject], etc)
 * will only have a single instances within the target [value] scope.
 *
 * Note that the [value] does not actually need to be a [Scope]-annotated
 * annotation class. It is _solely_ a key.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class SingleIn(val value: KClass<*>)

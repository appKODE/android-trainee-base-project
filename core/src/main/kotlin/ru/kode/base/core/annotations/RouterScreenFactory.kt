package ru.kode.base.core.annotations

import javax.inject.Qualifier

/**
 * A qualifier to specify that injected function represents a
 * router screen factory function
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RouterScreenFactory

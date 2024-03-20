package ru.kode.base.core.viewmodel

import dagger.MapKey
import ru.kode.base.core.BaseViewModel
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out BaseViewModel<*, *>>)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelFactoryKey(val value: KClass<out BaseViewModel<*, *>>)

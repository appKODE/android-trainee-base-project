package ru.kode.base.internship.core.data.storage.preferences.di

import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
import dagger.Module
import ru.kode.base.core.di.AppScope
import ru.kode.base.core.di.SingleIn
import ru.kode.base.internship.core.data.storage.persistence.TokensPersistence
import ru.kode.base.internship.core.data.storage.persistence.TokensPersistenceImpl
import ru.kode.base.internship.core.data.storage.preferences.AndroidAppPreferencesImpl
import ru.kode.base.internship.core.data.storage.preferences.AppPreferences

@Module
@ContributesTo(AppScope::class)
interface AppPreferencesModule {
  @Binds
  @SingleIn(AppScope::class)
  fun provideAppPreferences(preferences: AndroidAppPreferencesImpl): AppPreferences

  @Binds
  @SingleIn(AppScope::class)
  fun provideTokensPersistence(persistence: TokensPersistenceImpl): TokensPersistence
}

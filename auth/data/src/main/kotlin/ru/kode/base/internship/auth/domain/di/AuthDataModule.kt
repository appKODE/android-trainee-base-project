package ru.kode.base.internship.auth.domain.di

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.kode.base.core.di.AppScope
import ru.kode.base.core.di.SingleIn
import ru.kode.base.internship.auth.domain.network.AuthApi

@Module
@ContributesTo(AppScope::class)
object AuthDataModule {
  @Provides
  @SingleIn(AppScope::class)
  fun provideAuthApi(retrofit: Retrofit): AuthApi {
    return retrofit.create(AuthApi::class.java)
  }
}

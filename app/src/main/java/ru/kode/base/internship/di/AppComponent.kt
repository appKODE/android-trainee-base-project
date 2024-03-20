package ru.kode.base.internship.di

import android.content.Context
import coil.ImageLoader
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import ru.kode.base.core.annotations.ApplicationContext
import ru.kode.base.core.di.AppScope
import ru.kode.base.core.di.SingleIn

@SingleIn(AppScope::class)
@MergeComponent(AppScope::class)
interface AppComponent {
  fun foregroundComponentFactory(): ForegroundComponent.Factory
  fun imageLoader(): ImageLoader

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun applicationContext(
      @ApplicationContext context: Context,
    ): Builder

    fun build(): AppComponent
  }
}

@ContributesTo(AppScope::class)
@Module
object AppModule {
  @Provides
  @SingleIn(AppScope::class)
  fun provideCoilImageLoader(
    @ApplicationContext context: Context,
    client: OkHttpClient,
  ): ImageLoader {
    return ImageLoader.Builder(context)
      .okHttpClient(client)
      .build()
  }
}

interface AppComponentHolder {
  val appComponent: AppComponent
}

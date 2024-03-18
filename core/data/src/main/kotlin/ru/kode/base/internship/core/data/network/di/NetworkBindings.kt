package ru.kode.base.internship.core.data.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.kode.base.core.util.ToothpickModuleBindings
import ru.kode.base.internship.core.BuildConfig
import ru.kode.base.internship.core.data.network.interceptor.AttachAccessTokenInterceptor
import ru.kode.base.internship.core.data.storage.persistence.TokensPersistence
import timber.log.Timber
import toothpick.config.Module
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

internal object NetworkBindings : ToothpickModuleBindings {
  override fun bindInto(module: Module) {

    module.bind(Json::class.java)
      .toInstance(createJson())

    module.bind(OkHttpClient::class.java)
      .toProvider(AuthorizedOkHttpClientProvider::class.java)
      .providesSingletonInScope()

    module.bind(Retrofit::class.java)
      .toProvider(AuthorizedRetrofitProvider::class.java)
      .providesSingletonInScope()
  }
}

private fun createJson(): Json {
  return Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
    explicitNulls = false
  }
}

internal class AuthorizedOkHttpClientProvider @Inject constructor(
  private val tokensPersistence: TokensPersistence,
) : Provider<OkHttpClient> {
  override fun get(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor { message -> Timber.tag("OkHttp"); Timber.d(message) }
    loggingInterceptor.level = HTTP_LOG_LEVEL
    return OkHttpClient.Builder()
      .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
      .readTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
      .writeTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
      .addNetworkInterceptor(AttachAccessTokenInterceptor(tokensPersistence))
      .addNetworkInterceptor(loggingInterceptor)
      .build()
  }
}

internal class AuthorizedRetrofitProvider @Inject constructor(
  private val json: Json,
  private val httpClient: OkHttpClient,
) : Provider<Retrofit> {
  override fun get(): Retrofit {
    return Retrofit.Builder()
      .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
      .client(httpClient)
      .baseUrl("$BASE_URL/").build()
  }
}

internal const val BASE_URL = "https://stoplight.io/mocks/kode-education/kode-bank/27774162"
internal const val HTTP_CONNECT_TIMEOUT = 60_000L
internal val HTTP_LOG_LEVEL =
  if (BuildConfig.RELEASE) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.BODY
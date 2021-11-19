package ru.kode.base.internship.core.data.network.di

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.kode.base.core.util.ToothpickModuleBindings
import ru.kode.base.internship.core.BuildConfig
import ru.kode.base.internship.core.data.network.adapter.LocalDateJsonAdapter
import ru.kode.base.internship.core.data.network.adapter.LocalDateTimeJsonAdapter
import ru.kode.base.internship.core.data.network.adapter.LocalTimeJsonAdapter
import timber.log.Timber
import toothpick.config.Module
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

internal object NetworkBindings : ToothpickModuleBindings {
  override fun bindInto(module: Module) {
    module.bind(Moshi::class.java)
      .toInstance(createMoshi())

    module.bind(OkHttpClient::class.java)
      .toProvider(AuthorizedOkHttpClientProvider::class.java)
      .providesSingletonInScope()

    module.bind(Retrofit::class.java)
      .toProvider(AuthorizedRetrofitProvider::class.java)
      .providesSingletonInScope()
  }
}

private fun createMoshi(): Moshi {
  return Moshi.Builder()
    .add(LocalDateJsonAdapter(LOCAL_DATE_SERVER_FORMAT))
    .add(LocalDateTimeJsonAdapter(LOCAL_DATE_TIME_SERVER_FORMAT))
    .add(LocalTimeJsonAdapter(LOCAL_TIME_SERVER_FORMAT))
    .build()
}

internal class AuthorizedOkHttpClientProvider @Inject constructor() : Provider<OkHttpClient> {
  override fun get(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor { message -> Timber.tag("OkHttp"); Timber.d(message) }
    loggingInterceptor.level = HTTP_LOG_LEVEL
    return OkHttpClient.Builder()
      .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
      .readTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
      .writeTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
      .addNetworkInterceptor(loggingInterceptor)
      .build()
  }
}

internal class AuthorizedRetrofitProvider @Inject constructor(
  private val moshi: Moshi,
  private val httpClient: OkHttpClient,
) : Provider<Retrofit> {
  override fun get(): Retrofit {
    // TODO add call adapter
    // https://git.appkode.ru/dom-invest/dom-invest-android/-/tree/dev/core/data/src/main/kotlin/ru/kode/dominvest/mobile/core/data/network/adapter/ApiErrorCallAdapterFactory.kt
//    val resultCallAdapterFactory = ResultCallAdapterFactory(
//      errorResponseBodyConverter = ErrorResponseBodyConverter(moshi),
//      nonApiErrorConverter = errorConverter
//    )
    return Retrofit.Builder()
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      //.addCallAdapterFactory(resultCallAdapterFactory)
      .client(httpClient)
      .baseUrl("$BASE_URL/").build()
  }
}

internal const val BASE_URL = "" // TODO add api url
internal const val HTTP_CONNECT_TIMEOUT = 60_000L
internal val HTTP_LOG_LEVEL =
  if (BuildConfig.RELEASE) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.BODY
internal val LOCAL_DATE_SERVER_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE
internal val LOCAL_DATE_TIME_SERVER_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME
internal val LOCAL_TIME_SERVER_FORMAT = DateTimeFormatter.ISO_LOCAL_TIME
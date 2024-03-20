package ru.kode.base.internship.core.data.storage.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import app.cash.sqldelight.logs.LogSqliteDriver
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import ru.kode.base.core.annotations.ApplicationContext
import ru.kode.base.core.di.AppScope
import ru.kode.base.core.di.SingleIn
import ru.kode.base.internship.core.data.InMemoryDB
import timber.log.Timber

@Module
@ContributesTo(AppScope::class)
object StorageModule {
  @Provides
  @SingleIn(AppScope::class)
  fun provideSqlDriver(
    @ApplicationContext
    context: Context
  ): SqlDriver {
    return AndroidSqliteDriver(
      InMemoryDB.Schema,
      context,
      // use in-memory database
      name = null
    ).let { driver ->
      if (ENABLE_LOGGING) {
        LogSqliteDriver(driver) { log ->
          Timber.e(log)
        }
      } else {
        driver
      }
    }
  }
}

private const val ENABLE_LOGGING = false

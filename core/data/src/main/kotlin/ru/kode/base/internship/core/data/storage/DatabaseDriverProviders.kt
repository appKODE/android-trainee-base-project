@file:Suppress("MatchingDeclarationName") // intentionally contains several provider classes
package ru.kode.base.internship.core.data.storage

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import app.cash.sqldelight.logs.LogSqliteDriver
import ru.kode.base.core.annotations.ApplicationContext
import ru.kode.base.internship.core.data.InMemoryDB
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

internal class InMemoryDatabaseDriverProvider @Inject constructor(
  @ApplicationContext
  private val context: Context,
) : Provider<SqlDriver> {

  override fun get(): SqlDriver {
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
      } else driver
    }
  }
}

private const val ENABLE_LOGGING = false

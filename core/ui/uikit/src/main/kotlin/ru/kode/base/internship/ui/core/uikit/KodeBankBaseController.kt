package ru.kode.base.internship.ui.core.uikit

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import coil.ImageLoader
import coil.compose.LocalImageLoader
import ru.kode.base.core.BaseComposeController
import ru.kode.base.core.BaseViewIntents
import ru.kode.base.core.util.foregroundScope
import ru.kode.base.core.util.instance
import ru.kode.base.internship.ui.core.uikit.theme.AppTheme

abstract class KodeBankBaseController<VS : Any, VI : BaseViewIntents> : BaseComposeController<VS, VI>() {
  private lateinit var imageLoader: ImageLoader

  override fun onContextAvailable(context: Context) {
    super.onContextAvailable(context)
    imageLoader = foregroundScope.instance()
  }

  @Composable
  final override fun Content(state: VS) {
    AppTheme {
      CompositionLocalProvider(LocalImageLoader provides imageLoader) {
        ScreenContent(state)
      }
    }
  }

  @Composable
  abstract fun ScreenContent(state: VS)
}
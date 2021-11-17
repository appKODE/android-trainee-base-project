package ru.kode.base.internship.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.systemBarsPadding
import ru.kode.base.core.BaseComposeController
import ru.kode.base.core.BaseViewIntents
import ru.kode.base.ui.mvi.core.util.key
import ru.kode.base.ui.mvi.core.util.requireActivity

internal class DummyScreenController :
  BaseComposeController<Unit, BaseViewIntents>() {

  override fun createConfig(): Config<BaseViewIntents> {
    return object : Config<BaseViewIntents> {
      override val intentsConstructor = ::BaseViewIntents
    }
  }

  @Composable
  override fun Content(state: Unit) {
    Surface {
      Column(
        modifier = Modifier.systemBarsPadding()
      ) {
        TopAppBar(
          title = { },
          navigationIcon = {
            Icon(
              modifier = Modifier.clickable { requireActivity.onBackPressed() },
              imageVector = Icons.Default.ArrowBack,
              contentDescription = "navigate back icon",
            )
          },
        )
        Spacer(modifier = Modifier.weight(weight = 1f))
        Text(
          modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(horizontal = 24.dp),
          text = key<DummyKey>().title,
        )
        Spacer(modifier = Modifier.weight(weight = 1f))
      }
    }
  }
}

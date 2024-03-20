package ru.kode.base.internship.ui.core.uikit.screen

import androidx.compose.runtime.Composable
import ru.kode.base.core.BaseViewIntents
import ru.kode.base.core.BaseViewModel
import ru.kode.base.core.MviScreen

@Composable
fun <VS : Any, VI : BaseViewIntents, VM : BaseViewModel<VS, VI>> AppScreen(
  viewModel: VM,
  intents: VI,
  content: @Composable (VS, VI) -> Unit
) {
  MviScreen(
    viewModel = viewModel,
    intents = intents,
    content = content
  )
}

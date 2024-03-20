package ru.kode.base.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun <VS : Any, VI : BaseViewIntents, VM : BaseViewModel<VS, VI>> MviScreen(
  viewModel: VM,
  intents: VI,
  content: @Composable (VS, VI) -> Unit,
) {
  LifecycleEffect(viewModel = viewModel, intents = intents)
  val scope = rememberCoroutineScope()
  val state by viewModel.viewStateFlow.collectAsState(scope.coroutineContext)
  content(state, intents)
}

@Composable
inline fun <reified VI : BaseViewIntents> rememberViewIntents(): VI {
  return remember { VI::class.java.newInstance() }
}

@Composable
private fun <VI : BaseViewIntents, VM : BaseViewModel<*, VI>> LifecycleEffect(
  viewModel: VM,
  intents: VI,
) {
  DisposableEffect(Unit) {
    viewModel.attach(intents)
    onDispose { viewModel.detach() }
  }
}

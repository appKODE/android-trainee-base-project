package ru.kode.base.internship.ui.core.uikit.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

@Composable
fun HideKeyboardOnDisposeEffect() {
  val keyboardController = LocalSoftwareKeyboardController.current
  DisposableEffect(Unit) { onDispose { keyboardController?.hide() } }
}

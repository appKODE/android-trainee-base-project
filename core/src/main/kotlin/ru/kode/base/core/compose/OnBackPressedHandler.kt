package ru.kode.base.core.compose

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
fun OnBackPressedHandler(
  onBack: () -> Unit,
  enabled: Boolean = true,
) {
  BackHandler(enabled, onBack)
}

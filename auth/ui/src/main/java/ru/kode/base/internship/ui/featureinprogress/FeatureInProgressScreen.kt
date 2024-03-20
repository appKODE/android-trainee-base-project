package ru.kode.base.internship.ui.featureinprogress

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.kode.base.internship.auth.ui.R
import ru.kode.base.internship.ui.core.uikit.theme.AppTheme

@Composable
fun FeatureInProgressScreen() {
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(
      painter = painterResource(id = R.drawable.img_kode_logo_90x40),
      contentDescription = null
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
      text = stringResource(id = R.string.feature_in_development),
      color = AppTheme.colors.textPrimary,
      style = AppTheme.typography.subtitle
    )
  }
}

@Preview(showBackground = true)
@Composable
private fun FeatureInProgressScreenPreview() {
  AppTheme {
    FeatureInProgressScreen()
  }
}

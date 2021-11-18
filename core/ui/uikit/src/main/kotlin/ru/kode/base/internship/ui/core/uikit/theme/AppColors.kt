package ru.kode.base.internship.ui.core.uikit.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Stable
class AppColors(
  backgroundPrimary: Color,
  backgroundSecondary: Color,
  contentPrimary: Color,
  contentSecondary: Color,
  contentTertiary: Color,
  contentAccentPrimary: Color,
  contentAccentSecondary: Color,
  contentAccentTertiary: Color,
  textPrimary: Color,
  textSecondary: Color,
  textTertiary: Color,
  indicatorContentError: Color,
  indicatorContentDone: Color,
  indicatorContentSuccess: Color,
  primaryButton: Color,
  bottomMenuBackground: Color,
  scrimer: Color,
  calendarPeriod: Color,
  buttonSecondary: Color,
  textButton: Color,
  black: Color,
  isDark: Boolean,
) {
  var backgroundPrimary by mutableStateOf(backgroundPrimary)
    private set
  var backgroundSecondary by mutableStateOf(backgroundSecondary)
    private set
  var contentPrimary by mutableStateOf(contentPrimary)
    private set
  var contentSecondary by mutableStateOf(contentSecondary)
    private set
  var contentTertiary by mutableStateOf(contentTertiary)
    private set
  var contentAccentPrimary by mutableStateOf(contentAccentPrimary)
    private set
  var contentAccentSecondary by mutableStateOf(contentAccentSecondary)
    private set
  var contentAccentTertiary by mutableStateOf(contentAccentTertiary)
    private set
  var textPrimary by mutableStateOf(textPrimary)
    private set
  var textSecondary by mutableStateOf(textSecondary)
    private set
  var textTertiary by mutableStateOf(textTertiary)
    private set
  var indicatorContentError by mutableStateOf(indicatorContentError)
    private set
  var indicatorContentDone by mutableStateOf(indicatorContentDone)
    private set
  var indicatorContentSuccess by mutableStateOf(indicatorContentSuccess)
    private set
  var primaryButton by mutableStateOf(primaryButton)
    private set
  var bottomMenuBackground by mutableStateOf(bottomMenuBackground)
    private set
  var scrimer by mutableStateOf(scrimer)
    private set
  var calendarPeriod by mutableStateOf(calendarPeriod)
    private set
  var buttonSecondary by mutableStateOf(buttonSecondary)
    private set
  var textButton by mutableStateOf(textButton)
    private set
  var black by mutableStateOf(black)
    private set
  var isDark by mutableStateOf(isDark)
    private set

  fun update(other: AppColors) {
    backgroundPrimary = other.backgroundPrimary
    backgroundSecondary = other.backgroundSecondary
    contentPrimary = other.contentPrimary
    contentSecondary = other.contentSecondary
    contentTertiary = other.contentTertiary
    contentAccentPrimary = other.contentAccentPrimary
    contentAccentSecondary = other.contentAccentSecondary
    contentAccentTertiary = other.contentAccentTertiary
    textPrimary = other.textPrimary
    textSecondary = other.backgroundPrimary
    textTertiary = other.backgroundPrimary
    indicatorContentError = other.backgroundPrimary
    indicatorContentDone = other.backgroundPrimary
    indicatorContentSuccess = other.backgroundPrimary
    primaryButton = other.backgroundPrimary
    bottomMenuBackground = other.backgroundPrimary
    scrimer = other.backgroundPrimary
    calendarPeriod = other.backgroundPrimary
    buttonSecondary = other.backgroundPrimary
    textButton = other.backgroundPrimary
    black = other.backgroundPrimary
    isDark = other.isDark
  }
}

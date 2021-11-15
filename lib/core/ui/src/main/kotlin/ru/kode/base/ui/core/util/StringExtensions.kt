package ru.kode.base.ui.core.util

import kotlin.text.Typography.ellipsis

fun String.ellipsizeEnd(maxLength: Int): String {
  return if (length > maxLength) this.take(maxLength - 1) + ellipsis else this
}

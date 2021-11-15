package ru.kode.base.ui.core.util

import android.os.Handler
import android.os.Looper
import android.view.View

fun View.setDebouncingOnClickListener(listener: (View) -> Unit) {
  setOnClickListener(DebouncingOnClickListener(listener))
}

/**
 * A click listener that debounces multiple clicks posted in the
 * same frame. A click on one button disables all buttons for that frame.
 *
 * Based on ButterKnife's code:
 *
 * https://github.com/JakeWharton/butterknife/blob/master/butterknife-runtime/src/main/java/butterknife/internal/DebouncingOnClickListener.java
 */
private class DebouncingOnClickListener(val targetListener: (View) -> Unit) : View.OnClickListener {
  companion object {
    private val ENABLE_AGAIN = Runnable { enabled = true }
    private val MAIN = Handler(Looper.getMainLooper())
    private var enabled = true
  }

  override fun onClick(view: View) {
    if (enabled) {
      enabled = false

      // Post to the main looper directly rather than going through the view.
      // Ensure that ENABLE_AGAIN will be executed, avoid static field {@link #enabled}
      // staying in false state.
      MAIN.postDelayed(ENABLE_AGAIN, DEBOUNCE_CLICKS_TIMEOUT_MS)

      targetListener(view)
    }
  }
}

private const val DEBOUNCE_CLICKS_TIMEOUT_MS = 500L

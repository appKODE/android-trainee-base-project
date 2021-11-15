package ru.kode.base.ui.core.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import timber.log.Timber

fun hideKeyboard(activity: Activity, onHide: (() -> Unit)? = null) {
  val view: View = activity.currentFocus ?: activity.findViewById(android.R.id.content) ?: View(activity)
  hideKeyboard(activity, view, onHide)
}

fun hideKeyboard(activity: Activity, focusedView: View, onHide: (() -> Unit)? = null) {
  val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  imm.hideSoftInputFromWindow(focusedView.windowToken, 0)
  if (onHide != null) {
    runOnKeyboardVisibilityChange(activity, expectingHide = true, listener = onHide)
  }
}

fun hideKeyboard(view: View?) {
  if (view != null) {
    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
  }
}

fun showKeyboard(view: View, onShow: (() -> Unit)? = null) {
  if (view.requestFocus()) {
    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)

    if (onShow != null) {
      runOnKeyboardVisibilityChange(view.context as Activity, expectingHide = false, listener = onShow)
    }
  }
}

fun showKeyboard(view: View, delay: Long, onShow: (() -> Unit)? = null) {
  view.requestFocus()
  view.postDelayed(
    {
      val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
      imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)

      if (onShow != null) {
        runOnKeyboardVisibilityChange(view.context as Activity, expectingHide = false, listener = onShow)
      }
    },
    delay
  )
}

private fun runOnKeyboardVisibilityChange(
  activity: Activity,
  expectingHide: Boolean,
  listener: () -> Unit
) {
  val activityRootView: View? = activity.window.decorView.findViewById(android.R.id.content)
  if (activityRootView == null) {
    Timber.tag(DETECTOR_DEBUG_TAG).e("detect failed: didn't find activity root view")
    return
  }
  val vto = activityRootView.viewTreeObserver
  val layoutListener = KeyboardDetectLayoutListener(vto, activityRootView, expectingHide, listener)
  vto.addOnGlobalLayoutListener(layoutListener)
  // чтобы предотвратить возможные leak-и, настраиваем авто-отписку.
  // она также будет срабатывать в случаях, когда hideKeyboard/showKeyboard вызвана, когда по факту клавиатуры
  // на экране сейчас нет.
  // Это на данный момент является недостатком текущего подхода: если клавиатуры нет, то вызывающий код
  // по-любому будет ждать VISIBILITY_CHANGE_LISTENER_FALLBACK_DELAY миллисекунд, прежде чем вызовется listener.
  // В идеале, нужно бы в такой ситуации вызывать listener сразу (ведь клавы нет!), но для этого нужно знать,
  // что её нет, а это большое УВЫ с точки зрения Android SDK...
  activityRootView.postDelayed(
    {
      if (!layoutListener.detectionFinished) {
        Timber
          .tag(DETECTOR_DEBUG_TAG)
          .d("detection not succeeded in $VISIBILITY_CHANGE_LISTENER_FALLBACK_DELAY ms, forcing listener call")
        vto.removeOnGlobalLayoutListener(layoutListener)
        listener.invoke()
      }
    },
    VISIBILITY_CHANGE_LISTENER_FALLBACK_DELAY
  )
}

private class KeyboardDetectLayoutListener(
  private val vto: ViewTreeObserver,
  private val activityRootView: View,
  private val expectingHide: Boolean,
  private val listener: () -> Unit
) : ViewTreeObserver.OnGlobalLayoutListener {

  private val preLayoutHeight = activityRootView.height

  private var layoutCount = 0
  var detectionFinished = false

  override fun onGlobalLayout() {
    if (layoutCount++ > DETECTOR_MAX_LAYOUT_COUNT) {
      Timber.tag(DETECTOR_DEBUG_TAG).e("max layout count reached, failed to correctly detect keyboard state")
      finishDetection()
      return
    }
    val heightDiff = activityRootView.height - preLayoutHeight
    if (heightDiff == 0) {
      return
    }
    val hideDetectionFinished = expectingHide && heightDiff > MINIMUM_KEYBOARD_HEIGHT_DIFF
    val showDetectionFinished = !expectingHide && heightDiff <= MINIMUM_KEYBOARD_HEIGHT_DIFF
    if (hideDetectionFinished || showDetectionFinished) {
      finishDetection()
    }
  }

  private fun finishDetection() {
    vto.removeOnGlobalLayoutListener(this)
    detectionFinished = true
    listener.invoke()
  }
}

private const val MINIMUM_KEYBOARD_HEIGHT_DIFF = 100
private const val VISIBILITY_CHANGE_LISTENER_FALLBACK_DELAY = 200L
private const val DETECTOR_DEBUG_TAG = "KeyboardDetector"
private const val DETECTOR_MAX_LAYOUT_COUNT = 5

@file:Suppress("MatchingDeclarationName") // contains a set of extensions
package ru.kode.base.ui.mvi.core

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import ru.kode.base.ui.core.util.setDebouncingOnClickListener

interface Unbinder {
  fun unbind()
}

fun View.bindClicks(intent: Function0<Unit>) {
  this.setDebouncingOnClickListener {
    intent.invoke()
  }
}

fun <T : Any> View.bindClicks(intent: Function1<T, Unit>, valueProvider: () -> T) {
  this.setDebouncingOnClickListener {
    intent.invoke(valueProvider())
  }
}

fun <T : Any> EditText.bindTextChanges(intent: Function1<T, Unit>, inputMapper: (CharSequence) -> T): Unbinder {
  val watcher = object : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
    override fun afterTextChanged(text: Editable) {
      intent.invoke(inputMapper(text))
    }
  }
  this.addTextChangedListener(watcher)
  return object : Unbinder {
    override fun unbind() {
      this@bindTextChanges.removeTextChangedListener(watcher)
    }
  }
}

fun EditText.bindTextChanges(intent: Function1<String, Unit>): Unbinder {
  val watcher = object : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
    override fun afterTextChanged(text: Editable) {
      intent.invoke(text.toString())
    }
  }
  this.addTextChangedListener(watcher)
  return object : Unbinder {
    override fun unbind() {
      this@bindTextChanges.removeTextChangedListener(watcher)
    }
  }
}

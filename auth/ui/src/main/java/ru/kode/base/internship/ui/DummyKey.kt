package ru.kode.base.internship.ui

import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import ru.kode.base.core.model.ComponentConfig
import ru.kode.base.core.model.ScreenKey

@Parcelize
data class DummyKey(val title: String) : ScreenKey() {
  @IgnoredOnParcel
  override val componentConfig = ComponentConfig(
    presenterClass = DummyScreenPresenter::class.java,
    controllerClass = DummyScreenController::class.java,
  )
}

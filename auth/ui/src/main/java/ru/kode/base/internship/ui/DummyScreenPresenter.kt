package ru.kode.base.internship.ui

import ru.dimsuz.unicorn.coroutines.MachineDsl
import ru.kode.base.core.BaseViewIntents
import ru.kode.base.core.coroutine.BasePresenter

internal class DummyScreenPresenter : BasePresenter<Unit, BaseViewIntents, Unit>() {

  override fun MachineDsl<Unit, Unit>.buildMachine() {
    initial = Unit to null
  }
}

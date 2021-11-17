package ru.kode.base.internship.ui

import ru.dimsuz.unicorn.coroutines.MachineDsl
import ru.kode.base.core.BaseViewIntents
import ru.kode.base.core.coroutine.BasePresenter
import javax.inject.Inject

internal class DummyScreenPresenter @Inject constructor() : BasePresenter<Unit, BaseViewIntents, Unit>() {

  override fun MachineDsl<Unit, Unit>.buildMachine() {
    initial = Unit to null
  }
}

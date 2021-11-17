package ru.kode.base.internship.routing

import ru.kode.base.core.model.ScreenKey
import ru.kode.base.internship.ui.DummyKey

class ScreenKeyFactory : Function1<AppRoute, ScreenKey> {
  override fun invoke(route: AppRoute): ScreenKey {
    return when (route) {
      is AppRoute.Dummy -> DummyKey(route.title)
    }
  }
}

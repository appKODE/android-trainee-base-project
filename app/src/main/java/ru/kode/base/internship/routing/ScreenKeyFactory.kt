package ru.kode.base.internship.routing

import ru.kode.base.core.model.ScreenKey
import ru.kode.base.internship.ui.identification.UserIdentificationKey
import ru.kode.base.internship.ui.password.EnterPasswordKey

class ScreenKeyFactory : Function1<AppRoute, ScreenKey> {
  override fun invoke(route: AppRoute): ScreenKey {
    return when (route) {
      is AppRoute.Login.UserIdentificationKey -> UserIdentificationKey
      is AppRoute.Login.EnterPassword -> EnterPasswordKey
    }
  }
}

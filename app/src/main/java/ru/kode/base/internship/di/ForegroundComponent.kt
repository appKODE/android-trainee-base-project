package ru.kode.base.internship.di

import android.app.Activity
import androidx.navigation.NavHostController
import com.squareup.anvil.annotations.ContributesSubcomponent
import dagger.BindsInstance
import ru.kode.base.core.annotations.ForegroundActivityContext
import ru.kode.base.core.di.AppScope
import ru.kode.base.core.di.SingleIn
import ru.kode.base.core.viewmodel.ViewModelStore
import ru.kode.base.internship.routing.di.AppFlowComponent

@SingleIn(ForegroundScope::class)
@ContributesSubcomponent(scope = ForegroundScope::class, parentScope = AppScope::class)
interface ForegroundComponent {
  @ContributesSubcomponent.Factory
  interface Factory {
    fun create(
      @BindsInstance
      @ForegroundActivityContext
      activity: Activity,
      @BindsInstance
      navController: NavHostController,
    ): ForegroundComponent
  }

  fun appFlowComponent(): AppFlowComponent

  fun viewModelStore(): ViewModelStore
}

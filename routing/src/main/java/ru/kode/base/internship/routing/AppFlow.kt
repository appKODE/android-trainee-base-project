package ru.kode.base.internship.routing

import ru.kode.base.core.routing.coordinator.FlowConfig
import ru.kode.base.core.routing.coordinator.FlowConstructor
import ru.kode.base.core.routing.coordinator.FlowCoordinator

interface AppFlow {
  companion object : FlowConstructor<Coordinator, Unit, Unit>(
    FlowConfig(
      flowId = "app_flow",
      flowModules = listOf(AppFlowModule()),
      coordinatorClass = Coordinator::class.java
    )
  )

  interface Coordinator : FlowCoordinator<Event, Unit>

  object Event
}

package ru.kode.base.internship.core.domain.entity

sealed class LceState {
  data object None : LceState()
  data object Loading : LceState()
  data class Error(val message: String?) : LceState()
  data object Content : LceState()
}

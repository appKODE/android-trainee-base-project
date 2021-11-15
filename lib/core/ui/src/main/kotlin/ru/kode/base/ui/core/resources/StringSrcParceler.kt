package ru.kode.base.ui.core.resources

import android.os.Parcel
import kotlinx.parcelize.Parceler
import ru.kode.base.domain.core.resources.TextRef

object StringSrcParceler : Parceler<TextRef?> {

  override fun create(parcel: Parcel): TextRef? {
    return when (val type = parcel.readString()) {
      "null" -> null
      "__res" -> TextRef.Res(parcel.readInt())
      "__str" -> TextRef.Str(parcel.readString() as CharSequence)
      else -> error("unknown string src type $type")
    }
  }

  override fun TextRef?.write(parcel: Parcel, flags: Int) {
    when (this) {
      is TextRef.Res -> {
        parcel.writeString("__res")
        parcel.writeInt(this.id)
      }
      is TextRef.Str -> {
        parcel.writeString("__str")
        parcel.writeString(this.value.toString())
      }
      null -> parcel.writeString("null")
    }
  }
}

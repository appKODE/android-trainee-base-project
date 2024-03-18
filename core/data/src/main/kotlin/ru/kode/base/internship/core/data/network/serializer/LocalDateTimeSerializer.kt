package ru.kode.base.internship.core.data.network.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
  override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)
  private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  override fun serialize(encoder: Encoder, value: LocalDateTime) {
    encoder.encodeString(formatter.format(value))
  }

  override fun deserialize(decoder: Decoder): LocalDateTime {
    return LocalDateTime.parse(decoder.decodeString(), formatter)
  }
}

package com.dpfht.thestore_koin.data.model.remote

import androidx.annotation.Keep
import com.dpfht.thestore_koin.domain.entity.ImageEntity

@Keep
data class Image(
  val thumbnail: String = "",
  val large: String = ""
)

fun Image.toDomain(): ImageEntity {
  return ImageEntity(thumbnail, large)
}

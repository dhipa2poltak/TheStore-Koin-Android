package com.dpfht.thestore_koin.data.model.remote

import androidx.annotation.Keep
import com.dpfht.thestore_koin.domain.model.DomainImage

@Keep
data class Image(
  val thumbnail: String = "",
  val large: String = ""
)

fun Image.toDomain(): DomainImage {
  return DomainImage(thumbnail, large)
}

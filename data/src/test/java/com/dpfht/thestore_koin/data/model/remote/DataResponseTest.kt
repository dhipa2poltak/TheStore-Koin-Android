package com.dpfht.thestore_koin.data.model.remote

import com.dpfht.thestore_koin.data.helpers.FileReaderHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DataResponseTest {

  @Test
  fun `ensure converting DataResponse to domain is success`() {
    val expectedCount = 15

    val str = FileReaderHelper.readFileAsString("data.json")
    assertTrue(str.isNotEmpty())

    val typeToken = object : TypeToken<DataResponse>() {}.type
    val response = Gson().fromJson<DataResponse>(str, typeToken)
    val entity = response.toDomain()

    assertTrue(expectedCount == entity.data.products.size)
  }
}

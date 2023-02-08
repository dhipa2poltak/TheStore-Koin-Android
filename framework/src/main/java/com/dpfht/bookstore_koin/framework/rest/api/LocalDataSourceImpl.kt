package com.dpfht.bookstore_koin.framework.rest.api

import android.content.res.AssetManager
import com.dpfht.bookstore_koin.data.model.remote.DataResponse
import com.dpfht.bookstore_koin.data.model.remote.toDomain
import com.dpfht.bookstore_koin.data.repository.AppDataSource
import com.dpfht.bookstore_koin.domain.model.DomainDataResponse
import com.dpfht.bookstore_koin.usecases.UseCaseResultWrapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class LocalDataSourceImpl(private val assetManager: AssetManager): AppDataSource {

  override suspend fun getProducts(): UseCaseResultWrapper<DomainDataResponse> {
    return withContext(Dispatchers.IO) {
      var text = ""

      var reader: BufferedReader? = null
      try {
        reader = BufferedReader(InputStreamReader(assetManager.open("data.json")))

        var mLine = reader.readLine()
        while (mLine != null) {
          text += mLine
          mLine = reader.readLine()
        }
      } catch (e: IOException) {
        e.printStackTrace()
      } finally {
        if (reader != null) {
          try {
            reader.close()
          } catch (e: IOException) {
          }
        }
      }

      var dataResponse: DataResponse? = null
      try {
        val typeToken = object : TypeToken<DataResponse>() {}.type
        dataResponse = Gson().fromJson<DataResponse>(text, typeToken)
      } catch (e: Exception) {
        e.printStackTrace()
      }

      UseCaseResultWrapper.Success(dataResponse?.toDomain() ?: DataResponse().toDomain())
    }
  }
}

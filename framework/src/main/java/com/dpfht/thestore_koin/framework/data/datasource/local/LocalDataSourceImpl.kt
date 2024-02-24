package com.dpfht.thestore_koin.framework.data.datasource.local

import android.content.res.AssetManager
import com.dpfht.thestore_koin.data.model.remote.DataResponse
import com.dpfht.thestore_koin.data.datasource.AppDataSource
import com.dpfht.thestore_koin.data.model.remote.toDomain
import com.dpfht.thestore_koin.domain.entity.AppException
import com.dpfht.thestore_koin.domain.entity.DataDomain
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class LocalDataSourceImpl(private val assetManager: AssetManager): AppDataSource {

  override suspend fun getProducts(): DataDomain {
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
      } catch (e: Exception) {
        e.printStackTrace()

        throw AppException(e.message ?: "")
      } finally {
        if (reader != null) {
          try {
            reader.close()
          } catch (_: IOException) {
          }
        }
      }

      val dataResponse: DataResponse?
      try {
        val typeToken = object : TypeToken<DataResponse>() {}.type
        dataResponse = Gson().fromJson<DataResponse>(text, typeToken)
      } catch (e: Exception) {
        e.printStackTrace()

        throw AppException(e.message ?: "")
      }

      return@withContext dataResponse?.toDomain() ?: DataResponse().toDomain()
    }
  }
}

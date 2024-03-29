package com.dpfht.thestore_koin.framework.di

import com.dpfht.thestore_koin.framework.BuildConfig
import com.dpfht.thestore_koin.framework.Config
import com.dpfht.thestore_koin.framework.data.datasource.remote.rest.RestService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
  single { provideClient() }
  single { provideRetrofit(get()) }
  single { provideRestService(get()) }
}

fun provideClient(): OkHttpClient {
  val httpClientBuilder = OkHttpClient().newBuilder()

  if (BuildConfig.DEBUG) {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    httpClientBuilder.addInterceptor(httpLoggingInterceptor)
  }

  return httpClientBuilder.build()
}

fun provideRetrofit(client: OkHttpClient): Retrofit {
  return Retrofit.Builder()
    .baseUrl(Config.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()
}

fun provideRestService(retrofit: Retrofit): RestService {
  return retrofit.create(RestService::class.java)
}

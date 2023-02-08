package com.dpfht.bookstore_koin.di

import com.dpfht.bookstore_koin.BuildConfig
import com.dpfht.bookstore_koin.framework.rest.api.RestService
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
    .baseUrl(BuildConfig.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()
}

fun provideRestService(retrofit: Retrofit): RestService {
  return retrofit.create(RestService::class.java)
}

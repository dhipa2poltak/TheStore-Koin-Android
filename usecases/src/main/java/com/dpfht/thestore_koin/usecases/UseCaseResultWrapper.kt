package com.dpfht.thestore_koin.usecases

sealed class UseCaseResultWrapper<out T> {
  data class Success<out T>(val value: T): UseCaseResultWrapper<T>()
  data class ErrorResult(val message: String): UseCaseResultWrapper<Nothing>()
}

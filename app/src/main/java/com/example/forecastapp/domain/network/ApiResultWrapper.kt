package com.example.forecastapp.domain.network

sealed class ApiResultWrapper<out T> {
    data class Success<out T>(val value: T) : ApiResultWrapper<T>()
    data class Failure(val code: Int? = null) : ApiResultWrapper<Nothing>()
    object NetworkError : ApiResultWrapper<Nothing>()
}
package com.example.forecastapp.domain.repositories

import com.example.forecastapp.domain.network.ApiResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

open class BaseRepository {

    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T
    ): ApiResultWrapper<T> {
        return withContext(dispatcher) {
            try {
                ApiResultWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> ApiResultWrapper.NetworkError
                    is HttpException -> {
                        val code = throwable.code()
                        ApiResultWrapper.Failure(code)
                    }
                    else -> {
                        ApiResultWrapper.Failure(null)
                    }
                }
            }
        }
    }
}
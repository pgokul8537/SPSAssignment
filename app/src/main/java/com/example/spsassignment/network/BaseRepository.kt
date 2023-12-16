package com.example.spsassignment.network

import android.content.Context
import com.example.spsassignment.R
import com.example.spsassignment.network.model.CommonResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

abstract class BaseRepository(val context: Context) {


    suspend fun <T> enqueue(apiCall: suspend () -> T): DataHandler<T> {
        return withContext(Dispatchers.IO) {
            try {
                DataHandler.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> DataHandler.NoInternetConnectionFailure(
                        context.resources.getString(
                            R.string.internet_connection
                        )
                    )

                    is HttpException -> {
                        var errMsg = getErrorMessage(throwable.response()?.errorBody())
                        val errorCode = getErrorCode(throwable.response()?.errorBody())
                        if (errMsg.isNullOrEmpty()) {
                            errMsg = context.resources.getString(R.string.something_went_wrong)
                        }
                        DataHandler.Failure(errorCode, errMsg)
                    }

                    is SocketTimeoutException -> {
                        DataHandler.Failure(
                            "504",
                            context.resources.getString(R.string.server_not_responding)
                        )
                    }

                    else -> {
                        DataHandler.NoInternetConnectionFailure(context.resources.getString(R.string.something_went_wrong))
                    }
                }
            }
        }

    }

    open fun getErrorMessage(errorResponse: ResponseBody?): String? {
        var errorMessage: String? = null
        try {
            if (errorResponse != null) {
                val source = errorResponse.source()
                source.request(Long.MAX_VALUE) // Buffer the entire body.
                val buffer = source.buffer
                val errorBody = buffer.clone().readUtf8()
                if (errorBody.isNotEmpty()) {
                    val commonResponse = Gson().fromJson(
                        errorBody,
                        CommonResponse::class.java
                    )
                    if (commonResponse.statusMessage.isNotEmpty()
                    ) {
                        errorMessage = commonResponse.statusMessage
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return errorMessage
    }

    open fun getErrorCode(errorResponse: ResponseBody?): String? {
        try {
            if (errorResponse != null) {
                val errorBody = errorResponse.string()
                if (errorBody.isNotEmpty()) {
                    val commonResponse = Gson().fromJson(
                        errorBody,
                        CommonResponse::class.java
                    )
                    if (commonResponse?.statusCode != null) {
                        return commonResponse.statusCode.toString()
                    }
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return ""
    }
}
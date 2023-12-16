package com.example.spsassignment.network

sealed class DataHandler<out T> {
    data class Success<out T>(val data: T) : DataHandler<T>()
    data class Failure(val errorCode: String?, val message: String) : DataHandler<Nothing>()
    data class NoInternetConnectionFailure(val message: String) : DataHandler<Nothing>()
}
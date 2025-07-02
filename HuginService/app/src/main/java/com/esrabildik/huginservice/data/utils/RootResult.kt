package com.esrabildik.huginservice.data.utils

sealed class RootResult<out R> {
  data class Success<out R>(val data : R) : RootResult<R>()
  data class Error<out R>(val message : String) : RootResult<R>()
}
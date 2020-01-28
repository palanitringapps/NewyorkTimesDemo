package com.newyorktimesdemo.api

interface OperationCallback {
    fun onSuccess(obj:Any?)
    fun onError(obj:Any?)
}
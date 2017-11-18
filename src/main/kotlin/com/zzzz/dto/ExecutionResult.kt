package com.zzzz.dto

class ExecutionResult <T> {
    var isSuccess: Boolean
    var data: T? = null
    var error: String? = null

    constructor(data: T) {
        isSuccess = true
        this.data = data
    }

    constructor(exception: Exception) {
        isSuccess = false
        this.error = exception.message
    }

    constructor(isSuccess: Boolean, str: String) {
        this.isSuccess = isSuccess
        if (isSuccess)
            data = str as T
        else
            error = str
    }

    override fun toString(): String {
        return if(isSuccess) "ExecutionResult(isSuccess=$isSuccess, data=$data)"
        else "ExecutionResult(isSuccess=$isSuccess, error=$error)"
    }


}
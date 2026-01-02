package com.kklabs.themoviedb.utils

import platform.Foundation.NSLog

actual object Logger {
    actual fun d(tag: String, message: String) {
        NSLog("$tag: $message")
    }

    actual fun e(tag: String, message: String, throwable: Throwable?) {
        val errorMessage = throwable?.message ?: ""
        NSLog("$tag ERROR: $message $errorMessage")
    }
}
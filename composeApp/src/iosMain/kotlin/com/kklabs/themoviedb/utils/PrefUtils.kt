package com.kklabs.themoviedb.utils

import platform.Foundation.NSBundle

object NsBundleConfig {
    fun getValue(key: String): String {
        val value = NSBundle.mainBundle.objectForInfoDictionaryKey(key) as? String
        return value ?: error("Missing key in Info.plist: $key")
    }
}

package com.pp.cloud.pingback.net.error

/**
 * Created by hanzhang on 2018/5/22.
 */
open class CHttpException : Exception {

    constructor():super() {

    }

    constructor(exceptionMessage: String):super(exceptionMessage) {
    }

    constructor(exceptionMessage: String, reason: Throwable):super(exceptionMessage,reason){
    }

    constructor(cause: Throwable):super(cause) {
    }
}
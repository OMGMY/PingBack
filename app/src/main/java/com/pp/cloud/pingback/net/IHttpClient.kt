package com.pp.cloud.pingback.net

/**
 * Created by hanzhang on 2018/5/18.
 */
interface IHttpClient {
    fun get(req:CRequeset?,callback: IReaponseCallback?):Unit
    fun post(req:CRequeset?,callback: IReaponseCallback?):Unit
}
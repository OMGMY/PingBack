package com.pp.cloud.pingback.net

/**
 * Created by hanzhang on 2018/5/18.
 */
class CHttpClientImpl :IHttpClient {

    companion object {
        private var customeClient:IHttpClient? = null
        public fun initCustomeClient(client: IHttpClient?) {
            customeClient = client
        }
    }


    override fun get(req: CRequeset?, callback: IReaponseCallback?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun post(req: CRequeset?, callback: IReaponseCallback?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
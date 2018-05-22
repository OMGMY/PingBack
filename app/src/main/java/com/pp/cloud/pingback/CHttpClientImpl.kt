package com.pp.cloud.pingback

import com.pp.cloud.pingback.net.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by hanzhang on 2018/5/22.
 */
class CHttpClientImpl :IHttpClient {

    private constructor() {
        mDefaultClient = OkHttpClient.Builder().build()
    }

    private var mDefaultClient:OkHttpClient? = null

    private var mCoustomClient:IHttpClient? = null

    private var userAgent: String? = null

    private val mDefaultHeaders: MutableMap<String, String>? = null

    companion object {
        public fun getInstance():CHttpClientImpl {
            return innerHolder.mCHttpClientImpl
        }
        class innerHolder {
                companion object {
                    internal var mCHttpClientImpl:CHttpClientImpl = CHttpClientImpl()
                }
        }
    }

    var requestQueue:RequsetQueue? = null

    override fun get(req: CRequeset?, callback: IReaponseCallback?):CResponse {
        val response = CResponse(req)

        var baseUrl = req!!.url()
        try {
            val url = URL(req!!.url())
            baseUrl = url.host + url.path
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }


        val url = ""//OpUrlBuilder.buildPaoPaoUrlGet(baseUrl, request.data())
        try {
            val r = Request.Builder()
                   // .headers(getHeaders(request))
                    .url(url)
                    .build()

            val call = mDefaultClient?.let { mDefaultClient!!.newCall(r) }?:throw RuntimeException("make sure defaultClient is not null")
            val rp = call.execute()
           // response.setStatus(rp.code())
            response.setMultiHeaders(rp.headers().toMultimap())
            if (rp.body() != null) {
                response.setText(rp.body()!!.string())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }


        return response
    }

    override fun post(req: CRequeset?, callback: IReaponseCallback?):CResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
package com.pp.cloud.pingback.net

import java.io.UnsupportedEncodingException
import java.net.MalformedURLException
import java.net.URL
import java.net.URLDecoder
import java.util.HashMap
import java.util.LinkedHashMap

/**
 * Created by hanzhang on 2018/5/18.
 */
class CRequeset {
    private var mMethod: Int = -1
    private var mUrl: String? = null
    private var mTag: Any? = null
    private var mTimeOut: Int = 0
    private var mConnectTimeout: Int = 0
    private var mSocketTimeout: Int = 0

 //   private var mRetryPolicy: OpRetryPolicy?
    private var mParams: Map<String, String>? = null
    private var mHeaders: Map<String, String>? = null


    @Throws(UnsupportedEncodingException::class)
    private fun splitQuery(url: URL): Map<String, String> {
        val query_pairs = LinkedHashMap<String, String>()
        val query = url.query
        val pairs = query.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (pair in pairs) {
            val idx = pair.indexOf("=")
            if (idx >= 0) {

                query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
                        URLDecoder.decode(pair.substring(idx + 1), "UTF-8"))
            }

        }
        return query_pairs
    }


    constructor (builder: Builder){
        mMethod = builder.mMethod
        mUrl = builder.mUrl
        mTag = builder.mTag
     //   mRetryPolicy = builder.mRetryPolicy
        mTimeOut = builder.mTimeOut
        mSocketTimeout = builder.mSocketTimeout
        mConnectTimeout = builder.mConnectTimeout
        mHeaders = builder.mHeaders
        mParams = builder.mParams


        if (mUrl!!.contains("?")) {
            try {
                val url = URL(mUrl)
           //     PPLog.logUser(url.toString())
                val query = splitQuery(url)
                if (mParams == null) {
                    mParams = hashMapOf()

                }
                if (query != null) {
                    mParams!!.plus(query)
                }
                val index = mUrl!!.indexOf("?")
                if (index > 0) {
                    mUrl = mUrl!!.substring(0, index)
                }
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

        }
    }


    fun data(): Map<String, String>? {
        return mParams
    }

    fun url(): String? {
        return mUrl
    }

    fun headers(): Map<String, String>? {
        return mHeaders
    }


    class Builder {
         var mMethod: Int = 0
         var mUrl: String? = null
         var mTag: Any? = null
        // var mRetryPolicy: OpRetryPolicy? = null
         var mTimeOut: Int = 0
         var mConnectTimeout: Int = 0
         var mSocketTimeout: Int = 0
         var mParams: Map<String, String>? = null

         var mHeaders: Map<String, String>? = null

        fun method(`val`: Int): Builder {
            mMethod = `val`
            return this
        }

        fun url(`val`: String): Builder {
            mUrl = `val`
            return this
        }

        fun tag(`val`: Any): Builder {
            mTag = `val`
            return this
        }

        fun data(data: Map<String, String>): Builder {
            mParams = data
            return this
        }

       /* fun retryPolicy(`val`: OpRetryPolicy): Builder {
            mRetryPolicy = `val`
            return this
        }*/

        fun timeout(`val`: Int): Builder {
            mTimeOut = `val`
            return this
        }

        fun headers(`val`: Map<String, String>): Builder {
            mHeaders = `val`
            return this
        }

        fun connectTimeout(connectTimeout: Int) {
            mConnectTimeout = connectTimeout
        }

        fun socketTimeout(socketTimeout: Int) {
            mSocketTimeout = socketTimeout
        }

        fun build(): CRequeset {
            return CRequeset(this)
        }
    }
}
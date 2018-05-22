package com.pp.cloud.pingback.net

import org.json.JSONException
import org.json.JSONObject
import java.io.InputStream
import java.util.HashMap

/**
 * Created by hanzhang on 2018/5/18.
 */
class CResponse {

    private var mText: String? = null
    private var mJSONObject: JSONObject? = null
    private var mStatus: Int = 0
    private var mRequest: CRequeset? = null
    private var mHeaders: Map<String, String>? = null
    private var mInputStream: InputStream? = null

    fun getHeaders(): Map<String, String>? {
        return mHeaders
    }

    fun getRequest(): CRequeset? {
        return mRequest
    }


    constructor(request: CRequeset?) {
        mRequest = request
    }

    fun setInputStream(stream: InputStream) {
        mInputStream = stream
    }

    fun getInputStream(): InputStream? {
        return mInputStream
    }

    fun setText(text: String) {
        mText = text
    }

    fun getText(): String? {
        return mText
    }

    fun setHeaders(headers: Map<String, String>) {
        mHeaders = headers
    }

    fun setMultiHeaders(headers: Map<String, List<String>>) {
        mHeaders = convertMultiToRegularMap(headers)
    }

    /*fun setStatus(status: Int): OpHttpResponse {
        mStatus = status
        return this
    }*/

    private fun convertMultiToRegularMap(m: Map<String, List<String>>?): Map<String, String> {
        val map = HashMap<String, String>()
        if (m == null) {
            return map
        }
        for ((key, value) in m) {
            val sb = StringBuilder()
            for (s in value) {
                if (sb.length > 0) {
                    sb.append(',')
                }
                sb.append(s)
            }
            map.put(key, sb.toString())
        }
        return map
    }
/*
    fun isSuccess(): Boolean {
        return mStatus == OpHttpStatus.SC_OK
    }*/

    @Throws(JSONException::class)
    fun getJson(): JSONObject? {
        if (mJSONObject == null || mJSONObject!!.length() == 0) {
            val temp = getText()
            if (temp == null || temp.trim { it <= ' ' }.length == 0) {
                mJSONObject = JSONObject()
            } else {
                mJSONObject = JSONObject(temp)
            }
        }
        return mJSONObject
    }

    fun isDataEmpty(): Boolean {
        return mText == null || mText!!.trim { it <= ' ' }.length == 0
    }
}
package com.cindyle.traveltaipei.attractions

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cindyle.traveltaipei.bean.AttBean
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

class AttViewModel : ViewModel(){
    private val TAG = "LOG_TAG_" + AttViewModel::class.java.simpleName
    var data: MutableLiveData<List<AttBean.AttData>> = MutableLiveData<List<AttBean.AttData>>()
    var total = MutableLiveData<Int>()

    fun getData(lang : String?){
        var url = "https://www.travel.taipei/open-api/${lang}/Attractions/All"
        val okHttpClient = OkHttpClient()
        val headers = Headers.Builder()
            .add("Accept", "application/json")
            .build()

        val request = Request.Builder()
            .get()
            .headers(headers)
            .url(url)
            .build()

        var apiResponse = ""

        val call = okHttpClient.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (response.body != null) {
                    if (response.code == 200) {
                        try {
                            apiResponse = "" + response.body!!.string()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        Log.i(TAG, "onResponse: $apiResponse")
                    } else {
                        val responseCode = "response code:  + $response.code"
                        Log.e(TAG, "response code:  + $response.code")
                    }
                    val attBean : AttBean = Gson().fromJson(apiResponse, object : TypeToken<AttBean?>() {}.type)
                    data.postValue(attBean.data)
                    total.postValue(attBean.total)
                }
            }
        })
    }
}
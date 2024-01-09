package com.cindyle.traveltaipei.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cindyle.traveltaipei.bean.NewsBean
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

class NewsViewModel : ViewModel (){
    private val TAG = "LOG_TAG_" + NewsViewModel::class.java.simpleName
    var data: MutableLiveData<List<NewsBean.NewsData>> = MutableLiveData<List<NewsBean.NewsData>>()
    var total = MutableLiveData<Int>()

    fun getData(lang : String?){
        var url = "https://www.travel.taipei/open-api/${lang}/Events/News"
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
                    val newsBean : NewsBean = Gson().fromJson(apiResponse, object : TypeToken<NewsBean?>() {}.type)
                    data.postValue(newsBean.data)
                    total.postValue(newsBean.total)
                }
            }
        })
    }
}
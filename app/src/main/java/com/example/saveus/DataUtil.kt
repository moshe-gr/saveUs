package com.example.saveus

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest

const val numDead = "numDead"
const val numRecover = "numRecover"
const val numHospital = "numHospital"
const val timeUpdated = "timeUpdated"
const val baseUrl =
    "https://data.gov.il/api/3/action/datastore_search?resource_id="
const val deathUrl = "a2b2fceb-3334-44eb-b7b5-9327a573ea2c&limit=0"
const val recoverUrl = "8455d49f-ce32-4f8f-b1d4-1d764660cca3&limit=0"
const val hospitalUrl = "e4bf0ab8-ec88-4f9b-8669-f2cc78273edd&limit=0"

class DataUtil {
    companion object {
        private val TAG = this::class.java.simpleName
        private lateinit var requestQueue: RequestQueue
        fun fetchData(context: Context) {
            val sharedPreferences =
                context.getSharedPreferences("sharedPrefs", AppCompatActivity.MODE_PRIVATE)
            if (sharedPreferences.getLong(
                    timeUpdated,
                    0
                ) + (12 * 60 * 60 * 1000) < System.currentTimeMillis()
            ) {
                val appNetwork = BasicNetwork(HurlStack())
                val appcache = DiskBasedCache(context.cacheDir, 1024 * 1024)
                requestQueue = RequestQueue(appcache, appNetwork).apply {
                    start()
                }

                val jsonObjectRequestD = JsonObjectRequest(
                    Request.Method.GET, baseUrl + deathUrl, null, { response ->
                        if (response.get("success") == "False") {
                            Log.d(TAG, "fetchData: response false")
                        } else {
                            val result = response.getJSONObject("result")
                            sharedPreferences.edit().putInt(numDead, result.getInt("total")).apply()
                        }
                    },
                    { error ->
                        Log.d(TAG, error.toString())
                    }
                )

                val jsonObjectRequestR = JsonObjectRequest(
                    Request.Method.GET, baseUrl + recoverUrl, null, { response ->
                        if (response.get("success") == "False") {
                            Log.d(TAG, "fetchData: response false")
                        } else {
                            val result = response.getJSONObject("result")
                            sharedPreferences.edit().putInt(numRecover, result.getInt("total"))
                                .apply()
                        }
                    },
                    { error ->
                        Log.d(TAG, error.toString())
                    }
                )

                val jsonObjectRequestH = JsonObjectRequest(
                    Request.Method.GET, baseUrl + hospitalUrl, null, { response ->
                        if (response.get("success") == "False") {
                            Log.d(TAG, "fetchData: response false")
                        } else {
                            val result = response.getJSONObject("result")
                            sharedPreferences.edit().putInt(numHospital, result.getInt("total"))
                                .apply()
                        }
                    },
                    { error ->
                        Log.d(TAG, error.toString())
                    }
                )

                requestQueue.add(jsonObjectRequestD)
                requestQueue.add(jsonObjectRequestR)
                requestQueue.add(jsonObjectRequestH)
                sharedPreferences.edit().putLong(timeUpdated, System.currentTimeMillis())
                    .apply()
            }
        }
    }
}
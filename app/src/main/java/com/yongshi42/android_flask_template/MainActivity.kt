package com.yongshi42.android_flask_template

import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import api_stuff.APIKindaStuff
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    /**
     * creating activity wide variables
     * specific type with ? to be able to set to null
     * using !! to use these variables in a non-nullable way
     */
    private var accountjson: JsonObject = JsonObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enterusername.text.insert(0, "Foobar")

        APIKindaStuff
            .service
            .postusername(enterusername.text.toString())
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("ethnfc debug", "--- :: GET Throwable EXCEPTION:: ${t.message}")
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        val userid = response.body()?.string().toString()
                        Log.d("ethnfc debug", "Post new user: $userid")
                        accountjson.addProperty("UserID", userid)
                        useridresult.text = userid

                        APIKindaStuff
                            .service
                            .getusername(enteruserid.text.toString().toInt())
                            .enqueue(object : Callback<ResponseBody> {

                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                    Log.d("ethnfc debug", "GET Throwable EXCEPTION:: ${t.message}")
                                    Toast.makeText(applicationContext, "Failed to connect", Toast.LENGTH_SHORT).show()
                                }

                                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                    if (response.isSuccessful) {
                                        val username = response.body()?.string()
                                        Log.d("ethnfc debug", "GET username: $username")
                                        accountjson.addProperty("Username", username)
                                        usernameresult.text = username
                                    }
                                }
                            })
                    }
                }
            })

//        val bytes = bytesvalue
//        val bytes64 = Base64.encodeToString(bytes, 64)
//        val bytesjson: JsonObject = JsonObject()
//        bytesjson.addProperty("bytes64", bytes64)
//
//        APIKindaStuff
//            .service
//            .postbytes(bytesjson)
//            .enqueue(object : Callback<ResponseBody> {
//
//                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                    Log.d("ethnfc debug", "GET Throwable EXCEPTION:: ${t.message}")
//                    Toast.makeText(applicationContext, "Failed to connect", Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                    if (response.isSuccessful) {
//                        Log.d("ethnfc debug", "Post bytes succcesfully")
//                    }
//                }
//            })

    }
}
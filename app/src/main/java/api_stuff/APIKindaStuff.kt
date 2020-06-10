package api_stuff

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


/**
 *
 * Created by mohammed on 22/11/17.
 */

class APIKindaStuff {

    interface APIService {
        /**
         * All the get api's are the ones that don't change the blockchain
         * i.e. hashing of a pin and return the hash
         */
        @GET("/getusername/{userid}")
        fun getusername(@Path("userid") userid: Int): Call<ResponseBody>

        @POST("/postusername")
        fun postusername(@Body body: String): Call<ResponseBody>

        @POST("/postbytes")
        fun postbytes(@Body body: JsonObject): Call<ResponseBody>
    }

    companion object {
        private val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.178.235:5000")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()

        var service = retrofit.create(
            APIService::class.java)
    }
}
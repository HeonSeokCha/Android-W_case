package com.chs.bookcase.network_request

import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

fun get_category():Array<Array<String>>{//책장 긁어오기
    try {
        val mURL = URL("http://121.187.202.41/api/rfid/getcategories")
        val response = StringBuffer()

        with(mURL.openConnection() as HttpURLConnection) {
            requestMethod = "POST"
            connectTimeout=5000
            readTimeout=5000
            try {
            if(responseCode==200) {
                BufferedReader(InputStreamReader(inputStream)).use {
                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                        it.close()
                    }
                }
                val jsonarr = JSONArray("$response")
                var return_arr = Array<Array<String>>(jsonarr.length()) { arrayOf("","") }
                for(i in 0 until jsonarr.length()){
                    var jsonobj = jsonarr.getJSONObject((i))
                    return_arr[i][0] = responseCode.toString()
                    return_arr[i][1] = jsonobj.getString("category_name")
                }
                return return_arr
                }
            else return arrayOf(arrayOf("500","Failed"))
            disconnect()
            }
            catch (e:Exception){
                return arrayOf(arrayOf("500","Failed"))
            }
        }
    }
    catch (e: Exception) {
        return arrayOf(arrayOf("502",e.toString()))
    }
}
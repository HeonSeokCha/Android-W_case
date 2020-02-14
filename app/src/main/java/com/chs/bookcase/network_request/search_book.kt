package com.chs.bookcase.network_request

import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

fun search_book(title:String):Array<Array<String>> {//책 긁어오기
    try {
        val mURL = URL("http://121.187.202.41/api/rfid/search/all/$title")
        val response = StringBuffer()
        var get_title:String =""
        var get_author:String=""
        var get_category:String =""


        with(mURL.openConnection() as HttpURLConnection) {
            requestMethod = "POST"
            doOutput = true
            val wr = OutputStreamWriter(outputStream)
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
                var return_arr = Array<Array<String>>(jsonarr.length()) { arrayOf("","","","") }
                for(i in 0 until jsonarr.length()){
                    var jsonobj = JSONObject(jsonarr.getString(i))
                    return_arr[i][0] = responseCode.toString()
                    return_arr[i][1] = jsonobj.getString("title")
                    return_arr[i][2] = jsonobj.getString("author")
                    return_arr[i][3] = jsonobj.getJSONObject("Category").getString("category_name")
                }
                return return_arr
            }
            else return arrayOf(arrayOf("500","Failed","Failed","Failed"))
            disconnect()
        }
    }
    catch (e: Exception) {
        return arrayOf(arrayOf("502",e.toString(),e.toString(),e.toString()))
    }
}

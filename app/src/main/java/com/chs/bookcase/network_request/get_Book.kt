package com.chs.bookcase.network_request

import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

fun getBook_request(category_name:String):Array<Array<String>> {//책장에 책 긁어오기
    try {
        var reqParam = "category_name=$category_name"
        val mURL = URL("http://121.187.202.41/api/rfid/getbooksinshelf")
        val response = StringBuffer()


        with(mURL.openConnection() as HttpURLConnection) {
            requestMethod = "POST"
            doOutput = true
            val wr = OutputStreamWriter(outputStream)

            wr.write(reqParam)
            wr.flush()
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
                var return_arr = Array<Array<String>>(jsonarr.length()) { arrayOf("","","","","") }
                for(i in 0 until jsonarr.length()){
                    var jsonobj = jsonarr.getJSONObject(i)
                    return_arr[i][0] = responseCode.toString()
                    return_arr[i][1] = jsonobj.getString("title")
                    return_arr[i][2] = jsonobj.getString("author")
                    return_arr[i][3] = jsonobj.getJSONObject("Category").getString("category_name")
                    return_arr[i][4] = jsonobj.getString("tag_id")
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

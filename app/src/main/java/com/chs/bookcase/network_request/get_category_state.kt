package com.chs.bookcase.network_request

import org.json.JSONArray
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

fun get_Shelf_state():Array<String> {//해당 책장의 상태
    try {
        var reqParam = ""
        val mURL = URL("http://121.187.202.41/api/rfid/getinvalidbookinfo")
        val response = StringBuffer()
        var get_category:String =""
        var get_tagid:String = ""

        with(mURL.openConnection() as HttpURLConnection) {
            requestMethod = "POST"
            doOutput = true
            val wr = OutputStreamWriter(outputStream)

            wr.write(reqParam)
            wr.flush()
            BufferedReader(InputStreamReader(inputStream)).use {
                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                    it.close()
                }
            }
            val jsonarr = JSONArray("$response")
            var jsonobj = jsonarr.getJSONObject(0)
            var result_arr = Array<String>(jsonobj.getJSONArray("tag_ids").length()+1) {""}
            result_arr[0] = jsonobj.getString("category_name")
            for(i in 1..jsonobj.getJSONArray("tag_ids").length()) {
                result_arr[i] = jsonobj.getJSONArray("tag_ids").getString(i-1)

            }
            return result_arr
            disconnect()
        }
    }
    catch (e: Exception) {
        return arrayOf(e.toString())
    }
}

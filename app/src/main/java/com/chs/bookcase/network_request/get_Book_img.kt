package com.chs.bookcase.network_request
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

fun get_book_img(bookname:String):Array<String>{
    try {
        val _parm = URLEncoder.encode(bookname,"UTF-8")
        val mURL = URL("https://dapi.kakao.com/v2/search/image?query=$bookname&total_count=1&pageable_count=1")
        val response = StringBuffer()
        var get_title:String =""
        var get_author:String=""
        var get_category:String =""

        with(mURL.openConnection() as HttpURLConnection) {
            requestMethod = "GET"
            doOutput = true
            setRequestProperty("Authorization","KakaoAK a1076bbe17d60a608d20b159f2f2bc36")
            val wr = OutputStreamWriter(outputStream)

            BufferedReader(InputStreamReader(inputStream)).use {
                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                    it.close()
                }
            }
            val jsonobj = JSONObject("$response")
            val jsonarr = jsonobj.getJSONArray("documents")
            var return_arr = Array<String>(jsonobj.length()) { _ ->""}
            val small_img_url = jsonarr.getJSONObject(0).getString("thumbnail_url")
            val big_img_url = jsonarr.getJSONObject(0).getString("image_url")
            for(i in return_arr.indices){
                return_arr[0] = small_img_url
                return_arr[1] =big_img_url
            }
            return return_arr
            disconnect()
        }
    }
    catch (e: Exception) {
        return arrayOf("No_img")
    }
}

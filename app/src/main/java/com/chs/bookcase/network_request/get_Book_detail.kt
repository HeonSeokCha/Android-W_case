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

fun get_book_detail(bookname:String):Array<String>{
    try {
        val _parm = URLEncoder.encode(bookname,"UTF-8")
        val mURL = URL("https://dapi.kakao.com/v3/search/book?query=$_parm&sort=latest&page=1&size=1&target=title")
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
            val img_url = jsonarr.getJSONObject(0).getString("thumbnail")
            for(i in img_url.indices){
                return_arr[0] = img_url
            }
            return return_arr
            disconnect()
        }
    }
    catch (e: Exception) {
        return arrayOf("No_img")
    }
}

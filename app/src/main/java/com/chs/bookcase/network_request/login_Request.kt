package com.chs.bookcase.network_request
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONObject
import java.io.IOException


fun login_request(userid:String, userpw:String):Array<String>{//로그인
    try {
            var reqParam = "user_id=$userid&user_pw=$userpw"
            val mURL = URL("http://121.187.202.41/api/auth/login")
            val response = StringBuffer()
            var user_name:String =""
            var library_name:String =""
            with(mURL.openConnection() as HttpURLConnection) {
                connectTimeout=2000
                readTimeout=1500
                try {
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
                                val jsonObj = JSONObject("$response")
                                user_name = jsonObj.getString("username")
                                library_name = jsonObj.getString("library_name")
                            }
                        }
                        return arrayOf(user_name,library_name,"200")
                    }
                    else return arrayOf("Failed",responseCode.toString(),responseCode.toString())
                    disconnect()
                }
                catch (e:IOException){
                     return arrayOf("애욱 = ",responseCode.toString(),responseCode.toString())
                }
            }
        }
        catch (e: Exception) {
            return return arrayOf("콰욱 = ",e.toString())
    }
}




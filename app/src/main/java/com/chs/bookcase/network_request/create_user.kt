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


fun createUser_request(user_id:String, user_pw:String,username:String):String {//사용자 생성
    try {
        var reqParam = "user_id=$user_id&user_pw=$user_pw&username=$username&library_id=1"
        val mURL = URL("http://121.187.202.41/api/auth/register")

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
                        val response = StringBuffer()
                        var inputLine = it.readLine()

                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()
                        }
                        it.close()
                    }
                    return "200"
                }
                else return "500"
            }
            catch (e:IOException){
                return "502"
            }
            disconnect()
        }
    }
    catch (e: Exception)
    {
        return "502"
    }
}

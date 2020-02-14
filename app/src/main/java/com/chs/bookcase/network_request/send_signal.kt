package com.chs.bookcase.network_request

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

fun send_signal_pi(category_name:String):String {//해당책장 읽으라고 신호보냄
    try {
        var reqParam = "category_name=$category_name"
        val mURL = URL("http://121.187.202.41/api/rfid/readrfid")

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
            catch (e: IOException){
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
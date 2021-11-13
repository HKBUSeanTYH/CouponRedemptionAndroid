package comp4097.comp.hkbu.edu.hk.couponredemption

import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class Network {
    companion object {
        suspend fun getTextFromNetwork(url: String) : String {
            val builder = StringBuilder()
            val connection = URL(url).openConnection() as HttpURLConnection
            var data: Int = connection.inputStream.read()
            while (data != -1) {
                builder.append(data.toChar())
                data = connection.inputStream.read()
            }
            return builder.toString()
        }

        suspend fun makeRequest(url: String, body: String): String{
            var builder = StringBuilder()
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.setDoOutput(true)
            //connection.setChunkedStreamingMode(0)
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8")

            //val jsonbody = Gson().toJson(body)
            val wr = OutputStreamWriter(connection.outputStream, "utf-8")
            wr.write(body)
            wr.flush()

            try{
                var data: Int = connection.inputStream.read()
                while (data != -1) {
                    builder.append(data.toChar())
                    data = connection.inputStream.read()
                }
            }catch(e: Exception){
                builder = StringBuilder()
                builder.append(connection.responseMessage)
            }finally {
                wr.close()
            }
            return builder.toString()
        }
    }
}
package id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.tools

import android.os.AsyncTask
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.ui.main.SongNewsFragment
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class NewsHttpRequest(val caller:SongNewsFragment): AsyncTask<String, Void, String>() {
    val REQUEST_METHOD = "GET"
    val READ_TIMEOUT = 15000
    val CONNECTION_TIMEOUT = 15000

    override fun doInBackground(vararg params: String?): String {
        val stringUrl = params[0]
        var result:String
        var inputLine: String

        try {
            val myUrl = URL(stringUrl)

            val connection = myUrl.openConnection() as HttpURLConnection

            connection.requestMethod = REQUEST_METHOD
            connection.readTimeout = READ_TIMEOUT
            connection.connectTimeout = CONNECTION_TIMEOUT

            connection.connect()

            val streamReader = InputStreamReader(connection.inputStream)

            val reader = BufferedReader(streamReader)
            val stringBuilder = StringBuilder()

            while(true){
                try{
                    inputLine = reader.readLine()
                }
                catch (e:Exception){
                    break
                }
                stringBuilder.append(inputLine)
            }

            reader.close()
            streamReader.close()

            result = stringBuilder.toString()
        }
        catch(e: IOException){
            e.printStackTrace()
            result = ""
        }

        return result
    }

    override fun onPostExecute(result: String?) {
        val json = JSONObject(result!!)
        super.onPostExecute(result)
        caller.onGetResult(json)
    }

}
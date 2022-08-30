package com.bicyo.bicyo.tools

import android.os.AsyncTask
import com.google.android.gms.maps.model.LatLng
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

open class TaskRequestDirections :
    AsyncTask<LatLng, Void?, MutableList<LatLng>>() {

    override fun doInBackground(vararg latLngs: LatLng): MutableList<LatLng> {
        var responseString = ""
        try {
            responseString = requestDirection(getRequestUrl(latLngs[0],latLngs[1])?:"")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val parsedResponse = parsePathJson(responseString)
        return parsedResponse
    }

    fun getRequestUrl(origin: LatLng, dest: LatLng): String {
        //Value of origin
        val str_org = "origin=" + origin.latitude + "," + origin.longitude
        //Value of destination
        val str_dest = "destination=" + dest.latitude + "," + dest.longitude
        //Set value enable the sensor
        val sensor = "sensor=false"
        //Mode for find direction
        val mode = "mode=driving"
        //key
        val key = "key=AIzaSyCvNGAOwxkluZFfh--Hy8zpMqgnKc0h7yk"
        //Build the full param
        val param = "$str_org&$str_dest&$sensor&$mode&$key"
        //Output format
        val output = "json"
        //Create url to request
        return "https://maps.googleapis.com/maps/api/directions/$output?$param"
    }



    private fun parsePathJson(string:String): MutableList<LatLng> {
        var jsonObject: JSONObject? = null
        var routes: List<List<HashMap<String?, String?>?>?>? = null
        try {
            jsonObject = JSONObject(string)
            val directionsParser = DirectionsParser()
            routes = directionsParser.parse(jsonObject)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val routesLatLng = mutableListOf<LatLng>()

        if (routes != null) {
            for(subroutes in routes){
                if (subroutes != null) {
                    for(route in subroutes){
                        val lat: Double = route?.get("lat")?.toDouble() ?: 0.0
                        val lon: Double = route?.get("lon")?.toDouble() ?: 0.0
                        routesLatLng.add(LatLng(lat,lon));
                    }
                }
            }
        }
        return routesLatLng
    }

    @Throws(IOException::class)
    fun requestDirection(reqUrl: String): String  {
        var responseString = ""
        var inputStream: InputStream? = null
        var httpURLConnection: HttpURLConnection? = null
        try {
            val url = URL(reqUrl)
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.connect()

            //Get the response result
            inputStream = httpURLConnection!!.inputStream
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuffer = StringBuffer()
            var line: String? = ""
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuffer.append(line)
            }
            responseString = stringBuffer.toString()
            bufferedReader.close()
            inputStreamReader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
            httpURLConnection!!.disconnect()
        }
        return responseString
    }

}
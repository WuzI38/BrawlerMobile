package com.app.brawlermobile.json

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

object JsonParser {
    fun getColors(context: Context): Map<String, Map<String, String>>? {
        return getDataFromJson(context, "colors.json")
    }

    fun getImages(context: Context): Map<String, String>? {
        return getDataFromJson(context, "images.json")
    }

    private inline fun <reified T> getDataFromJson(context: Context, filename: String): T? {
        val jsonFileString: String?
        try {
            jsonFileString = context.assets.open(filename).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }

        val gson = Gson()
        val type = object : TypeToken<T>() {}.type

        return gson.fromJson(jsonFileString, type)
    }
}

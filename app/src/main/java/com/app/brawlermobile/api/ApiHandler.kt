package com.app.brawlermobile.api

import io.ktor.client.*
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive

object ApiHandler {
    private val client = HttpClient()

    suspend fun getCommandersPopularity(limit: Int): List<List<String>> {
        val response: HttpResponse = client.get("${ApiConfig.BASE_URL}/commanders/popularity?limit=$limit")
        val responseBody = response.bodyAsText()
        return Json.decodeFromString<List<List<String>>>(responseBody)
    }

    suspend fun getCommandersWinrate(limit: Int): List<List<String>> {
        val response: HttpResponse = client.get("${ApiConfig.BASE_URL}/commanders/winrate?limit=$limit")
        val responseBody = response.bodyAsText()
        return Json.decodeFromString<List<List<String>>>(responseBody)
    }

    suspend fun getCards(limit: Int): List<List<String>> {
        val response: HttpResponse = client.get("${ApiConfig.BASE_URL}/cards?limit=$limit")
        val responseBody = response.bodyAsText()
        return Json.decodeFromString<List<List<String>>>(responseBody)
    }

    suspend fun getCommandersByColor(colors: String): List<List<String>> {
        val response: HttpResponse = client.get("${ApiConfig.BASE_URL}/commanders/$colors")
        val responseBody = response.bodyAsText()

        val jsonArray = Json.parseToJsonElement(responseBody).jsonArray

        val commanders = jsonArray.map { jsonElement ->
            val commanderData = jsonElement.jsonArray
            val id = commanderData[0].jsonPrimitive.int.toString()
            val name = commanderData[1].jsonPrimitive.content
            val imageUrl = commanderData[2].jsonPrimitive.content
            listOf(id, name, imageUrl)
        }

        return commanders
    }

    suspend fun getCommanderById(id: String): Map<String, List<List<String>>> {
        val response: HttpResponse = client.get("${ApiConfig.BASE_URL}/commanders/$id")
        val responseBody = response.bodyAsText()
        return Json.decodeFromString<Map<String, List<List<String>>>>(responseBody)
    }
}
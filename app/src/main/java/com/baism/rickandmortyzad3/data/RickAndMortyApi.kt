package com.baism.rickandmortyzad3.data

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class RickAndMortyApi(
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()
) {
    fun fetchAllCharacters(): List<CharacterInfo> {
        val characters = mutableListOf<CharacterInfo>()
        var nextUrl: String? = "https://rickandmortyapi.com/api/character"

        while (nextUrl != null) {
            val page = fetchCharacterPage(nextUrl)
            characters.addAll(page.results)
            nextUrl = page.next
            if (nextUrl != null) {
                Thread.sleep(600)
            }
        }

        return characters
    }

    fun fetchCharacterById(id: Int): CharacterInfo {
        val body = executeGet("https://rickandmortyapi.com/api/character/$id")
        return parseCharacter(JSONObject(body))
    }

    private fun fetchCharacterPage(url: String): CharacterPage {
        val body = executeGet(url)
        val json = JSONObject(body)
        val info = json.getJSONObject("info")
        val next = if (info.isNull("next")) null else info.getString("next")
        val resultsJson = json.getJSONArray("results")
        val results = mutableListOf<CharacterInfo>()
        for (i in 0 until resultsJson.length()) {
            results.add(parseCharacter(resultsJson.getJSONObject(i)))
        }
        return CharacterPage(next = next, results = results)
    }

    private fun executeGet(url: String): String {
        var attempt = 0
        var lastError: Exception? = null

        while (attempt < 5) {
            attempt++
            val request = Request.Builder()
                .url(url)
                .get()
                .header("Accept", "application/json")
                .header("User-Agent", "RickAndMortyZad3/1.0 (Android; BAISM)")
                .header("Cache-Control", "no-cache")
                .build()

            client.newCall(request).execute().use { response ->
                if (response.code == 429) {
                    Thread.sleep(2000L * attempt)
                    lastError = IllegalStateException(
                        "Błąd HTTP 429 (zbyt wiele zapytań do API – poczekaj chwilę i spróbuj ponownie)"
                    )
                } else if (!response.isSuccessful) {
                    throw IllegalStateException("Błąd HTTP ${response.code}")
                } else {
                    return response.body?.string()
                        ?: throw IllegalStateException("Pusta odpowiedź serwera")
                }
            }
        }

        throw lastError ?: IllegalStateException("Błąd HTTP 429")
    }

    private fun parseCharacter(json: JSONObject): CharacterInfo {
        return CharacterInfo(
            id = json.getInt("id"),
            name = json.getString("name"),
            status = json.getString("status"),
            species = json.getString("species")
        )
    }
}

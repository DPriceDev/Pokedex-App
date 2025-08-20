package dev.dprice.pokemon.pokedex

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.InputStreamReader

class MockDispatcher: Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            "/pokemon" -> MockResponse()
                .setResponseCode(200)
                .setBody(getResourceAsText("pagedSummaryResponse.json"))
            "/pokemon/bulbasaur" -> MockResponse()
                .setResponseCode(200)
                .setBody(getResourceAsText("pokemonResponse.json"))
            else -> MockResponse().setResponseCode(400)
        }
    }

    private fun getResourceAsText(fileName: String): String {
        return InputStreamReader(this::class.java.getResourceAsStream(fileName)).use { it.readText() }
    }
}
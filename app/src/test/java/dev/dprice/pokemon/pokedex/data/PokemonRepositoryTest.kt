package dev.dprice.pokemon.pokedex.data

import dev.dprice.pokemon.pokedex.data.remote.PokemonApi
import dev.dprice.pokemon.pokedex.data.remote.PokemonResponse
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PokemonRepositoryTest {
    private lateinit var sut: RemotePokemonRepository

    private var error: Throwable? = null
    private val mockApi = mock<PokemonApi> {
        onBlocking { getPokemon(POKEMON_NAME) } doAnswer {
            error?.let { throw it }
            TEST_RESPONSE
        }
    }

    @Before
    fun setup() {
        sut = RemotePokemonRepository(mockApi)
    }

    @Test
    fun `given api success - when getPokemon - then pokemon returned`() = runTest {
        error = null

        val result = sut.getPokemon(POKEMON_NAME)

        assertEquals(TEST_POKEMON, result)
    }

    @Test
    fun `given http error - when getPokemon - then null returned`() = runTest {
        error = HttpException(Response.error<PokemonResponse>(404, "".toResponseBody()))

        val result = sut.getPokemon(POKEMON_NAME)

        assertNull(result)
    }

    @Test
    fun `given io error - when getPokemon - then null returned`() = runTest {
        error = IOException()

        val result = sut.getPokemon(POKEMON_NAME)

        assertNull(result)
    }

    companion object {
        private const val POKEMON_NAME = "pikachu"
        private val TEST_POKEMON = Pokemon(
            imageUrl = "https://example.com/image.png",
            stats = Pokemon.Stats(weight = 123)
        )
        private val TEST_RESPONSE = PokemonResponse(
            weight = 123,
            sprites = PokemonResponse.Sprites(frontDefault = "https://example.com/image.png")
        )
    }
}
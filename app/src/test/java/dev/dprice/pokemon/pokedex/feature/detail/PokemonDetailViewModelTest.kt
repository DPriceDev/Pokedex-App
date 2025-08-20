package dev.dprice.pokemon.pokedex.feature.detail

import androidx.lifecycle.SavedStateHandle
import dev.dprice.pokemon.pokedex.data.Pokemon
import dev.dprice.pokemon.pokedex.data.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import kotlin.test.Test
import kotlin.test.assertEquals

class PokemonDetailViewModelTest {
    private lateinit var sut: PokemonDetailViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    private var response: Pokemon? = TEST_POKEMON
    private val mockPokemonRepository = mock<PokemonRepository> {
        onBlocking { getPokemon(POKEMON_NAME) } doAnswer { response }
    }
    private val mockSavedStateHandle = mock<SavedStateHandle> {
        on { get<String>("name") } doReturn POKEMON_NAME
    }

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `when init - then name is fetched from saved state`() {
        sut = createViewModel()
        assertEquals(POKEMON_NAME, sut.name)
    }

    @Test
    fun `given api success - when init - then details are loaded`() {
        response = TEST_POKEMON
        sut = createViewModel()

        val expected = DetailState.Data(
            imageUrl = "https://example.com/image.png",
            stats = Pokemon.Stats(weight = 123)
        )
        assertEquals(expected, sut.details.value)
    }

    @Test
    fun `given api error - when init - then details are errored`() {
        response = null
        sut = createViewModel()

        val expected = DetailState.Error
        assertEquals(expected, sut.details.value)
    }

    @Test
    fun `given api error - when refresh - then details are loaded`() {
        response = null
        sut = createViewModel()

        response = TEST_POKEMON
        sut.refresh()

        val expected = DetailState.Data(
            imageUrl = "https://example.com/image.png",
            stats = Pokemon.Stats(weight = 123)
        )
        assertEquals(expected, sut.details.value)
    }

    private fun createViewModel() = PokemonDetailViewModel(mockPokemonRepository, mockSavedStateHandle)

    companion object {
        private const val POKEMON_NAME = "pikachu"
        private val TEST_POKEMON = Pokemon(
            imageUrl = "https://example.com/image.png",
            stats = Pokemon.Stats(weight = 123)
        )
    }
}
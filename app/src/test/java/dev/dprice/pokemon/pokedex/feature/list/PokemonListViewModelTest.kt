package dev.dprice.pokemon.pokedex.feature.list

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import dev.dprice.pokemon.pokedex.data.PokemonRepository
import dev.dprice.pokemon.pokedex.data.PokemonSummary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import kotlin.test.Test
import kotlin.test.assertEquals

class PokemonListViewModelTest {
    private lateinit var sut: PokemonListViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    private val mockRepository = mock<PokemonRepository> {
        on { getPokemonSummaries() } doReturn flowOf(PagingData.from(TEST_POKEMON))
    }

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        sut = PokemonListViewModel(mockRepository)
    }

    @Test
    fun `when paging data populated - then pokemon list is updated`() = runTest(testDispatcher) {
        val result = sut.pokemon.asSnapshot()

        assertEquals(TEST_POKEMON, result)
    }

    companion object {
        private val TEST_POKEMON = listOf(
            PokemonSummary("Bulbasaur"),
            PokemonSummary("Ivysaur"),
            PokemonSummary("Venusaur"),
        )
    }
}
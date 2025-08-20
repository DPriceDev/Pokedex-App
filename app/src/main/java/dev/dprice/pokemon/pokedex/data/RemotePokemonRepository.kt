package dev.dprice.pokemon.pokedex.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.dprice.pokemon.pokedex.data.mapper.toPokemon
import dev.dprice.pokemon.pokedex.data.mapper.toPokemonSummary
import dev.dprice.pokemon.pokedex.data.remote.PokemonApi
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class RemotePokemonRepository @Inject constructor(
    private val pokemonApi: PokemonApi,
): PokemonRepository {
    override fun getPokemonSummaries(): Flow<PagingData<PokemonSummary>> {
        val pager = Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PokemonListPagingSource(pokemonApi) }
        )

        return pager.flow
    }

    override suspend fun getPokemon(name: String): Pokemon? {
        return try {
            pokemonApi.getPokemon(name).toPokemon()
        } catch (error: IOException) {
            Timber.e(error)
            null
        } catch (error: HttpException) {
            Timber.e(error)
            null
        }
    }

    class PokemonListPagingSource(
        private val pokemonApi: PokemonApi,
    ) : PagingSource<Int, PokemonSummary>() {
        override fun getRefreshKey(state: PagingState<Int, PokemonSummary>): Int? {
            return state.anchorPosition
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonSummary> {
             return try {
                val offset = params.key ?: 0
                val result = pokemonApi.getPokemonSummaries(offset, params.loadSize)

                LoadResult.Page(
                    data = result.results.map { it.toPokemonSummary() },
                    prevKey = if (offset == 0) null else offset - params.loadSize,
                    nextKey = offset + params.loadSize
                )
            } catch (error: IOException) {
                Timber.e(error)
                LoadResult.Error(error)
            } catch (error: HttpException) {
                Timber.e(error)
                LoadResult.Error(error)
            }
        }

    }
}
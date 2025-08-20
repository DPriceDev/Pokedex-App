package dev.dprice.pokemon.pokedex.feature

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.dprice.pokemon.pokedex.MainActivity
import dev.dprice.pokemon.pokedex.MockDispatcher
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
@HiltAndroidTest
class PokemonJourneyTests {

    @get:Rule(order = 0)
    val rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        rule.inject()
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = MockDispatcher()
        mockWebServer.start(8080)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGivenAListOfPokemon_whenRowClicked_thenDetailDisplayed() {
        with(composeTestRule) {
            waitUntilExactlyOneExists(hasText("Bulbasaur"))

            onNode(hasText("Bulbasaur")).performClick()

            waitUntilExactlyOneExists(hasTestTag("detail_title"))

            onNode(hasTestTag("detail_title") and hasText("Bulbasaur")).assertExists()
        }





    }
}


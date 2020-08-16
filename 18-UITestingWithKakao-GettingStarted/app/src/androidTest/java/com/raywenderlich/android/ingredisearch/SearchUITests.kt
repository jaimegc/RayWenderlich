package com.raywenderlich.android.ingredisearch

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.intent.KIntent
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.raywenderlich.android.ingredisearch.recentIngredients.RecentIngredientsActivity
import com.raywenderlich.android.ingredisearch.search.SearchActivity
import com.raywenderlich.android.ingredisearch.searchResults.SearchResultsActivity
import org.junit.Rule
import org.junit.Test

// Kakao requires a class that inherits from Screen. In this Screen you add the views involved in
// the interactions of the tests.
class SearchScreen : Screen<SearchScreen>() {
    val searchButton = KButton { withId(R.id.searchButton) }
    val snackbar = KView {
        withId(com.google.android.material.R.id.snackbar_text)
    }
    val ingredients = KEditText { withId(R.id.ingredients) }
    val recentButton = KView { withId(R.id.recentButton) }
}

// This is not required but is recommended because you can run a group of tests with this annotation.
// In the Android ecosystem functional UI tests are considered large tests
@LargeTest
class SearchUITests {

    // This launches the activity before running each test
    @Rule
    @JvmField
    //var rule = ActivityTestRule(SearchActivity::class.java)
    // When doing intent testing you need to use IntentsTestRule, so replace the line where you
    // instantiated the ActivityTestRule
    var rule = IntentsTestRule(SearchActivity::class.java)

    private val screen = SearchScreen()

    @Test
    fun search_withEmptyText_shouldShowSnackbarError() {
        screen {
            searchButton.click()
            snackbar.isDisplayed()
        }
    }

    @Test
    fun search_withText_shouldNotShowSnackbarError() {
        screen {
            ingredients.typeText("eggs, ham, cheese")
            searchButton.click()
            // To make sure the Snackbar doesn’t show, a ViewAssertion
            snackbar.doesNotExist()
        }
    }

    @Test
    fun search_withText_shouldLaunchSearchResultsWithIntent() {
        screen {
            val query = "eggs, ham, cheese"
            ingredients.typeText(query)
            searchButton.click()

            val searchResultsIntent = KIntent {
                hasComponent(SearchResultsActivity::class.java.name)
                hasExtra("EXTRA_QUERY", query)
            }

            // Verifies the intent launches
            searchResultsIntent.intended()
        }
    }

    @Test
    fun choosingRecentIngredients_shouldSetCommaSeparatedIngredients() {
        screen {
            // Configures an Intent coming from RecentIngredientsActivity with a result containing
            // the Strings eggs and onion
            val recentIngredientsIntent = KIntent {
                hasComponent(RecentIngredientsActivity::class.java.name)
                withResult {
                    withCode(RESULT_OK)
                    withData(
                        Intent().putStringArrayListExtra(
                            RecentIngredientsActivity.EXTRA_INGREDIENTS_SELECTED,
                            ArrayList(listOf("eggs", "onion"))
                        )
                    )
                }
            }
            // Instructs Kakao to stub the intent you configured above
            recentIngredientsIntent.intending()

            recentButton.click()

            // Verifies the ingredients from the stubbed intent are set in the EditText
            ingredients.hasText("eggs,onion")
        }
    }

}

package com.raywenderlich.ingredisearch

import android.content.SharedPreferences
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test

class RepositoryTests {
    private lateinit var spyRepository: RecipeRepository
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    @Before
    fun setup() {
        sharedPreferences = mock()
        sharedPreferencesEditor = mock()
        whenever(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)

        // You’ll stub the RecipeRepository‘s getRecipes method but you also need to call the real
        // addFavorite method on the same object, instead of a mock you need a spy.
        spyRepository = spy(RecipeRepositoryImpl(sharedPreferences))
    }

    @Test
    fun addFavorite_withEmptyRecipes_savesJsonRecipe() {
        // Stub the getFavoriteRecipes() with an empty list. Notice that when stubbing spies you
        // need to use doReturn/whenever/method.
        doReturn(emptyList<Recipe>()).whenever(spyRepository).getFavoriteRecipes()

        // Call the real addFavorite method with a recipe
        val recipe = Recipe("id", "title", "imageUrl", "sourceUrl", false)
        spyRepository.addFavorite(recipe)

        // Check that the subsequent verifications are executed in the exact order
        inOrder(sharedPreferencesEditor) {
            // 4
            val jsonString = Gson().toJson(listOf(recipe))
            verify(sharedPreferencesEditor).putString(any(), eq(jsonString))
            verify(sharedPreferencesEditor).apply()
        }
    }
}
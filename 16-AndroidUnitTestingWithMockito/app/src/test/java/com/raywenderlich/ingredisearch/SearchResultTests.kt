package com.raywenderlich.ingredisearch

import com.nhaarman.mockito_kotlin.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SearchResultTests {

    private lateinit var repository: RecipeRepository
    private lateinit var presenter: SearchResultsPresenter
    private lateinit var view: SearchResultsPresenter.View

    @Before
    fun setup() {
        repository = mock()
        view = mock()
        presenter = SearchResultsPresenter(repository)
        presenter.attachView(view)
    }

    @Test
    fun search_callsShowLoading() {
        presenter.search("eggs")

        verify(view).showLoading()
    }

    @Test
    fun search_callsGetRecipes() {
        presenter.search("eggs")

        // The eq matcher is used to verify that it’s called with the same string
        verify(repository).getRecipes(eq("eggs"), any())
    }

    @Test
    fun search_withRepositoryHavingRecipes_callsShowRecipes() {
        val recipe = Recipe("id", "title", "imageUrl", "sourceUrl", false)
        val recipes = listOf(recipe)

        // Stub the repository getRecipes method to return these recipes
        doAnswer {
            val callback: RepositoryCallback<List<Recipe>> = it.getArgument(1)
            callback.onSuccess(recipes)
        }.whenever(repository).getRecipes(eq("eggs"), any())

        presenter.search("eggs")

        verify(view).showRecipes(eq(recipes))
        verify(view, never()).showError()
    }

    @Test
    fun search_withRepository_callsShowError() {
        // Stub the repository getRecipes method to return these recipes
        doAnswer {
            val callback: RepositoryCallback<List<Recipe>> = it.getArgument(1)
            callback.onError()
        }.whenever(repository).getRecipes(eq("eggs"), any())

        presenter.search("eggs")

        verify(view, never()).showRecipes(any())
        verify(view).showError()
    }

    // The following test is just a JUnit test:
    @Test
    fun addFavorite_shouldUpdateRecipeStatus() {
        val recipe = Recipe("id", "title", "imageUrl", "sourceUrl", false)

        presenter.addFavorite(recipe)

        Assert.assertTrue(recipe.isFavorited)
    }
}
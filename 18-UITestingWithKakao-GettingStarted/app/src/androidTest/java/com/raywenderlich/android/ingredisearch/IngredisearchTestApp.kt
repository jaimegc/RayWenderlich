package com.raywenderlich.android.ingredisearch

import com.raywenderlich.android.ingredisearch.common.Recipe
import com.raywenderlich.android.ingredisearch.repository.RecipeRepository
import com.raywenderlich.android.ingredisearch.repository.RepositoryCallback

/**
 * You’ll need to replace the existing recipe repository with a fake implementation to avoid
 * hitting the network in your tests. This implementation will always return the same results
 * which will allow you to have a stable test environment to avoid depending on a network
 * connection and an API returning different results
 */
class IngredisearchTestApp : IngredisearchApp() {

    override fun getRecipeRepository(): RecipeRepository {
        return object : RecipeRepository {
            override fun addFavorite(item: Recipe) {}
            override fun removeFavorite(item: Recipe) {}
            override fun getFavoriteRecipes() = emptyList<Recipe>()
            override fun saveRecentIngredients(query: String) {}

            override fun getRecipes(query: String,
                                    callback: RepositoryCallback<List<Recipe>>) {
                val list = listOf(
                        buildRecipe(1, false),
                        buildRecipe(2, true),
                        buildRecipe(3, false),
                        buildRecipe(4, false),
                        buildRecipe(5, false),
                        buildRecipe(6, false),
                        buildRecipe(7, false),
                        buildRecipe(8, false),
                        buildRecipe(9, false),
                        buildRecipe(10, false)
                )
                callback.onSuccess(list)
            }

            override fun getRecentIngredients() =
                    listOf("eggs", "ham", "onion", "tomato")
        }
    }

    private fun buildRecipe(id: Int, isFavorited: Boolean) =
            Recipe(id.toString(), "Title " + id.toString(), "", "", isFavorited)

}

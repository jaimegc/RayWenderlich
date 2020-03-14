/*
 * Copyright (c) 2019 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.rickycharacters.ui.views.fragments.classicalway

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.raywenderlich.android.rickycharacters.R
import com.raywenderlich.android.rickycharacters.data.models.Character
import com.raywenderlich.android.rickycharacters.data.models.CharactersResponseModel
import com.raywenderlich.android.rickycharacters.data.network.ApiClient
import com.raywenderlich.android.rickycharacters.data.network.ApiService
import com.raywenderlich.android.rickycharacters.ui.adapters.CharactersAdapter
import com.raywenderlich.android.rickycharacters.utils.hide
import com.raywenderlich.android.rickycharacters.utils.show
import kotlinx.android.synthetic.main.fragment_characters.*
import java.io.IOException

class CharactersFragment : Fragment(R.layout.fragment_characters) {
  private val apiService = ApiClient().getClient().create(ApiService::class.java)
  private lateinit var charactersAdapter: CharactersAdapter


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    charactersAdapter = CharactersAdapter { character -> displayCharacterDetails(character) }
    recyclerViewMovies.adapter = charactersAdapter
    fetchCharacters()
    showRefreshDialog()

    swipeContainer.setOnRefreshListener {
      fetchCharacters()
    }
  }

  private fun displayCharacterDetails(character: Character){
    //val characterFragmentAction =
        //CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailsFragment(character)
    //findNavController().navigate(characterFragmentAction)
  }

  private fun fetchCharacters() {
    lifecycleScope.launchWhenStarted {
      try {
        val response = apiService.getCharacters()
        val characters = response.body()

        if (response.isSuccessful) {
          if (response.body() != null) {
            hideEmptyView()
            showCharacters(characters)
          } else {
            showEmptyView()
            handleError("No characters found")
          }
        } else {
          showEmptyView()
          when(response.code()) {
            403 -> handleError("Access to resource is forbidden")
            404 -> handleError("Resource not found")
            500 -> handleError("Internal server error")
            502 -> handleError("Bad Gateway")
            301 -> handleError("Resource has been removed permanently")
            302 -> handleError("Resource moved, but has been found")
            else -> handleError("All cases have not been covered!!")
          }
        }
      } catch (error: IOException) {
        showEmptyView()
        error.message?.let { message -> handleError(message) }
      }
    }
  }

  private fun showCharacters(charactersResponseModel: CharactersResponseModel?) {
    charactersAdapter.updateList(charactersResponseModel!!.results)
  }

  private fun handleError(message : String){
    errorMessageText.text = message
  }

  private fun showEmptyView(){
    emptyViewLinear.show()
    recyclerViewMovies.hide()
    hideRefreshDialog()
  }

  private fun hideEmptyView(){
    emptyViewLinear.hide()
    recyclerViewMovies.show()
    hideRefreshDialog()
  }

  private fun showRefreshDialog(){
    swipeContainer.isRefreshing = true
  }

  private fun hideRefreshDialog(){
    swipeContainer.isRefreshing = false
  }

}
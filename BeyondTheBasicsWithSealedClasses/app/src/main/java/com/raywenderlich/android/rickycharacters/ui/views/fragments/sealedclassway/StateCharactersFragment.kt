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

package com.raywenderlich.android.rickycharacters.ui.views.fragments.sealedclassway

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
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

class StateCharactersFragment : Fragment(R.layout.fragment_characters) {
  private val apiService = ApiClient().getClient().create(ApiService::class.java)
  private lateinit var charactersAdapter: CharactersAdapter

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    charactersAdapter = CharactersAdapter { character ->
      displayCharacterDetails(character)
    }
    recyclerViewMovies.adapter = charactersAdapter

    getCharacters()
    swipeContainer.setOnRefreshListener {
      getCharacters()
    }
  }

  private fun getCharacters() {
    // TODO 8
  }

  // TODO 7

  private fun displayCharacterDetails(character: Character){
    val characterFragmentAction =
        StateCharactersFragmentDirections.actionStateCharactersFragmentToCharacterDetailsFragment(
            character)
    findNavController().navigate(characterFragmentAction)
  }

  private fun handleError(message : String){
    hideRefreshDialog()
    errorMessageText.text = message
  }

  private fun showCharacters(
      charactersResponseModel: CharactersResponseModel) {
    hideEmptyView()
    charactersAdapter.updateList(charactersResponseModel.results)
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
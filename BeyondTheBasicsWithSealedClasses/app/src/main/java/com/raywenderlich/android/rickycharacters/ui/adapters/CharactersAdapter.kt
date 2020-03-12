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

package com.raywenderlich.android.rickycharacters.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.raywenderlich.android.rickycharacters.R
import com.raywenderlich.android.rickycharacters.data.models.Character
import kotlinx.android.synthetic.main.list_item_character.view.*

typealias ClickListener = (Character) -> Unit

class CharactersAdapter(private val clickListener: ClickListener) : RecyclerView.Adapter<CharactersAdapter
.CharactersViewHolder>() {
  private var charactersList: List<Character> = ArrayList()

  class CharactersViewHolder(itemView: View, private val clickListener: ClickListener) :
      RecyclerView.ViewHolder(itemView) {
    private val imageViewCharacterImage: ImageView = itemView.imageViewCharacterImage
    private val textViewCharacterName: TextView = itemView.textViewCharacterName

    fun bindCharacters(character: Character){
      with(character){
        textViewCharacterName.text = name
        imageViewCharacterImage.load(image)
        itemView.setOnClickListener {
          clickListener(character)
        }
      }
    }

  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
    val itemView = LayoutInflater.from(parent.context)
        .inflate(R.layout.list_item_character, parent, false)
    return CharactersViewHolder(itemView, clickListener)
  }

  override fun getItemCount(): Int = charactersList.size

  override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
    holder.bindCharacters(charactersList[position])
  }
  fun updateList(characterList: List<Character>) {
    charactersList = characterList
    notifyDataSetChanged()
  }
}
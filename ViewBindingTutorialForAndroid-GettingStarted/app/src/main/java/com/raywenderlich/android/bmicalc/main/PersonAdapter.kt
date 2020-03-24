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

package com.raywenderlich.android.bmicalc.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.bmicalc.R
import com.raywenderlich.android.bmicalc.model.BMIState
import com.raywenderlich.android.bmicalc.model.Person
import com.raywenderlich.android.bmicalc.model.WeightLog
import com.raywenderlich.android.bmicalc.toFormattedString
import kotlinx.android.synthetic.main.item.view.*

class PersonAdapter(private val person: Person) : RecyclerView.Adapter<PersonAdapter.WeightViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeightViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
    return WeightViewHolder(itemView)
  }

  override fun getItemCount() = person.logs.size

  override fun onBindViewHolder(holder: WeightViewHolder, position: Int) {
    holder.bind(person.logs.sortedWith(compareByDescending { it.date.time })[position])
  }

  inner class WeightViewHolder(itemView: View) :
      RecyclerView.ViewHolder(itemView) {
    private val weightView = itemView.textView_weight
    private val bmiTextView = itemView.textView_bmi
    private val bmiView = itemView.view_bmi
    private val dateView = itemView.textView_date

    fun bind(weightLog: WeightLog) {
      weightView.text = weightView.context.getString(R.string.item_weight, weightLog.weight.toString())
      bmiTextView.text = bmiTextView.context.getString(R.string.item_bmi, person.bmi(weightLog).toFormattedString())
      bmiView.setBackgroundColor(getBmiColor(weightLog))
      dateView.text = weightLog.date.toFormattedString()
    }

    private fun getBmiColor(weightLog: WeightLog): Int {
      return when (person.bmiState(weightLog)) {
        BMIState.Underweight -> ContextCompat.getColor(itemView.context, R.color.colorBMIUnderweight)
        BMIState.Healthy -> ContextCompat.getColor(itemView.context, R.color.colorBMIHealthy)
        BMIState.Overweight -> ContextCompat.getColor(itemView.context, R.color.colorBMIOverweight)
        BMIState.Obese -> ContextCompat.getColor(itemView.context, R.color.colorBMIObese)
      }
    }
  }
}
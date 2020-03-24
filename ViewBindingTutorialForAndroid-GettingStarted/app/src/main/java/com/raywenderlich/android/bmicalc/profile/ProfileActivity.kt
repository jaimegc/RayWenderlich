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

package com.raywenderlich.android.bmicalc.profile

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.raywenderlich.android.bmicalc.BMIApplication
import com.raywenderlich.android.bmicalc.R
import com.raywenderlich.android.bmicalc.model.Person
import com.raywenderlich.android.bmicalc.profile.viewmodel.ProfileViewModel
import com.raywenderlich.android.bmicalc.profile.viewmodel.ProfileViewModelFactory
import com.raywenderlich.android.bmicalc.showDatePickerDialog
import com.raywenderlich.android.bmicalc.toFormattedString

class ProfileActivity : AppCompatActivity() {

  private lateinit var viewModel: ProfileViewModel

  private lateinit var editTextBirthdate: EditText
  private lateinit var editTextHeight: EditText
  private lateinit var fab: View

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val factory = ProfileViewModelFactory((application as BMIApplication).repository)
    viewModel = ViewModelProviders.of(this, factory)[ProfileViewModel::class.java]

    setContentView(R.layout.activity_profile)

    editTextBirthdate = findViewById(R.id.editText_birthdate)
    editTextHeight = findViewById(R.id.editText_height)
    fab = findViewById(R.id.fab)

    editTextBirthdate.setOnClickListener {
      showDatePickerDialog(editTextBirthdate)
    }

    viewModel.loadProfile()
    viewModel.person.observe(this, Observer(this::showProfile))
    viewModel.isSaved.observe(this, Observer(this::showSaved))
  }

  private fun showSaved(success: Boolean) {
    if (success) {
      setResult(Activity.RESULT_OK)
      finish()
    } else {
      Toast.makeText(this, getString(R.string.profile_invalid_inputs), Toast.LENGTH_LONG).show()
    }
  }

  private fun showProfile(person: Person?) {
    person?.let {
      editTextBirthdate.setText(it.birthdate.toFormattedString())
      editTextHeight.setText(it.height.toFormattedString())
    }

    fab.setOnClickListener {
      viewModel.saveProfile(editTextBirthdate.text.toString(), editTextHeight.text.toString())
    }
  }
}

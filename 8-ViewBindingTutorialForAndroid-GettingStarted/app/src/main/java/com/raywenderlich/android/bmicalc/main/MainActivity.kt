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

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.raywenderlich.android.bmicalc.BMIApplication
import com.raywenderlich.android.bmicalc.R
import com.raywenderlich.android.bmicalc.databinding.ActivityMainBinding
import com.raywenderlich.android.bmicalc.log.LogActivity
import com.raywenderlich.android.bmicalc.main.model.MainUIModel
import com.raywenderlich.android.bmicalc.model.Person
import com.raywenderlich.android.bmicalc.main.viewmodel.MainViewModel
import com.raywenderlich.android.bmicalc.main.viewmodel.MainViewModelFactory
import com.raywenderlich.android.bmicalc.profile.ProfileActivity

private const val ADD_LOG_REQUEST = 123
private const val PROFILE_REQUEST = 456

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var viewModel: MainViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    // Switch to AppTheme for displaying the activity
    setTheme(R.style.AppTheme)

    super.onCreate(savedInstanceState)

    val factory = MainViewModelFactory((application as BMIApplication).repository)
    viewModel = ViewModelProviders.of(this, factory)[MainViewModel::class.java]

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    viewModel.logs.observe(this, Observer(this::showLogs))

    viewModel.loadLogs()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return if (item.itemId == R.id.profile) {
      val intent = Intent(this, ProfileActivity::class.java)
      startActivityForResult(intent, PROFILE_REQUEST)
      true
    } else {
      super.onOptionsItemSelected(item)
    }
  }

  private fun showLogs(uiModel: MainUIModel) {
    when (uiModel) {
      is MainUIModel.ExistingProfile -> showExistingProfile(uiModel.person)
      is MainUIModel.MissingProfile -> showMissinProfile()
    }
  }

  private fun showMissinProfile() {
    binding.fab.visibility = View.GONE
    binding.textViewMissingProfile.visibility = View.VISIBLE
  }

  private fun showExistingProfile(person: Person) {
    binding.recyclerView.adapter = PersonAdapter(person)
    binding.recyclerView.scrollToPosition(0)
    binding.fab.setOnClickListener {
      val intent = LogActivity.newIntent(this, person)
      startActivityForResult(intent, ADD_LOG_REQUEST)
    }
    binding.fab.visibility = View.VISIBLE
    binding.textViewMissingProfile.visibility = View.GONE
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == ADD_LOG_REQUEST || requestCode == PROFILE_REQUEST) {
      if (resultCode == Activity.RESULT_OK) {
        viewModel.loadLogs()
      }
    } else {
      super.onActivityResult(requestCode, resultCode, data)
    }
  }
}

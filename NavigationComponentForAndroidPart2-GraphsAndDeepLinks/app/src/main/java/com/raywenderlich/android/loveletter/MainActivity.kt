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

package com.raywenderlich.android.loveletter

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.raywenderlich.android.loveletter.databinding.ActivityMainBinding
import com.raywenderlich.android.loveletter.databinding.NavHeaderMainBinding
import com.raywenderlich.android.loveletter.viewmodel.LettersViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

  private val navController by lazy { findNavController(R.id.nav_host_fragment) }
  private val appBarConfiguration by lazy {
    AppBarConfiguration(setOf(R.id.sentFragment, R.id.inboxFragment), drawerLayout)
  }

  private var lettersViewModel: LettersViewModel? = null
  private lateinit var headerBinding: NavHeaderMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupDataBinding()
    setSupportActionBar(toolbar)
    setupNavigation()
    setupViewModel()
    setupViews()
  }

  override fun onDestroy() {
    lettersViewModel?.apply { closeDb() }
    super.onDestroy()
  }

  private fun setupDataBinding() {
    val activityMainBinding =
      DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

    headerBinding = DataBindingUtil.inflate(
      layoutInflater, R.layout.nav_header_main, activityMainBinding.navView, false
    )
    headerBinding.ivEdit.setOnClickListener {
      // TODO: navigate to edit profile fragment

      drawerLayout.closeDrawer(GravityCompat.START)
    }
    activityMainBinding.navView.addHeaderView(headerBinding.root)
  }

  private fun setupNavigation() {
    NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
    NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)

    navController.addOnDestinationChangedListener { _, destination, _ ->
      if (destination.id in arrayListOf(
          R.id.createLetterFragment, R.id.presentLetter, R.id.editProfileFragment)
      ) {
        fab.hide()
      } else {
        if (destination.id == R.id.presentationFragment) {
          toolbar.visibility = View.GONE
        } else {
          toolbar.visibility = View.VISIBLE
        }
      }
    }
  }

  private fun setupViewModel() {
    // TODO: initialize lettersViewModel

    // TODO: assign lettersViewModel to headerBinding

    // TODO: load profile with lettersViewModel

  }

  private fun setupViews() {
    navView.setNavigationItemSelectedListener(this)

    fab.setOnClickListener {
      // TODO: navigate to create letter fragment

    }
  }

  override fun onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {

      R.id.nav_inbox -> {
        // TODO: navigate to inbox fragment
      }

      R.id.nav_sent -> {
        // TODO: navigate to sent fragment
      }

      R.id.nav_privacy_policy -> {
        // TODO: navigate to privacy policy fragment

      }

      R.id.nav_terms_of_service -> {
        // TODO: navigate to privacy terms of service fragment

      }
    }
    drawerLayout.closeDrawer(GravityCompat.START)
    return true
  }
}

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

package com.raywenderlich.android.bmicalc.log

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.raywenderlich.android.bmicalc.BMIApplication
import com.raywenderlich.android.bmicalc.databinding.ActivityLogBinding
import com.raywenderlich.android.bmicalc.log.viewmodel.LogViewModel
import com.raywenderlich.android.bmicalc.log.viewmodel.LogViewModelFactory
import com.raywenderlich.android.bmicalc.model.Person
import com.raywenderlich.android.bmicalc.showDatePickerDialog

class LogActivity : AppCompatActivity() {

  private lateinit var viewModel: LogViewModel

  companion object {
    private const val EXTRA_PERSON = "EXTRA_PERSON"

    fun newIntent(context: Context, person: Person): Intent {
      return Intent(context, LogActivity::class.java).apply {
        putExtra(EXTRA_PERSON, person)
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val person = intent.extras?.getParcelable<Person>(EXTRA_PERSON)!!
    val factory = LogViewModelFactory((application as BMIApplication).repository, person)
    viewModel = ViewModelProviders.of(this, factory)[LogViewModel::class.java]

    val binding = ActivityLogBinding.inflate(layoutInflater)
    binding.viewModel = viewModel
    binding.lifecycleOwner = this
    setContentView(binding.root)

    binding.editTextDate.setOnClickListener {
      showDatePickerDialog(binding.editTextDate)
    }

    binding.fab.setOnClickListener {
      viewModel.saveLog()
      setResult(Activity.RESULT_OK)
      finish()
    }
  }
}

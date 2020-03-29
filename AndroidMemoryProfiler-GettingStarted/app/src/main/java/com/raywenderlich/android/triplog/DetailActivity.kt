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

package com.raywenderlich.android.triplog

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import com.raywenderlich.android.triplog.model.TripLog
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.*

const val EXTRA_VIEW_LOG = "EXTRA_VIEW_LOG"
const val EXTRA_NEW_LOG = "EXTRA_NEW_LOG"

class DetailActivity : BaseActivity() {

  private val viewLog: TripLog? by lazy {
    intent.getParcelableExtra<TripLog>(EXTRA_VIEW_LOG)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detail)

    viewLog?.let {
      showLog(it)
    } ?: showNewLog()
  }

  private fun showNewLog() {
    textViewDate.text = dateFormatter.format(Date())
    textViewLocation.text = coordinatesFormatter.format(null)

    editTextLog.isEnabled = true
  }

  private fun showLog(log: TripLog) {
    editTextLog.setText(log.log)
    textViewDate.text = dateFormatter.format(log.date)
    textViewLocation.text = coordinatesFormatter.format(log.coordinates)

    editTextLog.isEnabled = false
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    if (viewLog == null) {
      menuInflater.inflate(R.menu.detail_menu, menu)
    }
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (R.id.save_log == item?.itemId) {
      newLog()
      return true
    }
    return super.onOptionsItemSelected(item)
  }

  private fun newLog() {
    val log = TripLog(editTextLog.text.toString(), Date(), null)

    val intent = Intent()
    intent.putExtra(EXTRA_NEW_LOG, log)
    setResult(Activity.RESULT_OK, intent)

    ActivityCompat.finishAfterTransition(this)
  }
}

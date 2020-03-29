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
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.raywenderlich.android.triplog.model.TripLog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_log_item.view.*
import java.util.*

private const val NEW_LOG_REQUEST = 249

class MainActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    fab.setOnClickListener {
      showNewLog()
    }
    buttonAddMultipleLogs.setOnClickListener {
      addMultipleLogs()
    }
  }

  private fun addMultipleLogs() {
    val size = repository.getLogs().size
    for (i in size..size + 99) {
      val randomLog = "This is a log: $i"
      val log = TripLog(randomLog, Date(), null)
      repository.addLog(log)
    }
    refreshLogs()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (R.id.delete_logs == item?.itemId) {
      repository.clearLogs()
      refreshLogs()
      return true
    }
    return super.onOptionsItemSelected(item)
  }

  private fun showNewLog() {
    val intent = Intent(this@MainActivity, DetailActivity::class.java)
    startActivityForResult(intent, NEW_LOG_REQUEST)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == NEW_LOG_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
        val log = data.getParcelableExtra<TripLog>(EXTRA_NEW_LOG)
        repository.addLog(log)
    } else {
      super.onActivityResult(requestCode, resultCode, data)
    }
  }

  override fun onResume() {
    super.onResume()

    refreshLogs()
  }

  private fun refreshLogs() {
    layoutContainer.removeAllViews()
    repository.getLogs().forEach { tripLog ->
      val child = inflateLogViewItem(tripLog)
      layoutContainer.addView(child)
    }
  }

  private fun inflateLogViewItem(tripLog: TripLog): View? {
    return layoutInflater.inflate(R.layout.view_log_item, null, false).apply {
      textViewLog.text = tripLog.log
      textViewDate.text = dateFormatter.format(tripLog.date)
      textViewLocation.text = coordinatesFormatter.format(tripLog.coordinates)
      setOnClickListener {
        showDetailLog(tripLog)
      }
    }
  }

  private fun showDetailLog(tripLog: TripLog) {
    val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
      putExtra(EXTRA_VIEW_LOG, tripLog)
    }
    startActivity(intent)
  }
}

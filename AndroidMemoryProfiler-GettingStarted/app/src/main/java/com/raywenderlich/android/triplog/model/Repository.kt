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

package com.raywenderlich.android.triplog.model

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface Repository {
  fun getLogs(): List<TripLog>
  fun addLog(log: TripLog)
  fun clearLogs()
}

private const val LOGS_KEY = "LOGS_KEY"

class SharedPreferencesRepositoryImpl(private val sharedPreferences: SharedPreferences) : Repository {

  private val gson = Gson()

  override fun getLogs(): List<TripLog> {
    val logs = sharedPreferences.getString(LOGS_KEY, null)
    if (logs != null) {
      return gson.fromJson(logs)
    }
    return emptyList()
  }

  override fun addLog(log: TripLog) {
    val logs = getLogs() + log
    saveLogs(logs)
  }

  private fun saveLogs(logs: List<TripLog>) {
    val editor = sharedPreferences.edit()
    editor.putString(LOGS_KEY, gson.toJson(logs))
    editor.apply()
  }

  override fun clearLogs() {
    val editor = sharedPreferences.edit()
    editor.clear()
    editor.apply()
  }

  private inline fun <reified T> Gson.fromJson(json: String): T =
      this.fromJson<T>(json, object : TypeToken<T>() {}.type)
}


class InMemoryRepositoryImpl : Repository {

  private val _logs = mutableListOf<TripLog>()

  override fun getLogs(): List<TripLog> {
    return _logs
  }

  override fun addLog(log: TripLog) {
    _logs.add(log)
  }

  override fun clearLogs() {
    _logs.clear()
  }
}
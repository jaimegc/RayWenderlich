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

import android.app.Application
import com.raywenderlich.android.triplog.model.InMemoryRepositoryImpl
import com.raywenderlich.android.triplog.model.Repository
import com.raywenderlich.android.triplog.utils.CoordinatesFormatter
import com.raywenderlich.android.triplog.utils.CoordinatesFormatterImpl
import com.raywenderlich.android.triplog.utils.DateFormatter
import com.raywenderlich.android.triplog.utils.DateFormatterImpl

class MainApplication: Application() {

  val repository: Repository by lazy {
    //SharedPreferencesRepositoryImpl(getSharedPreferences("TRIP_LOG_REPO", Context.MODE_PRIVATE))
    InMemoryRepositoryImpl()
  }

  val dateFormatter: DateFormatter by lazy {
    DateFormatterImpl()
  }

  val coordinatesFormatter: CoordinatesFormatter by lazy {
    CoordinatesFormatterImpl(applicationContext)
  }
}
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

package com.raywenderlich.android.bmicalc

import android.app.DatePickerDialog
import android.widget.EditText
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private val df = SimpleDateFormat("yyyy-MM-dd", Locale.US)

fun String.toDateOrToday(): Date = try {
  df.parse(this) ?: Date()
} catch (e: ParseException) {
  Date()
}

fun String.toCalendar(): Calendar = Calendar.getInstance().apply {
  time = toDateOrToday()
}

fun Date.toFormattedString(): String = df.format(this)

fun Calendar.toFormattedString(): String = df.format(time)

fun showDatePickerDialog(editText: EditText) {
  val listener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
    val cal = Calendar.getInstance().apply {
      set(Calendar.YEAR, year)
      set(Calendar.MONTH, monthOfYear)
      set(Calendar.DAY_OF_MONTH, dayOfMonth)
    }
    editText.setText(cal.toFormattedString())
  }

  val cal = editText.text.toString().toCalendar()
  DatePickerDialog(
      editText.context,
      listener,
      cal.get(Calendar.YEAR),
      cal.get(Calendar.MONTH),
      cal.get(Calendar.DAY_OF_MONTH)
  ).show()
}

fun String.toFloatOrZero(): Float {
  return toFloatOrNull() ?: 0f
}

fun Float.toFormattedString() = "%.2f".format(Locale.US, this)
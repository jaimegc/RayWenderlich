package com.raywenderlich.android.triplog.utils

import java.text.SimpleDateFormat
import java.util.*

interface DateFormatter {
  fun format(date: Date): String
}

class DateFormatterImpl : DateFormatter {

  private val simpleFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

  override fun format(date: Date): String = simpleFormat.format(date)

}
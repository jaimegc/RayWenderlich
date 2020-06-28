package com.raywenderlich.android.triplog

import android.app.Application
import android.content.Context
import com.raywenderlich.android.triplog.model.InMemoryRepositoryImpl
import com.raywenderlich.android.triplog.model.Repository
import com.raywenderlich.android.triplog.model.SharedPreferencesRepositoryImpl
import com.raywenderlich.android.triplog.utils.CoordinatesFormatter
import com.raywenderlich.android.triplog.utils.CoordinatesFormatterImpl
import com.raywenderlich.android.triplog.utils.DateFormatter
import com.raywenderlich.android.triplog.utils.DateFormatterImpl

class MainApplication: Application() {

  val repository: Repository by lazy {
    SharedPreferencesRepositoryImpl(getSharedPreferences("TRIP_LOG_REPO", Context.MODE_PRIVATE))
  }

  val dateFormatter: DateFormatter by lazy {
    DateFormatterImpl()
  }

  val coordinatesFormatter: CoordinatesFormatter by lazy {
    CoordinatesFormatterImpl(applicationContext)
  }
}
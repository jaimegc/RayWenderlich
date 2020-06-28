package com.raywenderlich.android.triplog

import androidx.appcompat.app.AppCompatActivity
import com.raywenderlich.android.triplog.model.Repository
import com.raywenderlich.android.triplog.utils.CoordinatesFormatter
import com.raywenderlich.android.triplog.utils.DateFormatter

abstract class BaseActivity: AppCompatActivity() {

  val repository: Repository by lazy {
    (application as MainApplication).repository
  }

  val dateFormatter: DateFormatter by lazy {
    (application as MainApplication).dateFormatter
  }

  val coordinatesFormatter: CoordinatesFormatter by lazy {
    (application as MainApplication).coordinatesFormatter
  }
}
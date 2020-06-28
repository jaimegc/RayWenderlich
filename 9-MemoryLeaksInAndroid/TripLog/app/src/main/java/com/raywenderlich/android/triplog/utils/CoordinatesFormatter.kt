package com.raywenderlich.android.triplog.utils

import android.content.Context
import com.raywenderlich.android.triplog.R
import com.raywenderlich.android.triplog.model.Coordinates

interface CoordinatesFormatter {
  fun format(coordinates: Coordinates?): String
}

class CoordinatesFormatterImpl(private val context: Context): CoordinatesFormatter {
  override fun format(coordinates: Coordinates?): String {
    if (coordinates == null) {
      return context.getString(R.string.location_unknown)
    }

    return context.getString(R.string.location_known, coordinates.latitude.toString(), coordinates.longitude.toString())
  }
}
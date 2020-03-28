package com.raywenderlich.android.triplog.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class TripLog(val log: String, val date: Date, val coordinates: Coordinates? = null, val happyMood: Boolean = true) : Parcelable

@Parcelize
data class Coordinates(val latitude: Double, val longitude: Double) : Parcelable
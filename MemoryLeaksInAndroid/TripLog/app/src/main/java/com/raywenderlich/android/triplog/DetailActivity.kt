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

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.raywenderlich.android.triplog.model.TripLog
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.*
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.raywenderlich.android.triplog.model.Coordinates


const val EXTRA_VIEW_LOG = "EXTRA_VIEW_LOG"
const val EXTRA_NEW_LOG = "EXTRA_NEW_LOG"

private const val LOCATION_REQUEST = 992

class DetailActivity : BaseActivity() {

  private val viewLog: TripLog? by lazy {
    intent.getParcelableExtra<TripLog>(EXTRA_VIEW_LOG)
  }

  private lateinit var fusedLocationClient: FusedLocationProviderClient
  private lateinit var locationRequest: LocationRequest
  private lateinit var locationCallback: LocationCallback

  private var currentCoordinates: Coordinates? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detail)

    viewLog?.let {
      showLog(it)
    } ?: showNewLog()

    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    locationRequest = LocationRequest.create().apply {
      priority = LocationRequest.PRIORITY_HIGH_ACCURACY
      interval = 5000
    }
    locationCallback = object : LocationCallback() {
      override fun onLocationResult(locationResult: LocationResult?) {
        if (locationResult == null) {
          return
        }
        val location = locationResult.lastLocation
        currentCoordinates = Coordinates(location.latitude, location.longitude)
        textViewLocation.text = coordinatesFormatter.format(currentCoordinates)
      }
    }
  }

  override fun onResume() {
    super.onResume()
    if (viewLog == null) {
      startLocationUpdates()
    }
  }

  private fun startLocationUpdates() {
    if (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
      ActivityCompat.requestPermissions(
          this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST
      )
    } else {
      doRequestLocationUpdates()
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if (requestCode == LOCATION_REQUEST) {
      if (grantResults.isNotEmpty()
          && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        doRequestLocationUpdates()
      }
    }
  }

  @SuppressLint("MissingPermission")
  private fun doRequestLocationUpdates() {
    fusedLocationClient.requestLocationUpdates(
        locationRequest,
        locationCallback,
        null
    )
  }

  private fun showNewLog() {
    textViewDate.text = dateFormatter.format(Date())
    textViewLocation.text = coordinatesFormatter.format(currentCoordinates)

    editTextLog.isEnabled = true
    toggleButtonMood.isEnabled = true
  }

  private fun showLog(log: TripLog) {
    editTextLog.setText(log.log)
    textViewDate.text = dateFormatter.format(log.date)
    textViewLocation.text = coordinatesFormatter.format(log.coordinates)
    toggleButtonMood.isChecked = log.happyMood

    editTextLog.isEnabled = false
    toggleButtonMood.isEnabled = false
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
    val log = TripLog(editTextLog.text.toString(), Date(), currentCoordinates, toggleButtonMood.isChecked)

    val intent = Intent()
    intent.putExtra(EXTRA_NEW_LOG, log)
    setResult(Activity.RESULT_OK, intent)

    ActivityCompat.finishAfterTransition(this)
  }
}

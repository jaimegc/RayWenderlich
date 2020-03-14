package com.raywenderlich.android.studeo.data

class RecordingStateData : StudeoData() {
  companion object {
    const val STARTED = 0
    const val PAUSED = 1
    const val STOPPED = 2
  }
}
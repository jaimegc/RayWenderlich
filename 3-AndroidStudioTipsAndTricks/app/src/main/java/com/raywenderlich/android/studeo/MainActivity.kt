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

package com.raywenderlich.android.studeo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.raywenderlich.android.studeo.data.LightData
import com.raywenderlich.android.studeo.data.RecordingStateData
import com.raywenderlich.android.studeo.data.SoundData
import com.raywenderlich.android.studeo.data.StudeoData
import kotlin.system.measureTimeMillis

/**
 * Main Screen
 */
class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    printHelloStudeo()

    printSoundAndLightCheck()

    printRecordingSelection()
  }

  fun printHelloStudeo() {
    val finalString = concatStrings("Hello", "Studeo")
    printInformation(finalString)
  }

  private fun printRecordingSelection() {
    // Option + enter: quick fix
    var selection = RecordingStateData.STARTED
    if (selection == RecordingStateData.STARTED) {
      printRecordingState("Started")

      // CMD + Backspace on below line to delete the line
      printRecordingState("Started")

    } else if (selection == RecordingStateData.PAUSED) {

      // Shift + Option + Up: this line to move line up
      // Shift + Option + Down: this line to move line down

      printRecordingState("Paused")

    } else if (selection == RecordingStateData.STOPPED) {

      // CMD + D on below line to duplicate line
      printRecordingState("Stopped")
    }
  }

  private fun printSoundAndLightCheck() {
    val studeoData = StudeoData()
    val soundData = SoundData()
    val lightData = LightData()

    val soundCheckPassed = studeoData.soundCheck(soundData)
    val lightCheckPassed = studeoData.lightCheck(lightData)

    val printStr = "Sound check passed: $soundCheckPassed\nLight check passed: $lightCheckPassed"

    printInformation(printStr)
  }

  // Shift + CMD + Up: move method up
  // Shift + CMD + Down: move method down
  // Shift + F6: rename method/variable name
  // CMD + F6: change method signature
  private fun concatStrings(str1: String, str2: String): String {
    val finalString = "$str1 $str2"
    return finalString
  }


  // Option + CMD + N: inline
  private fun printRecordingState(state: String) {
    printInformation("Recording " + state)
  }

  // Ctrl + T: open refactoring menu
  private fun printInformation(dataStr: String) {
    // Option + CMD + V: extract variable
    // Option + CMD + M: extract method
    // Option + CMD + P: extract method parameter
    val TAG = "Studeo Log"
    printLog(TAG)

    measureTimeMillis {
      Log.d(TAG, dataStr)
    }

    Log.d(TAG, ">> printInformation() finished")
  }

  private fun printLog(TAG: String) {
    Log.d(TAG, ">> printInformation() called")
  }

  // F6 : Move class
  class DiscoBall {

    var numberOfColors = 5
  }
}

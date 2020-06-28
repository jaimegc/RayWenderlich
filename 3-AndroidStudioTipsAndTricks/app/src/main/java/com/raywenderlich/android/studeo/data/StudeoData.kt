package com.raywenderlich.android.studeo.data

open class StudeoData {
  var name: String = "Studeo"
  var description: String = "Recording Studio equipped with light and sound processing"

  fun soundCheck(soundData: SoundData): Boolean {
    if (soundData.numberOfSoundsInLibrary > 4) {
      if (soundData.typesOfSound.equals("Acoustic")) {
        return true
      }
    }
    return false
  }

  fun lightCheck(lightData: LightData): Boolean {
    if (lightData.numberOfLight > 4 && lightData.typeOfLight.equals("Standard")) {
      return true
    } else {
      return false
    }
  }
}
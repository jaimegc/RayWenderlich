package com.raywenderlich.android.triplog.model

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface Repository {
  fun getLogs(): List<TripLog>
  fun addLog(log: TripLog)
  fun clearLogs()
}

private const val LOGS_KEY = "LOGS_KEY"

class SharedPreferencesRepositoryImpl(private val sharedPreferences: SharedPreferences) : Repository {

  private val gson = Gson()
  private var _logs: List<TripLog>? = null

  override fun getLogs(): List<TripLog> {
    if (_logs == null) {
      _logs = getLogsFromSharedPreferences()
    }
    return _logs.orEmpty()
  }

  private fun getLogsFromSharedPreferences(): List<TripLog> {
    val logs = sharedPreferences.getString(LOGS_KEY, null)
    if (logs != null) {
      return gson.fromJson(logs)
    }
    return mutableListOf()
  }

  override fun addLog(log: TripLog) {
    _logs = getLogs() + log
    saveLogs(_logs!!)
  }

  override fun clearLogs() {
    _logs = mutableListOf()
    saveLogs(_logs!!)
  }

  private fun saveLogs(logs: List<TripLog>) {
    val editor = sharedPreferences.edit()
    if (logs.isEmpty()) {
      editor.clear()
    } else {
      editor.putString(LOGS_KEY, gson.toJson(logs))
    }
    editor.apply()
  }

  private inline fun <reified T> Gson.fromJson(json: String): T =
      this.fromJson<T>(json, object : TypeToken<T>() {}.type)
}


class InMemoryRepositoryImpl : Repository {

  private val _logs = mutableListOf<TripLog>()

  override fun getLogs(): List<TripLog> {
    return _logs
  }

  override fun addLog(log: TripLog) {
    _logs.add(log)
  }

  override fun clearLogs() {
    _logs.clear()
  }
}
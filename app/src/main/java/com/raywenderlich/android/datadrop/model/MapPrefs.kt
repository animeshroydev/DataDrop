package com.raywenderlich.android.datadrop.model

import android.preference.PreferenceManager
import com.raywenderlich.android.datadrop.app.DataDropApplication

object MapPrefs {

    // key (used to save color in map marker)
    private const val KEY_MARKER_COLOR = "KEY_MARKER_COLOR"
    private const val KEY_MAP_TYPE = "KEY_MAP_TYPE"

    // Access default preference manager (Default Shared Prefs)
    fun sharedPrefs() = PreferenceManager.getDefaultSharedPreferences(DataDropApplication.getAppContext())

    // SAVE THE MARKER COLOR INTO SHARED PREFS
    fun saveMarkerColor(markerColor: String) {
        val editor = sharedPrefs().edit()
        editor.putString(KEY_MARKER_COLOR, markerColor).apply()
    }
    // GET THE MARKER COLOR INTO SHARED PREFS
    // If the marker color not saved the default value gonna be RED
    fun getMarkerColor(): String = sharedPrefs().getString(KEY_MARKER_COLOR, "Red")

    // 2nd step is in map ? MapContract add method in interface

    // challenge
    fun saveMapType(mapType: String) {
        val editor = sharedPrefs().edit()
        editor.putString(KEY_MAP_TYPE, mapType).apply()
    }

    fun getMapType(): String = sharedPrefs().getString(KEY_MAP_TYPE, "Normal")
}
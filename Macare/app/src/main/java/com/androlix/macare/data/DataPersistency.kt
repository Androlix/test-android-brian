package com.androlix.macare.data

import android.content.Context
import android.preference.PreferenceManager
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object DataPersistency {

    private const val DATA_KEY = "data_key"

    fun getData(context: Context): ArrayList<GlycemicLevelEntry> {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val json = sharedPreferences.getString(DATA_KEY, "")
        return if (json.isNullOrEmpty())
            arrayListOf()
        else {
            val type = object : TypeToken<List<GlycemicLevelEntry>>() {}
            Gson().fromJson<ArrayList<GlycemicLevelEntry>>(json, type.type)
        }
    }

    fun saveData(context: Context, list: List<GlycemicLevelEntry>) {
        PreferenceManager.getDefaultSharedPreferences(context).edit {
            putString(DATA_KEY, Gson().toJson(list))
            apply()
        }
    }
}
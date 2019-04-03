package com.androlix.macare.data

import android.content.Context
import androidx.lifecycle.MutableLiveData

object Repository {


    val data: MutableLiveData<ArrayList<GlycemicLevelEntry>> = MutableLiveData()

    fun initializeRepository(context: Context) {
        data.value = DataPersistency.getData(context)
    }

    fun addNewGlycemicLevelEntry(entry: GlycemicLevelEntry) {
        data.value?.add(entry)
        triggerObserver()
    }

    fun saveDataList(context: Context) {
        DataPersistency.saveData(context, data.value!!)
    }

    fun triggerObserver() {
        data.value = data.value
    }
}
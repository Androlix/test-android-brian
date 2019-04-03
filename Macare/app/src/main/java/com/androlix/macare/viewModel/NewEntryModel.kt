package com.androlix.macare.viewModel

import androidx.lifecycle.ViewModel;
import com.androlix.macare.data.GlycemicLevelEntry
import com.androlix.macare.data.Repository

class NewEntryModel : ViewModel() {

    val entry = GlycemicLevelEntry.newInstance()
    fun addNewEntry() {
        Repository.addNewGlycemicLevelEntry(entry)
    }
}

package com.androlix.macare.viewModel

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel;
import com.androlix.macare.data.GlycemicLevelEntry
import com.androlix.macare.data.Repository

class ListViewModel : ViewModel() {

    var query = ""

    val data = Transformations.map(Repository.data) { rawData ->
        val filteredData = filterData(rawData)
        filteredData.sortedBy { it.timestamp }
    }

    fun onQueryChanged() {
        Repository.triggerObserver()
    }

    private fun filterData(data: List<GlycemicLevelEntry>): List<GlycemicLevelEntry> {
        if (query.isEmpty())
            return data
        return data.filter {
            it.day.toString().contains(query) ||
                    it.month.toString().contains(query) ||
                    it.year.toString().contains(query) ||
                    it.level.toString().contains(query)
        }
    }
}

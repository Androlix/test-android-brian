package com.androlix.macare.viewModel

import androidx.lifecycle.ViewModel;
import com.androlix.macare.data.Repository

class GraphViewModel : ViewModel() {
    val data = Repository.data
}

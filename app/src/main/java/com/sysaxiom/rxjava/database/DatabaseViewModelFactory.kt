package com.sysaxiom.mvvmbasics.ui.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sysaxiom.coroutines.util.PingRepository

class DatabaseViewModelFactory(
    private val repository: PingRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DatabaseViewModel(repository) as T
    }
}
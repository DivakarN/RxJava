package com.sysaxiom.mvvmbasics.ui.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sysaxiom.coroutines.util.PingRepository
import com.sysaxiom.rxjava.database.db.Ping
import com.sysaxiom.rxjava.util.Data
import com.sysaxiom.rxjava.util.PingResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DatabaseViewModel(
    private val repository: PingRepository
) : ViewModel() {

    fun upsertPing() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertPing(Ping(1,true,"Hello"))
        }
    }

    fun getPing() = repository.getPing()

}
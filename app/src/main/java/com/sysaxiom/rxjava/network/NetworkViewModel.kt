package com.sysaxiom.mvvmbasics.ui.room

import androidx.lifecycle.ViewModel
import com.sysaxiom.coroutines.util.PingRepository
import com.sysaxiom.rxjava.util.PingResponse
import io.reactivex.Single

class NetworkViewModel(
    private val repository: PingRepository
) : ViewModel() {

    fun pingResponse(): Single<PingResponse> {
        return repository.pingNetwork()
    }

}
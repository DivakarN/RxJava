package com.sysaxiom.coroutines.util

import com.sysaxiom.mvvmbasics.data.db.RxJavaDatabase
import com.sysaxiom.rxjava.database.db.Ping
import com.sysaxiom.rxjava.util.NetworkApis
import com.sysaxiom.rxjava.util.PingResponse
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class PingRepository (val networkApis: NetworkApis, private val db: RxJavaDatabase) : SafeApiRequest(),CoroutineScope {

    val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    var compositeDisposable = CompositeDisposable()

    suspend fun ping(): PingResponse {
        return apiRequest { networkApis.ping() }
    }

    fun pingNetwork(): Single<PingResponse> {
        return networkApis.pingNetwork()
    }

    suspend fun insertPing(ping: Ping) {
        db.getPingDao().upsertPing(ping)
    }

    fun getPing(): Maybe<List<Ping>> {
        return db.getPingDao().getPing()
    }

}
package com.sysaxiom.rxjava.util

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface NetworkApis {

    @GET("ping")
    fun pingSimple() : Call<PingResponse>

    @GET("ping")
    fun pingCallWithouSuspend() : Call<PingResponse>

    @GET("ping")
    fun ping() : Response<PingResponse>

    @GET("ping")
    fun pingNetwork() : Single<PingResponse>

    @GET("ping")
    fun pingNetworkObservable() : Observable<PingResponse>
}
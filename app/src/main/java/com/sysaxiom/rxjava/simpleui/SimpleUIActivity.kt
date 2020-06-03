package com.sysaxiom.rxjava.simpleui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sysaxiom.coroutines.util.NetworkConnectionInterceptor
import com.sysaxiom.rxjava.util.PingResponse
import com.sysaxiom.coroutines.util.RetrofitHandler
import com.sysaxiom.rxjava.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class SimpleUIActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_ui)

        pingServer()
    }

    private fun pingServer(){
        loadPostAsSingle().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ pingResponse ->
                println(pingResponse)
            }, {error ->
                println(error)
            })
    }

    private fun loadPostAsSingle() : Single<PingResponse> {
        return Single.create { observer ->
            Thread.sleep(1000)
            RetrofitHandler(NetworkConnectionInterceptor(this)).pingSimple().enqueue(object :
                Callback<PingResponse> {
                override fun onFailure(call: Call<PingResponse>, t: Throwable) {
                    observer.onError(IOException("Unknown Network Exception"))
                }

                override fun onResponse(call: Call<PingResponse>, response: Response<PingResponse>) {
                    if(response.body()!=null){
                        observer.onSuccess(response.body())
                    } else{
                        observer.onError(IOException("Unknown Network Exception"))
                    }
                }
            })
        }
    }
}

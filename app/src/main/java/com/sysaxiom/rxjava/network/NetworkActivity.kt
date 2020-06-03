package com.sysaxiom.rxjava.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.sysaxiom.mvvmbasics.ui.room.NetworkViewModel
import com.sysaxiom.mvvmbasics.ui.room.NetworkViewModelFactory
import com.sysaxiom.rxjava.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class NetworkActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val factory : NetworkViewModelFactory by instance()

    lateinit var viewModel: NetworkViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)

        viewModel = ViewModelProviders.of(this, factory).get(NetworkViewModel::class.java)

        viewModel.pingResponse()
            .subscribeOn(Schedulers.io())
            .subscribe({ pingResponse ->
                println(pingResponse)
            },{error ->
                println(error)
            })

    }
}

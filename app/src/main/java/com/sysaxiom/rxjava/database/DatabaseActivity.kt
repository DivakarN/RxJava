package com.sysaxiom.rxjava.database

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.sysaxiom.mvvmbasics.ui.room.DatabaseViewModel
import com.sysaxiom.mvvmbasics.ui.room.DatabaseViewModelFactory
import com.sysaxiom.rxjava.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class DatabaseActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val factory : DatabaseViewModelFactory by instance()

    lateinit var viewModel: DatabaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        viewModel = ViewModelProviders.of(this, factory).get(DatabaseViewModel::class.java)

        viewModel.upsertPing()

        viewModel.getPing()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({ pingResponse ->
                println(pingResponse)
            },{error ->
                println(error)
            },{
                println("completed")
            })
    }

}

package com.sysaxiom.mvvmbasics.application

import android.app.Application
import android.system.Os.bind
import com.sysaxiom.coroutines.util.NetworkConnectionInterceptor
import com.sysaxiom.coroutines.util.PingRepository
import com.sysaxiom.coroutines.util.RetrofitHandler
import com.sysaxiom.mvvmbasics.data.db.RxJavaDatabase
import com.sysaxiom.mvvmbasics.ui.room.DatabaseViewModelFactory
import com.sysaxiom.mvvmbasics.ui.room.NetworkViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class CoroutinesApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@CoroutinesApplication))
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { RetrofitHandler(instance()) }
        bind() from singleton { RxJavaDatabase(instance()) }
        bind() from singleton { PingRepository(instance(),instance()) }

        bind() from provider { DatabaseViewModelFactory(instance()) }
        bind() from provider { NetworkViewModelFactory(instance()) }

    }

}
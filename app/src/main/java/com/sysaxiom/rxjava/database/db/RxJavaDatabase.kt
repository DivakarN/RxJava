package com.sysaxiom.mvvmbasics.data.db

import android.content.Context
import android.service.autofill.UserData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sysaxiom.rxjava.database.db.Ping
import com.sysaxiom.rxjava.util.Data
import com.sysaxiom.rxjava.util.PingResponse

@Database(
    entities = [Ping::class],
    version = 1,
    exportSchema = false
)
abstract class RxJavaDatabase: RoomDatabase() {

    abstract fun getPingDao(): PingDao

    companion object {
        @Volatile
        private var instance: RxJavaDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
            instance
                ?: createDatabase(
                    context
                ).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                RxJavaDatabase::class.java, "RxJavaDB.db").build()
    }
}

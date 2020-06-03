package com.sysaxiom.mvvmbasics.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sysaxiom.rxjava.database.db.Ping
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface PingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPing(ping: Ping)

    @Query("SELECT * FROM ping")
    fun getPing(): Maybe<List<Ping>>
}

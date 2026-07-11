package com.jerlendds.moblab.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Flowable

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY login")
    fun observeUsers(): Flowable<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertUsers(users: List<UserEntity>)

    @Query("DELETE FROM users")
    fun clearUsers()
}

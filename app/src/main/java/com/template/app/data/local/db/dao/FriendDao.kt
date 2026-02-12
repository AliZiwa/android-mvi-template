package com.template.app.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.template.app.data.local.db.entity.FriendEntity

@Dao
interface FriendDao {

    @Query("SELECT * FROM friends")
    suspend fun getAllFriends(): List<FriendEntity>

    @Query("SELECT * FROM friends WHERE id = :friendId")
    suspend fun getFriendById(friendId: String): FriendEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriends(friends: List<FriendEntity>)

    @Query("DELETE FROM friends")
    suspend fun deleteAll()
}

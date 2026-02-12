package com.template.app.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.template.app.data.local.db.dao.FriendDao
import com.template.app.data.local.db.dao.UserDao
import com.template.app.data.local.db.entity.FriendEntity
import com.template.app.data.local.db.entity.UserEntity

@Database(
    entities = [UserEntity::class, FriendEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun friendDao(): FriendDao
}

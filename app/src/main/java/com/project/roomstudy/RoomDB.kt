package com.project.roomstudy

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MainData::class], version = 1)
abstract class RoomDB : RoomDatabase() {

    companion object {

        private var instance: RoomDB? = null
        fun getInstance(_context: Context): RoomDB? {
            if(instance == null) {
                synchronized(RoomDB::class) {
                    instance = Room.databaseBuilder(_context.applicationContext,
                        RoomDB::class.java, "RoomStudy.db")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance
        }
    }
    abstract fun mainDao(): MainDao
}
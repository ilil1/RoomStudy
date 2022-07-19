package com.project.roomstudy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RoomStudy")
data class MainData(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "text") val text: String?
)
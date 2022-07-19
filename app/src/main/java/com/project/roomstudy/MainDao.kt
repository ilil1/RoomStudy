package com.project.roomstudy

import androidx.room.*

@Dao
interface MainDao {

    @Query("UPDATE RoomStudy SET text = :sText WHERE ID = :sID")
    fun update(sID: Int?, sText: String?)

    @Query("SELECT * FROM RoomStudy")
    fun getAll(): MutableList<MainData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mainData: MainData)

    @Delete
    fun delete(mainData: MainData)

    @Delete
    fun reset(mainData: MutableList<MainData>)
}
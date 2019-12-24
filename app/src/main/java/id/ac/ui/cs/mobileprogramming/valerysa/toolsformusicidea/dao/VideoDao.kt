package id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.VideoEntity
import kotlin.collections.ArrayList

@Dao
interface VideoDao {
    @Query("SELECT * from video")
    fun getAll(): LiveData<List<VideoEntity>>

    @Insert(onConflict = REPLACE)
    fun insert(videoEntity: VideoEntity)

    @Delete
    fun delete(videoEntity: VideoEntity)
}
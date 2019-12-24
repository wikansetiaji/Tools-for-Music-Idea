package id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.AudioEntity
import kotlin.collections.ArrayList

@Dao
interface AudioDao {
    @Query("SELECT * from audio")
    fun getAll(): LiveData<List<AudioEntity>>

    @Insert(onConflict = REPLACE)
    fun insert(audioEntity: AudioEntity)

    @Delete
    fun delete(audioEntity: AudioEntity)
}
package id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.dao.VideoDao
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.VideoEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.tools.Converters

@Database(entities = arrayOf(VideoEntity::class), version = 1)
@TypeConverters(Converters::class)
abstract class VideoDatabase : RoomDatabase() {

    abstract fun videoDao(): VideoDao
    companion object {
        private var INSTANCE: VideoDatabase? = null

        fun getInstance(context: Context): VideoDatabase? {
            if (INSTANCE == null) {
                synchronized(VideoDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        VideoDatabase::class.java, "videodata.db")
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}

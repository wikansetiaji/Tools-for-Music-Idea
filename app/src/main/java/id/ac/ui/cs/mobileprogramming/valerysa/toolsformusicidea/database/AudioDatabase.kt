package id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.dao.AudioDao
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.AudioEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.tools.Converters

@Database(entities = arrayOf(AudioEntity::class), version = 1)
@TypeConverters(Converters::class)
abstract class AudioDatabase : RoomDatabase() {

    abstract fun audioDao(): AudioDao
    companion object {
        private var INSTANCE: AudioDatabase? = null

        fun getInstance(context: Context): AudioDatabase? {
            if (INSTANCE == null) {
                synchronized(AudioDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AudioDatabase::class.java, "audiodata.db")
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

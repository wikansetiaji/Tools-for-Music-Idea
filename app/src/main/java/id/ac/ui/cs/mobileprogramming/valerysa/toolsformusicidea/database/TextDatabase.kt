package id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.dao.TextDao
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.TextEntity
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.tools.Converters

@Database(entities = arrayOf(TextEntity::class), version = 1)
@TypeConverters(Converters::class)
abstract class TextDatabase : RoomDatabase() {

    abstract fun textDao(): TextDao
    companion object {
        private var INSTANCE: TextDatabase? = null

        fun getInstance(context: Context): TextDatabase? {
            if (INSTANCE == null) {
                synchronized(TextDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        TextDatabase::class.java, "studentdata.db")
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

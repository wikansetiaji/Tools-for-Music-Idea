package id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "audio")
data class AudioEntity(
    @ColumnInfo(name = "name")var name: String,
    @ColumnInfo(name="timestamp")var timestamp: Date,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long = 0
) : ItemEntity {
    override fun getType(): Int {
        return 1
    }

    override fun getTimeStamp(): Date {
        return timestamp
    }
}
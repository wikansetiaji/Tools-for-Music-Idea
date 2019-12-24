package id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.ac.ui.cs.mobileprogramming.valerysa.toolsformusicidea.model.ItemEntity
import java.util.*

@Entity(tableName = "texts")
data class TextEntity(
    @ColumnInfo(name = "text")var text: String,
    @ColumnInfo(name="timestamp")var timestamp: Date,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long = 0
) : ItemEntity {
    override fun getType(): Int {
        return 0
    }

    override fun getTimeStamp(): Date {
        return timestamp
    }
}
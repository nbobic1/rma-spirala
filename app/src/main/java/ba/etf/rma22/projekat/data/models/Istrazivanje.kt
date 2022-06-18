package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
@Entity
data class Istrazivanje(
    @PrimaryKey @SerializedName("id")val id:Int,
    @SerializedName("naziv")var naziv: String,
    @SerializedName("godina") val godina:Int):Serializable

{}
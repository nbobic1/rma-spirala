package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
@Entity
data class Grupa(
    @PrimaryKey @SerializedName("id")val id:Int,
     @SerializedName("naziv")var naziv: String,
    var upisan:Boolean=false,
    var istrazivanjeId:Int
):Serializable
{}


package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity
data class Odgovor(
    @PrimaryKey @SerializedName("id") var id:Int,
     @SerializedName("odgovoreno")  var odgovoreno:Int,
     @SerializedName("PitanjeId")  var pitanjeId:Int,
var anketaid:Int)
{}
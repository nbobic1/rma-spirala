package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity
data class Opcije(@PrimaryKey @SerializedName("id") var id:Int, @SerializedName("opcije") var opcije:List<String>)
{}
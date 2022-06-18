package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*
@Entity
data class Anketa  (
 @PrimaryKey @SerializedName("id") var id: Int,
  @SerializedName("naziv")var naziv: String,
  var nazivIstrazivanja: String?,
@SerializedName("datumPocetak") var datumPocetak: Date?,
  @SerializedName("datumKraj") var datumKraj: Date?,
  var datumRada: Date?,
   @SerializedName("trajanje") var trajanje: Int,
  var nazivGrupe: String?,
 var progres: Float?,
 @SerializedName("message")  var message:String?,
 var upisan:Boolean=false,
 var grupaId:Int
)
{}
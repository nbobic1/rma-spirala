package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Anketa  (
 @SerializedName("id") var id: Int,
 @SerializedName("naziv")var naziv: String,
 var nazivIstrazivanja: String,
 @SerializedName("datumPocetak") var datumPocetak: Date,
 @SerializedName("datumKraj") var datumKraj: Date,
 var datumRada: Date?,
 @SerializedName("trajanje") var trajanje: Int,
 var nazivGrupe: String,
 var progres: Float,@SerializedName("message")  var message:String?
):Serializable

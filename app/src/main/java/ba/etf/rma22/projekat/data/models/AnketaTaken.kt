package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class AnketaTaken (@SerializedName("id") var id:Int, @SerializedName("progres")var progres:Int,
                        @SerializedName("student")var student:String, @SerializedName("datumRada")var datumRada: Date?,
                        @SerializedName("AnketumId")var AnketumId:Int, @SerializedName("message" )var message: String)
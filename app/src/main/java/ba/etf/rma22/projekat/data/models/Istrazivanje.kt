package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Istrazivanje( @SerializedName("id")val id:Int, @SerializedName("naziv")var naziv: String, @SerializedName("godina") val godina:Int ):Serializable

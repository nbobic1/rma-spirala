package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName

data class Pos(@SerializedName("odgovor") var odgovor: Int,@SerializedName("pitanje") var pitanje: Int,@SerializedName("progres") var progres: Int)

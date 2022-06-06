package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName

data class Odgovor(@SerializedName("id") var id:Int,@SerializedName("odgovoreno")  var odgovoreno:Int)

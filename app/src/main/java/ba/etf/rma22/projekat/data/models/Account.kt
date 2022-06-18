package ba.etf.rma22.projekat.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account(@PrimaryKey var id:Int,var acHash:String)

package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.grupe
import java.io.Serializable

object GrupaRepository : Serializable {
    fun getGroupsByIstrazivanjet(nazivIstrazivanja:String) : List<Grupa>{
        return grupe()//.filter { grupa ->grupa.nazivIstrazivanja==nazivIstrazivanja  }
    }
}
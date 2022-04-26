package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.models.Pitanje

object PitanjeAnketaRepository {
    fun getPitanja(nazivAnkete: String, nazivIstrazivanja: String):List<Pitanje>
    {
        return listOf(Pitanje("pitanje","obala Une Anketa", listOf("a","b","c"))
        ,Pitanje("pitanje1","anketa", listOf("a","b","c")),
            Pitanje("pitanje2","anketa1", listOf("a","b","c")))
    }
}
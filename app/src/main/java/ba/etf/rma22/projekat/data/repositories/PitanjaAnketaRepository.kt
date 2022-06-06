package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.models.Pitanje

object PitanjaAnketaRepository {
    fun getPitanja(nazivAnkete: String, nazivIstrazivanja: String):List<Pitanje>
    {
        return listOf(Pitanje(1,"pitanje","obala Une Anketa", listOf("a","b","c"))
        ,Pitanje(1,"pitanje1","anketa", listOf("a","b","c")),
            Pitanje(1,"pitanje2","anketa1", listOf("a","b","c")))
    }
}
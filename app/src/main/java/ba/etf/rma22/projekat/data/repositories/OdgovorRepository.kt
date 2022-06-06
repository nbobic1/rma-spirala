package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.ApiAdapter
import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.models.Pos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

object OdgovorRepository {
    suspend fun getOdgovoriAnketa(idAnkete:Int):List<Odgovor>
    {

        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getOdgovoriAnketa(AccountRepository.getHash(),idAnkete)

            val responseBody = response.body()
            if (responseBody==null)
            {
                println("odgovori null")
                return@withContext listOf()
            }
                return@withContext responseBody!!
        }
    }
    suspend fun postaviOdgovorAnketa(idAnketaTaken:Int,idPitanje:Int,odgovor:Int):Int
    {
        return withContext(Dispatchers.IO) {
            var progres=0
          var t= ApiAdapter.retrofit.getPoceteAnkete(AccountRepository.getHash()).body()
                ?.toMutableList()
            var we= t?.last{ i->i.id==idAnketaTaken }
            var z:List<Pitanje>?= listOf<Pitanje>()
            println("gagalgjalkgjagadagdggdgdg")
            if(we!=null) {
                println("ioaaaaaaaaaaaa")
                z = ApiAdapter.retrofit.getPitanja(we.AnketumId).body()
            }
            if(z!=null) {
                var gk=1+z.indexOf(z.last { i->i.id==idPitanje })
                var k: Double = kotlin.math.round((gk.toDouble() / z.size.toDouble()) / 0.2)

                k= Math.round(k).toDouble()
                println("k=${k}")
                progres = k.roundToInt() * 20
                println("goagioagaagga ${gk} ${z.size} ${progres} ${kotlin.math.round(2.5)} ${kotlin.math.round(2.51)}")
            }
                var response = ApiAdapter.retrofit.postaviOdgovorAnketa(AccountRepository.getHash(),idAnketaTaken,Pos(odgovor,idPitanje,progres))

        val responseBody = response.body()

        if (responseBody==null) {
            return@withContext -1
        }
        return@withContext progres
    }
    }
}
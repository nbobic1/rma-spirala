package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.ApiAdapter
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object IstrazivanjeIGrupaRepository {
    suspend fun getIstrazivanja(offset:Int):List<Istrazivanje>
    {
      return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getIstrazivanja(offset)
            val responseBody = response.body()
            return@withContext responseBody!!
        }
    }
    suspend fun getIstrazivanja():List<Istrazivanje>
    {
        return withContext(Dispatchers.IO) {
           var i=1
            var t= mutableListOf<Istrazivanje>()
            while(true)
            {
                val result =getIstrazivanja(i)
                if(result.size==0)
                    break
                i++
                t.addAll(result)
            }
            return@withContext t
        }
    }
    suspend fun getIstrazivanjaZaGrupu(idGrupa: Int):Istrazivanje
    {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getIstrazivanjaZaGrupu(idGrupa)
            val responseBody = response.body()
            return@withContext responseBody!!
        }
    }
    suspend fun getGrupe():List<Grupa>
    {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getGrupe()
            val responseBody = response.body()
            return@withContext responseBody!!
        }
    }
    suspend fun getGrupeZaIstrazivanje(idIstrazivanja:Int):List<Grupa>
    {
        var k=getGrupe().toMutableList()
        var t= mutableListOf<Grupa>()
        for(i in k)
        {
            var z=getIstrazivanjaZaGrupu(i.id)

                if(z.id==idIstrazivanja)
                {
                    t.add(i)
                    break
                }

        }
        return withContext(Dispatchers.IO) {
            return@withContext t
        }
    }
    suspend fun getGrupeZaA(idIstrazivanja:Int):List<Grupa>
    {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getGrupaZaA(idIstrazivanja)
            val responseBody = response.body()
            return@withContext responseBody!!
        }
    }
    suspend fun upisiUGrupu(idGrupa:Int):Boolean
    {

        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.upisiUGrupu(idGrupa,AccountRepository.getHash())
            val responseBody = response.body()
            if (responseBody != null) {
                return@withContext responseBody.message.contains("je dodan u grupu")
            }
            return@withContext false
        }
    }
    suspend  fun getUpisaneGrupe():List<Grupa>
    {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getUpisaneGrupe(AccountRepository.getHash())
            val responseBody = response.body()
            return@withContext responseBody!!
        }
    }
}
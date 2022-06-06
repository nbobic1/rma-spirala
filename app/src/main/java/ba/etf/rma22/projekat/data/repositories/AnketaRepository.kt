package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.ApiAdapter
import ba.etf.rma22.projekat.Korisnik
import ba.etf.rma22.projekat.ankete
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.korisnikA
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

object AnketaRepository {
    suspend fun getById(id:Int):Anketa?
    {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getById(id)

            val responseBody = response.body()

            if(responseBody!=null&&responseBody.message!=null)
                return@withContext null
            return@withContext responseBody
        }
    }
    suspend fun getAnketeGrupe(id:Int):List<Anketa>
    {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getAnketeGrupe(id)
            val responseBody = response.body()
            return@withContext responseBody!!
        }
    }
    suspend fun getUpisane():List<Anketa>
    {
        return withContext(Dispatchers.IO) {
            var u = IstrazivanjeIGrupaRepository.getUpisaneGrupe()
            var t = mutableListOf<Anketa>()
            for(i in u)
            {
                t.addAll(getAnketeGrupe(i.id))
            }
            return@withContext t
        }
    }
    suspend fun getAll(offset:Int):List<Anketa>
    {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getAll(offset)
            val responseBody = response.body()
            return@withContext responseBody!!
        }
    }
    suspend fun getAll():List<Anketa>
    {
        return withContext(Dispatchers.IO) {
            var t= mutableListOf<Anketa>()
            var i=1
            while(true)
            {
                var k= getAll(i)
                   if(k.size==0)
                    break
                i++
                t.addAll(k)
            }
            return@withContext t
        }
    }
    /*
    suspend fun getAll():List<Anketa>
    {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getAll()
            val responseBody = response.body()
            return@withContext responseBody!!
        }
    }*/
    //agkaghkačjgčagkjakčgjačgkkčgagjkagjag
    var korisnik : Korisnik = Korisnik()
    fun getMyAnkete() : List<Anketa>
    {
        var k= korisnik.getA().toMutableList()
        for( t in korisnik.getG())
        {
            k.addAll(AnketaRepository.getAll1().filter { anketa->anketa.nazivGrupe==t.naziv })
        }
        return k.distinct()
    }
    fun getAll1(): List<Anketa>
    {
        return ankete()
    }

    fun getDone(): List<Anketa>{
            return getMyAnkete().filter { anketa -> anketa.progres.compareTo(1.0)==0 }
    }
    fun getFuture(): List<Anketa>{
        var cal: Calendar = Calendar.getInstance()
        var datum: Date = cal.time;
        return getMyAnkete().filter { anketa ->anketa.datumPocetak>datum}
    }
    fun getNotTaken(): List<Anketa>{
        var cal: Calendar = Calendar.getInstance()
        var datum: Date = cal.time;
        return getMyAnkete().filter { anketa ->anketa.progres.compareTo(1.0)!=0&&anketa.datumKraj<datum  }
    }

}
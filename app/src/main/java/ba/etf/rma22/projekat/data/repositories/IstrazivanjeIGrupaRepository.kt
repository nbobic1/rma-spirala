package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import android.util.Log
import ba.etf.rma22.projekat.ApiAdapter
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.data.dao.AppDatabase
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object IstrazivanjeIGrupaRepository {
    var context: Context?=null
    suspend fun dbIstrazivanja(anke:List<Istrazivanje>)
    {
        return withContext(Dispatchers.IO) {
            if (context != null) {
                var db = AppDatabase.getInstance(context!!)

                for (i in anke) {
                    db.istrazivanjeDao().insertAll(i);
                }
            }
        }
    }
    suspend fun dbGrupe(anke:List<Grupa>)
    {
        return withContext(Dispatchers.IO) {
            if (context != null) {
                var db = AppDatabase.getInstance(context!!)

                for (i in anke) {
                    db.grupaDao().insertAll(i);
                }
            }
        }
    }
    suspend fun getIstrazivanja(offset:Int):List<Istrazivanje>
    {
      return withContext(Dispatchers.IO) {

          if(!MainActivity.connection)
          {
              if(AnketaRepository.context !=null) {
                  var db = AppDatabase.getInstance(AnketaRepository.context!!)
                  var tut=db.istrazivanjeDao().getIstrazivanje(offset)
                  return@withContext tut
              }
              else
                  return@withContext listOf()
          }
          else{
            var response = ApiAdapter.retrofit.getIstrazivanja(offset)
            val responseBody = response.body()
              if(responseBody!=null)
              {
                  dbIstrazivanja(responseBody)
              }
            return@withContext responseBody!!
          }
      }

    }
    suspend fun getIstrazivanja():List<Istrazivanje>
    {
        return withContext(Dispatchers.IO) {
            if(!MainActivity.connection)
            {
                if(context !=null) {
                    var db = AppDatabase.getInstance(context!!)
                    var tut=db.istrazivanjeDao().getIstrazivanje()
                    return@withContext tut
                }
                else
                    return@withContext listOf()
            }
            else {
                var i = 1
                var t = mutableListOf<Istrazivanje>()
                while (true) {
                    val result = getIstrazivanja(i)
                    if (result.size == 0)
                        break
                    i++
                    t.addAll(result)
                }
                dbIstrazivanja(t)
                return@withContext t
            }
        }
    }
    //prepraviti
    suspend fun getIstrazivanjaZaGrupu(idGrupa: Int):Istrazivanje
    {
        return withContext(Dispatchers.IO) {
            if(!MainActivity.connection)
            {
                if(AnketaRepository.context !=null) {
                    var db = AppDatabase.getInstance(AnketaRepository.context!!)
                    var tut=db.istrazivanjeDao().getIstrazivanjeZaGrupu(idGrupa)
                    return@withContext tut
                }
                else
                    return@withContext Istrazivanje(0,"",0)
            }
            else {
                var response = ApiAdapter.retrofit.getIstrazivanjaZaGrupu(idGrupa)
                val responseBody = response.body()
                return@withContext responseBody!!
            }
        }
    }
    suspend fun getGrupe():List<Grupa>
    {
        return withContext(Dispatchers.IO) {
            if(!MainActivity.connection)
            {
                    var tut=listOf<Grupa>()
                if(context!=null)
                {
                    var db = AppDatabase.getInstance(AnketaRepository.context!!)
                    var tut=db.grupaDao().getGrupe()

                }
                return@withContext tut
            }
            else
            {
            var response = ApiAdapter.retrofit.getGrupe()
            val responseBody = response.body()
                if(responseBody!=null)
                dbGrupe(responseBody)
                return@withContext responseBody!!
        }
        }
    }
    //dodraditi
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
    //doraditi
    suspend fun getGrupeZaA(idIstrazivanja:Int):List<Grupa>
    {
        return withContext(Dispatchers.IO) {
            if(!MainActivity.connection)
            {
                var tut=listOf<Grupa>()
                if(context!=null)
                {

                var db = AppDatabase.getInstance(AnketaRepository.context!!)
                 tut=db.grupaDao().getGrupeAnketa(idIstrazivanja)
                }
                return@withContext tut
            }
            else{

            var response = ApiAdapter.retrofit.getGrupaZaA(idIstrazivanja)
            val responseBody = response.body()
            return@withContext responseBody!!
            }
        }
    }
    suspend fun upisiUGrupu(idGrupa:Int):Boolean
    {
        if(MainActivity.connection)
        return withContext(Dispatchers.IO) {
            Log.i("upisane","upisane")
            var response = ApiAdapter.retrofit.upisiUGrupu(idGrupa,AccountRepository.getHash())
            val responseBody = response.body()
            if (responseBody != null) {
                if(responseBody.message.contains("je dodan u grupu"))
                    getUpisaneGrupe()
                return@withContext responseBody.message.contains("je dodan u grupu")
            }
            Log.i("upisane","upisane####")
            return@withContext false
        }
        else {
            Log.i("upisane","upisane!!!!")
            return false
        }
    }
    suspend  fun getUpisaneGrupe():List<Grupa>
    {
        return withContext(Dispatchers.IO) {
            if(!MainActivity.connection)
            {
                var tut=listOf<Grupa>()
                if(context!=null)
                {
                    var db = AppDatabase.getInstance(AnketaRepository.context!!)
                     tut=db.grupaDao().getGrupeUpisane()
                }
                return@withContext tut
            }
            else {
                var response = ApiAdapter.retrofit.getUpisaneGrupe(AccountRepository.getHash())
                val responseBody = response.body()
                if(responseBody!=null) {
                    responseBody.map { er->er.upisan=true }
                    dbGrupe(responseBody)
                }
                return@withContext responseBody!!
            }
        }
    }
}
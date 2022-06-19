package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import android.util.Log
import ba.etf.rma22.projekat.*
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.dao.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AnketaRepository {
    var context:Context?=null
    suspend fun dbAnkete(anke:List<Anketa>)
    {
        return withContext(Dispatchers.IO) {
            if (context != null) {
                var db = AppDatabase.getInstance(context!!)

                for (i in anke) {
                    println("insertam anketeeeeeeeeee")
                    db.anketaDao().insertAll(i);
                }
            }
        }
    }
    suspend fun getById(id:Int):Anketa?
    {
        return withContext(Dispatchers.IO) {
            if(!MainActivity.connection)
            {
                if(context!=null) {
                    var db = AppDatabase.getInstance(context!!)
                    var tut=db.anketaDao().getById(id)
                    return@withContext  tut
                }
                else
                return@withContext null
            }
            else{

                var response = ApiAdapter.retrofit.getById(id)

                val responseBody = response.body()

                if(responseBody!=null&&responseBody.message!=null)
                    return@withContext null

                if(responseBody!=null) {
                    var zui:List<Anketa> = listOf(responseBody)
                    dbAnkete(zui)
                }
                return@withContext responseBody
            }
        }
    }
    suspend fun getAnketeGrupe(id:Int):List<Anketa>
    {
        return withContext(Dispatchers.IO) {
            if(!MainActivity.connection)
            {
                if(context!=null) {
                    var db = AppDatabase.getInstance(context!!)
                    var tut=db.anketaDao().getAnketeGrupe(id)
                    return@withContext  tut
                }
                else return@withContext listOf()
            }

            else {
                var response = ApiAdapter.retrofit.getAnketeGrupe(id)
                val responseBody = response.body()
                if(responseBody!=null)
                {
                    responseBody.map { er->er.grupaId=id }
                    dbAnkete(responseBody)
                }
                return@withContext responseBody!!
            }
        }
    }
    suspend fun getUpisane():List<Anketa>
    {
      return withContext(Dispatchers.IO) {
           if(!MainActivity.connection)
            {
                if(context!=null) {
                   var db = AppDatabase.getInstance(context!!)
                    var tut=db.anketaDao().getUpisane()
                   return@withContext  tut
                }
            }
            else {
            var u = IstrazivanjeIGrupaRepository.getUpisaneGrupe()
            var t = mutableListOf<Anketa>()
            for(i in u)
            {
                t.addAll(getAnketeGrupe(i.id))
            }
               var zui=t.map { it.copy() }
               for(i in zui)
               {
                   i.upisan=true
               }
               dbAnkete(zui)
               for(i in t)
               {
                   i.grupaId=0
               }
                   return@withContext t
            }

        return@withContext listOf<Anketa>()
        }
    }

    suspend fun getAll(offset:Int):List<Anketa>
    {
        return withContext(Dispatchers.IO) {
            if(!MainActivity.connection)
            {
                if(context!=null) {
                    var db = AppDatabase.getInstance(context!!)
                    var tut=db.anketaDao().getAllO(offset)
                    return@withContext  tut
                }
                else return@withContext listOf()
            }
            else
            {

            var response = ApiAdapter.retrofit.getAll(offset)
            val responseBody = response.body()
                if(responseBody!=null)
                    dbAnkete(responseBody)
            return@withContext responseBody!!
            }
        }
    }
    suspend fun getAll():List<Anketa>
    {
        return withContext(Dispatchers.IO) {
            if(!MainActivity.connection)
            {
                if(context!=null) {
                    var db = AppDatabase.getInstance(context!!)
                    var tut=db.anketaDao().getAll()
                    return@withContext  tut
                }
                else return@withContext listOf()
            }
            else {
                var t = mutableListOf<Anketa>()
                var i = 1
                while (true) {
                    var k = getAll(i)
                    if (k.size == 0)
                        break
                    i++
                    t.addAll(k)
                }
                    dbAnkete(t)
                    return@withContext t
            }
        }
    }


}
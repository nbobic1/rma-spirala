package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import ba.etf.rma22.projekat.ApiAdapter
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.data.dao.AppDatabase
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object TakeAnketaRepository {
    var context: Context?=null
    suspend fun dbAnketeTaken(anke:List<AnketaTaken>)
    {
        return withContext(Dispatchers.IO) {
            if (context != null) {
                var db = AppDatabase.getInstance(context!!)

                for (i in anke) {
                    println("insertam anketeeeeeeeeee")
                    db.anketaTakenDao().insertAll(i);
                }
            }
        }
    }
    suspend fun zapocniAnketu(idAnkete:Int):AnketaTaken?
    {
        return withContext(Dispatchers.IO) {
            if(MainActivity.connection){


            var response = ApiAdapter.retrofit.zapocniAnketu(AccountRepository.getHash(),idAnkete)
            val responseBody = response.body()
          if(responseBody!=null&&responseBody.message!=null)
            return@withContext null
                getPoceteAnkete()
            return@withContext responseBody!!
            }
            return@withContext null
        }
    }


    suspend  fun getPoceteAnkete():List<AnketaTaken>?
    {
        return withContext(Dispatchers.IO) {
            if(!MainActivity.connection)
            {
                if(AnketaRepository.context !=null) {
                    var db = AppDatabase.getInstance(AnketaRepository.context!!)
                    var tut=db.anketaTakenDao().getAnkete()
                    return@withContext  tut
                }
                else
                    return@withContext null
            }
            else {
                var response = ApiAdapter.retrofit.getPoceteAnkete(AccountRepository.getHash())
                val responseBody = response.body()
                if (responseBody == null || responseBody.size == 0)
                    return@withContext null
                dbAnketeTaken(responseBody)
                return@withContext responseBody
            }
        }
    }
}
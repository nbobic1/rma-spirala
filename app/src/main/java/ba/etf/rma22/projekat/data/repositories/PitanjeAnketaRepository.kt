package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import ba.etf.rma22.projekat.ApiAdapter
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.data.dao.AppDatabase
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Pitanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


object PitanjeAnketaRepository {
    var context: Context?=null
    suspend fun dbAnkete(anke:List<Pitanje>)
    {
        return withContext(Dispatchers.IO) {
            if (context != null) {
                var db = AppDatabase.getInstance(context!!)

                for (i in anke) {
                    db.pitanjeDao().insertAll(i);
                }
            }
        }
    }
    suspend fun getPitanja(id:Int
    ) : List<Pitanje>{
        return withContext(Dispatchers.IO) {
            if(!MainActivity.connection)
            {
                if(AnketaRepository.context !=null) {
                    var db = AppDatabase.getInstance(AnketaRepository.context!!)
                    var tut=db.pitanjeDao().getById(id)
                    return@withContext  tut
                }
                else
                    return@withContext listOf()
            }
            else {
                var response = ApiAdapter.retrofit.getPitanja(id)
                val responseBody = response.body()
                if (responseBody != null) {
                    responseBody.map { er -> er.anketaid = id }
                    dbAnkete(responseBody)
                }
                return@withContext responseBody!!
            }
        }
    }

}
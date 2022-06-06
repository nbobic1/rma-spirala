package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.ApiAdapter
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

    suspend fun getPitanja(id:Int
    ) : List<Pitanje>{
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getPitanja(id)
            val responseBody = response.body()
            return@withContext responseBody!!
        }
    }

}
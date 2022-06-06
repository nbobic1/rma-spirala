package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.ApiAdapter
import ba.etf.rma22.projekat.data.models.AnketaTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

object TakeAnketaRepository {
    suspend fun zapocniAnketu(idAnkete:Int):AnketaTaken?
    {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.zapocniAnketu(AccountRepository.getHash(),idAnkete)
            val responseBody = response.body()
          if(responseBody!=null&&responseBody.message!=null)
            return@withContext null
                return@withContext responseBody!!
        }
    }


    suspend  fun getPoceteAnkete():List<AnketaTaken>?
    {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getPoceteAnkete(AccountRepository.getHash())
            val responseBody = response.body()
            if(responseBody==null||responseBody.size==0)
                return@withContext null
            return@withContext responseBody
        }
    }
}
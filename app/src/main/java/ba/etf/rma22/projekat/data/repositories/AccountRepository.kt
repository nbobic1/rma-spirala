package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.dao.AppDatabase
import ba.etf.rma22.projekat.data.models.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AccountRepository {
     var acHash:String ="bd382302-7595-4f36-977c-b4f9f5cafb64"
   suspend fun postaviHash(acHash1:String):Boolean

   {
       return withContext(Dispatchers.IO){
           if(AnketaRepository.context !=null) {
               var db = AppDatabase.getInstance(AnketaRepository.context!!)
               db.accountDao().deleteAll()
                   db.accountDao().insertAll(Account(1,acHash1))
           }
        var k= acHash
        acHash =acHash1
        return@withContext k==acHash1
         }
   }
    fun getHash():String
    {
        return acHash
    }
}
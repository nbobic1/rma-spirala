package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.repositories.AccountRepository
import kotlinx.coroutines.*

class AccountViewModel {
    val scope = CoroutineScope(Job() + Dispatchers.IO)
    fun setHash(str:String){
        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
            AccountRepository.postaviHash(str)
        }
    }
}
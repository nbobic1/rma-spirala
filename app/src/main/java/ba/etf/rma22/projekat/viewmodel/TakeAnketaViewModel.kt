package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository
import ba.etf.rma22.projekat.data.repositories.TakeAnketaRepository
import kotlinx.coroutines.*

class TakeAnketaViewModel {
    var k : TakeAnketaRepository= TakeAnketaRepository
    val scope = CoroutineScope(Job() + Dispatchers.IO)
    fun zapocniAnketu(id:Int, onSuccess: (pitanje: AnketaTaken?) -> Unit,
                   onError: () -> Unit){

        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result =k.zapocniAnketu(id)
            // Display result of the network request to the user
            withContext(Dispatchers.Main) {
                when (result) {
                    is AnketaTaken -> onSuccess?.invoke(result)
                    else-> onError?.invoke()
                }
            }
        }
    }
    fun getPoceteAnkete( onSuccess: (pitanje: List<AnketaTaken>) -> Unit,
                      onError: () -> Unit){

        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result =listOf<AnketaTaken>()//k.getPoceteAnkete()
            // Display result of the network request to the user
            withContext(Dispatchers.Main) {
                when (result) {
                    is List<AnketaTaken> -> onSuccess?.invoke(result)
                    else-> onError?.invoke()
                }
            }
        }
    }
}
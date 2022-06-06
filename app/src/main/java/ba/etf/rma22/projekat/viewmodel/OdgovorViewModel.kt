package ba.etf.rma22.projekat.viewmodel

import android.content.Context
import ba.etf.rma22.projekat.AnketaListAdapter
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.repositories.OdgovorRepository
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository
import kotlinx.coroutines.*

class OdgovorViewModel {
    var k : OdgovorRepository = OdgovorRepository
    val scope = CoroutineScope(Job() + Dispatchers.IO)
    fun getOdgovoriAnketa(kid:Int, onSuccess: (pitanje: List<Odgovor>) -> Unit,
                   onError: () -> Unit){

        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result =k.getOdgovoriAnketa(kid)
            // Display result of the network request to the user
            withContext(Dispatchers.Main) {
                when (result) {
                    is List<Odgovor> -> onSuccess?.invoke(result)
                    else -> onError?.invoke()
                }
            }
        }
    }
    fun getOdgovoriAnketaH(kid:Int, onSuccess: (odgovori: List<Odgovor>,p:List<Pitanje>,t1:AnketaListAdapter.AnketaViewHolder,context1:Context,anketa1:Anketa) -> Unit,
                          onError: () -> Unit,t:AnketaListAdapter.AnketaViewHolder,context:Context,anketa:Anketa){
        var paRep=PitanjeAnketaRepository
        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result =k.getOdgovoriAnketa(kid)
            val result1=paRep.getPitanja(kid)
            // Display result of the network request to the user
            withContext(Dispatchers.Main) {
                when (result) {
                    is List<Odgovor> -> onSuccess?.invoke(result,result1,t, context,anketa)
                    else -> onError?.invoke()
                }
            }
            }
        }
    fun postaviOdgovorAnketa(idAnketaTaken:Int,idPitanje:Int,odgovor:Int,onError: (pro:Int) -> Unit)
    {
        scope.launch{
            // Make the network call and suspend execution until it finishes
           var t= k.postaviOdgovorAnketa(idAnketaTaken,idPitanje,odgovor)
            withContext(Dispatchers.Main) {
                onError(t)
            }
        }
    }
    fun crrrnEnterrijerr(id:Int,kid:Int, onSuccess: (pitanja:List<Pitanje>, odgovori: List<Odgovor>,pred:Boolean,prog:Float,u:Int) -> Unit,
                         onError: () -> Unit,pred1:Boolean,prog1:Float,u1:Int){
        var paRep=PitanjeAnketaRepository
        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result1 =k.getOdgovoriAnketa(kid)
            println("oodg kid=${kid}  %%${result1.size}")
            val result=paRep.getPitanja(id)
            // Display result of the network request to the user
            withContext(Dispatchers.Main) {
                when (result) {
                    is List<Pitanje> -> onSuccess?.invoke(result,result1,pred1,prog1,u1)
                    else -> onError?.invoke()
                }
            }
        }
    }

}
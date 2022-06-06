package ba.etf.rma22.projekat.viewmodel

import android.content.Context
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeIGrupaRepository
import kotlinx.coroutines.*

class IstrazivanjeIGrupaViewModel {

    var k : IstrazivanjeIGrupaRepository = IstrazivanjeIGrupaRepository
    val scope = CoroutineScope(Job() + Dispatchers.IO)
    fun getIstrazivanja(id:Int, onSuccess: (istrazivanja: List<Istrazivanje>) -> Unit,
                   onError: () -> Unit){

        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes

            val result =k.getIstrazivanja(id)
            // Display result of the network request to the user
            withContext(Dispatchers.Main) {
                when (result) {
                    is List<Istrazivanje> -> onSuccess?.invoke(result)
                    else-> onError?.invoke()
                }
            }
        }
    }
    fun getIstrazivanja( onSuccess: (istrazivanja: List<Istrazivanje>) -> Unit,
                        onError: () -> Unit){

        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
        var i=1
            var t= mutableListOf<Istrazivanje>()
            while(true)
            {
                val result =k.getIstrazivanja(i)
                    if(result.size==0)
                        break
                i++
                t.addAll(result)
            }
            // Display result of the network request to the user
            withContext(Dispatchers.Main) {
                when (t) {
                    is List<Istrazivanje> -> onSuccess?.invoke(t)
                    else-> onError?.invoke()
                }
            }
        }
    }
    fun getNeupisanaIstrazivanjaZaGod(god:Int, onSuccess: (istrazivanja: List<Istrazivanje>,con: Context) -> Unit,
                                      onError: () -> Unit,con1:Context){

        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes


            var i=1
            var result= mutableListOf<Istrazivanje>()
            while(true)
            {
                val result1 =k.getIstrazivanja(i)
                if(result1.size==0)
                    break
                i++
                result.addAll(result1)
            }
            val z=result.filter { t->t.godina==god }.toMutableList()

            val u=k.getUpisaneGrupe()
            for(i in u)
            {
                z.remove(k.getIstrazivanjaZaGrupu(i.id))
            }
            // Display result of the network request to the user
            withContext(Dispatchers.Main) {
                when (result) {
                    is List<Istrazivanje> -> onSuccess?.invoke(z,con1)
                    else-> onError?.invoke()
                }
            }
        }
    }
    fun getGrupeZaIstrazivanje(id:Int,onSuccess: (istrazivanja: List<Grupa>,con: Context) -> Unit,
                               onError: () -> Unit,con1:Context)
    {
        scope.launch{
            // Make the network call and suspend execution until it finishes

            val u=k.getGrupeZaIstrazivanje(id).toMutableList()
                u.removeAll(k.getUpisaneGrupe())

            // Display result of the network request to the user
            withContext(Dispatchers.Main) {
                when (u) {
                    is List<Grupa> -> onSuccess?.invoke(u,con1)
                    else-> onError?.invoke()
                }
            }
        }
    }
    fun getGrupe( onSuccess: (istrazivanja: List<Grupa>) -> Unit,
                         onError: () -> Unit){

        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes

            val result =k.getGrupe()
            // Display result of the network request to the user
            withContext(Dispatchers.Main) {
                when (result) {
                    is List<Grupa> -> onSuccess?.invoke(result)
                    else-> onError?.invoke()
                }
            }
        }
    }
    fun getUpisaneGrupe( onSuccess: (istrazivanja: List<Grupa>) -> Unit,
                  onError: () -> Unit){

        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes

            val result =k.getUpisaneGrupe()
            // Display result of the network request to the user
            withContext(Dispatchers.Main) {
                when (result) {
                    is List<Grupa> -> onSuccess?.invoke(result)
                    else-> onError?.invoke()
                }
            }
        }
    }

    fun upisiUGrupu( id: Int,onSuccess: (istrazivanja: Boolean) -> Unit,onError: () -> Unit){

        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes

            val result =k.upisiUGrupu(id)
            withContext(Dispatchers.Main) {
                if (result)
                    onSuccess?.invoke(result)
                else
                    onError?.invoke()
            }
        }
    }
}
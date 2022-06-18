package ba.etf.rma22.projekat.viewmodel

import android.content.Context
import ba.etf.rma22.projekat.Korisnik
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.OdgovorRepository
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository
import ba.etf.rma22.projekat.data.repositories.TakeAnketaRepository
import kotlinx.coroutines.*
import java.util.*

class AnketaViewModel (context:Context?=null){
    var k : AnketaRepository = AnketaRepository
    var context=context
    val scope = CoroutineScope(Job() + Dispatchers.IO)
    fun getAll( onSuccess: (anketa: List<Anketa>) -> Unit,
                          onError: () -> Unit){
        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes

            val result =k.getAll()
            withContext(Dispatchers.Main) { when (result) {
                is List<Anketa> -> onSuccess?.invoke(result)
                else-> onError?.invoke()}
            }
        }
    }
    fun getUpisane( onSuccess: (anketa: List<Anketa>) -> Unit,
                onError: () -> Unit){

        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
            println("Upisane ankete viiiiii")
            val result =k.getUpisane()
            println("upisaneeeeeeeeeeeeee${result.size}")
            // Display result of the network request to the user
            withContext(Dispatchers.Main) {

                when (result) {
                    is List<Anketa> -> onSuccess?.invoke(result)
                    else-> onError?.invoke()
                }


            }
        }
    }
    fun getUradjene( onSuccess: (anketa: List<Anketa>) -> Unit,
                    onError: () -> Unit){

        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes

            val result =k.getUpisane()
            var t= mutableListOf<Anketa>()
            for(i in result) {
                var er = TakeAnketaRepository.getPoceteAnkete()
                if (er != null) {
                var op = er.indexOfLast { a -> a.id == i.id }

                if (op != -1 && OdgovorRepository.getOdgovoriAnketa(er.get(op).id).size == PitanjeAnketaRepository.getPitanja(
                        i.id
                    ).size
                ) {
                    t.add(i)
                }
            }
            }
            // Display result of the network request to the user
            withContext(Dispatchers.Main) {
                when (result) {
                    is List<Anketa> -> onSuccess?.invoke(t)
                    else-> onError?.invoke()
                }
            }
        }
    }
    fun getProsle( onSuccess: (anketa: List<Anketa>) -> Unit,
                     onError: () -> Unit){
        var cal: Calendar = Calendar.getInstance()
        var date: Date = cal.time
        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes

            val result =k.getUpisane()
            var t= mutableListOf<Anketa>()
            for(i in result)
            {
                if(i.datumKraj!=null&& i.datumKraj!! <date)
                {
                    t.add(i)
                }
            }
            // Display result of the network request to the user
            withContext(Dispatchers.Main) {
                when (result) {
                    is List<Anketa> -> onSuccess?.invoke(t)
                    else-> onError?.invoke()
                }
            }
        }
    }
    fun getBuduce( onSuccess: (anketa: List<Anketa>) -> Unit,
                     onError: () -> Unit){
        var cal: Calendar = Calendar.getInstance()
        var date: Date = cal.time
        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes

            val result =k.getUpisane()
            var t= mutableListOf<Anketa>()
            for(i in result)
            {
                if(date<i.datumPocetak)
                {
                    t.add(i)
                }
            }
            // Display result of the network request to the user
            withContext(Dispatchers.Main) {
                when (result) {
                    is List<Anketa> -> onSuccess?.invoke(t)
                    else-> onError?.invoke()
                }
            }
        }
    }
    fun getAll( offset:Int,onSuccess: (anketa: List<Anketa>) -> Unit,
                onError: () -> Unit){

        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes

            val result =k.getAll(offset)
            // Display result of the network request to the user
            withContext(Dispatchers.Main) {
                when (result) {
                    is List<Anketa> -> onSuccess?.invoke(result)
                    else-> onError?.invoke()
                }
            }
        }
    }
    fun getById( id:Int,onSuccess: (anketa: Anketa) -> Unit,
                onError: () -> Unit){

        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes

            val result =k.getById(id)
            // Display result of the network request to the user
            withContext(Dispatchers.Main) {
                when (result) {
                    is Anketa -> onSuccess?.invoke(result)
                    else-> onError?.invoke()
                }
            }
        }
    }



fun getAll1():List<Anketa>
{
    return listOf()
}


}
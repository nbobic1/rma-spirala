package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.Korisnik
import ba.etf.rma22.projekat.ankete
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.OdgovorRepository
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository
import ba.etf.rma22.projekat.data.repositories.TakeAnketaRepository
import kotlinx.coroutines.*
import java.util.*

class AnketaViewModel {
    var k : AnketaRepository = AnketaRepository
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

            val result =k.getUpisane()
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
                if(i.datumKraj!=null&&i.datumKraj<date)
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
    fun setKori( k: Korisnik)
    {
        AnketaRepository.korisnik.setGod(k.getGod())
        AnketaRepository.korisnik.setG(k.getG())
        AnketaRepository.korisnik.setI(k.getI())
        AnketaRepository.korisnik.setA(k.getA())
    }
    fun getKori():Korisnik{
        return AnketaRepository.korisnik
    }
    fun getMyAnkete() : List<Anketa>
    {
        var t= AnketaRepository.getMyAnkete().toMutableList()
        t.addAll(getKori().getA())
        return t.distinct()
    }
fun getAll1():List<Anketa>
{
    return listOf()
}

    fun getDone(): List<Anketa>{
        return getMyAnkete().filter { anketa -> anketa.progres.compareTo(1.0)==0 }
    }
    fun getFuture(): List<Anketa>{
        var cal: Calendar = Calendar.getInstance()
        var datum: Date = cal.time;
        return getMyAnkete().filter { anketa ->anketa.datumPocetak>datum}
    }
    fun getNotTaken(): List<Anketa>{
        var cal: Calendar = Calendar.getInstance()
        var datum: Date = cal.time;
        return getMyAnkete().filter { anketa ->anketa.progres.compareTo(1.0)!=0&&anketa.datumKraj<datum  }
    }
}
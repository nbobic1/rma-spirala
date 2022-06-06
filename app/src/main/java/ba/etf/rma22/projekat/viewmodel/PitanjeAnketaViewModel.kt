package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.*
import ba.etf.rma22.projekat.data.repositories.*
import kotlinx.coroutines.*
import java.util.*

class PitanjeAnketaViewModel {
    var k :PitanjeAnketaRepository= PitanjeAnketaRepository
    val scope = CoroutineScope(Job() + Dispatchers.IO)
    fun getPitanja(id:Int, onSuccess: (pitanje: List<Pitanje>) -> Unit,
                    onError: () -> Unit){

        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result =k.getPitanja(id)
            // Display result of the network request to the user
            withContext(Dispatchers.Main) {
                when (result) {
                    is List<Pitanje> -> onSuccess?.invoke(result)
                    else-> onError?.invoke()
                }
            }
        }
    }
    fun provjeraOtvaranjaA(id:Int,pos:Int, onSuccess: (mojeAnkete:List<Anketa>, buduce:List<Anketa>, uradjene:List<Anketa>,
                                                       prosle:List<Anketa>, pita:List<Pitanje>, odg:List<Odgovor>,at:AnketaTaken
                                                       , position:Int,nazivI:String) -> Unit,
                           onError: () -> Unit)
    {
        var cal: Calendar = Calendar.getInstance()
        var date: Date = cal.time
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val pitanja =PitanjeAnketaRepository.getPitanja(id)
            val odgovoir=OdgovorRepository.getOdgovoriAnketa(id)

            val moj=AnketaRepository.getUpisane()

            val bud= mutableListOf<Anketa>()
            for(i in moj)
            {
                if(date<i.datumPocetak)
                {
                    bud.add(i)
                }
            }
            val por= mutableListOf<Anketa>()
            var gr=IstrazivanjeIGrupaRepository.getGrupeZaA(id)
            var str=""
            if(gr.size!=0)
            {
                str=IstrazivanjeIGrupaRepository.getIstrazivanjaZaGrupu(gr[0].id).naziv

            }
            for(i in moj)
            {
                if(i.datumKraj!=null&&i.datumKraj<date)
                {
                    por.add(i)
                }
            }

            println("Usaoooooooooooooo")
            var pros= mutableListOf<Anketa>()
            for (i in moj)
            {
                if(i.datumKraj!=null&&i.datumKraj<date)
                {
                    var er= TakeAnketaRepository.getPoceteAnkete()
                    if(er!=null)
                    {
                        var op = er.indexOfLast { a -> a.id == i.id }

                        if (op != -1 && OdgovorRepository.getOdgovoriAnketa(er.get(op).id).size == PitanjeAnketaRepository.getPitanja(
                                i.id
                            ).size
                        )
                            pros.add(i)
                    }
                }
            }
            var et= TakeAnketaRepository.getPoceteAnkete()
            var ut=-1
            if(et!=null) {
    et.sortedBy { i -> i.datumRada }
    et = et.filter { i -> i.AnketumId == id }
   ut = et.indexOfLast { i -> i.AnketumId == id }
    // Display result of the network request to the user
}
             if(ut==-1)
            {
                var gh=TakeAnketaRepository.zapocniAnketu(id)
                if(gh!=null)
                withContext(Dispatchers.Main) {
                when (pitanja) {
                    is List<Pitanje> -> onSuccess?.invoke(moj,bud,por,pros,pitanja, odgovoir,gh,pos,str)
                    else-> onError?.invoke()
                }
            }
            }
            else

                withContext(Dispatchers.Main) {
                    if(et!=null)
                    when (pitanja) {
                        is List<Pitanje> -> onSuccess?.invoke(moj,bud,por,pros,pitanja, odgovoir,et.get(ut),pos,str)
                        else-> onError?.invoke()
                    }
                }
        }
    }
}
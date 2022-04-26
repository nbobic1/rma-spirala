package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.Korisnik
import ba.etf.rma22.projekat.ankete
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import java.util.*

class AnketaViewModel {
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
    fun getAll(): List<Anketa>
    {
        return AnketaRepository.getAll()
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
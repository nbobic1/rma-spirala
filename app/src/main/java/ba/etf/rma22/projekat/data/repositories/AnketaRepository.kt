package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.Korisnik
import ba.etf.rma22.projekat.ankete
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.korisnikA
import java.text.SimpleDateFormat
import java.util.*

object AnketaRepository {
    var korisnik : Korisnik = Korisnik()
    fun getMyAnkete() : List<Anketa>
    {
        var k= korisnik.getA().toMutableList()
        for( t in korisnik.getG())
        {
            k.addAll(AnketaRepository.getAll().filter { anketa->anketa.nazivGrupe==t.naziv })
        }
        return k.distinct()
    }
    fun getAll(): List<Anketa>
    {
        return ankete()
    }

    fun getDone(): List<Anketa>{
            return getMyAnkete().filter { anketa -> anketa.progres.compareTo(1.0)==0 }
    }
    fun getFuture(): List<Anketa>{
        var cal: Calendar = Calendar.getInstance()
        cal.set(2022,4,15)
        var datum: Date = cal.time;
        return getMyAnkete().filter { anketa ->anketa.datumPocetak>datum||(anketa.datumKraj>datum&&anketa.progres.compareTo(1.0)!=0)}
    }
    fun getNotTaken(): List<Anketa>{
        var cal: Calendar = Calendar.getInstance()
        cal.set(2022,4,15)
        var datum: Date = cal.time;
        return getMyAnkete().filter { anketa ->anketa.progres.compareTo(1.0)!=0&&anketa.datumKraj<datum  }
    }

}
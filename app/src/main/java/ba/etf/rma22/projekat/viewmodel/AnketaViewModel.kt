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
        return AnketaRepository.getMyAnkete();
    }
    fun getAll(): List<Anketa>
    {
        return AnketaRepository.getAll();
    }

    fun getDone(): List<Anketa>{
        return AnketaRepository.getDone();
    }
    fun getFuture(): List<Anketa>{
        return AnketaRepository.getFuture()
    }
    fun getNotTaken(): List<Anketa>{
        return AnketaRepository.getNotTaken()
    }
}
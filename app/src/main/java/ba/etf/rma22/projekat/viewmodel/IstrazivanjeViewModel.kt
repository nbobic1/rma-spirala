package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.Korisnik
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjaRepository
import ba.etf.rma22.projekat.istrazivanja

class IstrazivanjeViewModel {
    fun setKori( k: Korisnik)
    {
        AnketaRepository.korisnik.setGod(k.getGod())
        AnketaRepository.korisnik.setG(k.getG())
        AnketaRepository.korisnik.setI(k.getI())
        AnketaRepository.korisnik.setA(k.getA())
    }
    fun getKori(): Korisnik {
        return AnketaRepository.korisnik
    }

    fun getIstrazivanjeByGodina(godina:Int): List<Istrazivanje>{
        return IstrazivanjaRepository.getIstrazivanjeByGodina(godina)
    }
    fun getAll(): List<Istrazivanje>{
        return  IstrazivanjaRepository.getAll()
    }
    fun getUpisani(): List<Istrazivanje>{
        return IstrazivanjaRepository.getUpisani()
    }
}
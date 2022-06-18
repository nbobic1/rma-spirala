package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.Korisnik
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjaRepository
import ba.etf.rma22.projekat.istrazivanja

class IstrazivanjeViewModel {

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
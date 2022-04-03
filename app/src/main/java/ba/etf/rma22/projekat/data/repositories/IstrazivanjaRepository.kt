package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.Korisnik
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.istrazivanja
import ba.etf.rma22.projekat.korisnikI

object IstrazivanjaRepository {
    var korisnik :Korisnik =Korisnik()
    fun getIstrazivanjeByGodina(godina:Int): List<Istrazivanje>{
        return istrazivanja().filter { istra-> istra.godina==godina };
    }
    fun getAll(): List<Istrazivanje>{
        return istrazivanja();
    }
    fun getUpisani(): List<Istrazivanje>{
        return korisnik.getI()
    }
}
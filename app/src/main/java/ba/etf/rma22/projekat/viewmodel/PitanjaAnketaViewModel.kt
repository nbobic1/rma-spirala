package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.repositories.PitanjaAnketaRepository

class PitanjaAnketaViewModel {
    fun getPitanja(string: String,string1: String):List<Pitanje>
    {
        return PitanjaAnketaRepository.getPitanja(string,string1)
    }
}
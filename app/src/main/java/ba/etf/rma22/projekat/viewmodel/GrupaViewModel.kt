package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.repositories.GrupaRepository
import ba.etf.rma22.projekat.grupe

class GrupaViewModel {
    fun getGroupsByIstrazivanjet(nazivIstrazivanja:String) : List<Grupa>{
        return GrupaRepository.getGroupsByIstrazivanjet(nazivIstrazivanja)
    }
}
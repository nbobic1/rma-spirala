package ba.etf.rma22.projekat

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import java.io.Serializable
import java.security.acl.Group

data class paketG(var gh:List<Grupa>):Serializable

data class paketA(var gh:List<Anketa>):Serializable

data class paketI(var gh:List<Istrazivanje>):Serializable

package ba.etf.rma22.projekat

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.models.Odgovor1
import java.io.Serializable

class Korisnik :Serializable{
    var godina:Int = -1
var odgovori:List<Odgovor1> =listOf()

    var listaA:List<Anketa> = listOf()

    var listaG:List<Grupa> = listOf()

    var listaI:List<Istrazivanje> = listOf()
    fun getGod():Int
    {
        return godina
    }
    fun setGod(a:Int)
    {
        godina=a
    }
    fun setOdg(a:List<Odgovor1>)
    {
        odgovori=a
    }
    fun getOdg():List<Odgovor1>
    {
        return odgovori
    }
    fun getA():List<Anketa>
    {
        return listaA
    }
    fun setA(a:List<Anketa>)
    {
        listaA=a
    }
    fun getG():List<Grupa>
    {
        return listaG
    }
    fun setG(a:List<Grupa>)
    {
        listaG=a
    }
    fun getI():List<Istrazivanje>
    {
        return listaI
    }
    fun setI(a:List<Istrazivanje>)
    {
        listaI=a
    }
}
package ba.etf.rma22.projekat

import ba.etf.rma22.projekat.data.models.Istrazivanje

fun istrazivanja():List<Istrazivanje>
{
    return listOf(
        Istrazivanje("obala Une",1),
        Istrazivanje("moj mozak",2),
        Istrazivanje("utjecaj racunara",3),
        Istrazivanje("bolesti u regionu",4),
        Istrazivanje("problem lutalica",4),
        Istrazivanje("mentalitet",6),
    )
}
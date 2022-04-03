package ba.etf.rma22.projekat

import ba.etf.rma22.projekat.data.models.Istrazivanje

fun istrazivanja():List<Istrazivanje>
{
    return listOf(
        Istrazivanje("obala Une",2022),
        Istrazivanje("moj mozak",2022),
        Istrazivanje("utjecaj racunara",2022),
        Istrazivanje("bolesti u regionu",2022),
        Istrazivanje("problem lutalica",2022),
        Istrazivanje("mentalitet",2022),
    )
}
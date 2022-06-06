package ba.etf.rma22.projekat

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
fun kornikGod():Int
{
    return 2021;
}
fun korisnikA():List<Anketa>
{
    return listOf(ankete()[0])
}
fun korisnikG():List<Grupa>
{
    return listOf()//grupe()[0])
}
fun korisnikI():List<Istrazivanje>
{
    return listOf(istrazivanja()[0])
}
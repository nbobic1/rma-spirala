package ba.etf.rma22.projekat

import ba.etf.rma22.projekat.data.models.Grupa

fun grupe():List<Grupa>
{
    return listOf(
        Grupa("prva", istrazivanja()[0].naziv),
        Grupa("druga", istrazivanja()[0].naziv),
        Grupa("treca", istrazivanja()[1].naziv),
        Grupa("cetvrta", istrazivanja()[1].naziv),
        Grupa("peta", istrazivanja()[2].naziv),
        Grupa("sesta", istrazivanja()[2].naziv)
    )
}
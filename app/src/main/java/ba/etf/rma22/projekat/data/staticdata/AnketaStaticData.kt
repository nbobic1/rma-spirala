package ba.etf.rma22.projekat

import ba.etf.rma22.projekat.data.models.Anketa
import java.text.SimpleDateFormat
import java.util.*
fun non(k:Date?):Date
{var cal: Calendar = Calendar.getInstance()
    cal.set(2021,3,10)
    var date: Date = cal.time;
    return k ?: date

}
fun ankete():List<Anketa>
{
    var form=SimpleDateFormat("dd.MM.yyyy.")
    return listOf(
        Anketa( grupe().get(0).nazivIstrazivanja+" Anketa", grupe().get(0).nazivIstrazivanja,non(form.parse("02.03.2021.")),non(form.parse("02.03.2023.")),form.parse("03.03.2023."),4, grupe().get(0).naziv,0.33F),
        Anketa( grupe().get(2).nazivIstrazivanja+" Anketa", grupe().get(2).nazivIstrazivanja,non(form.parse("02.03.2024.")),non(form.parse("02.03.2025.")),form.parse("02.03.2025."),4, grupe().get(2).naziv,0.12F),
        Anketa( grupe().get(3).nazivIstrazivanja+" Anketa", grupe().get(3).nazivIstrazivanja,non(form.parse("02.03.2019.")),non(form.parse("02.03.2020.")),form.parse("02.03.2021."),4, grupe().get(3).naziv,0.6F),
        Anketa( grupe().get(1).nazivIstrazivanja+" Anketa", grupe().get(1).nazivIstrazivanja,non(form.parse("02.03.2021.")),non(form.parse("02.03.2024.")),form.parse("02.03.2021."),4, grupe().get(1).naziv,1.0F),
        Anketa( grupe().get(4).nazivIstrazivanja+" Anketa", grupe().get(4).nazivIstrazivanja,non(form.parse("02.03.2021.")),non(form.parse("02.03.2021.")),form.parse("02.03.2021."),4, grupe().get(4).naziv,0.65F),
        Anketa( grupe().get(5).nazivIstrazivanja+" Anketa", grupe().get(5).nazivIstrazivanja,non(form.parse("02.03.2021.")),non(form.parse("02.03.2021.")),form.parse("02.03.2021."),4, grupe().get(5).naziv,0.83F),
       )
}
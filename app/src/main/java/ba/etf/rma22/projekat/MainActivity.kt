package ba.etf.rma22.projekat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.viewpager2.widget.ViewPager2
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.viewmodel.AnketaViewModel
import ba.etf.rma22.projekat.viewmodel.GrupaViewModel
import ba.etf.rma22.projekat.viewmodel.IstrazivanjeViewModel
import java.util.*

class MainActivity : AppCompatActivity() {
    var call = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            if (position==0)
                Handler(Looper.getMainLooper()).postDelayed({
                    k.promijeni2()
                }, 500)
            else if(position==k.broj()-1)
            {
                k.upoti()
            }
        }
    }
    private lateinit var viewPager :ViewPager2
    var k=ViewPagerAdapter(this)
    var korisnik:Korisnik =  Korisnik()
    var asrg=""
    var asrg1=""
    var args2=Anketa("","", Date(),Date(),Date(),0,"",0.0f)
    var istrazivanjeViewModel : IstrazivanjeViewModel = IstrazivanjeViewModel()
    var grupaViewModel: GrupaViewModel = GrupaViewModel()
        var anketaViewModel: AnketaViewModel = AnketaViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        korisnik.setG( korisnikG())
        korisnik.setI(korisnikI())
        anketaViewModel.setKori(korisnik)
        istrazivanjeViewModel.setKori(korisnik)
      viewPager=findViewById(R.id.pager)

        viewPager.registerOnPageChangeCallback(call)
        viewPager.adapter=k
    }
    override fun onDestroy() {
        viewPager.unregisterOnPageChangeCallback(call)
        super.onDestroy()
    }
    override fun onBackPressed()
    {
       if(k.fragments[k.fragments.size-1] is FragmentPredaj)
        {
            zaustavi()
       }
    }

    fun promie(t:Int)
    {
        k.promijeni(t)
        viewPager.adapter=k
        viewPager.currentItem = 1
    }
    fun update()
    {
        viewPager.adapter=k
        println("update")
    }

    fun pitanja(a: Anketa, u:Int)
    {
        asrg=a.naziv
        asrg1=a.nazivIstrazivanja
        args2=a
        k.pitanja(a.naziv,a.nazivIstrazivanja,a.progres,a,u)
    }
    fun pitanjaKraj()
    {
        println("kraj")
        if(korisnik.getOdg().map { t->t.anketa }.contains(args2))
        {
            var lis=korisnik.getOdg().toMutableList()
            var lis2=k.pitanjaKraj()
            var k1:Int=lis.map { t->t.anketa }.indexOf(args2)
            println("setam k1=${k1}")
            lis[k1]=Odgovor(args2,lis2,1.0f,true)
            korisnik.setOdg(lis)
            anketaViewModel.setKori(korisnik)
            istrazivanjeViewModel.setKori(korisnik)
        }
        else
        {
            var lis=korisnik.getOdg().toMutableList()
            var lis2=k.pitanjaKraj()
            lis.add(Odgovor(args2,lis2,1.0f,true))
            println("dodajem odg")
            korisnik.setOdg(lis)
            anketaViewModel.setKori(korisnik)
            istrazivanjeViewModel.setKori(korisnik)

        }
        k.predaj(asrg,asrg1)
        viewPager.adapter=k
        viewPager.currentItem = 1
    }

    fun zaustavi() {
        println("zaustavi555")
        if(args2.progres.compareTo(1.0f)==0)
        {
            println("zaustavi return")
            k.pocetna()
            return
        }
        if(korisnik.getOdg().map { t->t.anketa }.contains(args2))
        {
            var lis=korisnik.getOdg().toMutableList()
            var lis2=k.pitanjaKraj()
            var k1:Int=lis.map { t->t.anketa }.indexOf(args2)

            println("size=${lis2.size} i ${k.broj()}")
            if(lis[k1].progrs.compareTo(1.0f)==0)
            {
                k.pocetna()
                return
            }
            var lis3=lis2.toMutableList()
                lis3.removeIf { t->t.size==0 }
            var z:Float=((lis3.size.toFloat())/((k.broj().toFloat()-1.0f)))
            lis[k1]=Odgovor(args2,lis2,z,false)
            korisnik.setOdg(lis)
            anketaViewModel.setKori(korisnik)
            istrazivanjeViewModel.setKori(korisnik)
        }
        else
        {
            var lis=korisnik.getOdg().toMutableList()
            var lis2=k.pitanjaKraj()
            var lis3=lis2.toMutableList()
            lis3.removeIf { t->t.size==0 }

            var z:Float=((lis3.size.toFloat())/((k.broj().toFloat()-1.0f)))
            lis.add(Odgovor(args2,lis2,z,false))
            korisnik.setOdg(lis)
            anketaViewModel.setKori(korisnik)
            istrazivanjeViewModel.setKori(korisnik)
        }
        k.pocetna()
    }
}
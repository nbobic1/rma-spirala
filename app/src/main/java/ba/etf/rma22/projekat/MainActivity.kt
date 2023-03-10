package ba.etf.rma22.projekat

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.models.Odgovor1
import ba.etf.rma22.projekat.data.repositories.*
import ba.etf.rma22.projekat.viewmodel.*
import java.util.*


class MainActivity : AppCompatActivity() {
    companion object{
        var connection:Boolean =true
    }
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
        }
        return false
    }
    var aT:AnketaTaken=AnketaTaken(1,0,"",null,1,"")
    var anketaViewModel: AnketaViewModel = AnketaViewModel()
    var odgovorViewModel=OdgovorViewModel()
    var istrazivanjeIGrupaViewModel=IstrazivanjeIGrupaViewModel()
    var takeAnketaViewModel=TakeAnketaViewModel()
    var pitanjeAnketaViewModel=PitanjeAnketaViewModel()
     var call = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            if (position==0)
                Handler(Looper.getMainLooper()).postDelayed({
                    k.promijeni2()
                }, 500)
            else if(position==k.broj()-1)
            {
                Handler(Looper.getMainLooper()).postDelayed({
                    k.upoti()
                }, 500)
            }
        }
    }
    private lateinit var viewPager :ViewPager2
    var k=ViewPagerAdapter(this)
    var korisnik:Korisnik =  Korisnik()
    var asrg=""
    var asrg1=""
    var args2=Anketa(0,"","", Date(),Date(),Date(),0,"",0.0f,null,true,2)
    var istrazivanjeViewModel : IstrazivanjeViewModel = IstrazivanjeViewModel()
    var grupaViewModel: GrupaViewModel = GrupaViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        AnketaRepository.context=this
        IstrazivanjeIGrupaRepository.context=this
        OdgovorRepository.context=this
        PitanjeAnketaRepository.context=this
        TakeAnketaRepository.context=this
        connection=isOnline(this)
        var tui=intent.getStringExtra("payload")
        if (tui != null) {
            var avm=AccountViewModel()
                avm.setHash(tui)
        }
        viewPager=findViewById(R.id.pager)
        anketaViewModel=AnketaViewModel(this)
        viewPager.registerOnPageChangeCallback(call)
        viewPager.adapter=k
    }
    fun onSuccess(movies:List<Anketa>){
        val toast = Toast.makeText(viewPager.context, "Upcoming movies found", Toast.LENGTH_SHORT)
        toast.show()
           println(movies.size)
        println("=algjal===============jgal")
        /* for(i in movies)
        {
            println(i.naziv)
        }*/
    }
    fun onError() {
        val toast = Toast.makeText(viewPager.context, "Search error", Toast.LENGTH_SHORT)
        toast.show()
        println("error")
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

    fun pitanja(a:Anketa,nazivIstra:String,a1:AnketaTaken, u:Int)
    {
        println("PITANJAAA")
        asrg=a.naziv
        asrg1=nazivIstra
        args2=a
        k.pitanja(a.naziv,nazivIstra,a1.progres,a,a1,u)
        aT=a1
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
            lis[k1]=Odgovor1(args2,lis2,1.0f,true)
            korisnik.setOdg(lis)

        }
        else
        {
            var lis=korisnik.getOdg().toMutableList()
            var lis2=k.pitanjaKraj()
            lis.add(Odgovor1(args2,lis2,1.0f,true))
            println("dodajem odg")
            korisnik.setOdg(lis)

        }
        k.predaj(asrg,asrg1)
        viewPager.adapter=k
        viewPager.currentItem = 1
    }

    fun zaustavi() {
        println("zaustavi555")
        if(args2.progres!!.compareTo(1.0f)==0)
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
            lis[k1]=Odgovor1(args2,lis2,z,false)
            korisnik.setOdg(lis)
        }
        else
        {
            var lis=korisnik.getOdg().toMutableList()
            var lis2=k.pitanjaKraj()
            var lis3=lis2.toMutableList()
            lis3.removeIf { t->t.size==0 }

            var z:Float=((lis3.size.toFloat())/((k.broj().toFloat()-1.0f)))
            lis.add(Odgovor1(args2,lis2,z,false))
            korisnik.setOdg(lis)
        }
        k.pocetna()
    }
}
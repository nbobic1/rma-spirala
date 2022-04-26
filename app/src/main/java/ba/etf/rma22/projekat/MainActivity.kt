package ba.etf.rma22.projekat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.viewpager2.widget.ViewPager2
import ba.etf.rma22.projekat.viewmodel.AnketaViewModel
import ba.etf.rma22.projekat.viewmodel.GrupaViewModel
import ba.etf.rma22.projekat.viewmodel.IstrazivanjeViewModel

class MainActivity : AppCompatActivity() {
    var k=ViewPagerAdapter(this)
    private lateinit var viewPager :ViewPager2
    var korisnik:Korisnik =  Korisnik()
    var asrg=""
    var asrg1=""
    var anketaViewModel: AnketaViewModel = AnketaViewModel()
    var istrazivanjeViewModel : IstrazivanjeViewModel = IstrazivanjeViewModel()
    var grupaViewModel: GrupaViewModel = GrupaViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        korisnik.setG( korisnikG())
        korisnik.setI(korisnikI())
        anketaViewModel.setKori(korisnik)
        istrazivanjeViewModel.setKori(korisnik)
      viewPager=findViewById(R.id.pager)
        var call = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position==0)
                    Handler(Looper.getMainLooper()).postDelayed({
                        k.promijeni2()
                    }, 500)
            }
        }
        viewPager.registerOnPageChangeCallback(call)
        viewPager.adapter=k
    }

    override fun onBackPressed() {
       if(k.fragments[k.fragments.size-1] is FragmentPredaj)
        {
            println("falgjlčajgč")
            k.pitanjaKraj(asrg,asrg1)
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
    }

fun pitanja(t:String,t1:String,prog:Float,u:Int)
{
    asrg=t
    asrg1=t1
    k.pitanja(t,t1,prog,u)
}
    fun pitanjaKraj()
    {
        k.pitanjaKraj(asrg,asrg1)
        viewPager.adapter=k
        viewPager.currentItem = 1
    }
}
package ba.etf.rma22.projekat

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.viewmodel.OdgovorViewModel
import ba.etf.rma22.projekat.viewmodel.PitanjaAnketaViewModel

class ViewPagerAdapter(activity: AppCompatActivity):FragmentStateAdapter(activity) {
    var fragments:MutableList<Fragment> = mutableListOf(FragmentAnkete.newInstance(),FragmentIstrazivanje.newInstance())
    var grupa:String =""
    var istrazivanje:String =""
    var activity1=activity

    override fun createFragment(position: Int): Fragment {
     return fragments[position]
    }
    fun notAll()
    {
        notifyDataSetChanged()
    }
    fun remove(index: Int) {
        fragments.removeAt(index)
        notifyItemChanged(index)
    }
    fun pocetna()
    {
            fragments.clear()
        fragments.addAll(listOf(FragmentAnkete.newInstance(),FragmentIstrazivanje.newInstance()))
        notifyDataSetChanged()
        (activity1 as MainActivity).update()
    }
    fun promijeni2()
    {
        if(fragments[1]!is FragmentPoruka)
            return
        fragments[1]=FragmentIstrazivanje.newInstance()
        notifyItemChanged(1)
    }
    fun promijeni(t:Int)
    {
        fragments[1]=FragmentPoruka.newInstance(t,grupa,istrazivanje)
        notifyItemChanged(1)
    }
    fun predaj(s1:String,s2:String)
    {
        fragments.clear()
        fragments.addAll(listOf(FragmentAnkete.newInstance(),FragmentPoruka.newInstance(1,s1,s2)))
        notifyDataSetChanged()
    }
    fun pitanja(t:String, t1:String, prog1:Int, anketa: Anketa,anketaTaken: AnketaTaken, u :Int)
    {
        var prog=prog1
        grupa=t
        istrazivanje=t1
        fragments.clear()
        var pred:Boolean=u==1

        var ert=OdgovorViewModel()
        ert.crrrnEnterrijerr(anketa.id,anketaTaken.id, onSuccess = ::sucPitanja, onError = ::onError,pred, prog,u)
    }
    fun onError()
    {
        println("problem s API")
    }
    fun sucPitanja(a:List<Pitanje>,u1:List<Odgovor> ,pred:Boolean,prog:Int,u:Int)
    {
            println("uisize=${u1.size}")
        var zu:Int=0
        for(i in a )
        {
            if(u1.any { t->t.pitanjeId==i.id })
            {
                fragments.add(FragmentPitanje.newInstance(i, u1.last { t->t.pitanjeId==i.id }.odgovoreno, u == 1))
            } else
                fragments.add(FragmentPitanje.newInstance(i,-1,u==1))
            zu++
        }
        fragments.add(FragmentPredaj.newInstance(prog,pred))
        notifyDataSetChanged()
        (activity1 as MainActivity).update()
    }
    fun pitanjaKraj() :List<List<Int>>
    {
        var krtt= mutableListOf<List<Int>>()
        for(i in 0..(fragments.size-2))
        {
          //  krtt.add((fragments[i] as FragmentPitanje).odg())
        }
        return krtt
    }
    fun broj():Int
    {
        return fragments.size
    }
    override fun getItemCount(): Int {
        return fragments.size
    }

    fun upoti() {
        if(fragments[fragments.size-1] is FragmentPredaj)
        {
            if((fragments[fragments.size-1]as FragmentPredaj).got!=true)
            {
                var t =pitanjaKraj()
                var t1=pitanjaKraj().toMutableList()
                t1.removeIf{t->t.size==0}
                println("t=${t.size}  t1=${t1.size}")
                //(fragments[fragments.size-1]as FragmentPredaj).setPro(t1.size.toFloat()/t.size.toFloat())
            }
          }
    }
}
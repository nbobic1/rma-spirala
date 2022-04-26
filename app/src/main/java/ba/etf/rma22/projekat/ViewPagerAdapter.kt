package ba.etf.rma22.projekat

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ba.etf.rma22.projekat.data.models.Pitanje
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
        (activity1 as MainActivity).update()
    }
    fun pitanja(t:String,t1:String,prog:Float,u :Int)
    {
        println("u=${u}")
        grupa=t
        istrazivanje=t1
        fragments.clear()
       var viewmod= PitanjaAnketaViewModel()
        for(i in viewmod.getPitanja(t,t1) )
        {
            fragments.add(FragmentPitanje.newInstance(i,listOf(1),u==1))
        }
        fragments.add(FragmentPredaj.newInstance(prog,u==1))
        notifyDataSetChanged()
        (activity1 as MainActivity).update()
    }
    fun pitanjaKraj(s1: String,s2: String) :List<List<Int>>
    {
        var krtt= mutableListOf<List<Int>>()
        for(i in 0..(fragments.size-2))
        {
            krtt.add((fragments[i] as FragmentPitanje).odg())
            println("i==${i}")
            for(t in (fragments[i] as FragmentPitanje).odg())
                println("to je to=${t}")
        }
        predaj(s1,s2)
        return krtt
    }
    override fun getItemCount(): Int {
        return fragments.size
    }
}
package ba.etf.rma22.projekat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class FragmentPoruka : Fragment() {
    var tec:String=""
    var tec1:String=""
    var tec2:Int=0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view=inflater.inflate(R.layout.poruka,container,false)
        var kot:TextView =view.findViewById<TextView>(R.id.tvPoruka)
        if(tec2==0)
        kot.text="Uspješno ste upisani u grupu ${tec} istraživanja ${tec1}!"
        else
        kot.text="Završili ste anketu ${tec} u okviru istraživanja ${tec1}"
        return view
    }
    companion object {
        fun newInstance(tro:Int,grupa: String, istrazivanje: String): FragmentPoruka {
            var zu=FragmentPoruka()
            zu.tec2=tro
            zu.tec=grupa
            zu.tec1=istrazivanje
            return zu
        }
    }
}
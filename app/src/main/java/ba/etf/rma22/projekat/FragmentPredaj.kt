package ba.etf.rma22.projekat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlin.math.roundToInt

class FragmentPredaj : Fragment() {
    var prog:Int=0
    var got=false
    private lateinit var progres: TextView
    private lateinit var dugme: Button
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.predaj, container, false)
            dugme=view.findViewById(R.id.dugmePredaj)
        if(got)
            dugme.isEnabled=false
        dugme.setOnClickListener { (activity as MainActivity).pitanjaKraj() }
        progres=view.findViewById(R.id.progresTekst)
        progres.text="${prog}%"
        return view
    }
    fun setPro(k1:Int)
    {
        prog=k1
        if(::progres.isInitialized)
        progres.text="${k1}%"
    }
    companion object {
        fun newInstance(zu:Int,u:Boolean): FragmentPredaj {
            var t=FragmentPredaj()
                t.prog=zu
            t.got=u
            return t
        }
    }
}
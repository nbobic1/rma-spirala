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
    var prog:Float=0.0f
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
        val k: Double = kotlin.math.round(prog / 0.2) *20
        var k1=k.roundToInt()
        progres.text="${k1}%"
        return view
    }
    fun setPro(k1:Int)
    {
        progres.text="${k1}%"
    }
    companion object {
        fun newInstance(zu:Float,u:Boolean): FragmentPredaj {
            var t=FragmentPredaj()
                t.prog=zu
            t.got=u
            return t
        }
    }
}
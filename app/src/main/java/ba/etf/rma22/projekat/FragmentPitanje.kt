package ba.etf.rma22.projekat

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.data.models.Pitanje

class FragmentPitanje : Fragment() {
    var lis:List<Int> = listOf()
    var gotova=false
    var odgovor1:MutableList<Int> = mutableListOf()
    private lateinit var pitanje:TextView
    private lateinit var odgovor: ListView
    private lateinit var dugme: Button
    private lateinit var zet:Pitanje
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.pitanje, container, false)
        pitanje=view.findViewById(R.id.tekstPitanja)
        odgovor=view.findViewById(R.id.odgovoriLista)
        dugme=view.findViewById(R.id.dugmeZaustavi)
        if(gotova)
            dugme.isEnabled=false
        pitanje.text=zet.tekst
        odgovor.adapter=StringAdapter(view.context,R.layout.string_adapter, zet.opcije,this)

        return view
    }

    fun odg():List<Int>{
        return odgovor1.distinct()
    }
    companion object {
        fun newInstance(zu: Pitanje,lis:List<Int>,got:Boolean): FragmentPitanje {
            var t=FragmentPitanje()
                t.zet=zu
            t.lis=lis
            t.odgovor1.addAll(lis)
            t.gotova=got
            return t
        }
    }
}
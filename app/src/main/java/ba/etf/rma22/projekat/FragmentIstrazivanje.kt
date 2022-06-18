package ba.etf.rma22.projekat

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje

class FragmentIstrazivanje:Fragment() {
    private lateinit var upis: Button
    private lateinit var spin1: Spinner
    private lateinit var spin2: Spinner
    private lateinit var spin3: Spinner
  private lateinit var main:MainActivity
  var indi=mutableListOf<Istrazivanje>()

    var indi1=mutableListOf<Grupa>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        main=(activity as MainActivity)
        var view=inflater.inflate(R.layout.activity_upis_istrazivanje,container,false)
        upis=view.findViewById(R.id.upisDugme)
        upis.isEnabled=false
        spin1=view.findViewById(R.id.odabirGodina)
        var kow=((1..5).toList().map { broj->broj.toString() }).toMutableList()
        kow.add(" ")
        var  arr= ArrayAdapter(view.context, android.R.layout.simple_spinner_item, kow)
        arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin1.adapter=arr
       if(main.korisnik.getGod()==-1)
            spin1.setSelection(arr.getPosition(" "))
        else
            spin1.setSelection(arr.getPosition(main.korisnik.getGod().toString()))
        spin2=view.findViewById(R.id.odabirIstrazivanja)
        spin3=view.findViewById(R.id.odabirGrupa)
        if(MainActivity.connection)
        spin1.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                if(p0!=null&&(p0.getItemAtPosition(p2)as String)!=" ")
                {
                   main.istrazivanjeIGrupaViewModel.getNeupisanaIstrazivanjaZaGod((p0.getItemAtPosition(p2)as String).toInt(),
                       onSuccess = ::postaviSpin2,onError=::onError,p0.context)
                    spin2.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                            if(p0!=null)
                            {
                                if(indi.size>p2)
                                main.istrazivanjeIGrupaViewModel.getGrupeZaIstrazivanje(indi.get(p2).id, onSuccess = ::postaviSpin3,onError=::onError,p0.context)
                            }
                        }
                        override fun onNothingSelected(p0: AdapterView<*>?) {
                        }
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        if(MainActivity.connection)
        upis.setOnClickListener{

            main.istrazivanjeIGrupaViewModel.upisiUGrupu(indi1.get(spin3.selectedItemPosition).id, onSuccess = ::onSuccess,onError = ::onError)
            main.k.grupa=spin3.selectedItem.toString()
            main.k.istrazivanje=spin2.selectedItem.toString()

            main.promie(0)
        }
        return view
    }
    fun onError()
    {
        println("problem s API")
    }
    fun postaviSpin2(a:List<Istrazivanje>,con:Context)
    {
        var  arr1= ArrayAdapter(con, android.R.layout.simple_spinner_item,a.map { istar->istar.naziv }.toMutableList())
        arr1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin2.adapter=arr1
        indi=a.toMutableList()
        if(a.size==0)
        {
            var  arr2= ArrayAdapter(con, android.R.layout.simple_spinner_item,mutableListOf<String>())
            arr2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spin3.adapter=arr2
            upis.isEnabled=false
        }
    }
    fun postaviSpin3(a:List<Grupa>,con:Context) {
       indi1=a.toMutableList()
        var  arr2= ArrayAdapter(con, android.R.layout.simple_spinner_item,a.map { istar->istar.naziv }
        )
        arr2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin3.adapter=arr2
        upis.isEnabled=a.isNotEmpty()

    }
    fun onSuccess(a:Boolean)
    {

    }
    companion object {
        fun newInstance(): FragmentIstrazivanje = FragmentIstrazivanje() }
}
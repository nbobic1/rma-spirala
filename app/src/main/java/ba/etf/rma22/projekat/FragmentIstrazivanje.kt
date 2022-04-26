package ba.etf.rma22.projekat

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
        spin1.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                if(p0!=null&&(p0.getItemAtPosition(p2)as String)!=" ")
                {
                    var lis=main.istrazivanjeViewModel.getIstrazivanjeByGodina((p0.getItemAtPosition(p2)as String).toInt()).toMutableList()
                    lis.removeAll(main.istrazivanjeViewModel.getUpisani())
                    lis.removeAll(main.korisnik.getI())

                    var  arr1= ArrayAdapter(p0.context, android.R.layout.simple_spinner_item,lis.map { istar->istar.naziv }.toMutableList())
                    arr1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spin2.adapter=arr1
                    spin2.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                            if(p0!=null)
                            {
                                var  arr2= ArrayAdapter(p0.context, android.R.layout.simple_spinner_item,main.grupaViewModel.getGroupsByIstrazivanjet(p0.getItemAtPosition(p2) as String).map { istar->istar.naziv }
                                )
                                arr2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                spin3.adapter=arr2
                                upis.isEnabled=main.grupaViewModel.getGroupsByIstrazivanjet(p0.getItemAtPosition(p2) as String).isNotEmpty()
                            }
                        }
                        override fun onNothingSelected(p0: AdapterView<*>?) {
                        }
                    }
                    if(lis.size==0)
                    {
                        var  arr2= ArrayAdapter(p0.context, android.R.layout.simple_spinner_item,mutableListOf<String>())
                        arr2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spin3.adapter=arr2
                        upis.isEnabled=false
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        upis.setOnClickListener{
            var tut=(activity as MainActivity)
            var godina=spin1.selectedItem.toString()
            var itra=spin2.selectedItem.toString()
            var kot=  main.istrazivanjeViewModel.getKori().getI().toMutableList()
            kot.add(Istrazivanje(itra.toString(), godina.toString().toInt()))
            main.korisnik.setI(kot.distinct())
            main.korisnik.setGod(godina.toString().toInt())
            var kot1=  main.anketaViewModel.getKori().getG().toMutableList()
            kot1.add(Grupa(spin3.selectedItem.toString(),itra.toString()))
            main.korisnik.setG(kot1)
            main.istrazivanjeViewModel.setKori(main.korisnik)
            main.anketaViewModel.setKori(main.korisnik)
            tut.k.grupa=spin3.selectedItem.toString()
            tut.k.istrazivanje=itra
            tut.promie(0)

        }
        return view
    }
    companion object {
        fun newInstance(): FragmentIstrazivanje = FragmentIstrazivanje() }
}
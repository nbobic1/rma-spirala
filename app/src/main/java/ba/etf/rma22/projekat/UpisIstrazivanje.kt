package ba.etf.rma22.projekat

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import ba.etf.rma22.projekat.data.repositories.GrupaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjaRepository
import ba.etf.rma22.projekat.viewmodel.GrupaViewModel
import ba.etf.rma22.projekat.viewmodel.IstrazivanjeViewModel

class UpisIstrazivanje : AppCompatActivity() {
    private lateinit var upis: Button
    private lateinit var spin1:Spinner
    private lateinit var spin2:Spinner
    private lateinit var spin3:Spinner
    private var istrazivanjeViewModel :IstrazivanjeViewModel = IstrazivanjeViewModel()
    private var grupaViewModel:GrupaViewModel =GrupaViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_upis_istrazivanje)
        var korisnik:Korisnik = Korisnik()
        korisnik.setI((intent.extras!!["korisnik"] as paketI).gh)
        korisnik.setGod(intent.extras!!["godina"]as Int)
        upis=findViewById(R.id.dodajIstrazivanjeDugme)
        upis.isEnabled=false
        spin1=findViewById(R.id.odabirGodina)
        var kow=((1..5).toList().map { broj->broj.toString() }).toMutableList()
        kow.add(" ")
        var  arr= ArrayAdapter(this, android.R.layout.simple_spinner_item, kow)
        arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin1.adapter=arr
        if(korisnik.getGod()==-1)
            spin1.setSelection(arr.getPosition(" "))
        else
        spin1.setSelection(arr.getPosition(korisnik.getGod().toString()))
        spin2=findViewById(R.id.odabirIstrazivanja)
        spin3=findViewById(R.id.odabirGrupa)
       spin1.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                if(p0!=null&&(p0.getItemAtPosition(p2)as String)!=" ")
                {
                    var lis=istrazivanjeViewModel.getIstrazivanjeByGodina((p0.getItemAtPosition(p2)as String).toInt()).toMutableList()
                    lis.removeAll(istrazivanjeViewModel.getUpisani())
                    lis.removeAll(korisnik.getI())

                    var  arr1= ArrayAdapter(p0.context, android.R.layout.simple_spinner_item,lis.map { istar->istar.naziv }.toMutableList())
                    arr1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spin2.adapter=arr1
                    spin2.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                            if(p0!=null)
                            {
                                var  arr2= ArrayAdapter(p0.context, android.R.layout.simple_spinner_item,grupaViewModel.getGroupsByIstrazivanjet(p0.getItemAtPosition(p2) as String).map { istar->istar.naziv }
                                )
                                arr2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                spin3.adapter=arr2
                                upis.isEnabled=grupaViewModel.getGroupsByIstrazivanjet(p0.getItemAtPosition(p2) as String).isNotEmpty()
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
            var inter:Intent=Intent()
            inter.putExtra("godina",spin1.selectedItem.toString())
            inter.putExtra("istrazivanje",spin2.selectedItem.toString())
            inter.putExtra("grupa",spin3.selectedItem.toString())
                setResult(Activity.RESULT_OK,inter)
            finish()
        }
    }
}


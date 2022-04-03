package ba.etf.rma22.projekat

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.GrupaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjaRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var listaAnketa : RecyclerView
    private lateinit var adapterAnketa:AnketaListAdapter
    private lateinit var spiner: Spinner
    private lateinit var upis: FloatingActionButton
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            if (data != null) {
                    var godina=data.getStringExtra("godina")
                var itra=data.getStringExtra("istrazivanje")
                var grupa=data.getStringExtra("grupa")
                var kot=  IstrazivanjaRepository.korisnik.getI().toMutableList()
                    kot.add(Istrazivanje(itra.toString(), godina.toString().toInt()))
                        IstrazivanjaRepository.korisnik.setI(kot)
                IstrazivanjaRepository.korisnik.setGod(godina.toString().toInt())
                AnketaRepository.korisnik.setGod(godina.toString().toInt())

                 var kot1=  AnketaRepository.korisnik.getG().toMutableList()
                kot1.add(Grupa(grupa.toString(),itra.toString()))
                AnketaRepository.korisnik.setG(kot1)
                if(spiner.selectedItem.toString()=="Sve moje ankete")
                {
                    adapterAnketa.updateAnkete(AnketaRepository.getMyAnkete())
                }
            }
        }
    }
    var arrSpin:Array<String> = arrayOf(
        "Sve moje ankete",
        "Sve ankete",
        "Urađene ankete",
        "Buduće ankete",
        "Prošle ankete")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AnketaRepository.korisnik.setG( korisnikG())

        AnketaRepository.korisnik.setGod( 2022)
        IstrazivanjaRepository.korisnik.setGod(2022)
        IstrazivanjaRepository.korisnik.setI(korisnikI())
        spiner=findViewById(R.id.filterAnketa)
        var  arr= ArrayAdapter(this, android.R.layout.simple_spinner_item,arrSpin)
        arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spiner.adapter=arr
        spiner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {


                if(p0!=null) {
                    println(p0.selectedItem.toString())
                    if(p0.selectedItem.toString() == "Sve moje ankete")
                    {
                        adapterAnketa.updateAnkete(AnketaRepository.getMyAnkete())
                    }
                    else if(p0.selectedItem.toString() == "Sve ankete")
                        {
                            adapterAnketa.updateAnkete(AnketaRepository.getAll())
                        }
                    else if(p0.selectedItem.toString()=="Urađene ankete")
                        {
                            adapterAnketa.updateAnkete(AnketaRepository.getDone())
                        }
                    else if(p0.selectedItem.toString()=="Buduće ankete")
                    {
                        adapterAnketa.updateAnkete(AnketaRepository.getFuture())
                    }
                    else if(p0.selectedItem.toString()=="Prošle ankete")
                    {
                        adapterAnketa.updateAnkete(AnketaRepository.getNotTaken())
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        listaAnketa=findViewById(R.id.listaAnketa)
        listaAnketa.layoutManager= GridLayoutManager(this,2, LinearLayoutManager.VERTICAL,false)

        adapterAnketa= AnketaListAdapter(listOf()){ anketa->{}}
        listaAnketa.adapter=adapterAnketa
        upis=findViewById(R.id.dodajIstrazivanjeDugme)
        upis.setOnClickListener{
            val intent = Intent(this, UpisIstrazivanje::class.java)
            intent.putExtra("korisnik",paketI(IstrazivanjaRepository.korisnik.getI()))
            intent.putExtra("godina",AnketaRepository.korisnik.getGod())
            resultLauncher.launch(intent)
        }
    }


}
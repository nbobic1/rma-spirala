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
import ba.etf.rma22.projekat.viewmodel.AnketaViewModel
import ba.etf.rma22.projekat.viewmodel.IstrazivanjeViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var listaAnketa : RecyclerView
    private lateinit var adapterAnketa:AnketaListAdapter
    private lateinit var spiner: Spinner
    private lateinit var upis: FloatingActionButton
    private var korisnik:Korisnik =  Korisnik()
    private var anketaViewModel: AnketaViewModel =AnketaViewModel()
    private var istrazivanjeViewModel:IstrazivanjeViewModel = IstrazivanjeViewModel()
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            if (data != null) {
                    var godina=data.getStringExtra("godina")
                var itra=data.getStringExtra("istrazivanje")
                var grupa=data.getStringExtra("grupa")
                var kot=  istrazivanjeViewModel.getKori().getI().toMutableList()
                    kot.add(Istrazivanje(itra.toString(), godina.toString().toInt()))
                        korisnik.setI(kot)
                korisnik.setGod(godina.toString().toInt())
                istrazivanjeViewModel.setKori(korisnik)
                 var kot1=  anketaViewModel.getKori().getG().toMutableList()
                kot1.add(Grupa(grupa.toString(),itra.toString()))
                korisnik.setG(kot1)
                anketaViewModel.setKori(korisnik)
                if(spiner.selectedItem.toString()=="Sve moje ankete")
                {
                    adapterAnketa.updateAnkete(anketaViewModel.getMyAnkete())
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
       korisnik.setG( korisnikG())
        korisnik.setI(korisnikI())
        anketaViewModel.setKori(korisnik)
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
                        adapterAnketa.updateAnkete(anketaViewModel.getMyAnkete())
                    }
                    else if(p0.selectedItem.toString() == "Sve ankete")
                        {
                            adapterAnketa.updateAnkete(anketaViewModel.getAll())
                        }
                    else if(p0.selectedItem.toString()=="Urađene ankete")
                        {
                            adapterAnketa.updateAnkete(anketaViewModel.getDone())
                        }
                    else if(p0.selectedItem.toString()=="Buduće ankete")
                    {
                        adapterAnketa.updateAnkete(anketaViewModel.getFuture())
                    }
                    else if(p0.selectedItem.toString()=="Prošle ankete")
                    {
                        adapterAnketa.updateAnkete(anketaViewModel.getNotTaken())
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
        upis=findViewById(R.id.upisDugme)
        upis.setOnClickListener{
            val intent = Intent(this, UpisIstrazivanje::class.java)
            intent.putExtra("korisnik",paketI(istrazivanjeViewModel.getKori().getI()))
            intent.putExtra("godina",anketaViewModel.getKori().getGod())
            resultLauncher.launch(intent)
        }
    }


}
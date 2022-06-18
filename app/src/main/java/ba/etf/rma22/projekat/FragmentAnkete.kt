package ba.etf.rma22.projekat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.viewmodel.AnketaViewModel
import ba.etf.rma22.projekat.viewmodel.IstrazivanjeViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentAnkete :Fragment() {

    var arrSpin:Array<String> = arrayOf(
        "Sve moje ankete",
        "Sve ankete",
        "Urađene ankete",
        "Buduće ankete",
        "Prošle ankete")
    private lateinit var listaAnketa : RecyclerView
    private lateinit var adapterAnketa:AnketaListAdapter
    private lateinit var spiner: Spinner
    private lateinit var main:MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        main=(activity as MainActivity)
        var view= inflater.inflate(R.layout.activity_main,container,false)
        spiner=view.findViewById(R.id.filterAnketa)
        var  arr= ArrayAdapter(view.context, android.R.layout.simple_spinner_item,arrSpin)
        arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spiner.adapter=arr
        spiner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {


                if(p0!=null) {
                    println(p0.selectedItem.toString())
                    if(p0.selectedItem.toString() == "Sve moje ankete")
                    {
                       main.anketaViewModel.getUpisane(onSuccess = ::updateAdapter, onError=::onError)
                    }
                    else if(p0.selectedItem.toString() == "Sve ankete")
                    {
                        MainActivity.connection=false;
                      main.anketaViewModel.getAll(onSuccess = ::updateAdapter, onError=::onError)
                    }
                    else if(p0.selectedItem.toString()=="Urađene ankete")
                    {
                        main.anketaViewModel.getUradjene(onSuccess = ::updateAdapter, onError=::onError)

                    }
                    else if(p0.selectedItem.toString()=="Buduće ankete")
                    {
                        main.anketaViewModel.getBuduce(onSuccess = ::updateAdapter, onError=::onError)
                    }
                    else if(p0.selectedItem.toString()=="Prošle ankete")
                    {
                        main.anketaViewModel.getProsle(onSuccess = ::updateAdapter, onError=::onError)
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        listaAnketa=view.findViewById(R.id.listaAnketa)
        listaAnketa.layoutManager= GridLayoutManager(view.context,2, LinearLayoutManager.VERTICAL,false)

        adapterAnketa= AnketaListAdapter(main,listOf()){ anketa->{}}
        listaAnketa.adapter=adapterAnketa
        return view
    }
    fun updateAdapter(a:List<Anketa>)
    {
        println("u adapteruuuuuuuuuuuu ${a.size}")
        adapterAnketa.updateAnkete(a)
    }

    fun onError()
    {
        println("problem s API")
    }

    companion object {
        fun newInstance(): FragmentAnkete = FragmentAnkete()
    }
}
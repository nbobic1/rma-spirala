package ba.etf.rma22.projekat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.models.Pitanje
import java.lang.Math.ceil
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class AnketaListAdapter (private var main:MainActivity, private var anketaL :List<Anketa>, private val onItemClicked: (anketa: Anketa) -> Unit) : RecyclerView.Adapter<AnketaListAdapter.AnketaViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnketaViewHolder
    {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.layout, parent, false)
        return AnketaViewHolder(view)
    }
    override fun getItemCount(): Int = anketaL.size
    override fun onBindViewHolder(holder: AnketaViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
           main.pitanjeAnketaViewModel.provjeraOtvaranjaA(anketaL[position].id,position, onSuccess = ::naKlik,onError=::onError)
        }
       holder.anketaName.text = anketaL[position].naziv
        val k: Double = kotlin.math.round(anketaL[position].progres / 0.2) *0.2*holder.anketaProgress.max;
        holder.anketaProgress.progress=k.roundToInt()
        holder.anketaRnum.text=anketaL[position].nazivIstrazivanja
        val context: Context = holder.anketaCircle.getContext()
        main.odgovorViewModel.getOdgovoriAnketaH(1, onSuccess = ::promijeniBoju, onError = ::onError, holder,context,anketaL[position] )
    }
    fun onError()
    {
        println("problem s API")
    }
    fun naKlik(mojeAnkete:List<Anketa>,buduce:List<Anketa>,uradjene:List<Anketa>,prosle:List<Anketa>,
               pita:List<Pitanje>,odg:List<Odgovor>,at:AnketaTaken,position: Int,nazivI:String)
    {
        if(mojeAnkete.toMutableList().contains(anketaL[position])&&!buduce.toMutableList().contains(anketaL[position]))
        {
            if(uradjene.toMutableList().contains(anketaL[position])||prosle.contains(anketaL[position]))
                main.pitanja(anketaL[position],nazivI,at,1)
            else if(pita.size==odg.size)
            {
                main.pitanja(anketaL[position],nazivI,at,1)
            }
            else
                main.pitanja(anketaL[position],nazivI,at,0)
        }
    }
    fun promijeniBoju(a:List<Odgovor>, b:List<Pitanje>, holder:AnketaViewHolder, context:Context, anketa:Anketa)
    {
        var cal: Calendar = Calendar.getInstance()
        var date: Date = cal.time
        var id: Int=0
        var dateFormat:DateFormat =  SimpleDateFormat("dd.MM.yyyy.")
        if(a.size==b.size)
        {
            //plava
              holder.anketaDatum.text="Anketa urađena: "+dateFormat.format(anketa.datumRada)
            holder.anketaCircle.background= getDrawable(context,R.drawable.plava)
        }
        else if(date<anketa.datumPocetak)
        {
            //zuta
            holder.anketaDatum.text="Vrijeme aktiviranja: "+dateFormat.format(anketa.datumPocetak)
            holder.anketaCircle.background= getDrawable(context,R.drawable.zuta)
        }
        else if((anketa.datumKraj==null&&anketa.datumPocetak<date)||date<anketa.datumKraj)
        {
            //zelena
                if(anketa.datumKraj!=null)
            holder.anketaDatum.text="Vrijeme zatvaranja: "+dateFormat.format(anketa.datumKraj)
            else
                holder.anketaDatum.text="Vrijeme zatvaranja: "

            holder.anketaCircle.background= getDrawable(context,R.drawable.zelena)
        }
        else
        {
            //crvena
                if(anketa.datumKraj!=null)
            holder.anketaDatum.text="Anketa zatvorena: "+dateFormat.format(anketa.datumKraj)
            else println("anketa==${anketa.id}")
            holder.anketaCircle.background= getDrawable(context,R.drawable.crvena)
        }
    }
    fun updateAnkete(anketas: List<Anketa>) {
        this.anketaL = anketas
        notifyDataSetChanged()
    }
    inner class AnketaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val anketaCircle: ImageView = itemView.findViewById(R.id.circle)
        val anketaProgress: ProgressBar =itemView.findViewById(R.id.progresZavrsetka)
        val anketaDatum : TextView =itemView.findViewById(R.id.date1)
        val anketaRnum : TextView =itemView.findViewById(R.id.rNum)
        val anketaName: TextView = itemView.findViewById(R.id.name)
    }
}
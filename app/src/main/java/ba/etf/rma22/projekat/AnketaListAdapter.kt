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
import java.lang.Math.ceil
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class AnketaListAdapter (private var main:MainActivity, private var anketaL :List<Anketa>, private val onItemClicked: (anketa: Anketa) -> Unit) : RecyclerView.Adapter<AnketaListAdapter.AnketaViewHolder>() {
    var cal: Calendar = Calendar.getInstance()


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
            if(main.anketaViewModel.getMyAnkete().contains(anketaL[position])&&!main.anketaViewModel.getFuture().contains(anketaL[position]))
            {

                var k=main.korisnik.getOdg().map{t->t.anketa}
                if(main.anketaViewModel.getDone().contains(anketaL[position])||main.anketaViewModel.getNotTaken().contains(anketaL[position]))
                main.pitanja(anketaL[position],1)
                else if(k.contains(anketaL[position])&&main.korisnik.getOdg()[k.indexOf(anketaL[position])].progrs.compareTo(1.0f)==0)
                {
                    main.pitanja(anketaL[position],1)
                }
                else
                    main.pitanja(anketaL[position],0)
            }
        }
        var date: Date = cal.time
       holder.anketaName.text = anketaL[position].naziv
        val k: Double = kotlin.math.round(anketaL[position].progres / 0.2) *0.2*holder.anketaProgress.max;
        holder.anketaProgress.progress=k.roundToInt()
        holder.anketaRnum.text=anketaL[position].nazivIstrazivanja
        val context: Context = holder.anketaCircle.getContext()
        var id: Int=0
        var dateFormat:DateFormat =  SimpleDateFormat("dd.MM.yyyy.")
        var z=main.korisnik

        if(anketaL[position].progres.compareTo(1.0)==0||(z.getOdg().map { t->t.anketa }.contains(anketaL[position])&&z.getOdg()[z.getOdg().map { t->t.anketa }.indexOf(anketaL[position])].predao))
        {
        //plava
            holder.anketaDatum.text="Anketa urađena: "+dateFormat.format(anketaL[position].datumRada)
            holder.anketaCircle.background= getDrawable(context,R.drawable.plava)
        }
        else if(date<anketaL[position].datumPocetak)
        {
        //zuta
            holder.anketaDatum.text="Vrijeme aktiviranja: "+dateFormat.format(anketaL[position].datumPocetak)
            holder.anketaCircle.background= getDrawable(context,R.drawable.zuta)
        }
        else if(date<anketaL[position].datumKraj)
        {
        //zelena
            holder.anketaDatum.text="Vrijeme zatvaranja: "+dateFormat.format(anketaL[position].datumKraj)
            holder.anketaCircle.background= getDrawable(context,R.drawable.zelena)
        }
        else
        {
            //crvena
            holder.anketaDatum.text="Anketa zatvorena: "+dateFormat.format(anketaL[position].datumKraj)
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
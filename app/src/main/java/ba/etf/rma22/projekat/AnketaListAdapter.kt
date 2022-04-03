package ba.etf.rma22.projekat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.data.models.Anketa
import java.lang.Math.ceil
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class AnketaListAdapter (private var anketaL :List<Anketa>, private val onItemClicked: (anketa: Anketa) -> Unit) : RecyclerView.Adapter<AnketaListAdapter.AnketaViewHolder>() {
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
        cal.set(2021,3,10)
        var date: Date = cal.time
        holder.itemView.setOnClickListener{ onItemClicked(anketaL[position]) }
        holder.anketaName.text = anketaL[position].naziv
        val k: Double = kotlin.math.ceil(anketaL[position].progres / 0.2) *0.2*holder.anketaProgress.max;
        holder.anketaProgress.progress=k.roundToInt()
        holder.anketaRnum.text=anketaL[position].nazivIstrazivanja
        val context: Context = holder.anketaCircle.getContext()
        var id: Int=0
        var dateFormat:DateFormat =  SimpleDateFormat("dd.MM.yyyy.")
        if(anketaL[position].progres.compareTo(1.0)==0)
        {
        //plava
            holder.anketaDatum.text="Anketa urađena: "+dateFormat.format(anketaL[position].datumRada)
            id= context.getResources()
                .getIdentifier("plava", "drawable", context.getPackageName())

        }
        else if(date<anketaL[position].datumPocetak)
        {
        //zuta
            holder.anketaDatum.text="Anketa urađena: "+dateFormat.format(anketaL[position].datumPocetak)
            context.getResources()
                .getIdentifier("zuta", "drawable", context.getPackageName())

        }
        else if(date<anketaL[position].datumKraj)
        {
        //zelena
            holder.anketaDatum.text="Anketa urađena: "+dateFormat.format(anketaL[position].datumKraj)
            context.getResources()
                .getIdentifier("zelena", "drawable", context.getPackageName())

        }
        else
        {
         //crvena
            holder.anketaDatum.text="Anketa urađena: "+dateFormat.format(anketaL[position].datumKraj)
            context.getResources()
                .getIdentifier("crvena", "drawable", context.getPackageName())
        }
        holder.anketaCircle.setImageResource(id)

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
package ba.etf.rma22.projekat

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class StringAdapter(context: Context, @LayoutRes private val layoutResource: Int, private val elements: List<String>,main:FragmentPitanje):
    ArrayAdapter<String>(context, layoutResource, elements) {
    var main=main
    override fun getView(position: Int, newView: View?, parent: ViewGroup): View {
        var newView = newView
        newView = LayoutInflater.from(context).inflate(R.layout.string_adapter, parent, false)
        val textView = newView.findViewById<TextView>(R.id.textView5)
        textView.text=elements[position]
        if(MainActivity.connection)
        newView.setOnClickListener {
            if(!main.gotova){
                textView.setTextColor(Color.parseColor("#0000ff"))
                main.odgovor1=position
                var t:Int=(main.activity as MainActivity).k.fragments.indexOf(main)
                (main.activity as MainActivity).odgovorViewModel.postaviOdgovorAnketa((main.activity as MainActivity).aT.id,main.zet.id,position, onError = ::postavProg)

               }
        }

        if(main.lis==position)
            textView.setTextColor(Color.parseColor("#0000ff"))
        return newView
    }
    fun postavProg(pro:Int)
    {
        var upo=(main.activity as MainActivity).k
        if(upo.fragments.size>0&&upo.fragments[upo.fragments.size-1] is FragmentPredaj)
        {
            (upo.fragments[upo.fragments.size-1]as FragmentPredaj).setPro(pro)
        }
    }
}
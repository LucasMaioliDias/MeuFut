import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.trabalho.projetomeufut.AgendamentoItem
import com.trabalho.projetomeufut.R
import com.trabalho.projetomeufut.quadrasalugadas

class ItemAgendamentoAdapter(private val dataList: List<AgendamentoItem>) : RecyclerView.Adapter<ItemAgendamentoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_agendamento, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.textViewNome.text = item.nome
        holder.textViewData.text = item.data
        holder.textViewhora.text = item.hora
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNome: TextView = itemView.findViewById(R.id.textNomeQuadraAgen)
        val textViewData: TextView = itemView.findViewById(R.id.textDataAgen)
        val textViewhora: TextView = itemView.findViewById(R.id.textHoraAgen)
    }


}
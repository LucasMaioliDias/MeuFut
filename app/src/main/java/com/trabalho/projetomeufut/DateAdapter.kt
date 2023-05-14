import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.trabalho.projetomeufut.R
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import com.trabalho.projetomeufut.menuQuadra

class CardViewAdapter(
    private val items: List<String>,
    private val textAbove: List<String>,
    private val textBelow: String,
    private val onItemClickListener: ((String) -> Unit)? = null
) : RecyclerView.Adapter<CardViewAdapter.ViewHolder>() {
    private var selectedItem = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hora, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val number = items[position]
        holder.textAbove.text = textAbove[position]
        holder.textNumber.text = number
        holder.textBelow.text = textBelow

        if (holder.adapterPosition == selectedItem) {
            holder.cardView.setBackgroundResource(R.drawable.card_selected_background)
        } else {
            holder.cardView.setBackgroundResource(R.drawable.card_default_background)
        }

        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                selectedItem = position
                notifyDataSetChanged()

                val selectedDate = items[position] + " " + textBelow
                onItemClickListener?.invoke(selectedDate)
                

            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val textAbove: TextView = itemView.findViewById(R.id.textAbove)
        val textNumber: TextView = itemView.findViewById(R.id.textNumber)
        val textBelow: TextView = itemView.findViewById(R.id.textBelow)
    }
}

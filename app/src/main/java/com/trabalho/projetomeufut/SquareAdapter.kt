import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.trabalho.projetomeufut.R

class SquareAdapter(private val hours: List<String>) : RecyclerView.Adapter<SquareAdapter.ViewHolder>() {
    private var onItemClickListener: ((List<String>) -> Unit)? = null

    fun setOnItemClickListener(listener:( List<String>) -> Unit) {
        onItemClickListener = listener
    }
    private val selectedPositions = mutableSetOf<Int>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_square, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hour = hours[position]
        holder.bindViewHour(hour)

        val isSelected = selectedPositions.contains(position)
        holder.setSelected(isSelected)

        holder.itemView.setOnClickListener {
            if (isSelected) {
                selectedPositions.remove(position)
                holder.setSelected(false)
            } else {
                if (selectedPositions.size < 2) {
                    selectedPositions.add(position)
                    holder.setSelected(true)
                } else {
                    val firstSelectedPosition = selectedPositions.first()
                    selectedPositions.remove(firstSelectedPosition)
                    notifyItemChanged(firstSelectedPosition)
                    selectedPositions.add(position)
                    holder.setSelected(true)
                }
            }


            val selectedHours = selectedPositions.map { hours[it] }

            onItemClickListener?.invoke(selectedHours)
        }
    }


    override fun getItemCount(): Int {
        return hours.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardview2: CardView = itemView.findViewById(R.id.cardview2)
        private val textViewHour: TextView = itemView.findViewById(R.id.textViewHour)

        fun bindViewHour(hour: String) {
            textViewHour.text = hour
            itemView.setOnClickListener {

            }

        }

        fun setSelected(isSelected: Boolean) {
            if (isSelected) {
                cardview2.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.darckcyangreen))
            } else {
                cardview2.setCardBackgroundColor(Color.WHITE)
            }
        }
    }
}

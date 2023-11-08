package site.pnpl.mira.ui.home.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import site.pnpl.mira.R
import site.pnpl.mira.data.entity.CheckIn
import site.pnpl.mira.databinding.ItemCheckInBinding

class HomeAdapter() : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private val checkIns = mutableListOf<CheckIn>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_check_in, parent, false)
        return ViewHolder(ItemCheckInBinding.bind(view))
    }

    override fun getItemCount(): Int = checkIns.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        checkIns[position].apply {
            holder.day.text = emotionId.toString()
            holder.month.text = "сент"
            holder.dayOfWeekAndTime.text = "Пн, 12:00"
            holder.emotion.text = createdAt
            holder.emotionDrawable.setImageDrawable(AppCompatResources.getDrawable(holder.itemView.context, R.drawable.emotion_anger))
        }
    }

    fun setItemsList(items: List<CheckIn>) {
        checkIns.clear()
        checkIns.addAll(items)
        update()
    }

    fun addItem(item: CheckIn) {
        checkIns.add(item)
        update()
    }

    private fun update() {
        notifyDataSetChanged()
//        notifyItemInserted(items.size - 1)
    }

    class ViewHolder(binding: ItemCheckInBinding) : RecyclerView.ViewHolder(binding.root) {
        val day = binding.day
        val month = binding.month
        val dayOfWeekAndTime = binding.dayOfWeekAndTime
        val emotion = binding.emotion
        val emotionDrawable = binding.emotionDrawable
    }

    object HomeDiffCallback : DiffUtil.ItemCallback<CheckInItem>() {
        override fun areItemsTheSame(oldItem: CheckInItem, newItem: CheckInItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CheckInItem, newItem: CheckInItem): Boolean {
            return oldItem.dateTime == newItem.dateTime

        }
    }

}



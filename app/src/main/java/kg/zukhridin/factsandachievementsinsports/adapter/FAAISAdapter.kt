package kg.zukhridin.factsandachievementsinsports.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kg.zukhridin.factsandachievementsinsports.databinding.FaaisItemBinding
import kg.zukhridin.factsandachievementsinsports.dto.FAAISDto

class FAAISAdapter : ListAdapter<FAAISDto, FAAISAdapter.ViewHolder>(CustomDiffUtil()) {
    class ViewHolder(private val binding: FaaisItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(faaisDto: FAAISDto) = with(binding) {
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FaaisItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CustomDiffUtil() : DiffUtil.ItemCallback<FAAISDto>() {
    override fun areItemsTheSame(oldItem: FAAISDto, newItem: FAAISDto): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: FAAISDto, newItem: FAAISDto): Boolean {
        return oldItem.question == newItem.question
    }

}
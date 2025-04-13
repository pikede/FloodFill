package com.example.floodfill.view_based

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.floodfill.databinding.GridItemBinding

class GridItemAdapter(private val onFloodFill: (Int) -> Unit) :
    RecyclerView.Adapter<GridItemAdapter.GridItemViewHolder>() {

    inner class GridItemViewHolder(private val binding: GridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onFloodFill(adapterPosition)
            }
        }

        fun bind(gridItem: GridItem) {
            binding.root.setBackgroundColor(gridItem.color)
        }
    }

    private val gridDataSet = mutableListOf<GridItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = GridItemBinding.inflate(layoutInflater, parent, false)
        return GridItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridItemViewHolder, position: Int) {
        holder.bind(gridDataSet[position])
    }

    override fun getItemCount(): Int {
        return gridDataSet.size
    }

    fun updateGridDataset(newDataSet: List<GridItem>) {
        gridDataSet.clear()
        gridDataSet.addAll(newDataSet)
        notifyDataSetChanged()
    }
}
package com.tarc.edu.etrack.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tarc.edu.etrack.R
import com.tarc.edu.etrack.databinding.ItemLayoutBinding
import com.bumptech.glide.Glide
import com.tarc.edu.etrack.ui.station_details.StationData

class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private var stationList: List<StationData> = emptyList()

    inner class ViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(station: StationData) {
            binding.textViewStationTitle.text = station.stationName
            binding.textViewStationStatus.text = getStatusText(station.openTime, station.closeTime)
            binding.textViewStationNearby.text = station.stationAddress

            // Load station image from Firebase Storage
            Glide.with(binding.root)
                .load(station.imageUrl)
                .placeholder(R.drawable.placeholder_image) // Placeholder image while loading
                .error(R.drawable.error_image) // Error image if loading fails
                .into(binding.imageViewStation)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val station = stationList[position]
        holder.bind(station)
    }

    override fun getItemCount(): Int {
        return stationList.size
    }

    fun setData(data: List<StationData>) {
        stationList = data
        notifyDataSetChanged()
    }

    private fun getStatusText(openTime: Long, closeTime: Long): String {
        // Implement logic to check if the station is open based on the current time
        // You can use Calendar or other time-related methods for this
        // Example: return "Open" if the current time is between openTime and closeTime
        return "Open"
    }
}



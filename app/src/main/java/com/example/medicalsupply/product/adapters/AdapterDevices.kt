package com.example.medicalsupply.product.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.medicalsupply.databinding.ItemDeviceBinding
import com.example.medicalsupply.models.ModelDevices


class AdapterDevices : RecyclerView.Adapter<AdapterDevices.MyHolder>() {
    var deviceList: ArrayList<ModelDevices>? = null

    private lateinit var onClick: (String) -> Unit
    fun setOnclick(onClick: (String) -> Unit) {
        this.onClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = ItemDeviceBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return deviceList?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data = deviceList!![position]
        holder.bind(data)
    }


    inner class MyHolder(private val binding: ItemDeviceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onClick.invoke(deviceList!![layoutPosition].id)
            }
        }

        fun bind(deviceResults: ModelDevices) {
            binding.textTitle.text = deviceResults.name
            Glide.with(binding.root.context)
                .load(deviceResults.imageUrl)
                .into(binding.imageProduct)
        }

    }
}
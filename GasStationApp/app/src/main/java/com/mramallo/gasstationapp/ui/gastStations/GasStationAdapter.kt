package com.mramallo.gasstationapp.ui.gastStations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mramallo.gasstationapp.R
import com.mramallo.gasstationapp.databinding.ItemStoreBinding
import com.mramallo.gasstationapp.domain.model.GeneralDataGasStation

class GasStationAdapter(
    private val stores: List<GeneralDataGasStation>,
    private val onClickListener: (GeneralDataGasStation) -> Unit
) :
    RecyclerView.Adapter<GasStationAdapter.GasStationViewHolder>() {

    private lateinit var binding: ItemStoreBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GasStationViewHolder {
        binding = ItemStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GasStationViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: GasStationViewHolder, position: Int) {
        val item = stores[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = stores.size


    class GasStationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemStoreBinding.bind(view)

        fun render(store: GeneralDataGasStation, onClickListener: (GeneralDataGasStation) -> Unit) {

            binding.tvTitleStore.text = store.name
            binding.tvSubtitleStore.text = store.shortDescription

            if(store.logo != null) {
                Glide.with(binding.ivImageStore.context).load(store.logo.url).into(binding.ivImageStore)
            } else {
                binding.ivImageStore.setImageDrawable(ContextCompat.getDrawable(
                    itemView.context, R.drawable.placeholder_image))
            }

            when(store.category) {
                "SHOPPING" -> {
                    binding.ivIconCategory.setImageDrawable(
                        ContextCompat.getDrawable(
                        itemView.context, R.drawable.ees_white))
                    binding.llHeadStore.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.orange_dark))
                }

                "FOOD" -> {
                    binding.ivIconCategory.setImageDrawable(
                        ContextCompat.getDrawable(
                        itemView.context, R.drawable.catering_white))
                    binding.llHeadStore.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.yellow_dark))
                }

                "BEAUTY" -> {
                    binding.ivIconCategory.setImageDrawable(
                        ContextCompat.getDrawable(
                        itemView.context, R.drawable.cart_white))
                    binding.llHeadStore.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.pink_dark))

                }

                "LEISURE" -> {
                    binding.ivIconCategory.setImageDrawable(
                        ContextCompat.getDrawable(
                        itemView.context, R.drawable.leisure_white))
                    binding.llHeadStore.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.purple_dark))

                }
                "OTHER" -> {
                    binding.ivIconCategory.setImageDrawable(
                        ContextCompat.getDrawable(
                        itemView.context, R.drawable.car_wash_white))
                    binding.llHeadStore.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.green_dark))
                }
            }


            itemView.setOnClickListener {
                onClickListener(store)
            }
        }
    }


}
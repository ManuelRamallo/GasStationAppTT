package com.mramallo.gasstationapp.ui.gastStations

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mramallo.gasstationapp.R
import com.mramallo.gasstationapp.databinding.ItemCategoryBinding
import com.mramallo.gasstationapp.domain.model.GeneralDataGasStation
import kotlinx.coroutines.currentCoroutineContext
import java.util.*

class CategoriesAdapter(
    private val categoryList: List<String>,
    private val onClickListener: (String) -> Unit
) :
    RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    private lateinit var binding: ItemCategoryBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val item = categoryList[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = categoryList.size


    class CategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemCategoryBinding.bind(view)

        fun render(category: String, onClickListener: (String) -> Unit) {

            binding.tvCategory.text = category.lowercase().replaceFirstChar { it.uppercase() }

            when(category) {
                "SHOPPING" -> {
                    binding.ivCategoryIcon.setImageDrawable(ContextCompat.getDrawable(
                        itemView.context, R.drawable.ees_colour))
                    binding.tvCategory.setTextColor(ContextCompat.getColor(itemView.context, R.color.orange_dark))
                }

                "FOOD" -> {
                    binding.ivCategoryIcon.setImageDrawable(ContextCompat.getDrawable(
                        itemView.context, R.drawable.catering_colour))
                    binding.tvCategory.setTextColor(ContextCompat.getColor(itemView.context, R.color.yellow_dark))
                }

                "BEAUTY" -> {
                    binding.ivCategoryIcon.setImageDrawable(ContextCompat.getDrawable(
                        itemView.context, R.drawable.cart_colour))
                    binding.tvCategory.setTextColor(ContextCompat.getColor(itemView.context, R.color.pink_dark))

                }

                "LEISURE" -> {
                    binding.ivCategoryIcon.setImageDrawable(ContextCompat.getDrawable(
                        itemView.context, R.drawable.leisure_colour))
                    binding.tvCategory.setTextColor(ContextCompat.getColor(itemView.context, R.color.purple_dark))

                }
                "OTHER" -> {
                    binding.ivCategoryIcon.setImageDrawable(ContextCompat.getDrawable(
                        itemView.context, R.drawable.car_wash_colour))
                    binding.tvCategory.setTextColor(ContextCompat.getColor(itemView.context, R.color.green_dark))
                }
            }

            itemView.setOnClickListener {
                onClickListener(category)
            }

        }
    }


}




package comp4097.comp.hkbu.edu.hk.couponredemption.ui.coins

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import comp4097.comp.hkbu.edu.hk.couponredemption.R
import comp4097.comp.hkbu.edu.hk.couponredemption.data.Coupons
import comp4097.comp.hkbu.edu.hk.couponredemption.databinding.FragmentMallsItemBinding

class FilteredCoupons2RecyclerViewAdapter (
    private val values: List<Coupons>
) : RecyclerView.Adapter<FilteredCoupons2RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentMallsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.mall
        holder.contentView.text = item.restaurant
//        holder.contentView.text = item.content
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentMallsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
//        val contentView: TextView = binding.content

        init{
            binding.root.setOnClickListener{
                it.findNavController().navigate(
                    R.id.action_coinsFragment_to_couponDetails,
                    bundleOf(Pair("restaurant", contentView.text.toString()))
                )
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}
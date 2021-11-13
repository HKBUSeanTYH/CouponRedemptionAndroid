package comp4097.comp.hkbu.edu.hk.couponredemption.ui.redeem

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import comp4097.comp.hkbu.edu.hk.couponredemption.R
import comp4097.comp.hkbu.edu.hk.couponredemption.data.Coupons
import comp4097.comp.hkbu.edu.hk.couponredemption.databinding.FragmentRedeemedItemBinding

import comp4097.comp.hkbu.edu.hk.couponredemption.ui.redeem.placeholder.PlaceholderContent.PlaceholderItem
//import comp4097.comp.hkbu.edu.hk.couponredemption.ui.redeem.databinding.FragmentRedeemedItemBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class RedeemListRecyclerViewAdapter(
    private val values: List<Coupons>
) : RecyclerView.Adapter<RedeemListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentRedeemedItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        //holder.idView.text = item.id
        holder.contentView.text = item.restaurant
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentRedeemedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        init {
            binding.root.setOnClickListener{
                it.findNavController().navigate(
                    R.id.action_redeemedListFragment_to_couponDetails,
                    bundleOf(Pair("restaurant", contentView.text.toString()))
                )
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}
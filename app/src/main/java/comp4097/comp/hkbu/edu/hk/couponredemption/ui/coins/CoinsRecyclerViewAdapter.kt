package comp4097.comp.hkbu.edu.hk.couponredemption.ui.coins

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import comp4097.comp.hkbu.edu.hk.couponredemption.R
import comp4097.comp.hkbu.edu.hk.couponredemption.data.Coins
import comp4097.comp.hkbu.edu.hk.couponredemption.databinding.FragmentCoinsItemBinding

import comp4097.comp.hkbu.edu.hk.couponredemption.ui.coins.placeholder.PlaceholderContent.PlaceholderItem

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class CoinsRecyclerViewAdapter(
    private val values: List<Coins>
) : RecyclerView.Adapter<CoinsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentCoinsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.range
        //holder.contentView.text = item.content
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentCoinsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        //val contentView: TextView = binding.content

        init {
            binding.root.setOnClickListener {
                it.findNavController().navigate(
                    R.id.action_coinsFragment_self,
                    bundleOf(Pair("coins", idView.text.toString()))
                )
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + idView.text + "'"
        }
    }

}
package comp4097.comp.hkbu.edu.hk.couponredemption.ui.coupons

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.squareup.picasso.Picasso
import comp4097.comp.hkbu.edu.hk.couponredemption.R
import comp4097.comp.hkbu.edu.hk.couponredemption.data.Coupons
import comp4097.comp.hkbu.edu.hk.couponredemption.databinding.FragmentCouponsItemBinding

import comp4097.comp.hkbu.edu.hk.couponredemption.ui.coupons.placeholder.PlaceholderContent.PlaceholderItem
//import comp4097.comp.hkbu.edu.hk.couponredemption.ui.coupons.databinding.FragmentCouponsItemBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class CouponsRecyclerViewAdapter(
    private val values: List<Coupons>
) : RecyclerView.Adapter<CouponsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentCouponsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

//        Picasso.get().load(item.image).into(holder.couponsImageView)
        holder.restaurantTextView.text = item.restaurant
        holder.titleTextView.text = item.title
        holder.coinsTextView.text = item.coins.toString()

        if (item.image != "")
            Picasso.get().load(item.image).into(holder.couponsImageView)
        else
            holder.couponsImageView.setImageDrawable(holder.itemView
                .context.getDrawable(R.drawable.ic_notifications_black_24dp))

//        holder.idView.text = item.id
//        holder.contentView.text = item.content
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentCouponsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val couponsImageView: ImageView = binding.couponImageView
        val restaurantTextView: TextView = binding.restaurantTextView
        val titleTextView: TextView = binding.titleTextView
        val coinsTextView: TextView = binding.coinsTextView

        init {
            binding.root.setOnClickListener{
                it.findNavController().navigate(
                    R.id.action_couponsListFragment_to_couponDetails,
                    bundleOf(Pair("restaurant", restaurantTextView.text.toString()))
                )
            }
        }

//        val idView: TextView = binding.itemNumber
//        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + restaurantTextView.text + "'"
        }
    }

}
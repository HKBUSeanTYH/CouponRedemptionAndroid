package comp4097.comp.hkbu.edu.hk.couponredemption.ui.coupons

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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

        Picasso.get().load(item.image).into(holder.couponsImageView)
        holder.restaurantTextView.text = item.restaurant
        holder.descriptionTextView.text = item.description
        holder.coinsTextView.text = item.coins

//        holder.idView.text = item.id
//        holder.contentView.text = item.content
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentCouponsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val couponsImageView: ImageView = binding.couponImageView
        val restaurantTextView: TextView = binding.restaurantTextView
        val descriptionTextView: TextView = binding.descriptionTextView
        val coinsTextView: TextView = binding.coinsTextView

//        val idView: TextView = binding.itemNumber
//        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + restaurantTextView.text + "'"
        }
    }

}
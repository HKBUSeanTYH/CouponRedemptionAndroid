package comp4097.comp.hkbu.edu.hk.couponredemption.ui.profile

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import comp4097.comp.hkbu.edu.hk.couponredemption.R
import comp4097.comp.hkbu.edu.hk.couponredemption.data.ProfileItem

import comp4097.comp.hkbu.edu.hk.couponredemption.placeholder.PlaceholderContent.PlaceholderItem
import comp4097.comp.hkbu.edu.hk.couponredemption.databinding.FragmentProfileItemBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ProfileListRecyclerViewAdapter(
    private val values: List<ProfileItem>
) : RecyclerView.Adapter<ProfileListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentProfileItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        //holder.idView.text = item.id
        holder.contentView.text = item.str
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentProfileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        init{
            binding.root.setOnClickListener{
                if (contentView.text.toString().equals("Login")){
                    it.findNavController().navigate(
                        //replace with navigate to login fragment
                        R.id.action_profileListFragment_to_customLoginFragment
                        //, bundleOf(Pair("login", contentView.text.toString()))
                    )
                }else if (contentView.text.toString().equals("Redeemed")){

                }

            }
        }

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}
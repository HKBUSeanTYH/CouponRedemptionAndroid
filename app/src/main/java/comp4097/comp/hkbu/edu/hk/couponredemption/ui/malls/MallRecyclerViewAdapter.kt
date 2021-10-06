package comp4097.comp.hkbu.edu.hk.couponredemption.ui.malls

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import comp4097.comp.hkbu.edu.hk.couponredemption.R
import comp4097.comp.hkbu.edu.hk.couponredemption.data.Mall
import comp4097.comp.hkbu.edu.hk.couponredemption.databinding.FragmentMallsItemBinding

import comp4097.comp.hkbu.edu.hk.couponredemption.ui.malls.placeholder.PlaceholderContent.PlaceholderItem
//import comp4097.comp.hkbu.edu.hk.couponredemption.ui.malls.databinding.FragmentMallsItemBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MallRecyclerViewAdapter(
    private val values: List<Mall>
) : RecyclerView.Adapter<MallRecyclerViewAdapter.ViewHolder>() {

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
//        holder.contentView.text = item.content
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentMallsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.content
//        val contentView: TextView = binding.content

        init{
            binding.root.setOnClickListener{
                it.findNavController().navigate(
                    R.id.action_mallsFragment_self,
                    bundleOf(Pair("mall", idView.text.toString()))
                )
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + idView.text + "'"
        }
    }

}
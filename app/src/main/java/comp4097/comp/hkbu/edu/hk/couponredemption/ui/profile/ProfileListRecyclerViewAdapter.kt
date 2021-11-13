package comp4097.comp.hkbu.edu.hk.couponredemption.ui.profile

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import comp4097.comp.hkbu.edu.hk.couponredemption.R
import comp4097.comp.hkbu.edu.hk.couponredemption.data.ProfileItem

import comp4097.comp.hkbu.edu.hk.couponredemption.placeholder.PlaceholderContent.PlaceholderItem
import comp4097.comp.hkbu.edu.hk.couponredemption.databinding.FragmentProfileItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ProfileListRecyclerViewAdapter(
    private val values: List<ProfileItem>, private val model: LoginViewModel
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
                    it.findNavController().navigate(
                        //replace with navigate to login fragment
                        R.id.action_profileListFragment_to_redeemedListFragment
                        //, bundleOf(Pair("login", contentView.text.toString()))
                    )
                }else if (contentView.text.toString().equals("Logout")){
                    CoroutineScope(Dispatchers.IO).launch {
                        logoutReq(contentView.context.getString(R.string.url)+"user/logout")
                    }
                    val navcontroller = it.findNavController()
                    navcontroller.run {
                        popBackStack()
                        navigate(R.id.profileListFragment)
                    }
                }

            }
        }

        fun logoutReq(url: String): String{
            var builder = StringBuilder()
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.setDoOutput(true)

            try{
                var data: Int = connection.inputStream.read()
                while (data != -1) {
                    builder.append(data.toChar())
                    data = connection.inputStream.read()
                }
            }catch(e: Exception){
                builder = StringBuilder()
                builder.append(connection.responseMessage)
            }finally {
                connection.disconnect()
            }
            CoroutineScope(Dispatchers.Main).launch {
                model.sendMessage(false)
            }
            return builder.toString()
        }

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}
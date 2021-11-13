package comp4097.comp.hkbu.edu.hk.couponredemption.ui.redeem

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import comp4097.comp.hkbu.edu.hk.couponredemption.Network.Companion.getTextFromNetwork
import comp4097.comp.hkbu.edu.hk.couponredemption.R
import comp4097.comp.hkbu.edu.hk.couponredemption.data.Coupons
import comp4097.comp.hkbu.edu.hk.couponredemption.data.UserCoupon
import comp4097.comp.hkbu.edu.hk.couponredemption.ui.redeem.placeholder.PlaceholderContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 */
class redeemedListFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_redeemed_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                lateinit var coupons: List<Coupons>
                CoroutineScope(Dispatchers.IO).launch {
                    val response = getTextFromNetwork(getString(R.string.url)+"user/redeem")
                    val usercoupons = Gson().fromJson<UserCoupon>(response, object : TypeToken<UserCoupon>() {}.type)
                    coupons = usercoupons.coupons
                    CoroutineScope(Dispatchers.Main).launch {
                        adapter = RedeemListRecyclerViewAdapter(coupons)
                    }
                }
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            redeemedListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
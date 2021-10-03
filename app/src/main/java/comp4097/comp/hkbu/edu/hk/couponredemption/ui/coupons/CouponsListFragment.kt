package comp4097.comp.hkbu.edu.hk.couponredemption.ui.coupons

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import comp4097.comp.hkbu.edu.hk.couponredemption.R
import comp4097.comp.hkbu.edu.hk.couponredemption.data.Coupons
import comp4097.comp.hkbu.edu.hk.couponredemption.ui.coupons.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class CouponsListFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_coupons_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
//                adapter = CouponsRecyclerViewAdapter(PlaceholderContent.ITEMS)
                val couponImage = resources.getStringArray(R.array.couponImage)
                val couponRestaurant = resources.getStringArray(R.array.couponRestaurant)
                val couponDescription = resources.getStringArray(R.array.couponDescription)
                val couponCoins = resources.getStringArray(R.array.couponCoins)

                val coupons =   mutableListOf<Coupons>()
                for (i in 0..(couponRestaurant.size - 1)){
                    coupons.add(Coupons(couponImage[i], couponRestaurant[i], couponDescription[i], couponCoins[i]))
                }

                adapter = CouponsRecyclerViewAdapter(coupons)
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
            CouponsListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
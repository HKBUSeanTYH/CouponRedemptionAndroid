package comp4097.comp.hkbu.edu.hk.couponredemption.ui.malls

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import comp4097.comp.hkbu.edu.hk.couponredemption.R
import comp4097.comp.hkbu.edu.hk.couponredemption.data.AppDatabase
import comp4097.comp.hkbu.edu.hk.couponredemption.data.SampleData
import comp4097.comp.hkbu.edu.hk.couponredemption.ui.malls.placeholder.PlaceholderContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 */
class MallsFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_malls_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                val mall = arguments?.getString("mall")
                if (mall  == null)
                    adapter = MallRecyclerViewAdapter(SampleData.MALL)
                else {
                    CoroutineScope(Dispatchers.IO).launch {
                        val dao = AppDatabase.getInstance(context).couponsDao()
                        val coupons = dao.findCouponsByMall(mall)
                        CoroutineScope(Dispatchers.Main).launch {
                            adapter = FilteredCouponsRecyclerViewAdapter(coupons)
                        }
                    }
//                    adapter = FilteredCouponsRecyclerViewAdapter(SampleData.FILTEREDCOUPONS.filter {
//                        it.mall == mall
//                    })
//                adapter = MallRecyclerViewAdapter(SampleData.MALL)
                    //to enable the back-arrow in the ActionBar.
                    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
            MallsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
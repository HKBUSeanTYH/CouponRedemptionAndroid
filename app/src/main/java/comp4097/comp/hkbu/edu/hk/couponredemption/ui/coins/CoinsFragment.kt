package comp4097.comp.hkbu.edu.hk.couponredemption.ui.coins

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
import comp4097.comp.hkbu.edu.hk.couponredemption.ui.coins.placeholder.PlaceholderContent
import comp4097.comp.hkbu.edu.hk.couponredemption.ui.malls.FilteredCouponsRecyclerViewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 */
class CoinsFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_coins_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                val range = arguments?.getString("coins")
                val rangestr = range.toString()
                if (range == null){
                    adapter = CoinsRecyclerViewAdapter(SampleData.COINRANGE)
                }else if (rangestr.equals("Coins <= 300")) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val dao = AppDatabase.getInstance(context).couponsDao()
                        val somecoupons = dao.findCouponsLessThanEquals(300)
                        CoroutineScope(Dispatchers.Main).launch {
                            adapter = FilteredCouponsRecyclerViewAdapter(somecoupons)
                        }
                    }
                    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }else if (rangestr.equals("300 < Coins < 600")) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val dao = AppDatabase.getInstance(context).couponsDao()
                        val somecoupons = dao.findCouponsBetween(300, 600)
                        CoroutineScope(Dispatchers.Main).launch {
                            adapter = FilteredCouponsRecyclerViewAdapter(somecoupons)
                        }
                    }
                    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }else {
                    CoroutineScope(Dispatchers.IO).launch {
                        val dao = AppDatabase.getInstance(context).couponsDao()
                        val somecoupons = dao.findCouponsMoreThanEquals(600)
                        CoroutineScope(Dispatchers.Main).launch {
                            adapter = FilteredCouponsRecyclerViewAdapter(somecoupons)
                        }
                    }
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
            CoinsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
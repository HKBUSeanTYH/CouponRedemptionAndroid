package comp4097.comp.hkbu.edu.hk.couponredemption.ui.coupons

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.room.CoroutinesRoom
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import comp4097.comp.hkbu.edu.hk.couponredemption.Network
import comp4097.comp.hkbu.edu.hk.couponredemption.R
import comp4097.comp.hkbu.edu.hk.couponredemption.data.AppDatabase
import comp4097.comp.hkbu.edu.hk.couponredemption.data.Coupons
import comp4097.comp.hkbu.edu.hk.couponredemption.ui.coupons.placeholder.PlaceholderContent
import comp4097.comp.hkbu.edu.hk.couponredemption.ui.malls.MallsFragment.Companion.ARG_COLUMN_COUNT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

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

                CoroutineScope(Dispatchers.IO).launch {
                    val dao = AppDatabase.getInstance(view.context).couponsDao()
                    val coupons = dao.getAllCoupons()

                    CoroutineScope(Dispatchers.Main).launch {
                        adapter = CouponsRecyclerViewAdapter(coupons)
                    }
                }
//                adapter = CouponsRecyclerViewAdapter(PlaceholderContent.ITEMS)
//                val couponImage = resources.getStringArray(R.array.couponImage)
//                val couponRestaurant = resources.getStringArray(R.array.couponRestaurant)
//                val couponDescription = resources.getStringArray(R.array.couponDescription)
//                val couponCoins = resources.getStringArray(R.array.couponCoins)
//
//                val coupons =   mutableListOf<Coupons>()
//                for (i in 0..(couponRestaurant.size - 1)){
//                    coupons.add(Coupons(0,0,i,"",couponRestaurant[i],"",
//                        "", couponImage[i],0, Integer.parseInt(couponCoins[i]),"",couponDescription[i]))
//                }
//
//                adapter = CouponsRecyclerViewAdapter(coupons)
            }
        }
        return view
    }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val recyclerView = inflater.inflate(
//            R.layout.fragment_coupons_list, container, false
//        ) as RecyclerView
//        recyclerView.layoutManager = LinearLayoutManager(context)
//        reloadData(recyclerView)
//        return recyclerView
//    }
//    private fun reloadData(recyclerView: RecyclerView) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val dao = AppDatabase.getInstance(recyclerView.context).couponsDao()
//
//            val coupons = dao.getAllCoupons()
//            CoroutineScope(Dispatchers.Main).launch {
//                recyclerView.adapter = CouponsRecyclerViewAdapter(coupons)
//            }
//        }
//
//        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
//    }

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
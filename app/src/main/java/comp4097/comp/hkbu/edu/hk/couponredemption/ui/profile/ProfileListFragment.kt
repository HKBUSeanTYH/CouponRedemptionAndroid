package comp4097.comp.hkbu.edu.hk.couponredemption.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import comp4097.comp.hkbu.edu.hk.couponredemption.R
import comp4097.comp.hkbu.edu.hk.couponredemption.data.ProfileItem
import comp4097.comp.hkbu.edu.hk.couponredemption.data.SampleData
import comp4097.comp.hkbu.edu.hk.couponredemption.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class ProfileListFragment : Fragment() {

    private var columnCount = 1
    lateinit var model: LoginViewModel

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
        val view = inflater.inflate(R.layout.fragment_profile_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                val model = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
                model.loginstatus.observe(viewLifecycleOwner, Observer {
                    if (it){
                        adapter = ProfileListRecyclerViewAdapter(SampleData.LOGOUTITEMS)
                    }else if (!it){
                        adapter =  ProfileListRecyclerViewAdapter(SampleData.LOGINITEMS)
                    }
                })

//                if (status == false){
//                    adapter = ProfileListRecyclerViewAdapter(SampleData.LOGOUTITEMS)
//                }else if (status == true){
//                    adapter =  ProfileListRecyclerViewAdapter(SampleData.LOGINITEMS)
//                }else{
//                    adapter = ProfileListRecyclerViewAdapter(SampleData.profileitems)
//                }

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
            ProfileListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
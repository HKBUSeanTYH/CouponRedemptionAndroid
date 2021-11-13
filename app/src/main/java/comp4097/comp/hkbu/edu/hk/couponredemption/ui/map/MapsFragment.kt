package comp4097.comp.hkbu.edu.hk.couponredemption.ui.map

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import comp4097.comp.hkbu.edu.hk.couponredemption.R
import comp4097.comp.hkbu.edu.hk.couponredemption.data.AppDatabase
import comp4097.comp.hkbu.edu.hk.couponredemption.data.Mall
import comp4097.comp.hkbu.edu.hk.couponredemption.data.MallDao
import comp4097.comp.hkbu.edu.hk.couponredemption.ui.malls.MallRecyclerViewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapsFragment : Fragment() {
    private var malls: List<Mall> = emptyList()
    private var mallstr: String = ""
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        CoroutineScope(Dispatchers.IO).launch {
            val dao = AppDatabase.getInstance(requireContext()).mallDao()
//            val malls = dao.getAllMalls()
            val mall = dao.getMallByName(mallstr)

            val mallmarker = LatLng(mall.latitude, mall.longitude)

            //val dao = context?.let { AppDatabase.getInstance(it).mallDao() }
            //val malls = dao?.getAllMalls()
            //val mall = mallstr?.let { dao?.getMallByName(it) }

            //val mallmarker = mall?.let { LatLng(mall.latitude, mall.longitude) }

            CoroutineScope(Dispatchers.Main).launch {
                if (malls != null) {
                    for (item in malls){
                        val itemMarker = LatLng(item.latitude, item.longitude)
                        googleMap.addMarker(MarkerOptions().position(itemMarker).title(item.mall))
                    }
                }
                //googleMap.moveCamera(CameraUpdateFactory.newLatLng(mallmarker))
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mallmarker, 13F))
            }
        }

//        val sydney = LatLng(-34.0, 151.0)
//        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maps, container, false)

        mallstr = requireArguments().getString("mall")!!
        CoroutineScope(Dispatchers.IO).launch {
            val dao = AppDatabase.getInstance(requireContext()).mallDao()
            malls = dao.getAllMalls()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}
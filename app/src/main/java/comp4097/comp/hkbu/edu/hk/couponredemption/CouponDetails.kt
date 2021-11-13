package comp4097.comp.hkbu.edu.hk.couponredemption

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import comp4097.comp.hkbu.edu.hk.couponredemption.data.AppDatabase
import comp4097.comp.hkbu.edu.hk.couponredemption.data.Coupons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CouponDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class CouponDetails : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_coupon_details, container, false
        )

        val restaurant = arguments?.getString("restaurant")
        val str = restaurant.toString()

        CoroutineScope(Dispatchers.IO).launch {
            val dao = AppDatabase.getInstance(view.context).couponsDao()
            val coupon = dao.findCouponsByName(str)

            val couponImageView2 = view.findViewById<ImageView>(R.id.couponImageView2)
            val titletextview2 = view.findViewById<TextView>(R.id.titleTextView2)
            val detailsTextView3 = view.findViewById<TextView>(R.id.detailsTextView3)
            val mallTextView = view.findViewById<TextView>(R.id.mallTextView2)
            val quotaTextView = view.findViewById<TextView>(R.id.coinsTextView2)
            val validTextView = view.findViewById<TextView>(R.id.validTextView)

            val redeemButton = view.findViewById<Button>(R.id.redeemButton)
            val addressButton = view.findViewById<Button>(R.id.addressButton)

            CoroutineScope(Dispatchers.Main).launch {
                if (coupon.image != "")
                    Picasso.get().load(coupon.image).into(couponImageView2)
            }
            titletextview2.setText(coupon.title)
            detailsTextView3.setText(coupon.details)
            mallTextView.setText(coupon.mall)
            quotaTextView.setText(coupon.coins.toString())
            validTextView.setText(coupon.valid)

            redeemButton.setOnClickListener {
                showAlertDialog(view, coupon)
            }

            addressButton.setOnClickListener {
                view.findNavController().navigate(
                    R.id.action_couponDetails_to_mapsFragment,
                    bundleOf(Pair("mall", coupon.mall))
                )
            }
        }

        return view
        //return inflater.inflate(R.layout.fragment_coupon_details, container, false)
    }

    fun showAlertDialog(view: View, coupon: Coupons){
        this.context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle("Redeem?")
                .setMessage("Are you sure to redeem this coupon?")
                .setNegativeButton("cancel") { dialog, which ->
                    Snackbar.make( view , "redeem canceled", Snackbar.LENGTH_SHORT).show()
                }
                .setPositiveButton("Yes") {dialog, which ->
                    CoroutineScope(Dispatchers.IO).launch {
                        Snackbar.make(view, makeRequest(getString(R.string.url)+"user/coupons/add/"+coupon.id), Snackbar.LENGTH_LONG).show()
                    }
                }
                .show()
        }
    }

//    fun showAlertMsg(view: View, str: String){
//        this.context?.let {
//            MaterialAlertDialogBuilder(it)
//                .setTitle(str)
//                .setPositiveButton("Ok") {dialog, which ->
//                    //Snackbar.make( view , str, Snackbar.LENGTH_SHORT).show()
//                }
//                .show()
//        }
//    }

    fun makeRequest(url: String): String{
        var builder = StringBuilder()

        val connection = URL(url).openConnection() as HttpURLConnection
        connection.setDoOutput(true)

        try{
            var data: Int = connection.inputStream.read()
            while (data != -1) {
                builder.append(data.toChar())
                data = connection.inputStream.read()
            }
        }catch (e: Exception){
            builder = StringBuilder()
            builder.append(connection.responseMessage)
        }finally{
            if (connection.responseCode == 200){
                builder.append("coupon redeemed sucessfully")
            }
            connection.disconnect()
        }

        return builder.toString()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CouponDetails.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CouponDetails().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
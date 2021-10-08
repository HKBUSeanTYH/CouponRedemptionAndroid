package comp4097.comp.hkbu.edu.hk.couponredemption

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import comp4097.comp.hkbu.edu.hk.couponredemption.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

            CoroutineScope(Dispatchers.Main).launch {
                if (coupon.image != "")
                    Picasso.get().load(coupon.image).into(couponImageView2)
            }
            titletextview2.setText(coupon.title)
            detailsTextView3.setText(coupon.details)
            mallTextView.setText(coupon.mall)
            quotaTextView.setText(coupon.coins.toString())
            validTextView.setText(coupon.valid)
        }

        return view
        //return inflater.inflate(R.layout.fragment_coupon_details, container, false)
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
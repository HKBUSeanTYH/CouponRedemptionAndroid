package comp4097.comp.hkbu.edu.hk.couponredemption

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import comp4097.comp.hkbu.edu.hk.couponredemption.data.Coupons
import comp4097.comp.hkbu.edu.hk.couponredemption.data.User
import comp4097.comp.hkbu.edu.hk.couponredemption.ui.profile.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [customLoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class customLoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var columnCount = 1
    lateinit var model: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model =ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_custom_login, container, false)

//        if (view is RecyclerView){
//            with(view) {
//                layoutManager = when {
//                    columnCount <= 1 -> LinearLayoutManager(context)
//                    else -> GridLayoutManager(context, columnCount)
//                }
//
//
//            }
//        }
        CoroutineScope(Dispatchers.IO).launch {
            val userAccountTextField = view.findViewById<EditText>(R.id.editTextTextUserAccount)
            val userPasswordTextField = view.findViewById<EditText>(R.id.editTextTextPassword)
            val signInButton = view.findViewById<Button>(R.id.signInButton)

            //model = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
            //model.sendMessage(true)

            signInButton.setOnClickListener {
                val acc = userAccountTextField.text.toString()
                val pw = userPasswordTextField.text.toString()

                val body = """{"username":"$acc","password":"$pw"}"""
                CoroutineScope(Dispatchers.IO).launch{
                    val output = makeRequest( getString(R.string.url)+"user/login" , body)
                    //Snackbar.make( view , output, Snackbar.LENGTH_SHORT).show()
                    try{
                        val loggedInUser = Gson().fromJson<User>(output, object : TypeToken<User>() {}.type)
                        Snackbar.make( view , "Login success!", Snackbar.LENGTH_SHORT).show()
                        model.sendMessage(true)
                    }catch (e: Exception){
                        Snackbar.make( view , output, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }   


        return view;
    }

//    fun showAlertMsg(view:View, str: String){
//        this.context?.let {
//            MaterialAlertDialogBuilder(it)
//                .setTitle(str)
//                .setPositiveButton("Ok") {dialog, which ->
//
//                }
//                .show()
//        }
//    }

    fun makeRequest(url: String, body: String): String{
        var builder = StringBuilder()
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.setDoOutput(true)
        //connection.setChunkedStreamingMode(0)
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8")

        //val jsonbody = Gson().toJson(body)
        val wr = OutputStreamWriter(connection.outputStream, "utf-8")
        wr.write(body)
        wr.flush()

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
            wr.close()
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
         * @return A new instance of fragment customLoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            customLoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
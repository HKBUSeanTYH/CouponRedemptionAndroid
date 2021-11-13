package comp4097.comp.hkbu.edu.hk.couponredemption.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    val loginstatus = MutableLiveData(false)

    fun sendMessage(status: Boolean) {
        loginstatus.value = status
    }

    fun postMessage(status: Boolean){
        loginstatus.postValue(status)
    }
}
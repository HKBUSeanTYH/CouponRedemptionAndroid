package comp4097.comp.hkbu.edu.hk.couponredemption.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import comp4097.comp.hkbu.edu.hk.couponredemption.Network
import java.lang.Exception

@Database(entities = arrayOf(Coupons::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun couponsDao() : CouponsDao
    companion object {
        private var instance: AppDatabase? = null
        suspend fun getInstance(context: Context) : AppDatabase {
            if (instance != null)
                return instance!!
//build an instance
            instance = Room.databaseBuilder(context,
                AppDatabase::class.java,
                "infoDay").build()
            initDB()
            return instance!!
        }

        suspend fun initDB() {
            instance?.clearAllTables() //add this line when you are still debugging
            //SampleData.EVENT.forEach { instance?.eventDao()?.insert(it) }
            val NEWS_URL = "https://5dc8-158-182-201-22.ngrok.io/shop/json"

            try{
                val json = Network.getTextFromNetwork(NEWS_URL)
                //convert the string json into List<news>
                
                val coupons =
                    Gson().fromJson<List<Coupons>>(json, object : TypeToken<List<Coupons>>() {}.type)
                coupons.forEach { instance?.couponsDao()?.insert(it) }
            }catch (e: Exception){
                val placeholderCoupon = listOf(
                    Coupons(0,0,0, "Placeholder Coupon",
                        "No Coupons found","Nowhere","no mall",
                        "https://bulma.io/images/placeholders/128x128.png",0,
                        0,"not valid","Please check your connection")
                )
                placeholderCoupon.forEach { instance?.couponsDao()?.insert(it) }
            }

        }
    }
}
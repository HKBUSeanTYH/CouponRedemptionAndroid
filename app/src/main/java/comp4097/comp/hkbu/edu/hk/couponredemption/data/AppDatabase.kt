package comp4097.comp.hkbu.edu.hk.couponredemption.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import comp4097.comp.hkbu.edu.hk.couponredemption.Network
import comp4097.comp.hkbu.edu.hk.couponredemption.R
import java.lang.Exception

@Database(
    entities = arrayOf(Coupons::class, Mall::class),
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun couponsDao() : CouponsDao
    abstract fun mallDao() : MallDao

    companion object {
        private var instance: AppDatabase? = null
        suspend fun getInstance(context: Context) : AppDatabase {
            if (instance != null)
                return instance!!
//build an instance
            instance = Room.databaseBuilder(context,
                AppDatabase::class.java,
                "couponRedemption").build()
            initDB()
            return instance!!
        }

        suspend fun initDB() {
            instance?.clearAllTables() //add this line when you are still debugging
            //SampleData.EVENT.forEach { instance?.eventDao()?.insert(it) }
            val NEWS_URL = "https://e298-158-182-114-185.ngrok.io/shop/json"
            //change url here

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

            val malls = SampleData.MALL
            malls.forEach { instance?.mallDao()?.insert(it) }
        }
    }
}
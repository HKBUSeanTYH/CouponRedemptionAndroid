package comp4097.comp.hkbu.edu.hk.couponredemption.data
import androidx.room.*

@Dao
interface CouponsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coupons: Coupons)

    @Query("Select * from coupons")
    suspend fun getAllCoupons(): List<Coupons>

    @Query("Select * from coupons where mall = :mall")
    suspend fun findCouponsByMall(mall: String): List<Coupons>

    @Delete
    suspend fun delete(vararg coupons: Coupons)
    @Update
    suspend fun update(coupons: Coupons)
}
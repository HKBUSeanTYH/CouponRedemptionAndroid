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

    @Query("Select * from coupons where coins <= :coins")
    suspend fun findCouponsLessThan(coins: Int): List<Coupons>

    @Query("Select * from coupons where coins >= :mincoins and coins <= :maxcoins")
    suspend fun findCouponsBetween(mincoins: Int, maxcoins: Int): List<Coupons>

    @Query("Select * from coupons where coins >= :coins")
    suspend fun findCouponsMoreThan(coins: Int): List<Coupons>

    @Delete
    suspend fun delete(vararg coupons: Coupons)
    @Update
    suspend fun update(coupons: Coupons)
}
package comp4097.comp.hkbu.edu.hk.couponredemption.data
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Coupons (
    val createdAt: Long,
    val updatedAt: Long,
    @PrimaryKey val id: Int,
    val title: String,
    val restaurant: String,
    val region: String,
    val mall: String,
    val image: String,
    val quota: Int,
    val coins: Int,
    val valid: String,
    val details: String
    ){

}

//val image: String,
//val restaurant: String,
//val description: String,
//val coins: String


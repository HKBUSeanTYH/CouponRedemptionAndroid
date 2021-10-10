package comp4097.comp.hkbu.edu.hk.couponredemption.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Mall(
    @PrimaryKey val mall: String,
    val latitude: Double,
    val longitude: Double
) {
}
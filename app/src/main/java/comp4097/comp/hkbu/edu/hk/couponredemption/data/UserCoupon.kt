package comp4097.comp.hkbu.edu.hk.couponredemption.data

class UserCoupon(
    val coupons: List<Coupons>,
    val username: String,
    val wallet: Int,
    val role: String
) {
}
package comp4097.comp.hkbu.edu.hk.couponredemption.data

object SampleData {
    val MALL = listOf(
        Mall( "IFC Mall", 22.2849, 114.158917),
        Mall( "Pacific Place", 22.2774985,114.1663225),
        Mall( "Times Square", 22.2782079, 114.1822994),
        Mall( "Elements",22.3048708,114.1615219),
        Mall( "Harbour City",22.2950689,114.1668661),
        Mall( "Festival Walk",22.3372971,114.1745273),
        Mall("MegaBox",22.319857,114.208168    ),
        Mall("APM",22.3121738,114.22513219999996),
        Mall("Tsuen Wan Plaza",22.370735,114.111309),
        Mall("New Town Plaza",22.3814592,114.1889307)
    )

    val FILTEREDCOUPONS = listOf(
        FilteredCoupons(1,"Greyhound Cafe","Pacific Place"),
        FilteredCoupons(2,"Mongo Tree","Festival Walk"),
        FilteredCoupons(3,"Yoogane","New Town Plaza")
    )

    val COINRANGE = listOf(
        Coins("Coins <= 300"),
        Coins("300 < Coins < 600"),
        Coins("Coins >= 600")
    )

    val LOGINITEMS = listOf(
        ProfileItem("Login")

    )

    val LOGOUTITEMS = listOf(
        ProfileItem("Logout"),
        ProfileItem("Redeemed")
    )

    val profileitems = listOf(
        ProfileItem("Logout"),
        ProfileItem("Login"),
        ProfileItem("Redeemed")
    )
}
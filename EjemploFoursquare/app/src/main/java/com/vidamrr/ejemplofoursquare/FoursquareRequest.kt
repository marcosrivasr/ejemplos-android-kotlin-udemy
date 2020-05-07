package com.vidamrr.ejemplofoursquare


class FoursquareRequest {
    var response:FoursquareResponse? = null
}

class FoursquareResponse{
    var venues:ArrayList<Venue>?= null
}

class Venue{
    var id:String = ""
    var name:String = ""
    var categories:ArrayList<Category>? = null
}

class Category{
    var id:String = ""
    var name:String = ""
    var icon:Icon? = null
}

class Icon{
    var prefix:String = ""
    var suffix:String = ""
}
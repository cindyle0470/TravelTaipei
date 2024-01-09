package com.cindyle.traveltaipei.bean

class AttBean(var total: Int, var data: List<AttData>) {
    class AttData (var id:Int, var name: String, var name_zh: Any, var open_status: Int, var introduction: String,
                   var open_time: String, var zipcode: String, var distric: String, var address: String, var tel: String,
                   var fax: String, var email: String, var months: String, var nlat: Float, var elong: Float,
                   var official_site: String, var facebook: String, var ticket: String, var remind: String,
                   var staytime: String, var modified: String, var url: String, var category: List<Category>,
                   var target: List<Target>, var service: List<Service>, var friendly: List<Friendly>,
                   var images: List<Img>, var files: List<Any>, var links: List<Links>){

        class Category(var id: Int, var name: String)
        class Target(var id: Int, var name: String)
        class Service(var id: Int, var name: String)
        class Friendly(var id: Int, var name: String)
        class Img(var src: String, var subject: String, var ext: String)
        class Links(var src: String, var subject: String)
    }
}


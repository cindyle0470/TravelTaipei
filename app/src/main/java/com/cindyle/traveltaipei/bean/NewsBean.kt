package com.cindyle.traveltaipei.bean

class NewsBean (var total: Int, var data: List<NewsData>) {
    class NewsData (var id: Int, var title: String, var  description: String, var  begin: Object, var  end: Object,
                   var  posted: String, var  modified: String, var  url: String, var  files: List<File>,
                    var  links: List<Link>){

        class File(var src: String, var subject: String, var ext: String)
        class Link(var src: String, var subject: String)
    }
}


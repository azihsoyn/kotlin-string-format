package com.github.azihsoyn.ktformat

fun String.format(args: Map<String, String>): String{
    var start: Boolean = false
    var ret: String = ""
    var key: String = ""
    this.forEach {
        if(!start && it.equals('{')){
            start = true
            return@forEach
        }

        if(start && it.equals('}')){
            start = false
            val v = args[key]
            if(v != null){
                ret = ret.plus(v)
            }else{
                // TODO: Exception? or empty? or null?
                ret = ret.plus("{$key}")
            }
            key = ""
            return@forEach
        }
        if(start){
            key = key.plus(it)
        }
        if(!start){
            ret = ret.plus(it)
        }
    }
    return ret
}
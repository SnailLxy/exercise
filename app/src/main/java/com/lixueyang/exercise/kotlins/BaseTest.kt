package com.lixueyang.exercise.kotlins

/**
 * Created on 2020/12/27.
 *
 */
class BaseTest {
    var a: Int = 1
    val str: String = "BaseTest"
    var a_array: IntArray = intArrayOf(1, 2, 3)
    var char_array: CharArray = charArrayOf('a', 'b', 'c')
    var string_array: Array<String> = arrayOf("ldkdkd", "nncjd", "llll")
    var string_2_array = arrayOf("ldkdkd", "nncjd", "llll")

    constructor()

    public fun testFor() {
        var c4 = str[4]
        str.subSequence(0,4)//subString与subSequence
        for (c in str) {//todo 为什么不行

        }
    }
}
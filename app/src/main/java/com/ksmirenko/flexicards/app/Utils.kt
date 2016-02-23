package com.ksmirenko.flexicards.app

/**
 * Utilities such as data converting.
 *
 * @author Kirill Smirenko
 */
object Utils {
    private val separator = "_,_"

    fun <T> listToString(list : List<T>) : String {
        val sb = StringBuilder()
        list.forEach { str ->
            sb.append(separator)
            sb.append(str)
        }
        sb.delete(0, separator.length - 1)
        return sb.toString()
    }

    fun stringToIntList(str : String) : List<Int> {
        val arr = str.split(separator.toRegex()).dropLastWhile { it.isEmpty() }.map { it.toInt() }
        return arr
    }

    fun stringToSqlReadyString(str : String) = "(" + str.replace("_", "") + ")"
}

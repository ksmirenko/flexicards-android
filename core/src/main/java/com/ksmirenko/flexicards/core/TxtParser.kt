package com.ksmirenko.flexicards.core

import com.ksmirenko.flexicards.core.data.CardPack
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.util.ArrayList

/**
 * Parses a text file into a CardPack.
 *
 * @author Kirill Smirenko
 */
class TxtParser(file : File) {
    val reader : BufferedReader
    val pack : CardPack

    init {
        reader = BufferedReader(FileReader(file))
        pack = CardPack(null, null, null, ArrayList<Pair<String, String>>())
    }

    fun parse() : CardPack? {
        try {
            var line = reader.readLine()
            val list = pack.cardList as ArrayList
            while (line != null) {
                when {
                    line.startsWith("category=", true) ->
                        pack.categoryName = line.removeSurrounding("category=\"", "\"")
                    line.startsWith("lang=", true) ->
                        pack.language = line.removeSurrounding("lang=\"", "\"")
                    line.startsWith("module=", true) ->
                        pack.moduleName = line.removeSurrounding("module=\"", "\"")
                    line.contains('\t') -> {
                        val arr = line.split('\t')
                        if (arr.size == 2) {
                            list.add(arr[0] to arr[1])
                        }
                    }
                }
                line = reader.readLine()
            }
            return pack
        }
        catch (e : IOException) {
            return null
        }
        finally {
            reader.close()
        }
    }
}
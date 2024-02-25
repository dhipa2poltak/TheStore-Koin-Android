package com.dpfht.thestore_koin.data.helpers

import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader

object FileReaderHelper {

  fun readFileAsString(filename: String): String {
    val inputStream = javaClass.classLoader.getResourceAsStream(filename) ?:
    ByteArrayInputStream(ByteArray(1024))

    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
    var str = ""

    var line = bufferedReader.readLine()
    while (line != null) {
      str += line
      line = bufferedReader.readLine()
    }

    return str
  }
}

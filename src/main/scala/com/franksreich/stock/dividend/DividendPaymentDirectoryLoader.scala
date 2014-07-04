/*
 * Copyright 2014 Frank Krick
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.franksreich.stock.dividend

import java.io.File

class DividendFile(val symbol: String, val filename: String) {
  override def toString = symbol + " " + filename
}

/**
 * Scans the directory for dividend payment files
 */
class DividendPaymentDirectoryLoader {

  private def filenames = {
    var result = List[DividendFile]()
    val directory = new File("/Users/frank/Development/stocks/data/dividend/")
    if (directory.isDirectory) {
      for (file <- directory.listFiles) {
        val symbol = file.getName.split('.')(0).toUpperCase
        result = new DividendFile(symbol, file.toString) +: result
      }
    } else {
      println("Not a directory")
    }
    result
  }

  def dividendPayments = {
    val filenames = this.filenames
    var result = Map[String, List[DividendPayment]]()
    filenames.foreach{filename =>
      val dividendPaymentLoader = new DividendPaymentFileLoader(filename.filename)
      val dividendPayments = dividendPaymentLoader.createDividends
      result += (filename.symbol -> dividendPayments)
    }
    result
  }

}

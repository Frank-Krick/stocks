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
package com.franksreich.stock.symbol

import scala.io.Source

/** Loads stock symbols from file */
class StockSymbolFileLoader(private val filename: String) {

  def loadStockSymbols: Map[String, StockSymbol] = {
    val source = Source.fromFile(filename)
    var result = Map[String, StockSymbol]()
    for (lines <- source.getLines()) {
      val tokens = lines.split(',')
      val symbol = tokens(0).toUpperCase
      val name = if (tokens.size > 2) tokens(2) else "None"
      result = result + new Tuple2(symbol, new StockSymbol(symbol, name))
    }
    result
  }

}
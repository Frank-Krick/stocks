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
package com.franksreich.stock.ui

import com.franksreich.stock.config
import com.franksreich.stock.model.StockFactSheet
import com.franksreich.stock.model.source.database.{stockSymbolDatabase}
import com.franksreich.stock.model.source.quandl.quandlLoader

import com.github.nscala_time.time.Imports.{DateTime, DateTimeFormat}

import com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers
import org.slf4j.LoggerFactory

import scopt.OptionParser

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

/**
 * Stocks console application
 */
object stockConsole {

  val logger = LoggerFactory.getLogger(stockConsole.getClass)

  case class Config(
    quandlApiKey: String = "",
    mongoUrl: String = "",
    mode: String = ""
  )

  private def initialization() {
    RegisterJodaTimeConversionHelpers()
  }

  private def parser = {
    new OptionParser[Config]("stocks") {
      head("stocks", "0.0.1")
      opt[String]('k', "quandl-api-key") action { (x, c) => c.copy(quandlApiKey = x) }
      opt[String]('m', "mongo-db-url") action { (x, c) => c.copy(mongoUrl = x) }
    }
  }

  def main(args: Array[String]) {
    val options = parser.parse(args, Config())

    config.quandlApiKey = options.get.quandlApiKey
    config.mongoUrl = options.get.mongoUrl

    logger.debug("Using quandl API key {}", config.quandlApiKey)
    logger.debug("Using mongodb {}", config.mongoUrl)

    initialization()

    /*
    val targetDate = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("2014-07-11")
    val stockFactSheet = StockFactSheet("MSFT", targetDate)

    stockFactSheet onSuccess {
      case s => stockFactSheetDatabase.saveStockFactSheet(s)
    }

    Thread.sleep(5000 * 5)

    val stockSymbols = quandlLoader.stockSymbols
    Await.result(stockSymbols, 2 minutes)
    val symbols = stockSymbols.value.get.get
    symbols foreach { symbol => stockSymbolDatabase.saveStockSymbol(symbol) }
    println(symbols.length)
     */

  }

}
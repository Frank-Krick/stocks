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

import com.franksreich.stock.model.StockFactSheet
import com.franksreich.stock.model.dividend.DividendPaymentDirectoryLoader
import com.franksreich.stock.model.source.quandl.quandlLoader
import com.franksreich.stock.function.screener.dividendGrowthScreener
import com.franksreich.stock.model.symbol.StockSymbolFileLoader
import com.franksreich.stock.model.source.database.stockFactSheetDatabase

import com.github.nscala_time.time.Imports.DateTime

import org.bson.types.ObjectId

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

/**
 * Stocks console application
 */
object stockConsole {

  private def stockScreen() {
    val dividendPaymentDirectoryLoader = new DividendPaymentDirectoryLoader
    val dividendPaymentMap = dividendPaymentDirectoryLoader.dividendPayments
    val stockSymbolFileLoader = new StockSymbolFileLoader(
      "/Users/frank/Development/stocks/data/symbol/symbol_map_comnam.csv")
    val stockSymbols = stockSymbolFileLoader.loadStockSymbols

    var stockList = dividendPaymentMap.map( tuple => tuple._1 )

    stockList = stockList.filter{ stockSymbol =>
      val consecutiveYears = dividendGrowthScreener.countConsecutiveYears(2014, dividendPaymentMap(stockSymbol))
      consecutiveYears >= 10
    }

    val ratioListFutures = stockList.map{ stockSymbol =>
      val totalDebtFuture = quandlLoader.totalLongTermDebt(stockSymbol)
      val cashAndEquivalentsFuture = quandlLoader.cashAndEquivalents(stockSymbol)

      def calculateRatio(totalDebt: List[(DateTime, BigDecimal)],
                         cashAndEquivalents: List[(DateTime, BigDecimal)]): BigDecimal = {

        if (totalDebt.length > 0 && cashAndEquivalents.length > 0) {
          //TODO: What to do with zero ?
          if (totalDebt(0)._2 == 0) {
            cashAndEquivalents(0)._2 / 0.01
          } else {
            cashAndEquivalents(0)._2 / totalDebt(0)._2
          }
        }
        else BigDecimal(0)
      }

      for {
        totalDebt <- totalDebtFuture
        cashAndEquivalents <- cashAndEquivalentsFuture
      } yield (stockSymbol, calculateRatio(totalDebt, cashAndEquivalents))
    }

    val ratioListFuture = Future.sequence(ratioListFutures)
    Await.ready(ratioListFuture, 5 minutes)

    val ratioMap = if (ratioListFuture.isCompleted) ratioListFuture.value.get.get.toMap
    else Map[String, BigDecimal]()

    stockList = stockList.filter( stockSymbol =>
      if (ratioMap.contains(stockSymbol)) ratioMap(stockSymbol) > 1.0
      else false
    )

    val currentSharePriceFutures = stockList map { stockSymbol =>
      val currentSharePriceFuture = quandlLoader.currentStockPrice(stockSymbol)
      for {
        currentSharePrice <- currentSharePriceFuture
      } yield {
        (stockSymbol, currentSharePrice)
      }
    }

    val currentSharePriceFuture = Future.sequence(currentSharePriceFutures)
    Await.ready(currentSharePriceFuture, 5.minutes)

    stockList foreach { stockSymbol =>
      val currentSharePriceMap = currentSharePriceFuture.value.get.get.toMap
      println(stockSymbol)
      println(currentSharePriceMap(stockSymbol))
    }
  }

  def main(args: Array[String]) {
    val stockFactSheet = new StockFactSheet(new ObjectId(), "MSFT")

    stockFactSheet.cashAndEquivalents= List((DateTime.now, BigDecimal("2.05")), (DateTime.now, BigDecimal("2.03")),
      (DateTime.now, BigDecimal("2.01")))

    stockFactSheetDatabase.saveStockFactSheet(stockFactSheet)
    val sfs = stockFactSheetDatabase.loadStockFactSheet("MSFT")

    sfs match {
      case Some(s) => println("Found MSFT")
      case None => println("MSFT not found")
    }

    val sfss = stockFactSheetDatabase.loadStockFactSheet("XXX")

    sfss match {
      case Some(s) => println("Found XXX")
      case None => println("XXX not found")
    }
  }

}
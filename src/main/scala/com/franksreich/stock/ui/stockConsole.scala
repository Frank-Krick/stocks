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
import com.franksreich.stock.model.source.database.stockFactSheetDatabase

import com.github.nscala_time.time.Imports.{DateTime, DateTimeFormat}
import com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

/**
 * Stocks console application
 */
object stockConsole {

  def main(args: Array[String]) {
    RegisterJodaTimeConversionHelpers()

    val targetDate = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("2014-07-11")
    val stockFactSheet = StockFactSheet("MSFT", targetDate)

    /*
    stockFactSheet onSuccess {
      case s => stockFactSheetDatabase.saveStockFactSheet(s)
    }
     */

    Thread.sleep(5000 * 5)
  }

}
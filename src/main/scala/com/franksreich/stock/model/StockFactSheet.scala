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
package com.franksreich.stock.model

import com.franksreich.stock.config
import com.franksreich.stock.model.fundamentals.Fundamentals
import com.franksreich.stock.model.source.database.StockFactSheetDatabase

import org.bson.types.ObjectId

import com.github.nscala_time.time.Imports._

import org.slf4j.LoggerFactory

import scala.concurrent.{Promise, Future}
import scala.concurrent.ExecutionContext.Implicits.global

/** Companion class for stock fact sheets */
object StockFactSheet {
  val logger = LoggerFactory.getLogger(StockFactSheet.getClass)

  def apply(
      stockSymbol: String,
      toBeDate: DateTime,
      stockFactSheetDatabase: StockFactSheetDatabase = new StockFactSheetDatabase(config.mongoUrl)
    ): Future[StockFactSheet] = {

    val stockFactSheet = stockFactSheetDatabase.loadStockFactSheet(stockSymbol).getOrElse(StockFactSheet(stockSymbol))
    val fundamentals = Fundamentals(stockSymbol, stockFactSheet.fundamentals, toBeDate)
    val prices = StockPrice(stockSymbol, stockFactSheet.stockPrice, toBeDate)
    val result = Promise[StockFactSheet]()

    Future.sequence(List(fundamentals, prices)) onSuccess { case f =>
      result success StockFactSheet(
        stockFactSheet.id,
        stockSymbol,
        f(0).asInstanceOf[Fundamentals],
        f(1).asInstanceOf[StockPrice]
      )
    }

    result future
  }

  def apply(stockSymbol: String): StockFactSheet = {
    StockFactSheet(new ObjectId(), stockSymbol, Fundamentals(), StockPrice())
  }

  def apply(id: ObjectId, stockSymbol: String, fundamentals: Fundamentals, stockPrice: StockPrice): StockFactSheet = {
    new StockFactSheet(id, stockSymbol, fundamentals, stockPrice)
  }

}

/** Fact sheet for a single stock */
class StockFactSheet(
    val id: ObjectId,
    val stockSymbol: String,
    val fundamentals: Fundamentals,
    val stockPrice: StockPrice) {}
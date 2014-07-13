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

import com.franksreich.stock.model.fundamentals.Fundamentals
import com.franksreich.stock.model.source.database.stockFactSheetDatabase
import com.franksreich.stock.model.source.quandl.quandlLoader

import org.bson.types.ObjectId

import com.github.nscala_time.time.Imports._

import org.slf4j.LoggerFactory

import scala.concurrent.{Await, Promise, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

/** Companion class for stock fact sheets */
object StockFactSheet {
  val logger = LoggerFactory.getLogger(StockFactSheet.getClass)

  def apply(stockSymbol: String, toBeDate: DateTime): Future[StockFactSheet] = {
    val stockFactSheet = stockFactSheetDatabase.loadStockFactSheet(stockSymbol).getOrElse(StockFactSheet(stockSymbol))
    val fundamentals = Fundamentals(stockSymbol, stockFactSheet.fundamentals, toBeDate)
    val result = Promise[StockFactSheet]()
    fundamentals onSuccess { case f =>
      result success StockFactSheet(new ObjectId(), stockSymbol, f, StockPrice())
    }
    result future
  }

  def apply(stockSymbol: String): StockFactSheet = {
    StockFactSheet(new ObjectId(), stockSymbol, Fundamentals(), StockPrice())
  }

  def apply(id: ObjectId, stockSymbol: String, fundamentals: Fundamentals, stockPrice: StockPrice): StockFactSheet = {
    new StockFactSheet(id, stockSymbol, fundamentals, stockPrice)
  }

  /*
  /** Load stock fact sheet from database and update it if necessary.
    *
    * @param stockSymbol Identifies the company
    * @return Stock fact sheet
    */
  def apply(stockSymbol: String, toBeDate: DateTime): Future[Option[StockFactSheet]] = {
    logger.debug("Creating future to retrieve stock symbol {}", stockSymbol)
    Future {
      val local = stockFactSheetDatabase.loadStockFactSheet(stockSymbol)
      val future = local match {
        case Some(l) =>
          logger.debug("Stock symbol {} successfully retrieved from mongo db", stockSymbol)
          update(l, toBeDate)
        case None =>
          logger.debug("Stock symbol {} is not in database", stockSymbol)
          create(stockSymbol)
      }

      Await.ready(future, scala.concurrent.duration.DurationInt(1).minutes)

      future.value match {
        case Some(t) => t match {
          case Success(v) =>
            stockFactSheetDatabase.saveStockFactSheet(v)
            logger.debug("Stock symbol {} saved in database", stockSymbol)
            Option(v)
          case Failure(v) => None
        }
        case None => None
      }
    }
  }

  /** Create a new stock fact sheet for the stock symbol
    *
    * @param stockSymbol Identifies the company
    * @return Updated stock fact sheet
    */
  private def create(stockSymbol: String): Future[StockFactSheet] = {
    val stockFactSheet = new StockFactSheet(new ObjectId(), stockSymbol, Fundamentals())
    update(stockFactSheet, DateTime.now)
  }

  /** Update stock fact sheet information from quandl
    *
    * @param stockFactSheet Identifies the company
    * @return Updated stock fact sheet
    */
  private def update(stockFactSheet: StockFactSheet, toBeDate: DateTime): Future[StockFactSheet] = {
    logger.debug("Updating stock fact sheet for stock symbol {}", stockFactSheet.stockSymbol)
    val resultPromise = Promise[StockFactSheet]()

    val cashAndEquivalentsOption = updateVariable(
      toBeDate,
      stockFactSheet.timestamps.cashAndEquivalents,
      quandlLoader.balanceSheet.cashEquivalents,
      stockFactSheet.stockSymbol
    )

    val longTermDebtOption = updateVariable(
      toBeDate,
      stockFactSheet.timestamps.longTermDebt,
      quandlLoader.totalLongTermDebt,
      stockFactSheet.stockSymbol
    )

    cashAndEquivalentsOption foreach {
      f => f onSuccess {
        case result =>
          logger.debug("Retrieved cash and equivalents for stock symbol {} ", stockFactSheet.stockSymbol)
          stockFactSheet.cashAndEquivalents = result
          stockFactSheet.timestamps.cashAndEquivalents = DateTime.now
      }
    }

    longTermDebtOption foreach {
      f => f onSuccess {
        case result =>
          logger.debug("Retrieved long term debt for stock symbol {} ", stockFactSheet.stockSymbol)
          stockFactSheet.longTermDebt = result
          stockFactSheet.timestamps.longTermDebt = DateTime.now
      }
    }

    val options = List(cashAndEquivalentsOption, longTermDebtOption)
    val onlySome = options.filter{ case Some(_) => true; case _ => false }
    val all = onlySome.map{ some => some.get }
    val allFuture = Future.sequence(all)

    allFuture.onComplete{ _ => resultPromise success stockFactSheet }

    resultPromise.future
  }

  private def needsUpdate(toBeDate: DateTime, timestamp: DateTime): Boolean = timestamp < toBeDate

  type LoaderFunction =  (String) => Future[List[(DateTime, BigDecimal)]]

  private def updateVariable(
      toBeDate: DateTime,
      timestamp: DateTime,
      loader: LoaderFunction,
      stockSymbol: String): Option[Future[List[(DateTime, BigDecimal)]]] = {
    if (needsUpdate(toBeDate, timestamp)) Option(loader(stockSymbol))
    else None
  }
   */
}

/** Fact sheet for a single stock */
class StockFactSheet(
    val id: ObjectId,
    val stockSymbol: String,
    val fundamentals: Fundamentals,
    val stockPrice: StockPrice) {}
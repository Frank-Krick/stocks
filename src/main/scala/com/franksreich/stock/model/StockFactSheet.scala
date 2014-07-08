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

import com.franksreich.stock.model.source.database.stockFactSheetDatabase
import com.franksreich.stock.model.source.quandl.quandlLoader

import org.bson.types.ObjectId

import com.github.nscala_time.time.Imports._

import scala.concurrent.{Promise, Future}
import scala.concurrent.ExecutionContext.Implicits.global

/** Fact sheet for a single stock */
class StockFactSheet(
    val id: ObjectId,
    val stockSymbol: String) {

  /** Timestamps of the last updates for each member */
  object timestamps {
    var cashAndEquivalents = DateTime.now
    var longTermDebt = DateTime.now
  }

  var cashAndEquivalents = List[(DateTime, BigDecimal)]()
  var longTermDebt = List[(DateTime, BigDecimal)]()

  private def update: Future[StockFactSheet] = {
    val resultPromise = Promise[StockFactSheet]()

    val cashAndEquivalentsOption = updateVariable(timestamps.cashAndEquivalents, quandlLoader.cashAndEquivalents)
    val longTermDebtOption = updateVariable(timestamps.longTermDebt, quandlLoader.totalLongTermDebt)

    cashAndEquivalentsOption foreach {
      f => f onSuccess {
        case result =>
          cashAndEquivalents = result
          timestamps.cashAndEquivalents = DateTime.now
      }
    }

    longTermDebtOption foreach {
      f => f onSuccess {
        case result =>
          longTermDebt = result
          timestamps.longTermDebt = DateTime.now
      }
    }

    val options = List(cashAndEquivalentsOption, longTermDebtOption)
    val onlySome = options.filter{ case Some(_) => true; case _ => false }
    val all = onlySome.map{ some => some.get }
    val future = Future.sequence(all)

    future.onComplete{ _ => resultPromise success this }

    resultPromise.future
  }

  private def needsUpdate(timestamp: DateTime): Boolean = timestamp + 1.day < DateTime.now

  type LoaderFunction =  (String) => Future[List[(DateTime, BigDecimal)]]

  private def updateVariable(timestamp: DateTime, loader: LoaderFunction):
      Option[Future[List[(DateTime, BigDecimal)]]] = {

    if (needsUpdate(timestamp)) Option(loader(stockSymbol))
    else None
  }

}
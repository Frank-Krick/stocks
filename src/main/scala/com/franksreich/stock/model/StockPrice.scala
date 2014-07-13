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

import com.franksreich.stock.model.source.quandl.quandlLoader
import com.franksreich.stock.model.types.TimeSeries

import com.github.nscala_time.time.Imports._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/** Factory for stock prices */
object StockPrice {
  def apply(stockSymbol: String, old: StockPrice, toBeDate: DateTime): Future[StockPrice] = {
    if (old.timestamp < toBeDate) quandlLoader.stockPrices(stockSymbol)
    else Future { old }
  }

  def apply(
      timestamp: DateTime,
      open: TimeSeries,
      high: TimeSeries,
      low: TimeSeries,
      close: TimeSeries,
      volume: TimeSeries,
      split: TimeSeries,
      adjustedOpen: TimeSeries,
      adjustedHigh: TimeSeries,
      adjustedLow: TimeSeries,
      adjustedClose: TimeSeries,
      adjustedVolume: TimeSeries): StockPrice = {

    new StockPrice(
      timestamp,
      open,
      high,
      low,
      close,
      volume,
      split,
      adjustedOpen,
      adjustedHigh,
      adjustedLow,
      adjustedClose,
      adjustedVolume
    )

  }

  def apply() = new StockPrice(
    new DateTime(0), List(), List(), List(), List(), List(), List(), List(), List(), List(), List(), List()
  )
}

/** Stock price information */
class StockPrice(
    val timestamp: DateTime,
    val open: TimeSeries,
    val high: TimeSeries,
    val low: TimeSeries,
    val close: TimeSeries,
    val volume: TimeSeries,
    val split: TimeSeries,
    val adjustedOpen: TimeSeries,
    val adjustedHigh: TimeSeries,
    val adjustedLow: TimeSeries,
    val adjustedClose: TimeSeries,
    val adjustedVolume: TimeSeries) {}

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
package com.franksreich.stock.model.symbol

import com.github.nscala_time.time.Imports._

import org.bson.types.ObjectId

object StockSymbol {

  def apply(stockSymbol: String, name: String): StockSymbol = {
    new StockSymbol(new ObjectId(), stockSymbol, name, DateTime.now, new DateTime(0))
  }

  def apply(id: ObjectId, stockSymbol: String, name: String, timestamp: DateTime, lastLoad: DateTime): StockSymbol = {
    new StockSymbol(id, stockSymbol, name, timestamp, lastLoad)
  }
}

/**
 * Represents a stock symbol
 */
class StockSymbol(
    val id: ObjectId,
    val stockSymbol: String,
    val name: String,
    val timestamp: DateTime,
    val lastLoad: DateTime) {

  override def toString = stockSymbol + " : " + name
}
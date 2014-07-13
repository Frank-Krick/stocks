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
package com.franksreich.stock.model.source.database.utils

import com.franksreich.stock.model.symbol.StockSymbol

import com.github.nscala_time.time.Imports._

import com.mongodb.casbah.Imports._
import org.bson.types.ObjectId

/**
 * Converter utilities for stock symbols
 */
object stockSymbolConverter {

  def stockSymbolToBson(stockSymbol: StockSymbol): DBObject = {
    MongoDBObject(
      "_id" -> stockSymbol.id,
      "stockSymbol" -> stockSymbol.stockSymbol,
      "name" -> stockSymbol.name,
      "timestamp" -> stockSymbol.timestamp,
      "lastLoad" -> stockSymbol.lastLoad
    )
  }

  def stockSymbolFromBson(bson: MongoDBObject): StockSymbol = {
    StockSymbol(
      bson.as[ObjectId]("_id"),
      bson.as[String]("stockSymbol"),
      bson.as[String]("name"),
      bson.as[DateTime]("timestamp"),
      bson.as[DateTime]("lastLoad")
    )
  }

}

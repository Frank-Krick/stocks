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
package com.franksreich.stock.model.source.database

import com.franksreich.stock.config
import com.franksreich.stock.model.source.database.utils.stockSymbolConverter
import com.franksreich.stock.model.symbol.StockSymbol

import com.mongodb.casbah.Imports._

import org.slf4j.LoggerFactory

/**
 * Database to store information about stock symbols
 */
object stockSymbolDatabase {
  val logger = LoggerFactory.getLogger(stockSymbolDatabase.getClass)
  val mongoClientUrl = MongoClientURI(config.mongoUrl)
  val mongoClient = MongoClient(mongoClientUrl)
  val database = mongoClient("stocks")
  val collection = database("stockSymbols")

  collection.ensureIndex(MongoDBObject( "stockSymbol" -> 1 ), MongoDBObject( "unique" -> true ))

  def saveStockSymbol(stockSymbol: StockSymbol) {
    logger.debug("Save stock symbol {} to mongo db", List(stockSymbol.id.toString(), stockSymbol.stockSymbol))
    val query = MongoDBObject( "_id" -> stockSymbol.id )
    val document = stockSymbolConverter.stockSymbolToBson(stockSymbol)
    collection.update(query, document, upsert=true)
  }

  def loadStockSymbol(stockSymbol: String): Option[StockSymbol] = {
    logger.debug("Try to load stock symbol {} from mongo db", stockSymbol)
    val query = MongoDBObject("stockSymbol" -> stockSymbol)
    val result = collection.findOne(query)

    result match {
      case Some(document) =>
        logger.debug("Stock symbol {} successfully loaded", stockSymbol)
        Option(stockSymbolConverter.stockSymbolFromBson(document))
      case None =>
        logger.debug("Stock symbol {} not found", stockSymbol)
        None
    }
  }

}
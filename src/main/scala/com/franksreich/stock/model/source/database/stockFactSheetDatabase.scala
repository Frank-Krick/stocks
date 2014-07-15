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

import com.franksreich.stock.model.source.database.utils.stockFactSheetConverter
import com.franksreich.stock.model.StockFactSheet
import com.franksreich.stock.config

import com.mongodb.casbah.Imports._

import org.slf4j.LoggerFactory

/** Access stock fact sheets */
class StockFactSheetDatabase(mongoUrl: String) {
  val logger = LoggerFactory.getLogger(this.getClass)
  val mongoClientUrl = MongoClientURI(config.mongoUrl)
  val mongoClient = MongoClient(mongoClientUrl)
  val database = mongoClient("stocks")
  val collection = database("factSheets")

  collection.ensureIndex(MongoDBObject( "stockSymbol" -> 1 ), MongoDBObject( "unique" -> true ))

  /** Save fact sheet to database
    *
    * @param factSheet Fact sheet to save to database
    */
  def saveStockFactSheet(factSheet: StockFactSheet) {
    logger.debug("Save stock fact sheet {} to mongo db", List(factSheet.id.toString(), factSheet.stockSymbol))
    val query = MongoDBObject("_id" -> factSheet.id)
    val document = stockFactSheetConverter.convertStockFactSheetToBson(factSheet)
    collection.update(query, document, upsert=true)
  }

  /** Loads a fact sheet identified by stock symbol
    *
    * @param stockSymbol Stock symbol of the fact sheet to retrieve
    * @return Stock fact sheet loaded from database
    */
  def loadStockFactSheet(stockSymbol: String): Option[StockFactSheet] = {
    logger.debug("Try to load stock fact sheet {} from mongo db", stockSymbol)
    val query = MongoDBObject("stockSymbol" -> stockSymbol)
    val result = collection.findOne(query)

    result match {
      case Some(document) =>
        logger.debug("Stock fact sheet {} successfully loaded", stockSymbol)
        Option(stockFactSheetConverter.convertStockFactSheetFromBson(new MongoDBObject(document)))
      case None =>
        logger.debug("Stock fact sheet {} not found", stockSymbol)
        None
    }
  }

}
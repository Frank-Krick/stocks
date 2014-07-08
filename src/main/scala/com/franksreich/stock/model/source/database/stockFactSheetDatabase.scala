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

import com.franksreich.stock.model.source.database.utils.converter
import com.franksreich.stock.model.StockFactSheet
import com.franksreich.stock.config

import com.mongodb.casbah.Imports.MongoClientURI
import com.mongodb.casbah.Imports.MongoClient
import com.mongodb.casbah.Imports.MongoDBObject

import scala.Option

/** Access stock fact sheets */
object stockFactSheetDatabase {
  val mongoClientUrl = MongoClientURI(config.mongoUrl)
  val mongoClient = MongoClient(mongoClientUrl)
  val database = mongoClient("stocks")
  val collection = database("factSheets")

  /** Save fact sheet to database
   *
   * @param factSheet Fact sheet to save to database
   */
  def saveStockFactSheet(factSheet: StockFactSheet) {
    collection.insert(converter.convertStockFactSheetToBson(factSheet))
  }

  /** Loads a fact sheet identified by stock symbol
   *
   * @param stockSymbol Stock symbol of the fact sheet to retrieve
   * @return Stock fact sheet loaded from database
   */
  def loadStockFactSheet(stockSymbol: String): Option[StockFactSheet] = {
    val query = MongoDBObject("stockSymbol" -> stockSymbol)
    val result = collection.findOne(query)

    result match {
      case Some(document) => Option(converter.convertStockFactSheetFromBson(new MongoDBObject(document)))
      case None => None
    }
  }

}

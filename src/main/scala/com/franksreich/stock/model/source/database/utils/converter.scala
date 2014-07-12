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

import com.franksreich.stock.model.StockFactSheet
import com.franksreich.stock.model.fundamentals.Fundamentals
import com.mongodb.BasicDBList

import com.mongodb.casbah.Imports.MongoDBObject
import com.mongodb.casbah.commons.Imports.DBObject
import com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers

import org.bson.types.ObjectId

import org.joda.time.DateTime

/** Converter between model classes and bson representation */
object converter {
  RegisterJodaTimeConversionHelpers()

  /** Convert stock fact sheet to bson
   *
   * @param factSheet Fact sheet to convert
   * @return Bson representation of the fact sheet
   */
  def convertStockFactSheetToBson(factSheet: StockFactSheet): DBObject = {
    val timestamps = MongoDBObject(
      "cashAndEquivalents" -> factSheet.timestamps.cashAndEquivalents,
      "longTermDebt" -> factSheet.timestamps.longTermDebt
    )

    MongoDBObject(
      "_id" -> factSheet.id,
      "stockSymbol" -> factSheet.stockSymbol,
      "cashAndEquivalents" -> factSheet.cashAndEquivalents.map( tuple => (tuple._1, tuple._2.toString())),
      "longTermDebt" -> factSheet.longTermDebt.map( tuple => (tuple._1, tuple._2.toString())),
      "timestamps" -> timestamps
    )
  }

  /** Convert bson to stock fact sheet
   *
   * @param factSheetBson Bson representation of a stock fact sheet
   * @return Stock fact sheet
   */
  def convertStockFactSheetFromBson(factSheetBson: MongoDBObject): StockFactSheet = {
    val factSheet = new StockFactSheet(
      factSheetBson.as[ObjectId]("_id"),
      factSheetBson.as[String]("stockSymbol"),
      Fundamentals())

    factSheet.cashAndEquivalents = parseBsonDateValueList("cashAndEquivalents", factSheetBson)
    factSheet.longTermDebt = parseBsonDateValueList("longTermDebt", factSheetBson)

    val timestampsMongoDb = factSheetBson.as[MongoDBObject]("timestamps")
    factSheet.timestamps.cashAndEquivalents = timestampsMongoDb.as[DateTime]("cashAndEquivalents")
    factSheet.timestamps.longTermDebt = timestampsMongoDb.as[DateTime]("longTermDebt")
    factSheet
  }

  /** Parse a bson list
   *
   * @param key Key of the bson list
   * @param bson Bson representation
   * @return List with date and value
   */
  private def parseBsonDateValueList(key: String, bson: MongoDBObject): List[(DateTime, BigDecimal)] = {
    val cashAndEquivalents = bson.as[List[BasicDBList]](key)
    cashAndEquivalents map { list =>
      (list.get(0).asInstanceOf[DateTime], BigDecimal(list.get(1).asInstanceOf[String]))
    }
  }

}
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
package com.franksreich.stock.model.database.utils

import com.franksreich.stock.model.StockFactSheet
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

  def convertStockFactSheetFromBson(factSheetBson: MongoDBObject): StockFactSheet = {
    val factSheet = new StockFactSheet(
      factSheetBson.as[ObjectId]("_id"),
      factSheetBson.as[String]("stockSymbol"))

    val cashAndEquivalents = factSheetBson.as[List[BasicDBList]]("cashAndEquivalents")
    val cashAndEquivalentsConverted = cashAndEquivalents map { list =>
      (list.get(0).asInstanceOf[DateTime], BigDecimal(list.get(1).asInstanceOf[String]))
    }

    factSheet.cashAndEquivalents = cashAndEquivalentsConverted
    factSheet
  }

}
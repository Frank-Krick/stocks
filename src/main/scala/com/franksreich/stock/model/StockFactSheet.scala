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

import org.bson.types.ObjectId

import org.joda.time.DateTime

/** Fact sheet for a single stock */
class StockFactSheet(
    val id: ObjectId,
    val stockSymbol: String) {

  object timestamps {
    var cashAndEquivalents = DateTime.now
    var longTermDebt = DateTime.now
  }

  var cashAndEquivalents = List[(DateTime, BigDecimal)]()
  var longTermDebt = List[(DateTime, BigDecimal)]()
}
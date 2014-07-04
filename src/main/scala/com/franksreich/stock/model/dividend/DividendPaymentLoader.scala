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
package com.franksreich.stock.model.dividend

import com.github.nscala_time.time.Imports._

import scala.io.Source

/**
 * Loads dividend payments from csv files
 */
class DividendPaymentLoader(private val source: Source) {

  def createDividends = {
    var result = List[DividendPayment]()
    for (line <- source.getLines()) {
      val tokens = line.split(',')
      try {
        val dateTime = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime(tokens(0))
        val payment = new BigDecimal(new java.math.BigDecimal(tokens(1)))
        val dividendPayment = new DividendPayment(dateTime, payment)
        result = dividendPayment +: result
      } catch {
        case e: Exception =>
      }
    }
    result.sortWith(_.dateTime.getMillis > _.dateTime.getMillis)
  }

}
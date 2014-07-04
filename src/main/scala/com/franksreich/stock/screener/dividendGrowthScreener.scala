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
package com.franksreich.stock.screener

import com.franksreich.stock.dividend.DividendPayment
import scala.annotation.tailrec

/**
 * Screens stocks based on dividend payments
 */
object dividendGrowthScreener {

  def groupYearlyPayments(dividendPayments: List[DividendPayment]): List[DividendPayment] = {
    val dividendPaymentsMap = dividendPayments.groupBy(dividendPayment => dividendPayment.dateTime.getYear)

    @tailrec
    def sumYearlyPayments(
        targetDividendPayments: List[DividendPayment],
        sourceDividendPayments: List[DividendPayment],
        dividendPaymentsMap: Map[Int, List[DividendPayment]]): List[DividendPayment] = {

      val summarizedDividendPayment = sourceDividendPayments.reduce[DividendPayment]{ (A, B) =>
        new DividendPayment(A.dateTime, A.payment + B.payment)
      }

      if (dividendPaymentsMap.isEmpty) {
        summarizedDividendPayment +: targetDividendPayments
      } else {
        sumYearlyPayments(
          summarizedDividendPayment +: targetDividendPayments,
          dividendPaymentsMap.head._2,
          dividendPaymentsMap.drop(1)
        )
      }
    }

    if (dividendPaymentsMap.isEmpty) List[DividendPayment]()
    else {
      val result = sumYearlyPayments(List[DividendPayment](), dividendPaymentsMap.head._2, dividendPaymentsMap.drop(1))
      result.sortWith(_.dateTime.getMillis > _.dateTime.getMillis)
    }
  }

  def countConsecutiveYears(startYear: Int, dividendPayments: List[DividendPayment]): Int = {

    @tailrec
    def countConsecutiveYearsInnerFunction(
        laterDividendPayment: DividendPayment,
        dividendPayments: List[DividendPayment],
        consecutiveYears: Int): Int = {

      if (dividendPayments.isEmpty) {
        consecutiveYears
      } else {
        if (laterDividendPayment.payment >= dividendPayments.head.payment &&
            dividendPayments.head.payment > 0 &&
            (laterDividendPayment.dateTime.getYear == dividendPayments.head.dateTime.getYear ||
             laterDividendPayment.dateTime.getYear == dividendPayments.head.dateTime.getYear + 1)) {
          if (laterDividendPayment.dateTime.getYear > dividendPayments.head.dateTime.getYear) {
            countConsecutiveYearsInnerFunction(dividendPayments.head, dividendPayments.drop(1), consecutiveYears + 1)
          } else {
            countConsecutiveYearsInnerFunction(dividendPayments.head, dividendPayments.drop(1), consecutiveYears)
          }
        } else {
          consecutiveYears
        }
      }
    }

    val filteredDividendPayments = dividendPayments.dropWhile(_.dateTime.getYear > startYear)
    if (filteredDividendPayments.isEmpty) {
      0
    } else {
      if (filteredDividendPayments.head.dateTime.getYear == startYear) {
        countConsecutiveYearsInnerFunction(filteredDividendPayments.head, filteredDividendPayments.drop(1), 0)
      } else 0
    }
  }
}

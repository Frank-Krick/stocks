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
package com.franksreich.stock.model.fundamentals

import com.github.nscala_time.time.Imports._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Fundamentals {

  def apply(stockSymbol: String,
            fundamentals: Fundamentals,
            toBeDate: DateTime): Future[Fundamentals] = {

    val futureList = List(
      BalanceSheet(stockSymbol, fundamentals.balanceSheet, toBeDate),
      CashFlow(stockSymbol, fundamentals.cashFlow, toBeDate),
      IncomeStatement(stockSymbol, fundamentals.incomeStatement, toBeDate)
    )

    val f = Future.sequence(futureList)

    f map { l => new Fundamentals(
      l(0).asInstanceOf[BalanceSheet],
      l(1).asInstanceOf[CashFlow],
      l(2).asInstanceOf[IncomeStatement])
    }
  }

  def apply(): Fundamentals = {
    new Fundamentals(BalanceSheet(), CashFlow(), IncomeStatement())
  }

  def apply(balanceSheet: BalanceSheet, cashFlow: CashFlow, incomeStatement: IncomeStatement): Fundamentals = {
    new Fundamentals(balanceSheet, cashFlow, incomeStatement)
  }
}
/**
 * Fundamentals
 */
class Fundamentals(
    val balanceSheet: BalanceSheet,
    val cashFlow: CashFlow,
    val incomeStatement: IncomeStatement) {}
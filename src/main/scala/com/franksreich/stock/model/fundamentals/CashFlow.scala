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

import com.franksreich.stock.model.source.quandl.quandlLoader
import com.franksreich.stock.model.types.TimestampedTimeSeries

import com.github.nscala_time.time.Imports._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/** Companion class for cash flow */
object CashFlow {

  def apply(stockSymbol: String, old: CashFlow, toBeDate: DateTime): Future[CashFlow] = {
    val netIncomeStartingLine = if (old.netIncomeStartingLine._1 < toBeDate) {
      quandlLoader.cashFlow.netIncomeStartingLine(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.netIncomeStartingLine }
    }

    val depreciationDepletion = if (old.depreciationDepletion._1 < toBeDate) {
      quandlLoader.cashFlow.depreciationDepletion(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.depreciationDepletion }
    }

    val amortization = if (old.amortization._1 < toBeDate) {
      quandlLoader.cashFlow.amortization(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.amortization }
    }

    val cashFromOperatingActivities = if (old.cashFromOperatingActivities._1 < toBeDate) {
      quandlLoader.cashFlow.cashFromOperatingActivities(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.cashFromOperatingActivities }
    }

    val issuanceRetirementOfDebtNet = if (old.issuanceRetirementOfDebtNet._1 < toBeDate) {
      quandlLoader.cashFlow.issuanceRetirementOfDebtNet(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.issuanceRetirementOfDebtNet }
    }

    val cashFromFinancingActivities = if (old.cashFromFinancingActivities._1 < toBeDate) {
      quandlLoader.cashFlow.cashFromFinancingActivities(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.cashFromFinancingActivities }
    }

    val netChangeInCash = if (old.netChangeInCash._1 < toBeDate) {
      quandlLoader.cashFlow.netChangeInCash(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.netChangeInCash }
    }

    val cashInterestPaidSupplemental = if (old.cashInterestPaidSupplemental._1 < toBeDate) {
      quandlLoader.cashFlow.cashInterestPaidSupplemental(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.cashInterestPaidSupplemental }
    }

    val cashTaxesPaidSupplemental = if (old.cashTaxesPaidSupplemental._1 < toBeDate) {
      quandlLoader.cashFlow.cashTaxesPaidSupplemental(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.cashTaxesPaidSupplemental }
    }

    val deferredTaxes = if (old.deferredTaxes._1 < toBeDate) {
      quandlLoader.cashFlow.deferredTaxes(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.deferredTaxes }
    }

    val changesInWorkingCapital = if (old.changesInWorkingCapital._1 < toBeDate) {
      quandlLoader.cashFlow.changesInWorkingCapital(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.changesInWorkingCapital }
    }

    val cashFromInvestingActivities = if (old.cashFromInvestingActivities._1 < toBeDate) {
      quandlLoader.cashFlow.cashFromInvestingActivities(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.cashFromInvestingActivities }
    }

    val foreignExchangeEffects = if (old.foreignExchangeEffects._1 < toBeDate) {
      quandlLoader.cashFlow.foreignExchangeEffects(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.foreignExchangeEffects }
    }

    val nonCashItems = if (old.nonCashItems._1 < toBeDate) {
      quandlLoader.cashFlow.nonCashItems(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.nonCashItems }
    }

    val otherInvestingCashFlowItemsTotal = if (old.otherInvestingCashFlowItemsTotal._1 < toBeDate) {
      quandlLoader.cashFlow.otherInvestingCashFlowItemsTotal(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.otherInvestingCashFlowItemsTotal }
    }

    val financingCashFlowItems = if (old.financingCashFlowItems._1 < toBeDate) {
      quandlLoader.cashFlow.financingCashFlowItems(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.financingCashFlowItems }
    }

    val totalCashDividendsPaid = if (old.totalCashDividendsPaid._1 < toBeDate) {
      quandlLoader.cashFlow.totalCashDividendsPaid(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.totalCashDividendsPaid }
    }

    val issuanceRetirementOfStockNet = if (old.issuanceRetirementOfStockNet._1 < toBeDate) {
      quandlLoader.cashFlow.issuanceRetirementOfStockNet(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.issuanceRetirementOfStockNet }
    }

    val capitalExpenditures = if (old.capitalExpenditures._1 < toBeDate) {
      quandlLoader.cashFlow.capitalExpenditures(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.capitalExpenditures }
    }

    val futureList = List(
      netIncomeStartingLine,
      depreciationDepletion,
      amortization,
      cashFromOperatingActivities,
      issuanceRetirementOfDebtNet,
      cashFromFinancingActivities,
      netChangeInCash,
      cashInterestPaidSupplemental,
      cashTaxesPaidSupplemental,
      deferredTaxes,
      changesInWorkingCapital,
      cashFromInvestingActivities,
      foreignExchangeEffects,
      nonCashItems,
      otherInvestingCashFlowItemsTotal,
      financingCashFlowItems,
      totalCashDividendsPaid,
      issuanceRetirementOfStockNet,
      capitalExpenditures
    )

    val f = Future.sequence(futureList)

    f map { l => CashFlow(l) }
  }

  def apply(l: List[TimestampedTimeSeries]): CashFlow = {
    CashFlow(l(0), l(1), l(2), l(3), l(4), l(5), l(6), l(7), l(8), l(9), l(10), l(11), l(12), l(13), l(14), l(15),
      l(16), l(17), l(18))
  }

  def apply(
      netIncomeStartingLine: TimestampedTimeSeries,
      depreciationDepletion: TimestampedTimeSeries,
      amortization: TimestampedTimeSeries,
      cashFromOperatingActivities: TimestampedTimeSeries,
      issuanceRetirementOfDebtNet: TimestampedTimeSeries,
      cashFromFinancingActivities: TimestampedTimeSeries,
      netChangeInCash: TimestampedTimeSeries,
      cashInterestPaidSupplemental: TimestampedTimeSeries,
      cashTaxesPaidSupplemental: TimestampedTimeSeries,
      deferredTaxes: TimestampedTimeSeries,
      changesInWorkingCapital: TimestampedTimeSeries,
      cashFromInvestingActivities: TimestampedTimeSeries,
      foreignExchangeEffects: TimestampedTimeSeries,
      nonCashItems: TimestampedTimeSeries,
      otherInvestingCashFlowItemsTotal: TimestampedTimeSeries,
      financingCashFlowItems: TimestampedTimeSeries,
      totalCashDividendsPaid: TimestampedTimeSeries,
      issuanceRetirementOfStockNet: TimestampedTimeSeries,
      capitalExpenditures: TimestampedTimeSeries): CashFlow = {

    new CashFlow(
      netIncomeStartingLine,
      depreciationDepletion,
      amortization,
      cashFromOperatingActivities,
      issuanceRetirementOfDebtNet,
      cashFromFinancingActivities,
      netChangeInCash,
      cashInterestPaidSupplemental,
      cashTaxesPaidSupplemental,
      deferredTaxes,
      changesInWorkingCapital,
      cashFromInvestingActivities,
      foreignExchangeEffects,
      nonCashItems,
      otherInvestingCashFlowItemsTotal,
      financingCashFlowItems,
      totalCashDividendsPaid,
      issuanceRetirementOfStockNet,
      capitalExpenditures
    )
  }

  def apply(): CashFlow = {
    new CashFlow(
      (new DateTime(0), List()),
      (new DateTime(0), List()),
      (new DateTime(0), List()),
      (new DateTime(0), List()),
      (new DateTime(0), List()),
      (new DateTime(0), List()),
      (new DateTime(0), List()),
      (new DateTime(0), List()),
      (new DateTime(0), List()),
      (new DateTime(0), List()),
      (new DateTime(0), List()),
      (new DateTime(0), List()),
      (new DateTime(0), List()),
      (new DateTime(0), List()),
      (new DateTime(0), List()),
      (new DateTime(0), List()),
      (new DateTime(0), List()),
      (new DateTime(0), List()),
      (new DateTime(0), List())
    )
  }

}

/**
 * Cash flow fundamentals
 */
class CashFlow(
    val netIncomeStartingLine: TimestampedTimeSeries,
    val depreciationDepletion: TimestampedTimeSeries,
    val amortization: TimestampedTimeSeries,
    val cashFromOperatingActivities: TimestampedTimeSeries,
    val issuanceRetirementOfDebtNet: TimestampedTimeSeries,
    val cashFromFinancingActivities: TimestampedTimeSeries,
    val netChangeInCash: TimestampedTimeSeries,
    val cashInterestPaidSupplemental: TimestampedTimeSeries,
    val cashTaxesPaidSupplemental: TimestampedTimeSeries,
    val deferredTaxes: TimestampedTimeSeries,
    val changesInWorkingCapital: TimestampedTimeSeries,
    val cashFromInvestingActivities: TimestampedTimeSeries,
    val foreignExchangeEffects: TimestampedTimeSeries,
    val nonCashItems: TimestampedTimeSeries,
    val otherInvestingCashFlowItemsTotal: TimestampedTimeSeries,
    val financingCashFlowItems: TimestampedTimeSeries,
    val totalCashDividendsPaid: TimestampedTimeSeries,
    val issuanceRetirementOfStockNet: TimestampedTimeSeries,
    val capitalExpenditures: TimestampedTimeSeries) {}

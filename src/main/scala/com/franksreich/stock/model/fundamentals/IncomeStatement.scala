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

import com.franksreich.stock.model.source.quandl.QuandlLoader
import com.franksreich.stock.model.types.TimestampedTimeSeries

import com.github.nscala_time.time.Imports._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/** Companion class for income statements */
object IncomeStatement {

  def apply(
      stockSymbol: String,
      old: IncomeStatement,
      toBeDate: DateTime,
      quandlLoader: QuandlLoader = new QuandlLoader): Future[IncomeStatement] = {
    
    val revenue = if (old.revenue._1 < toBeDate) {
      quandlLoader.incomeStatement.revenue(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.revenue }
    }

    val totalRevenue = if (old.totalRevenue._1 < toBeDate) {
      quandlLoader.incomeStatement.totalRevenue(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.totalRevenue }
    }

    val costOfRevenueTotal = if (old.costOfRevenueTotal._1 < toBeDate) {
      quandlLoader.incomeStatement.costOfRevenueTotal(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.costOfRevenueTotal }
    }

    val grossProfit = if (old.grossProfit._1 < toBeDate) {
      quandlLoader.incomeStatement.grossProfit(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.grossProfit }
    }

    val sellingGeneralAdminExpensesTotal = if (old.sellingGeneralAdminExpensesTotal._1 < toBeDate) {
      quandlLoader.incomeStatement.sellingGeneralAdminExpensesTotal(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.sellingGeneralAdminExpensesTotal }
    }

    val gainLossOnSaleOfAssets = if (old.gainLossOnSaleOfAssets._1 < toBeDate) {
      quandlLoader.incomeStatement.gainLossOnSaleOfAssets(stockSymbol) map { v => (DateTime.now, v)}
    } else {
      Future { old.gainLossOnSaleOfAssets }
    }

    val otherNet = if (old.otherNet._1 < toBeDate) {
      quandlLoader.incomeStatement.otherNet(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.otherNet }
    }

    val incomeBeforeTax = if (old.incomeBeforeTax._1 < toBeDate) {
      quandlLoader.incomeStatement.incomeBeforeTax(stockSymbol) map { v => (DateTime.now, v)}
    } else {
      Future { old.incomeBeforeTax }
    }

    val incomeAfterTax = if (old.incomeAfterTax._1 < toBeDate) {
      quandlLoader.incomeStatement.incomeAfterTax(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.incomeAfterTax }
    }

    val netIncomeBeforeExtraItems = if (old.netIncomeBeforeExtraItems._1 < toBeDate) {
      quandlLoader.incomeStatement.netIncomeBeforeExtraItems(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.netIncomeBeforeExtraItems }
    }

    val netIncome = if (old.netIncome._1 < toBeDate) {
      quandlLoader.incomeStatement.netIncome(stockSymbol) map { v => (DateTime.now, v)}
    } else {
      Future { old.netIncome }
    }

    val incomeAvailableToCommonExcludingExtraItems = if (old.netIncome._1 < toBeDate) {
      quandlLoader.incomeStatement.incomeAvailableToCommonExcludingExtraItems(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.incomeAvailableToCommonExcludingExtraItems }
    }

    val incomeAvailableToCommonIncludingExtraItems = if (old.incomeAvailableToCommonIncludingExtraItems._1 < toBeDate) {
      quandlLoader.incomeStatement.incomeAvailableToCommonIncludingExtraItems(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.incomeAvailableToCommonIncludingExtraItems }
    }

    val dilutedWeightedAverageShares = if (old.dilutedWeightedAverageShares._1 < toBeDate) {
      quandlLoader.incomeStatement.dilutedWeightedAverageShares(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.dilutedWeightedAverageShares }
    }

    val dilutedEpsExcludingExtraordinaryItems = if (old.dilutedEpsExcludingExtraordinaryItems._1 < toBeDate) {
      quandlLoader.incomeStatement.dilutedEpsExcludingExtraordinaryItems(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.dilutedEpsExcludingExtraordinaryItems }
    }

    val researchDevelopment = if (old.researchDevelopment._1 < toBeDate) {
      quandlLoader.incomeStatement.researchDevelopment(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.researchDevelopment }
    }

    val unusualExpenseIncome = if (old.unusualExpenseIncome._1 < toBeDate) {
      quandlLoader.incomeStatement.unusualExpenseIncome(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.unusualExpenseIncome }
    }

    val operatingIncome = if (old.operatingIncome._1 < toBeDate) {
      quandlLoader.incomeStatement.operatingIncome(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.operatingIncome }
    }

    val minorityInterest = if (old.minorityInterest._1 < toBeDate) {
      quandlLoader.incomeStatement.minorityInterest(stockSymbol) map { v => (DateTime.now, v)}
    } else {
      Future { old.minorityInterest }
    }

    val dividendPerShareCommonStockPrimaryIssue = if (old.dividendPerShareCommonStockPrimaryIssue._1 < toBeDate) {
      quandlLoader.incomeStatement.dividendPerShareCommonStockPrimaryIssue(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.dividendPerShareCommonStockPrimaryIssue }
    }

    val depreciationAmortization = if (old.depreciationAmortization._1 < toBeDate) {
      quandlLoader.incomeStatement.depreciationAmortization(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.depreciationAmortization }
    }

    val equityInAffiliates = if (old.equityInAffiliates._1 < toBeDate) {
      quandlLoader.incomeStatement.equityInAffiliates(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.equityInAffiliates }
    }

    val totalOperatingExpenses = if (old.totalOperatingExpenses._1 < toBeDate) {
      quandlLoader.incomeStatement.totalOperatingExpenses(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.totalOperatingExpenses }
    }

    val dilutedNormalizedEps = if (old.dilutedNormalizedEps._1 < toBeDate) {
      quandlLoader.incomeStatement.dilutedNormalizedEps(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.dilutedNormalizedEps }
    }

    val otherOperatingExpensesTotal = if (old.otherOperatingExpensesTotal._1 < toBeDate) {
      quandlLoader.incomeStatement.otherOperatingExpensesTotal(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.otherOperatingExpensesTotal }
    }

    val dilutionAdjustment = if (old.dilutionAdjustment._1 < toBeDate) {
      quandlLoader.incomeStatement.dilutionAdjustment(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.dilutionAdjustment }
    }

    val otherRevenueTotal = if (old.otherRevenueTotal._1 < toBeDate) {
      quandlLoader.incomeStatement.otherRevenueTotal(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.otherRevenueTotal }
    }

    val interestIncomeExpenseNetNonOperating = if (old.interestIncomeExpenseNetNonOperating._1 < toBeDate) {
      quandlLoader.incomeStatement.interestIncomeExpenseNetNonOperating(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.interestIncomeExpenseNetNonOperating }
    }

    val futureList = List(
      revenue,
      totalRevenue,
      costOfRevenueTotal,
      grossProfit,
      sellingGeneralAdminExpensesTotal,
      gainLossOnSaleOfAssets,
      otherNet,
      incomeBeforeTax,
      incomeAfterTax,
      netIncomeBeforeExtraItems,
      netIncome,
      incomeAvailableToCommonExcludingExtraItems,
      incomeAvailableToCommonIncludingExtraItems,
      dilutedWeightedAverageShares,
      dilutedEpsExcludingExtraordinaryItems,
      researchDevelopment,
      unusualExpenseIncome,
      operatingIncome,
      minorityInterest,
      dividendPerShareCommonStockPrimaryIssue,
      depreciationAmortization,
      equityInAffiliates,
      totalOperatingExpenses,
      dilutedNormalizedEps,
      otherOperatingExpensesTotal,
      dilutionAdjustment,
      otherRevenueTotal,
      interestIncomeExpenseNetNonOperating
    )

    val f = Future.sequence(futureList)

    f map { l => IncomeStatement(l) }
  }

  def apply(l: List[TimestampedTimeSeries]): IncomeStatement = {
    IncomeStatement(l(0), l(1), l(2), l(3), l(4), l(5), l(6), l(7), l(8), l(9), l(10), l(11), l(12), l(13), l(14),
      l(15), l(16), l(17), l(18), l(19), l(20), l(21), l(22), l(23), l(24), l(25), l(26), l(27))
  }

  def apply(
      revenue: TimestampedTimeSeries,
      totalRevenue: TimestampedTimeSeries,
      costOfRevenueTotal: TimestampedTimeSeries,
      grossProfit: TimestampedTimeSeries,
      sellingGeneralAdminExpensesTotal: TimestampedTimeSeries,
      gainLossOnSaleOfAssets: TimestampedTimeSeries,
      otherNet: TimestampedTimeSeries,
      incomeBeforeTax: TimestampedTimeSeries,
      incomeAfterTax: TimestampedTimeSeries,
      netIncomeBeforeExtraItems: TimestampedTimeSeries,
      netIncome: TimestampedTimeSeries,
      incomeAvailableToCommonExcludingExtraItems: TimestampedTimeSeries,
      incomeAvailableToCommonIncludingExtraItems: TimestampedTimeSeries,
      dilutedWeightedAverageShares: TimestampedTimeSeries,
      dilutedEpsExcludingExtraordinaryItems: TimestampedTimeSeries,
      researchDevelopment: TimestampedTimeSeries,
      unusualExpenseIncome: TimestampedTimeSeries,
      operatingIncome: TimestampedTimeSeries,
      minorityInterest: TimestampedTimeSeries,
      dividendPerShareCommonStockPrimaryIssue: TimestampedTimeSeries,
      depreciationAmortization: TimestampedTimeSeries,
      equityInAffiliates: TimestampedTimeSeries,
      totalOperatingExpenses: TimestampedTimeSeries,
      dilutedNormalizedEps: TimestampedTimeSeries,
      otherOperatingExpensesTotal: TimestampedTimeSeries,
      dilutionAdjustment: TimestampedTimeSeries,
      otherRevenueTotal: TimestampedTimeSeries,
      interestIncomeExpenseNetNonOperating: TimestampedTimeSeries): IncomeStatement = {

    new IncomeStatement(
      revenue,
      totalRevenue,
      costOfRevenueTotal,
      grossProfit,
      sellingGeneralAdminExpensesTotal,
      gainLossOnSaleOfAssets,
      otherNet,
      incomeBeforeTax,
      incomeAfterTax,
      netIncomeBeforeExtraItems,
      netIncome,
      incomeAvailableToCommonExcludingExtraItems,
      incomeAvailableToCommonIncludingExtraItems,
      dilutedWeightedAverageShares,
      dilutedEpsExcludingExtraordinaryItems,
      researchDevelopment,
      unusualExpenseIncome,
      operatingIncome,
      minorityInterest,
      dividendPerShareCommonStockPrimaryIssue,
      depreciationAmortization,
      equityInAffiliates,
      totalOperatingExpenses,
      dilutedNormalizedEps,
      otherOperatingExpensesTotal,
      dilutionAdjustment,
      otherRevenueTotal,
      interestIncomeExpenseNetNonOperating
    )
  }

  def apply(): IncomeStatement = {
    new IncomeStatement(
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

/** Income statement */
class IncomeStatement(
    val revenue: TimestampedTimeSeries,
    val totalRevenue: TimestampedTimeSeries,
    val costOfRevenueTotal: TimestampedTimeSeries,
    val grossProfit: TimestampedTimeSeries,
    val sellingGeneralAdminExpensesTotal: TimestampedTimeSeries,
    val gainLossOnSaleOfAssets: TimestampedTimeSeries,
    val otherNet: TimestampedTimeSeries,
    val incomeBeforeTax: TimestampedTimeSeries,
    val incomeAfterTax: TimestampedTimeSeries,
    val netIncomeBeforeExtraItems: TimestampedTimeSeries,
    val netIncome: TimestampedTimeSeries,
    val incomeAvailableToCommonExcludingExtraItems: TimestampedTimeSeries,
    val incomeAvailableToCommonIncludingExtraItems: TimestampedTimeSeries,
    val dilutedWeightedAverageShares: TimestampedTimeSeries,
    val dilutedEpsExcludingExtraordinaryItems: TimestampedTimeSeries,
    val researchDevelopment: TimestampedTimeSeries,
    val unusualExpenseIncome: TimestampedTimeSeries,
    val operatingIncome: TimestampedTimeSeries,
    val minorityInterest: TimestampedTimeSeries,
    val dividendPerShareCommonStockPrimaryIssue: TimestampedTimeSeries,
    val depreciationAmortization: TimestampedTimeSeries,
    val equityInAffiliates: TimestampedTimeSeries,
    val totalOperatingExpenses: TimestampedTimeSeries,
    val dilutedNormalizedEps: TimestampedTimeSeries,
    val otherOperatingExpensesTotal: TimestampedTimeSeries,
    val dilutionAdjustment: TimestampedTimeSeries,
    val otherRevenueTotal: TimestampedTimeSeries,
    val interestIncomeExpenseNetNonOperating: TimestampedTimeSeries) {}

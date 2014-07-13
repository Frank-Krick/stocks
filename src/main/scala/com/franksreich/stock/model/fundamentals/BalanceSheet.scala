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

/** Companion class for balance sheet */
object BalanceSheet {

  def apply(stockSymbol: String, old: BalanceSheet, toBeDate: DateTime): Future[BalanceSheet] = {
    val accountsReceivable = if (old.accountsReceivable._1 < toBeDate) {
      quandlLoader.balanceSheet.accountsReceivable(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.accountsReceivable }
    }

    val totalInventory = if (old.totalInventory._1 < toBeDate) {
      quandlLoader.balanceSheet.totalInventory(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.totalInventory }
    }

    val prepaidExpenses = if (old.prepaidExpenses._1 < toBeDate) {
      quandlLoader.balanceSheet.prepaidExpenses(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.prepaidExpenses }
    }

    val otherCurrentAssetsTotal = if (old.otherCurrentAssetsTotal._1 < toBeDate) {
      quandlLoader.balanceSheet.otherCurrentAssetsTotal(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.otherCurrentAssetsTotal }
    }

    val totalCurrentAssets = if (old.totalCurrentAssets._1 < toBeDate) {
      quandlLoader.balanceSheet.totalCurrentAssets(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.totalCurrentAssets }
    }

    val goodwillNet = if (old.goodwillNet._1 < toBeDate) {
      quandlLoader.balanceSheet.goodwillNet(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.goodwillNet }
    }

    val totalAssets = if (old.totalAssets._1 < toBeDate) {
      quandlLoader.balanceSheet.totalAssets(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.totalAssets }
    }

    val accountsPayable = if (old.accountsPayable._1 < toBeDate) {
      quandlLoader.balanceSheet.accountsPayable(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.accountsPayable }
    }

    val currentPortOfLongTermDebtCapitalLeases = if (old.currentPortOfLongTermDebtCapitalLeases._1 < toBeDate) {
      quandlLoader.balanceSheet.currentPortOfLongTermDebtCapitalLeases(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.currentPortOfLongTermDebtCapitalLeases }
    }

    val totalCurrentLiabilities = if (old.totalCurrentLiabilities._1 < toBeDate) {
      quandlLoader.balanceSheet.totalCurrentLiabilities(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.totalCurrentLiabilities }
    }

    val deferredIncomeTax = if (old.deferredIncomeTax._1 < toBeDate) {
      quandlLoader.balanceSheet.deferredIncomeTax(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.deferredIncomeTax }
    }

    val totalLiabilities = if (old.totalLiabilities._1 < toBeDate) {
      quandlLoader.balanceSheet.totalLiabilities(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.totalLiabilities }
    }

    val commonStockTotal = if (old.commonStockTotal._1 < toBeDate) {
      quandlLoader.balanceSheet.commonStockTotal(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.commonStockTotal }
    }

    val additionalPaidInCapital = if (old.additionalPaidInCapital._1 < toBeDate) {
      quandlLoader.balanceSheet.additionalPaidInCapital(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.additionalPaidInCapital }
    }

    val retainedEarningsAccumulatedDeficit = if (old.retainedEarningsAccumulatedDeficit._1 < toBeDate) {
      quandlLoader.balanceSheet.retainedEarningsAccumulatedDeficit(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.retainedEarningsAccumulatedDeficit }
    }

    val totalEquity = if (old.totalEquity._1 < toBeDate) {
      quandlLoader.balanceSheet.totalEquity(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.totalEquity }
    }

    val totalLiabilitiesShareholdersEquity = if (old.totalLiabilitiesShareholdersEquity._1 < toBeDate) {
      quandlLoader.balanceSheet.totalLiabilitiesShareholdersEquity(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.totalLiabilitiesShareholdersEquity }
    }

    val totalCommonSharesOutstanding = if (old.totalCommonSharesOutstanding._1 < toBeDate) {
      quandlLoader.balanceSheet.totalCommonSharesOutstanding(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.totalCommonSharesOutstanding }
    }

    val cashEquivalents = if (old.cashEquivalents._1 < toBeDate) {
      quandlLoader.balanceSheet.cashEquivalents(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.cashEquivalents }
    }

    val cashAndShortTermInvestments = if (old.cashAndShortTermInvestments._1 < toBeDate) {
      quandlLoader.balanceSheet.cashAndShortTermInvestments(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.cashAndShortTermInvestments }
    }

    val intangiblesNet = if (old.intangiblesNet._1 < toBeDate) {
      quandlLoader.balanceSheet.intangiblesNet(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.intangiblesNet }
    }

    val otherLongTermAssetsTotal = if (old.otherLongTermAssetsTotal._1 < toBeDate) {
      quandlLoader.balanceSheet.otherLongTermAssetsTotal(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.otherLongTermAssetsTotal }
    }

    val longTermDebt = if (old.longTermDebt._1 < toBeDate) {
      quandlLoader.balanceSheet.longTermDebt(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.longTermDebt }
    }

    val totalLongTermDebt = if (old.totalLongTermDebt._1 < toBeDate) {
      quandlLoader.balanceSheet.totalLongTermDebt(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.totalLongTermDebt }
    }

    val totalDebt = if (old.totalDebt._1 < toBeDate) {
      quandlLoader.balanceSheet.totalDebt(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.totalDebt }
    }

    val minorityInterest = if (old.minorityInterest._1 < toBeDate) {
      quandlLoader.balanceSheet.minorityInterest(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.minorityInterest }
    }

    val otherEquityTotal = if (old.otherEquityTotal._1 < toBeDate) {
      quandlLoader.balanceSheet.otherEquityTotal(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.otherEquityTotal }
    }

    val propertyPlantEquipmentTotalGross = if (old.propertyPlantEquipmentTotalGross._1 < toBeDate) {
      quandlLoader.balanceSheet.propertyPlantEquipmentTotalGross(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.propertyPlantEquipmentTotalGross }
    }

    val otherCurrentLiabilitiesTotal = if (old.otherCurrentLiabilitiesTotal._1 < toBeDate) {
      quandlLoader.balanceSheet.otherCurrentLiabilitiesTotal(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.otherCurrentLiabilitiesTotal }
    }

    val otherLiabilitiesTotal = if (old.otherLiabilitiesTotal._1 < toBeDate) {
      quandlLoader.balanceSheet.otherLiabilitiesTotal(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.otherLiabilitiesTotal }
    }

    val longTermInvestments = if (old.longTermInvestments._1 < toBeDate) {
      quandlLoader.balanceSheet.longTermInvestments(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.longTermInvestments }
    }

    val accruedExpenses = if (old.accruedExpenses._1 < toBeDate) {
      quandlLoader.balanceSheet.accruedExpenses(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.accruedExpenses }
    }

    val notesPayableShortTermDebt = if (old.notesPayableShortTermDebt._1 < toBeDate) {
      quandlLoader.balanceSheet.notesPayableShortTermDebt(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.notesPayableShortTermDebt }
    }

    val totalReceivablesNet = if (old.totalReceivablesNet._1 < toBeDate) {
      quandlLoader.balanceSheet.totalReceivablesNet(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.totalReceivablesNet }
    }

    val preferredStockNonRedeemableDebt = if (old.preferredStockNonRedeemableDebt._1 < toBeDate) {
      quandlLoader.balanceSheet.preferredStockNonRedeemableDebt(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.preferredStockNonRedeemableDebt }
    }

    val shortTermInvestments = if (old.shortTermInvestments._1 < toBeDate) {
      quandlLoader.balanceSheet.shortTermInvestments(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.shortTermInvestments }
    }

    val capitalLeaseObligations = if (old.capitalLeaseObligations._1 < toBeDate) {
      quandlLoader.balanceSheet.capitalLeaseObligations(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.capitalLeaseObligations }
    }

    val accumulatedDepreciationTotal = if (old.accumulatedDepreciationTotal._1 < toBeDate) {
      quandlLoader.balanceSheet.accumulatedDepreciationTotal(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.accumulatedDepreciationTotal }
    }

    val redeemablePreferredStockTotal = if (old.redeemablePreferredStockTotal._1 < toBeDate) {
      quandlLoader.balanceSheet.redeemablePreferredStockTotal(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.redeemablePreferredStockTotal }
    }

    val treasuryStockCommon = if (old.treasuryStockCommon._1 < toBeDate) {
      quandlLoader.balanceSheet.treasuryStockCommon(stockSymbol) map { v => (DateTime.now, v) }
    } else {
      Future { old.treasuryStockCommon }
    }

    val futureList = List(
      accountsReceivable,
      totalInventory,
      prepaidExpenses,
      otherCurrentAssetsTotal,
      totalCurrentAssets,
      goodwillNet,
      totalAssets,
      accountsPayable,
      currentPortOfLongTermDebtCapitalLeases,
      totalCurrentLiabilities,
      deferredIncomeTax,
      totalLiabilities,
      commonStockTotal,
      additionalPaidInCapital,
      retainedEarningsAccumulatedDeficit,
      totalEquity,
      totalLiabilitiesShareholdersEquity,
      totalCommonSharesOutstanding,
      cashEquivalents,
      cashAndShortTermInvestments,
      intangiblesNet,
      otherLongTermAssetsTotal,
      longTermDebt,
      totalLongTermDebt,
      totalDebt,
      minorityInterest,
      otherEquityTotal,
      propertyPlantEquipmentTotalGross,
      otherCurrentLiabilitiesTotal,
      otherLiabilitiesTotal,
      longTermInvestments,
      accruedExpenses,
      notesPayableShortTermDebt,
      totalReceivablesNet,
      preferredStockNonRedeemableDebt,
      shortTermInvestments,
      capitalLeaseObligations,
      accumulatedDepreciationTotal,
      redeemablePreferredStockTotal,
      treasuryStockCommon
    )

    val f = Future.sequence(futureList)

    f map { l => BalanceSheet(l)
    }
  }

  def apply(l: List[TimestampedTimeSeries]): BalanceSheet = {
    BalanceSheet(l(0), l(1), l(2), l(3), l(4), l(5), l(6), l(7), l(8), l(9), l(10), l(11), l(12), l(13),
      l(14), l(15), l(16), l(17), l(18), l(19), l(20), l(21), l(22), l(23), l(24), l(25), l(26), l(27), l(28), l(29),
      l(30), l(31), l(32), l(33), l(34), l(35), l(36), l(37), l(38), l(39))
  }

  def apply(
      accountsReceivable: TimestampedTimeSeries,
      totalInventory: TimestampedTimeSeries,
      prepaidExpenses: TimestampedTimeSeries,
      otherCurrentAssetsTotal: TimestampedTimeSeries,
      totalCurrentAssets: TimestampedTimeSeries,
      goodwillNet: TimestampedTimeSeries,
      totalAssets: TimestampedTimeSeries,
      accountsPayable: TimestampedTimeSeries,
      currentPortOfLongTermDebtCapitalLeases: TimestampedTimeSeries,
      totalCurrentLiabilities: TimestampedTimeSeries,
      deferredIncomeTax: TimestampedTimeSeries,
      totalLiabilities: TimestampedTimeSeries,
      commonStockTotal: TimestampedTimeSeries,
      additionalPaidInCapital: TimestampedTimeSeries,
      retainedEarningsAccumulatedDeficit: TimestampedTimeSeries,
      totalEquity: TimestampedTimeSeries,
      totalLiabilitiesShareholdersEquity: TimestampedTimeSeries,
      totalCommonSharesOutstanding: TimestampedTimeSeries,
      cashEquivalents: TimestampedTimeSeries,
      cashAndShortTermInvestments: TimestampedTimeSeries,
      intangiblesNet: TimestampedTimeSeries,
      otherLongTermAssetsTotal: TimestampedTimeSeries,
      longTermDebt: TimestampedTimeSeries,
      totalLongTermDebt: TimestampedTimeSeries,
      totalDebt: TimestampedTimeSeries,
      minorityInterest: TimestampedTimeSeries,
      otherEquityTotal: TimestampedTimeSeries,
      propertyPlantEquipmentTotalGross: TimestampedTimeSeries,
      otherCurrentLiabilitiesTotal: TimestampedTimeSeries,
      otherLiabilitiesTotal: TimestampedTimeSeries,
      longTermInvestments: TimestampedTimeSeries,
      accruedExpenses: TimestampedTimeSeries,
      notesPayableShortTermDebt: TimestampedTimeSeries,
      totalReceivablesNet: TimestampedTimeSeries,
      preferredStockNonRedeemableDebt: TimestampedTimeSeries,
      shortTermInvestments: TimestampedTimeSeries,
      capitalLeaseObligations: TimestampedTimeSeries,
      accumulatedDepreciationTotal: TimestampedTimeSeries,
      redeemablePreferredStockTotal: TimestampedTimeSeries,
      treasuryStockCommon: TimestampedTimeSeries): BalanceSheet = {

    new BalanceSheet(
      accountsReceivable,
      totalInventory,
      prepaidExpenses,
      otherCurrentAssetsTotal,
      totalCurrentAssets,
      goodwillNet,
      totalAssets,
      accountsPayable,
      currentPortOfLongTermDebtCapitalLeases,
      totalCurrentLiabilities,
      deferredIncomeTax,
      totalLiabilities,
      commonStockTotal,
      additionalPaidInCapital,
      retainedEarningsAccumulatedDeficit,
      totalEquity,
      totalLiabilitiesShareholdersEquity,
      totalCommonSharesOutstanding,
      cashEquivalents,
      cashAndShortTermInvestments,
      intangiblesNet,
      otherLongTermAssetsTotal,
      longTermDebt,
      totalLongTermDebt,
      totalDebt,
      minorityInterest,
      otherEquityTotal,
      propertyPlantEquipmentTotalGross,
      otherCurrentLiabilitiesTotal,
      otherLiabilitiesTotal,
      longTermInvestments,
      accruedExpenses,
      notesPayableShortTermDebt,
      totalReceivablesNet,
      preferredStockNonRedeemableDebt,
      shortTermInvestments,
      capitalLeaseObligations,
      accumulatedDepreciationTotal,
      redeemablePreferredStockTotal,
      treasuryStockCommon
    )
  }

  def apply(): BalanceSheet = {
    new BalanceSheet(
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

/** Balance sheet data */
class BalanceSheet(
    val accountsReceivable: TimestampedTimeSeries,
    val totalInventory: TimestampedTimeSeries,
    val prepaidExpenses: TimestampedTimeSeries,
    val otherCurrentAssetsTotal: TimestampedTimeSeries,
    val totalCurrentAssets: TimestampedTimeSeries,
    val goodwillNet: TimestampedTimeSeries,
    val totalAssets: TimestampedTimeSeries,
    val accountsPayable: TimestampedTimeSeries,
    val currentPortOfLongTermDebtCapitalLeases: TimestampedTimeSeries,
    val totalCurrentLiabilities: TimestampedTimeSeries,
    val deferredIncomeTax: TimestampedTimeSeries,
    val totalLiabilities: TimestampedTimeSeries,
    val commonStockTotal: TimestampedTimeSeries,
    val additionalPaidInCapital: TimestampedTimeSeries,
    val retainedEarningsAccumulatedDeficit: TimestampedTimeSeries,
    val totalEquity: TimestampedTimeSeries,
    val totalLiabilitiesShareholdersEquity: TimestampedTimeSeries,
    val totalCommonSharesOutstanding: TimestampedTimeSeries,
    val cashEquivalents: TimestampedTimeSeries,
    val cashAndShortTermInvestments: TimestampedTimeSeries,
    val intangiblesNet: TimestampedTimeSeries,
    val otherLongTermAssetsTotal: TimestampedTimeSeries,
    val longTermDebt: TimestampedTimeSeries,
    val totalLongTermDebt: TimestampedTimeSeries,
    val totalDebt: TimestampedTimeSeries,
    val minorityInterest: TimestampedTimeSeries,
    val otherEquityTotal: TimestampedTimeSeries,
    val propertyPlantEquipmentTotalGross: TimestampedTimeSeries,
    val otherCurrentLiabilitiesTotal: TimestampedTimeSeries,
    val otherLiabilitiesTotal: TimestampedTimeSeries,
    val longTermInvestments: TimestampedTimeSeries,
    val accruedExpenses: TimestampedTimeSeries,
    val notesPayableShortTermDebt: TimestampedTimeSeries,
    val totalReceivablesNet: TimestampedTimeSeries,
    val preferredStockNonRedeemableDebt: TimestampedTimeSeries,
    val shortTermInvestments: TimestampedTimeSeries,
    val capitalLeaseObligations: TimestampedTimeSeries,
    val accumulatedDepreciationTotal: TimestampedTimeSeries,
    val redeemablePreferredStockTotal: TimestampedTimeSeries,
    val treasuryStockCommon: TimestampedTimeSeries) {}
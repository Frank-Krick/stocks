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

import com.github.nscala_time.time.Imports.DateTime

import com.franksreich.stock.model.types.TimestampedTimeSeries

/** Factory for balance sheet */
object BalanceSheet {
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
      totalReceivablesDebt: TimestampedTimeSeries,
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
      totalReceivablesDebt,
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
    val totalReceivablesDebt: TimestampedTimeSeries,
    val preferredStockNonRedeemableDebt: TimestampedTimeSeries,
    val shortTermInvestments: TimestampedTimeSeries,
    val capitalLeaseObligations: TimestampedTimeSeries,
    val accumulatedDepreciationTotal: TimestampedTimeSeries,
    val redeemablePreferredStockTotal: TimestampedTimeSeries,
    val treasuryStockCommon: TimestampedTimeSeries) {}
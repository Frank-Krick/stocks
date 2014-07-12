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

import com.franksreich.stock.model.types.TimestampedTimeSeries

import com.github.nscala_time.time.Imports.DateTime

/** Factory for income statements */
object IncomeStatement {

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
      depreciationAppreciation: TimestampedTimeSeries,
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
      depreciationAppreciation,
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
    val depreciationAppreciation: TimestampedTimeSeries,
    val equityInAffiliates: TimestampedTimeSeries,
    val totalOperatingExpenses: TimestampedTimeSeries,
    val dilutedNormalizedEps: TimestampedTimeSeries,
    val otherOperatingExpensesTotal: TimestampedTimeSeries,
    val dilutionAdjustment: TimestampedTimeSeries,
    val otherRevenueTotal: TimestampedTimeSeries,
    val interestIncomeExpenseNetNonOperating: TimestampedTimeSeries) {}

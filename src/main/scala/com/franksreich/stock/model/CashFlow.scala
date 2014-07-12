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

/** Factory for cash flow */
object CashFlow {
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

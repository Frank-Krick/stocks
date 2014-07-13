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
package com.franksreich.stock.model.source.database.utils

import com.franksreich.stock.model.{StockPrice, StockFactSheet}
import com.franksreich.stock.model.fundamentals.{CashFlow, IncomeStatement, BalanceSheet, Fundamentals}
import com.franksreich.stock.model.types.{TimeSeries, TimestampedTimeSeries}

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers

import org.bson.types.ObjectId

import org.joda.time.DateTime

/** Converter between model classes and bson representation */
object stockFactSheetConverter {
  RegisterJodaTimeConversionHelpers()

  /** Convert stock fact sheet to bson
   *
   * @param factSheet Fact sheet to convert
   * @return Bson representation of the fact sheet
   */
  def convertStockFactSheetToBson(factSheet: StockFactSheet): DBObject = {
    val fundamentals = convertFundamentalsToBson(factSheet.fundamentals)
    val stockPrice = convertStockPriceToBson(factSheet.stockPrice)

    MongoDBObject(
      "_id" -> factSheet.id,
      "stockSymbol" -> factSheet.stockSymbol,
      "fundamentals" -> fundamentals,
      "stockPrice" -> stockPrice
    )
  }

  /** Convert bson to stock fact sheet
   *
   * @param bson Bson representation of a stock fact sheet
   * @return Stock fact sheet
   */
  def convertStockFactSheetFromBson(bson: MongoDBObject): StockFactSheet = {
    val fundamentals = convertFundamentalsFromBson(bson.as[DBObject]("fundamentals"))
    val stockPrice = convertStockPriceFromBson(bson.as[DBObject]("stockPrice"))
    new StockFactSheet(
      bson.as[ObjectId]("_id"),
      bson.as[String]("stockSymbol"),
      fundamentals,
      stockPrice)
  }

  def convertStockPriceToBson(stockPrice: StockPrice): DBObject = {
    MongoDBObject(
      "timestamp" -> stockPrice.timestamp,
      "open" -> stockPrice.open.map( v => (v._1, v._2.toString()) ),
      "high" -> stockPrice.high.map( v => (v._1, v._2.toString()) ),
      "low" -> stockPrice.low.map( v => (v._1, v._2.toString()) ),
      "close" -> stockPrice.close.map( v => (v._1, v._2.toString()) ),
      "volume" -> stockPrice.volume.map( v => (v._1, v._2.toString()) ),
      "split" -> stockPrice.split.map( v => (v._1, v._2.toString()) ),
      "adjustedOpen" -> stockPrice.adjustedOpen.map( v => (v._1, v._2.toString()) ),
      "adjustedHigh" -> stockPrice.adjustedHigh.map( v => (v._1, v._2.toString()) ),
      "adjustedLow" -> stockPrice.adjustedLow.map( v => (v._1, v._2.toString()) ),
      "adjustedClose" -> stockPrice.adjustedClose.map( v => (v._1, v._2.toString()) ),
      "adjustedVolume" -> stockPrice.adjustedVolume.map( v => (v._1, v._2.toString()) )
    )
  }

  def convertStockPriceFromBson(bson: MongoDBObject): StockPrice = {
    StockPrice(
      bson.as[Some[DateTime]]("timestamp").getOrElse(new DateTime(0)),
      timeSeriesFromBson("open", bson),
      timeSeriesFromBson("high", bson),
      timeSeriesFromBson("low", bson),
      timeSeriesFromBson("close", bson),
      timeSeriesFromBson("volume", bson),
      timeSeriesFromBson("split", bson),
      timeSeriesFromBson("adjustedOpen", bson),
      timeSeriesFromBson("adjustedHigh", bson),
      timeSeriesFromBson("adjustedLow", bson),
      timeSeriesFromBson("adjustedClose", bson),
      timeSeriesFromBson("adjustedVolume", bson)
    )
  }

  def convertFundamentalsToBson(fundamentals: Fundamentals): DBObject = {
    MongoDBObject(
      "cashFlow" -> convertCashFlowToBson(fundamentals.cashFlow),
      "incomeStatement" -> convertIncomeStatementToBson(fundamentals.incomeStatement),
      "balanceSheet" -> convertBalanceSheetToBson(fundamentals.balanceSheet)
    )
  }

  def convertFundamentalsFromBson(bson: MongoDBObject): Fundamentals = {
    val balanceSheet = convertBalanceSheetFromBson(bson.as[DBObject]("balanceSheet"))
    val cashFlow = convertCashFlowFromBson(bson.as[DBObject]("cashFlow"))
    val incomeStatement = convertIncomeStatementFromBson(bson.as[DBObject]("incomeStatement"))

    Fundamentals(balanceSheet, cashFlow, incomeStatement)
  }

  def convertBalanceSheetToBson(balanceSheet: BalanceSheet): DBObject = {
    val accountsReceivable = timestampedTimeSeriesToBson(balanceSheet.accountsReceivable)
    val totalInventory = timestampedTimeSeriesToBson(balanceSheet.totalInventory)
    val prepaidExpenses = timestampedTimeSeriesToBson(balanceSheet.prepaidExpenses)
    val otherCurrentAssetsTotal = timestampedTimeSeriesToBson(balanceSheet.otherCurrentAssetsTotal)
    val totalCurrentAssets = timestampedTimeSeriesToBson(balanceSheet.totalCurrentAssets)
    val goodwillNet = timestampedTimeSeriesToBson(balanceSheet.goodwillNet)
    val totalAssets = timestampedTimeSeriesToBson(balanceSheet.totalAssets)
    val accountsPayable = timestampedTimeSeriesToBson(balanceSheet.accountsPayable)
    val currentPortOfLongTermDebtCapitalLeases = timestampedTimeSeriesToBson(
        balanceSheet.currentPortOfLongTermDebtCapitalLeases)
    val totalCurrentLiabilities = timestampedTimeSeriesToBson(balanceSheet.totalCurrentLiabilities)
    val deferredIncomeTax = timestampedTimeSeriesToBson(balanceSheet.deferredIncomeTax)
    val totalLiabilities = timestampedTimeSeriesToBson(balanceSheet.totalLiabilities)
    val commonStockTotal = timestampedTimeSeriesToBson(balanceSheet.commonStockTotal)
    val additionalPaidInCapital = timestampedTimeSeriesToBson(balanceSheet.additionalPaidInCapital)
    val retainedEarningsAccumulatedDeficit = timestampedTimeSeriesToBson(
        balanceSheet.retainedEarningsAccumulatedDeficit)
    val totalEquity = timestampedTimeSeriesToBson(balanceSheet.totalEquity)
    val totalLiabilitiesShareholdersEquity = timestampedTimeSeriesToBson(
        balanceSheet.totalLiabilitiesShareholdersEquity)
    val totalCommonSharesOutstanding = timestampedTimeSeriesToBson(balanceSheet.totalCommonSharesOutstanding)
    val cashEquivalents = timestampedTimeSeriesToBson(balanceSheet.cashEquivalents)
    val cashAndShortTermInvestments = timestampedTimeSeriesToBson(balanceSheet.cashAndShortTermInvestments)
    val intangiblesNet = timestampedTimeSeriesToBson(balanceSheet.intangiblesNet)
    val otherLongTermAssetsTotal = timestampedTimeSeriesToBson(balanceSheet.otherLongTermAssetsTotal)
    val longTermDebt = timestampedTimeSeriesToBson(balanceSheet.longTermDebt)
    val totalLongTermDebt = timestampedTimeSeriesToBson(balanceSheet.totalLongTermDebt)
    val totalDebt = timestampedTimeSeriesToBson(balanceSheet.totalDebt)
    val minorityInterest = timestampedTimeSeriesToBson(balanceSheet.minorityInterest)
    val otherEquityTotal = timestampedTimeSeriesToBson(balanceSheet.otherEquityTotal)
    val propertyPlantEquipmentTotalGross = timestampedTimeSeriesToBson(balanceSheet.propertyPlantEquipmentTotalGross)
    val otherCurrentLiabilitiesTotal = timestampedTimeSeriesToBson(balanceSheet.otherCurrentLiabilitiesTotal)
    val otherLiabilitiesTotal = timestampedTimeSeriesToBson(balanceSheet.otherLiabilitiesTotal)
    val longTermInvestments = timestampedTimeSeriesToBson(balanceSheet.longTermInvestments)
    val accruedExpenses = timestampedTimeSeriesToBson(balanceSheet.accruedExpenses)
    val notesPayableShortTermDebt = timestampedTimeSeriesToBson(balanceSheet.notesPayableShortTermDebt)
    val totalReceivablesNet = timestampedTimeSeriesToBson(balanceSheet.totalReceivablesNet)
    val preferredStockNonRedeemableDebt = timestampedTimeSeriesToBson(balanceSheet.preferredStockNonRedeemableDebt)
    val shortTermInvestments = timestampedTimeSeriesToBson(balanceSheet.shortTermInvestments)
    val capitalLeaseObligations = timestampedTimeSeriesToBson(balanceSheet.capitalLeaseObligations)
    val accumulatedDepreciationTotal = timestampedTimeSeriesToBson(balanceSheet.accumulatedDepreciationTotal)
    val redeemablePreferredStockTotal = timestampedTimeSeriesToBson(balanceSheet.redeemablePreferredStockTotal)
    val treasuryStockCommon = timestampedTimeSeriesToBson(balanceSheet.treasuryStockCommon)

    MongoDBObject(
      "accountsReceivable" -> accountsReceivable,
      "totalInventory" -> totalInventory,
      "prepaidExpenses" -> prepaidExpenses,
      "otherCurrentAssetsTotal" -> otherCurrentAssetsTotal,
      "totalCurrentAssets" -> totalCurrentAssets,
      "goodwillNet" -> goodwillNet,
      "totalAssets" -> totalAssets,
      "accountsPayable" -> accountsPayable,
      "currentPortOfLongTermDebtCapitalLeases" -> currentPortOfLongTermDebtCapitalLeases,
      "totalCurrentLiabilities" -> totalCurrentLiabilities,
      "deferredIncomeTax" -> deferredIncomeTax,
      "totalLiabilities" -> totalLiabilities,
      "commonStockTotal" -> commonStockTotal,
      "additionalPaidInCapital" -> additionalPaidInCapital,
      "retainedEarningsAccumulatedDeficit" -> retainedEarningsAccumulatedDeficit,
      "totalEquity" -> totalEquity,
      "totalLiabilitiesShareholdersEquity" -> totalLiabilitiesShareholdersEquity,
      "totalCommonSharesOutstanding" ->totalCommonSharesOutstanding,
      "cashEquivalents" -> cashEquivalents,
      "cashAndShortTermInvestments" -> cashAndShortTermInvestments,
      "intangiblesNet" -> intangiblesNet,
      "otherLongTermAssetsTotal" -> otherLongTermAssetsTotal,
      "longTermDebt" -> longTermDebt,
      "totalLongTermDebt" -> totalLongTermDebt,
      "totalDebt" -> totalDebt,
      "minorityInterest" -> minorityInterest,
      "otherEquityTotal" -> otherEquityTotal,
      "propertyPlantEquipmentTotalGross" -> propertyPlantEquipmentTotalGross,
      "otherCurrentLiabilitiesTotal" -> otherCurrentLiabilitiesTotal,
      "otherLiabilitiesTotal" -> otherLiabilitiesTotal,
      "longTermInvestments" -> longTermInvestments,
      "accruedExpenses" -> accruedExpenses,
      "notesPayableShortTermDebt" -> notesPayableShortTermDebt,
      "totalReceivablesNet" -> totalReceivablesNet,
      "preferredStockNonRedeemableDebt" -> preferredStockNonRedeemableDebt,
      "shortTermInvestments" -> shortTermInvestments,
      "capitalLeaseObligations" -> capitalLeaseObligations,
      "accumulatedDepreciationTotal" -> accumulatedDepreciationTotal,
      "redeemablePreferredStockTotal" -> redeemablePreferredStockTotal,
      "treasuryStockCommon" -> treasuryStockCommon
    )
  }

  def convertBalanceSheetFromBson(bson: MongoDBObject): BalanceSheet = {
    val keys = List(
      "accountsReceivable",
      "totalInventory",
      "prepaidExpenses",
      "otherCurrentAssetsTotal",
      "totalCurrentAssets",
      "goodwillNet",
      "totalAssets",
      "accountsPayable",
      "currentPortOfLongTermDebtCapitalLeases",
      "totalCurrentLiabilities",
      "deferredIncomeTax",
      "totalLiabilities",
      "commonStockTotal",
      "additionalPaidInCapital",
      "retainedEarningsAccumulatedDeficit",
      "totalEquity",
      "totalLiabilitiesShareholdersEquity",
      "totalCommonSharesOutstanding",
      "cashEquivalents",
      "cashAndShortTermInvestments",
      "intangiblesNet",
      "otherLongTermAssetsTotal",
      "longTermDebt",
      "totalLongTermDebt",
      "totalDebt",
      "minorityInterest",
      "otherEquityTotal",
      "propertyPlantEquipmentTotalGross",
      "otherCurrentLiabilitiesTotal",
      "otherLiabilitiesTotal",
      "longTermInvestments",
      "accruedExpenses",
      "notesPayableShortTermDebt",
      "totalReceivablesNet",
      "preferredStockNonRedeemableDebt",
      "shortTermInvestments",
      "capitalLeaseObligations",
      "accumulatedDepreciationTotal",
      "redeemablePreferredStockTotal",
      "treasuryStockCommon"
    )

    val balanceSheetData = keys map { key =>
      timestampedTimeSeriesFromBson(bson.as[DBObject](key))
    }

    BalanceSheet(balanceSheetData)
  }

  def convertIncomeStatementToBson(incomeStatement: IncomeStatement): DBObject = {
    val revenue = timestampedTimeSeriesToBson(incomeStatement.revenue)
    val totalRevenue = timestampedTimeSeriesToBson(incomeStatement.totalRevenue)
    val costOfRevenueTotal = timestampedTimeSeriesToBson(incomeStatement.costOfRevenueTotal)
    val grossProfit = timestampedTimeSeriesToBson(incomeStatement.grossProfit)
    val sellingGeneralAdminExpensesTotal = timestampedTimeSeriesToBson(
        incomeStatement.sellingGeneralAdminExpensesTotal)
    val gainLossOnSaleOfAssets = timestampedTimeSeriesToBson(incomeStatement.gainLossOnSaleOfAssets)
    val otherNet = timestampedTimeSeriesToBson(incomeStatement.otherNet)
    val incomeBeforeTax = timestampedTimeSeriesToBson(incomeStatement.incomeBeforeTax)
    val incomeAfterTax = timestampedTimeSeriesToBson(incomeStatement.incomeAfterTax)
    val netIncomeBeforeExtraItems = timestampedTimeSeriesToBson(incomeStatement.netIncomeBeforeExtraItems)
    val netIncome = timestampedTimeSeriesToBson(incomeStatement.netIncome)
    val incomeAvailableToCommonExcludingExtraItems = timestampedTimeSeriesToBson(
        incomeStatement.incomeAvailableToCommonExcludingExtraItems)
    val incomeAvailableToCommonIncludingExtraItems = timestampedTimeSeriesToBson(
        incomeStatement.incomeAvailableToCommonIncludingExtraItems)
    val dilutedWeightedAverageShares = timestampedTimeSeriesToBson(incomeStatement.dilutedWeightedAverageShares)
    val dilutedEpsExcludingExtraordinaryItems = timestampedTimeSeriesToBson(
        incomeStatement.dilutedEpsExcludingExtraordinaryItems)
    val researchDevelopment = timestampedTimeSeriesToBson(incomeStatement.researchDevelopment)
    val unusualExpenseIncome = timestampedTimeSeriesToBson(incomeStatement.unusualExpenseIncome)
    val operatingIncome = timestampedTimeSeriesToBson(incomeStatement.operatingIncome)
    val minorityInterest = timestampedTimeSeriesToBson(incomeStatement.minorityInterest)
    val dividendPerShareCommonStockPrimaryIssue = timestampedTimeSeriesToBson(
        incomeStatement.dividendPerShareCommonStockPrimaryIssue)
    val depreciationAmortization = timestampedTimeSeriesToBson(incomeStatement.depreciationAmortization)
    val equityInAffiliates = timestampedTimeSeriesToBson(incomeStatement.equityInAffiliates)
    val totalOperatingExpenses = timestampedTimeSeriesToBson(incomeStatement.totalOperatingExpenses)
    val dilutedNormalizedEps = timestampedTimeSeriesToBson(incomeStatement.dilutedNormalizedEps)
    val otherOperatingExpensesTotal = timestampedTimeSeriesToBson(incomeStatement.otherOperatingExpensesTotal)
    val dilutionAdjustment = timestampedTimeSeriesToBson(incomeStatement.dilutionAdjustment)
    val otherRevenueTotal = timestampedTimeSeriesToBson(incomeStatement.otherRevenueTotal)
    val interestIncomeExpenseNetNonOperating = timestampedTimeSeriesToBson(
        incomeStatement.interestIncomeExpenseNetNonOperating)

    MongoDBObject(
      "revenue" -> revenue,
      "totalRevenue" -> totalRevenue,
      "costOfRevenueTotal" -> costOfRevenueTotal,
      "grossProfit" -> grossProfit,
      "sellingGeneralAdminExpensesTotal" -> sellingGeneralAdminExpensesTotal,
      "gainLossOnSaleOfAssets" -> gainLossOnSaleOfAssets,
      "otherNet" -> otherNet,
      "incomeBeforeTax" -> incomeBeforeTax,
      "incomeAfterTax" -> incomeAfterTax,
      "netIncomeBeforeExtraItems" -> netIncomeBeforeExtraItems,
      "netIncome" -> netIncome,
      "incomeAvailableToCommonExcludingExtraItems" -> incomeAvailableToCommonExcludingExtraItems,
      "incomeAvailableToCommonIncludingExtraItems" -> incomeAvailableToCommonIncludingExtraItems,
      "dilutedWeightedAverageShares" -> dilutedWeightedAverageShares,
      "dilutedEpsExcludingExtraordinaryItems" -> dilutedEpsExcludingExtraordinaryItems,
      "researchDevelopment" -> researchDevelopment,
      "unusualExpenseIncome" -> unusualExpenseIncome,
      "operatingIncome" -> operatingIncome,
      "minorityInterest" -> minorityInterest,
      "dividendPerShareCommonStockPrimaryIssue" -> dividendPerShareCommonStockPrimaryIssue,
      "depreciationAmortization" -> depreciationAmortization,
      "equityInAffiliates" -> equityInAffiliates,
      "totalOperatingExpenses" -> totalOperatingExpenses,
      "dilutedNormalizedEps" -> dilutedNormalizedEps,
      "otherOperatingExpensesTotal" -> otherOperatingExpensesTotal,
      "dilutionAdjustment" -> dilutionAdjustment,
      "otherRevenueTotal" -> otherRevenueTotal,
      "interestIncomeExpenseNetNonOperating" -> interestIncomeExpenseNetNonOperating
    )
  }

  def convertIncomeStatementFromBson(bson: MongoDBObject): IncomeStatement = {
    val keys = List(
      "revenue",
      "totalRevenue",
      "costOfRevenueTotal",
      "grossProfit",
      "sellingGeneralAdminExpensesTotal",
      "gainLossOnSaleOfAssets",
      "otherNet",
      "incomeBeforeTax",
      "incomeAfterTax",
      "netIncomeBeforeExtraItems",
      "netIncome",
      "incomeAvailableToCommonExcludingExtraItems",
      "incomeAvailableToCommonIncludingExtraItems",
      "dilutedWeightedAverageShares",
      "dilutedEpsExcludingExtraordinaryItems",
      "researchDevelopment",
      "unusualExpenseIncome",
      "operatingIncome",
      "minorityInterest",
      "dividendPerShareCommonStockPrimaryIssue",
      "depreciationAmortization",
      "equityInAffiliates",
      "totalOperatingExpenses",
      "dilutedNormalizedEps",
      "otherOperatingExpensesTotal",
      "dilutionAdjustment",
      "otherRevenueTotal",
      "interestIncomeExpenseNetNonOperating"
    )

    val incomeStatementData = keys map { key =>
      timestampedTimeSeriesFromBson(bson.as[DBObject](key))
    }

    IncomeStatement(incomeStatementData)
  }

  def convertCashFlowToBson(cashFlow: CashFlow): DBObject = {
    val netIncomeStartingLine = timestampedTimeSeriesToBson(cashFlow.netIncomeStartingLine)
    val depreciationDepletion = timestampedTimeSeriesToBson(cashFlow.depreciationDepletion)
    val amortization = timestampedTimeSeriesToBson(cashFlow.amortization)
    val cashFromOperatingActivities = timestampedTimeSeriesToBson(cashFlow.cashFromOperatingActivities)
    val issuanceRetirementOfDebtNet = timestampedTimeSeriesToBson(cashFlow.issuanceRetirementOfDebtNet)
    val cashFromFinancingActivities = timestampedTimeSeriesToBson(cashFlow.cashFromFinancingActivities)
    val netChangeInCash = timestampedTimeSeriesToBson(cashFlow.netChangeInCash)
    val cashInterestPaidSupplemental = timestampedTimeSeriesToBson(cashFlow.cashInterestPaidSupplemental)
    val cashTaxesPaidSupplemental = timestampedTimeSeriesToBson(cashFlow.cashTaxesPaidSupplemental)
    val deferredTaxes = timestampedTimeSeriesToBson(cashFlow.deferredTaxes)
    val changesInWorkingCapital = timestampedTimeSeriesToBson(cashFlow.changesInWorkingCapital)
    val cashFromInvestingActivities = timestampedTimeSeriesToBson(cashFlow.cashFromInvestingActivities)
    val foreignExchangeEffects = timestampedTimeSeriesToBson(cashFlow.foreignExchangeEffects)
    val nonCashItems = timestampedTimeSeriesToBson(cashFlow.nonCashItems)
    val otherInvestingCashFlowItemsTotal = timestampedTimeSeriesToBson(cashFlow.otherInvestingCashFlowItemsTotal)
    val financingCashFlowItems = timestampedTimeSeriesToBson(cashFlow.financingCashFlowItems)
    val totalCashDividendsPaid = timestampedTimeSeriesToBson(cashFlow.totalCashDividendsPaid)
    val issuanceRetirementOfStockNet = timestampedTimeSeriesToBson(cashFlow.issuanceRetirementOfStockNet)
    val capitalExpenditures = timestampedTimeSeriesToBson(cashFlow.capitalExpenditures)

    MongoDBObject(
      "netIncomeStartingLine" -> netIncomeStartingLine,
      "depreciationDepletion" -> depreciationDepletion,
      "amortization" -> amortization,
      "cashFromOperatingActivities" -> cashFromOperatingActivities,
      "issuanceRetirementOfDebtNet" -> issuanceRetirementOfDebtNet,
      "cashFromFinancingActivities" -> cashFromFinancingActivities,
      "netChangeInCash" -> netChangeInCash,
      "cashInterestPaidSupplemental" -> cashInterestPaidSupplemental,
      "cashTaxesPaidSupplemental" -> cashTaxesPaidSupplemental,
      "deferredTaxes" -> deferredTaxes,
      "changesInWorkingCapital" -> changesInWorkingCapital,
      "cashFromInvestingActivities" -> cashFromInvestingActivities,
      "foreignExchangeEffects" -> foreignExchangeEffects,
      "nonCashItems" -> nonCashItems,
      "otherInvestingCashFlowItemsTotal" -> otherInvestingCashFlowItemsTotal,
      "financingCashFlowItems" -> financingCashFlowItems,
      "totalCashDividendsPaid" -> totalCashDividendsPaid,
      "issuanceRetirementOfStockNet" -> issuanceRetirementOfStockNet,
      "capitalExpenditures" -> capitalExpenditures
    )
  }

  def convertCashFlowFromBson(bson: MongoDBObject): CashFlow = {
    val keys = List(
      "netIncomeStartingLine",
      "depreciationDepletion",
      "amortization",
      "cashFromOperatingActivities",
      "issuanceRetirementOfDebtNet",
      "cashFromFinancingActivities",
      "netChangeInCash",
      "cashInterestPaidSupplemental",
      "cashTaxesPaidSupplemental",
      "deferredTaxes",
      "changesInWorkingCapital",
      "cashFromInvestingActivities",
      "foreignExchangeEffects",
      "nonCashItems",
      "otherInvestingCashFlowItemsTotal",
      "financingCashFlowItems",
      "totalCashDividendsPaid",
      "issuanceRetirementOfStockNet",
      "capitalExpenditures"
    )

    val cashFlowData = keys map { key =>
      timestampedTimeSeriesFromBson(bson.as[DBObject](key))
    }

    CashFlow(cashFlowData)
  }

  def timestampedTimeSeriesToBson(timestampedTimeSeries: TimestampedTimeSeries): DBObject = {
    MongoDBObject(
      "timestamp" -> timestampedTimeSeries._1,
      "values" -> timestampedTimeSeries._2.map( v => (v._1, v._2.toString()) )
    )
  }

  def timestampedTimeSeriesFromBson(bson: MongoDBObject): TimestampedTimeSeries = {
    val timestamp = bson.as[Some[DateTime]]("timestamp")
    val valueList = bson.as[List[BasicDBList]]("values")
    val convertedList = valueList map { l =>
      (l.get(0).asInstanceOf[DateTime], BigDecimal(l.get(1).asInstanceOf[String]))
    }
    (timestamp.getOrElse(new DateTime(0)), convertedList)
  }

  /** Parse a bson list
   *
   * @param key Key of the bson list
   * @param bson Bson representation
   * @return List with date and value
   */
  private def timeSeriesFromBson(key: String, bson: MongoDBObject): TimeSeries = {
    val valueList = bson.as[List[BasicDBList]](key)
    valueList map { list =>
      (list.get(0).asInstanceOf[DateTime], BigDecimal(list.get(1).asInstanceOf[String]))
    }
  }

}
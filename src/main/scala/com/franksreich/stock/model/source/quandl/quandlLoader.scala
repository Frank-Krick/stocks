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
package com.franksreich.stock.model.source.quandl

import com.franksreich.stock.config
import com.franksreich.stock.model.StockPrice
import com.franksreich.stock.model.symbol.StockSymbol
import com.franksreich.stock.model.types.TimeSeries

import com.github.nscala_time.time.Imports.DateTimeFormat
import com.github.nscala_time.time.Imports.DateTime

import com.stackmob.newman.ApacheHttpClient
import com.stackmob.newman.dsl.GET
import com.stackmob.newman.response.HttpResponse

import net.liftweb.json._
import org.slf4j.LoggerFactory

import scala.math.BigDecimal
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import java.net.URL

/** Load data using the quandl rest api */
class QuandlLoader {
  val logger = LoggerFactory.getLogger(this.getClass)

  /** Type of the callback functions */
  type Callback = (List[(DateTime, BigDecimal)]) => Unit

  /** Http client for the requests */
  implicit val httpClient = new ApacheHttpClient

  /** Default formats for JSON value extraction */
  implicit val formats = DefaultFormats

  /** Quandl API key */
  val apiKey = config.quandlApiKey

  /** Quandl base url */
  val baseUrl = "http://www.quandl.com/"

  /** Quandl fundamentals data source */
  val fundamentalsDataSource = "RAYMOND/"

  /** Request json as data format */
  val dataFormat = ".json"

  /** Parses the dates in the Json response */
  val dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd")

  /** Load cash & equivalents from Quandl.
    *
    * @param stockSymbol Identifies the company
    * @return Future for list with tuples containing date and value
    */
  def interestIncomeExpenseNetNonOperatingcashAndEquivalents(
                                                              stockSymbol: String): Future[List[(DateTime, BigDecimal)]] = {

    secFundamentals(stockSymbol, "_CASH_EQUIVALENTS_Q")
  }

  /** Get total debt from quandl and return a future for it
    *
    * @param stockSymbol Identifies the company
    * @return Future for list with tuples containing date and value
    */
  def totalLongTermDebt(stockSymbol: String): Future[List[(DateTime, BigDecimal)]] = {
    secFundamentals(stockSymbol, "_TOTAL_LONG_TERM_DEBT_Q")
  }

  /** Quandl access for income statement fundamentals */
  object incomeStatement {
    /** Get revenue from quandl and return a future for it
      *
      * @param stockSymbol Identifies the company
      * @return Future for list with tuples containing date and value
      */
    def revenue(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_REVENUE_Q")
    }

    /** Get total revenue from quandl
      *
      * @param stockSymbol Identifies the company
      * @return Total revenue
      */
    def totalRevenue(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_TOTAL_REVENUE_Q")
    }

    /** Get cost of revenue from quandl
      *
      * @param stockSymbol Identifies the company
      * @return Cost of revenue total
      */
    def costOfRevenueTotal(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_COST_OF_REVENUE_TOTAL_Q")
    }

    def grossProfit(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_GROSS_PROFIT_Q")
    }

    def sellingGeneralAdminExpensesTotal(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_SELLING_GENERAL_ADMIN_EXPENSES_TOTAL_Q")
    }

    def gainLossOnSaleOfAssets(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_GAIN_LOSS_ON_SALE_OF_ASSETS_Q")
    }

    def otherNet(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_OTHER_NET_Q")
    }

    def incomeBeforeTax(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_INCOME_BEFORE_TAX_Q")
    }

    def incomeAfterTax(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_INCOME_AFTER_TAX_Q")
    }

    def netIncomeBeforeExtraItems(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_NET_INCOME_BEFORE_EXTRA_ITEMS_Q")
    }

    def netIncome(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_NET_INCOME_Q")
    }

    def incomeAvailableToCommonExcludingExtraItems(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_INCOME_AVAILABLE_TO_COMMON_EXCL_EXTRA_ITEMS_Q")
    }

    def incomeAvailableToCommonIncludingExtraItems(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_INCOME_AVAILABLE_TO_COMMON_INCL_EXTRA_ITEMS_Q")
    }

    def dilutedWeightedAverageShares(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_DILUTED_WEIGHTED_AVERAGE_SHARES_Q")
    }

    def dilutedEpsExcludingExtraordinaryItems(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_DILUTED_EPS_EXCLUDING_EXTRAORDINARY_ITEMS_Q")
    }

    def researchDevelopment(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_RESEARCH_DEVELOPMENT_Q")
    }

    def unusualExpenseIncome(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_UNUSUAL_EXPENSE_INCOME_Q")
    }

    def operatingIncome(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_OPERATING_INCOME_Q")
    }

    def minorityInterest(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_MINORITY_INTEREST_Q")
    }

    def dividendPerShareCommonStockPrimaryIssue(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_DIVIDENDS_PER_SHARE_COMMON_STOCK_PRIMARY_ISSUE_Q")
    }

    def depreciationAmortization(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_DEPRECIATION_AMORTIZATION_Q")
    }

    def equityInAffiliates(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_EQUITY_IN_AFFILIATES_Q")
    }

    def totalOperatingExpenses(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_TOTAL_OPERATING_EXPENSE_Q")
    }

    def dilutedNormalizedEps(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_DILUTED_NORMALIZED_EPS_Q")
    }

    def otherOperatingExpensesTotal(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_OTHER_OPERATING_EXPENSES_TOTAL_Q")
    }

    def dilutionAdjustment(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_DILUTION_ADJUSTMENT_Q")
    }

    def otherRevenueTotal(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_OTHER_REVENUE_TOTAL_Q")
    }

    def interestIncomeExpenseNetNonOperating(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_INTEREST_INCOME_EXPENSE_NET_NON_OPERATING_Q")
    }

  }

  object balanceSheet {

    def accountsReceivable(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_ACCOUNTS_RECEIVABLE_TRADE_NET_Q")
    }

    def totalInventory(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_TOTAL_INVENTORY_Q")
    }

    def prepaidExpenses(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_PREPAID_EXPENSES_Q")
    }

    def otherCurrentAssetsTotal(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_OTHER_CURRENT_ASSETS_TOTAL_Q")
    }

    def totalCurrentAssets(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_TOTAL_CURRENT_ASSETS_Q")
    }

    def goodwillNet(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_GOODWILL_NET_Q")
    }

    def totalAssets(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_TOTAL_ASSETS_Q")
    }

    def accountsPayable(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_ACCOUNTS_PAYABLE_Q")
    }

    def currentPortOfLongTermDebtCapitalLeases(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_CURRENT_PORT_OF_LT_DEBT_CAPITAL_LEASES_Q")
    }

    def totalCurrentLiabilities(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_TOTAL_CURRENT_LIABILITIES_Q")
    }

    def deferredIncomeTax(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_DEFERRED_INCOME_TAX_Q")
    }

    def totalLiabilities(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_TOTAL_LIABILITIES_Q")
    }

    def commonStockTotal(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_COMMON_STOCK_TOTAL_Q")
    }

    def additionalPaidInCapital(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_ADDITIONAL_PAID_IN_CAPITAL_Q")
    }

    def retainedEarningsAccumulatedDeficit(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_RETAINED_EARNINGS_ACCUMULATED_DEFICIT_Q")
    }

    def totalEquity(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_TOTAL_EQUITY_Q")
    }

    def totalLiabilitiesShareholdersEquity(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_TOTAL_LIABILITIES_SHAREHOLDERS_EQUITY_Q")
    }

    def totalCommonSharesOutstanding(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_TOTAL_COMMON_SHARES_OUTSTANDING_Q")
    }

    def cashEquivalents(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_CASH_EQUIVALENTS_Q")
    }

    def cashAndShortTermInvestments(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_CASH_AND_SHORT_TERM_INVESTMENTS_Q")
    }

    def intangiblesNet(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_INTANGIBLES_NET_Q")
    }

    def otherLongTermAssetsTotal(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_OTHER_LONG_TERM_ASSETS_TOTAL_Q")
    }

    def longTermDebt(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_LONG_TERM_DEBT_Q")
    }

    def totalLongTermDebt(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_TOTAL_LONG_TERM_DEBT_Q")
    }

    def totalDebt(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_TOTAL_DEBT_Q")
    }

    def minorityInterest(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_MINORITY_INTEREST_Q")
    }

    def otherEquityTotal(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_OTHER_EQUITY_TOTAL_Q")
    }

    def propertyPlantEquipmentTotalGross(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_PROPERTY_PLANT_EQUIPMENT_TOTAL_GROSS_Q")
    }

    def otherCurrentLiabilitiesTotal(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_OTHER_CURRENT_LIABILITIES_TOTAL_Q")
    }

    def otherLiabilitiesTotal(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_OTHER_LIABILITIES_TOTAL_Q")
    }

    def longTermInvestments(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_LONG_TERM_INVESTMENTS_Q")
    }

    def accruedExpenses(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_ACCRUED_EXPENSES_Q")
    }

    def notesPayableShortTermDebt(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_NOTES_PAYABLE_SHORT_TERM_DEBT_Q")
    }

    def totalReceivablesNet(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_TOTAL_RECEIVABLES_NET_Q")
    }

    def preferredStockNonRedeemableDebt(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_PREFERRED_STOCK_NON_REDEEMABLE_NET_Q")
    }

    def shortTermInvestments(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_SHORT_TERM_INVESTMENTS_Q")
    }

    def capitalLeaseObligations(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_CAPITAL_LEASE_OBLIGATIONS_Q")
    }

    def accumulatedDepreciationTotal(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_ACCUMULATED_DEPRECIATION_TOTAL_Q")
    }

    def redeemablePreferredStockTotal(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_REDEEMABLE_PREFERRED_STOCK_TOTAL_Q")
    }

    def treasuryStockCommon(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_TREASURY_STOCK_COMMON_Q")
    }

  }

  object cashFlow {

    def netIncomeStartingLine(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_NET_INCOME_STARTING_LINE_Q")
    }

    def depreciationDepletion(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_DEPRECIATION_DEPLETION_Q")
    }

    def amortization(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_AMORTIZATION_Q")
    }

    def cashFromOperatingActivities(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_CASH_FROM_OPERATING_ACTIVITIES_Q")
    }

    def issuanceRetirementOfDebtNet(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_ISSUANCE_RETIREMENT_OF_DEBT_NET_Q")
    }

    def cashFromFinancingActivities(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_CASH_FROM_FINANCING_ACTIVITIES_Q")
    }

    def netChangeInCash(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_NET_CHANGE_IN_CASH_Q")
    }

    def cashInterestPaidSupplemental(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_CASH_INTEREST_PAID_SUPPLEMENTAL_Q")
    }

    def cashTaxesPaidSupplemental(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_CASH_TAXES_PAID_SUPPLEMENTAL_Q")
    }

    def deferredTaxes(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_DEFERRED_TAXES_Q")
    }

    def changesInWorkingCapital(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_CHANGES_IN_WORKING_CAPITAL_Q")
    }

    def cashFromInvestingActivities(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_CASH_FROM_INVESTING_ACTIVITIES_Q")
    }

    def foreignExchangeEffects(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_FOREIGN_EXCHANGE_EFFECTS_Q")
    }

    def nonCashItems(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_NON_CASH_ITEMS_Q")
    }

    def otherInvestingCashFlowItemsTotal(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_OTHER_INVESTING_CASH_FLOW_ITEMS_TOTAL_Q")
    }

    def financingCashFlowItems(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_FINANCING_CASH_FLOW_ITEMS_Q")
    }

    def totalCashDividendsPaid(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_TOTAL_CASH_DIVIDENDS_PAID_Q")
    }

    def issuanceRetirementOfStockNet(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_ISSUANCE_RETIREMENT_OF_STOCK_NET_Q")
    }

    def capitalExpenditures(stockSymbol: String): Future[TimeSeries] = {
      secFundamentals(stockSymbol, "_CAPITAL_EXPENDITURES_Q")
    }

  }

  /** Creates a future for the sec query
    *
    * @param stockSymbol Identifies the company
    * @param dataTable Data table to query
    * @return Future for list with tuples containing date and value
    */
  private def secFundamentals(stockSymbol: String, dataTable: String): Future[List[(DateTime, BigDecimal)]] = {
    logger.debug("Loading SEC fundamental {} for stock symbol {}.", List(dataTable, stockSymbol))
    val url = fundamentalsUrl(fundamentalsDataSource, stockSymbol, dataTable)
    val future = GET(url).apply
    future map { response =>
      parseJsonListResponse(response)
    }
  }

  /** Get stock prices from quandl
    *
    * @param stockSymbol Identifies the company
    * @return Time series of stock prices
    */
  def stockPrices(stockSymbol: String): Future[StockPrice] = {
    logger.debug("Loading stock prices for stock symbol {}", stockSymbol)
    val url = stockPriceUrl(stockSymbol)
    val future = GET(url).apply
    future map { response =>
      parsePriceJsonListResponse(response)
    }
  }

  def stockSymbols: Future[List[StockSymbol]] = {
    val url = new URL("https://s3.amazonaws.com/quandl-static-content/Ticker+CSV%27s/WIKI_tickers.csv")
    val stockSymbolsResponse = GET(url).apply
    stockSymbolsResponse map { response =>
      val body = response.bodyString
      val lines = body.lines.drop(1)
      lines.toList map { line =>
        val csv = line.split(',')
        val stockSymbol = csv(0).split('/')(1)
        val name = csv(1).replace('"', ' ').trim
        StockSymbol(stockSymbol, name)
      }
    }
  }

  /** Retrieve the current stock price from quandl
    *
    * @param stockSymbol Identifies the company
    * @return Future with tuple containing date and latest stock price
    */
  def currentStockPrice(stockSymbol: String): Future[(DateTime, BigDecimal)] = {
    val url = currentStockPriceUrl(stockSymbol)
    val future = GET(url).apply
    future map { response =>
      val list = parseJsonListResponse(response)
      if (list.length > 0) list(0)
      else (DateTime.now, BigDecimal(0))
    }
  }

  /** Create the URL to retrieve the current stock price
    *
    * @param stockSymbol Identifies the company
    * @return URL
    */
  private def currentStockPriceUrl(stockSymbol: String): URL = {
    val apiUrl = "http://www.quandl.com/api/v1/datasets/WIKI/"
    val urlString = apiUrl + stockSymbol + ".json?rows=1&auth_token" + apiKey
    new URL(urlString)
  }

  private def stockPriceUrl(stockSymbol: String): URL = {
    val apiUrl = "http://www.quandl.com/api/v1/datasets/WIKI/"
    val urlString = apiUrl + stockSymbol + ".json?auth_token" + apiKey
    new URL(urlString)
  }

  /** Create the URL to retrieve fundamentals
    *
    * @return URL
    */
  private def fundamentalsUrl(dataSource: String, stockSymbol: String, dataTable: String): URL = {
    val urlString = baseUrl + fundamentalsDataSource + stockSymbol + dataTable + dataFormat + "?auth_token=" + apiKey
    new URL(urlString)
  }

  /** Parse the JSON response for price date
    *
    * @param response Response of the http call
    * @return Stock price data
    */
  private def parsePriceJsonListResponse(response: HttpResponse): StockPrice = {
    val jsonAst = parse(response.bodyString)
    val dataList = (jsonAst \\ "data").children

    val open = convertJsonArrayToTimeSeries(dataList, 0, 1)
    val high = convertJsonArrayToTimeSeries(dataList, 0, 2)
    val low = convertJsonArrayToTimeSeries(dataList, 0, 3)
    val close = convertJsonArrayToTimeSeries(dataList, 0, 4)
    val volume = convertJsonArrayToTimeSeries(dataList, 0, 5)
    val splitRatio = convertJsonArrayToTimeSeries(dataList, 0, 7)
    val adjustedOpen = convertJsonArrayToTimeSeries(dataList, 0, 8)
    val adjustedHigh = convertJsonArrayToTimeSeries(dataList, 0, 9)
    val adjustedLow = convertJsonArrayToTimeSeries(dataList, 0, 10)
    val adjustedClose = convertJsonArrayToTimeSeries(dataList, 0, 11)
    val adjustedVolume = convertJsonArrayToTimeSeries(dataList, 0, 12)

    StockPrice(DateTime.now, open, high, low, close, volume, splitRatio, adjustedOpen, adjustedHigh, adjustedLow,
      adjustedClose, adjustedVolume)
  }

  /** Parse the JSON response for lists of Json date and value pairs
    *
    * @param response Response of the http call
    * @return List of tuples with date and values
    */
  private def parseJsonListResponse(response: HttpResponse): List[(DateTime, BigDecimal)] = {
    val jsonAst = parse(response.bodyString)
    val dataList = (jsonAst \\ "data").children

    convertJsonArrayToTimeSeries(dataList, 0, 1)
  }

  private def convertJsonArrayToTimeSeries(
                                            dataList: List[JsonAST.JValue], dateIndex: Int, valueIndex: Int): TimeSeries = {

    dataList map { data =>
      val content = data.children
      val dateTime = dateTimeFormat.parseDateTime(content(dateIndex).extract[String])
      val value = BigDecimal(content(valueIndex).extract[String])
      Tuple2(dateTime, value)
    }
  }
}
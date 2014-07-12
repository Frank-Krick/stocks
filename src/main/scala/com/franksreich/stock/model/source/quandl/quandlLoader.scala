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
import com.franksreich.stock.model.types.TimeSeries

import com.github.nscala_time.time.Imports.DateTimeFormat
import com.github.nscala_time.time.Imports.DateTime

import com.stackmob.newman.ApacheHttpClient
import com.stackmob.newman.dsl.GET
import com.stackmob.newman.response.HttpResponse

import net.liftweb.json._

import scala.math.BigDecimal
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import java.net.URL

/** Load data using the quandl rest api */
object quandlLoader {
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
  def cashAndEquivalents(stockSymbol: String): Future[List[(DateTime, BigDecimal)]] = {
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

  /** Creates a future for the sec query
   *
   * @param stockSymbol Identifies the company
   * @param dataTable Data table to query
   * @return Future for list with tuples containing date and value
   */
  private def secFundamentals(stockSymbol: String, dataTable: String): Future[List[(DateTime, BigDecimal)]] = {
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
  def getStockPrices(stockSymbol: String): Future[StockPrice] = {
    val url = stockPriceUrl(stockSymbol)
    val future = GET(url).apply
    future map { response =>
      parsePriceJsonListResponse(response)
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
      val value = BigDecimal(content(value).extract[String])
      Tuple2(dateTime, value)
    }
  }
}
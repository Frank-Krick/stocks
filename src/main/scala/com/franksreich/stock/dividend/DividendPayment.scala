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
package com.franksreich.stock.dividend

import com.github.nscala_time.time.Imports.DateTime

import scala.math.BigDecimal

/**
 * Represents a dividend payment
 */
class DividendPayment(val dateTime: DateTime, val payment: BigDecimal) {
  override def toString = "Date: " + dateTime.toString() + "\nPayment: " + payment.toString
}
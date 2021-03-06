package com.example.sqldelight.hockey.data

import com.squareup.sqldelight.ColumnAdapter
import kotlin.math.floor
import platform.Foundation.NSCalendar
import platform.Foundation.NSDate
import platform.Foundation.NSDateComponents

actual class Date internal constructor(internal val nsDate: NSDate) {
  actual constructor(year: Int, month: Int, day: Int) : this(partsToDate(year, month + 1, day))
}

internal fun partsToDate(year: Int, month: Int, day: Int): NSDate {
  val cal = NSCalendar.currentCalendar
  val comps = NSDateComponents()
  comps.setDay(day.toLong())
  comps.setMonth(month.toLong())
  comps.setYear(year.toLong())
  return cal.dateFromComponents(comps)!!
}

actual class DateAdapter actual constructor() : ColumnAdapter<Date, Long> {
  override fun decode(databaseValue: Long): Date =
      Date(NSDate(databaseValue.toDouble() / 1000))

  override fun encode(value: Date): Long =
      floor(value.nsDate.timeIntervalSinceReferenceDate * 1000L).toLong()
}

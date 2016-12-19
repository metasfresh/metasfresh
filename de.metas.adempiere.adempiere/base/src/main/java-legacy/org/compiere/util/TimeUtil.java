/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.adempiere.util.Check;
import org.adempiere.util.time.SystemTime;

/**
 * Time Utilities
 *
 * @author Jorg Janke
 * @version $Id: TimeUtil.java,v 1.3 2006/07/30 00:54:35 jjanke Exp $
 */
public class TimeUtil
{
	/**
	 * Get earliest time of a day (truncate)
	 * 
	 * @param time day and time
	 * @return day with 00:00
	 */
	static public Timestamp getDay(long time)
	{
		if (time == 0)
			time = System.currentTimeMillis();

		// note-ts: not using a locale because this method may be used during early startup
		// (and I don't see what for we need a locale)
		// GregorianCalendar cal = new GregorianCalendar(Language.getLoginLanguage().getLocale());
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(time);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new Timestamp(cal.getTimeInMillis());
	}	// getDay

	/**
	 * Get earliest time of a day (truncate)
	 * 
	 * @param dayTime day and time
	 * @return day with 00:00
	 */
	static public Timestamp getDay(java.util.Date dayTime)
	{
		if (dayTime == null)
			return getDay(System.currentTimeMillis());
		return getDay(dayTime.getTime());
	}	// getDay

	/**
	 * Get earliest time of a day (truncate)
	 *
	 * @param day day 1..31
	 * @param month month 1..12
	 * @param year year (if two diguts: < 50 is 2000; > 50 is 1900)
	 * @return timestamp ** not too reliable
	 */
	static public Timestamp getDay(int year, int month, int day)
	{
		int hour = 0;
		int minute = 0;
		int second = 0;
		return getDay(year, month, day, hour, minute, second);
	}

	static public Timestamp getDay(int year, int month, int day, int hour, int minute, int second)
	{
		if (year < 50)
			year += 2000;
		else if (year < 100)
			year += 1900;
		if (month < 1 || month > 12)
			throw new IllegalArgumentException("Invalid Month: " + month);
		if (day < 1 || day > 31)
			throw new IllegalArgumentException("Invalid Day: " + month);
		GregorianCalendar cal = new GregorianCalendar(year, month - 1, day, hour, minute, second);
		return new Timestamp(cal.getTimeInMillis());
	}	// getDay

	/**
	 * Get today (truncate)
	 * 
	 * @return day with 00:00
	 */
	static public Calendar getToday()
	{
		GregorianCalendar cal = new GregorianCalendar();
		// cal.setTimeInMillis(System.currentTimeMillis());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}	// getToday

	/**
	 * Get earliest time of next day
	 * 
	 * @param day day
	 * @return next day with 00:00
	 */
	static public Timestamp getNextDay(Timestamp day)
	{
		if (day == null)
			day = new Timestamp(System.currentTimeMillis());
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(day.getTime());
		cal.add(Calendar.DAY_OF_YEAR, +1);	// next
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new Timestamp(cal.getTimeInMillis());
	}	// getNextDay

	/**
	 * Get earliest time of prev day
	 * 
	 * @param day day
	 * @return next day with 00:00
	 */
	static public Timestamp getPrevDay(Timestamp day)
	{
		if (day == null)
			day = new Timestamp(System.currentTimeMillis());
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(day.getTime());
		cal.add(Calendar.DAY_OF_YEAR, -1);	// prev
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new Timestamp(cal.getTimeInMillis());
	}	// getPrevDay

	/**
	 * Get last date in month
	 * 
	 * @param day day
	 * @return last day with 00:00
	 */
	static public Timestamp getMonthLastDay(Timestamp day)
	{
		if (day == null)
			day = new Timestamp(System.currentTimeMillis());
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(day.getTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		//
		cal.add(Calendar.MONTH, 1);			// next
		cal.set(Calendar.DAY_OF_MONTH, 1);	// first
		cal.add(Calendar.DAY_OF_YEAR, -1);	// previous
		return new Timestamp(cal.getTimeInMillis());
	}	// getNextDay

	/**
	 * Get 15'th day in month
	 * 
	 * @param day may be <code>null</code>, in which case the current time is used.
	 * @return 15'th with 00:00
	 */
	static public Timestamp getMonthMiddleDay(final Timestamp day)
	{
		final Timestamp dateToUse = day == null ? SystemTime.asDayTimestamp() : day;

		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(dateToUse.getTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		cal.set(Calendar.DAY_OF_MONTH, 16);	// 15'th
		cal.add(Calendar.DAY_OF_YEAR, -1);	// previous

		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * Compose <code>day</code> (year, month, day) and <code>time</code> (hour, minute) and return the resulting date+time.
	 * 
	 * Milliseconds will be set to zero.
	 * 
	 * @param day day part
	 * @param time time part
	 * @return day + time.
	 */
	public static Timestamp getDayTime(final Date day, final Date time)
	{
		Check.assumeNotNull(day, "day not null");
		Check.assumeNotNull(time, "time not null");

		final GregorianCalendar dayCal = new GregorianCalendar();
		dayCal.setTime(day);
		final GregorianCalendar timeCal = new GregorianCalendar();
		timeCal.setTime(time);
		//
		final GregorianCalendar cal = new GregorianCalendar();
		cal.set(dayCal.get(Calendar.YEAR),
				dayCal.get(Calendar.MONTH),
				dayCal.get(Calendar.DAY_OF_MONTH),
				timeCal.get(Calendar.HOUR_OF_DAY),
				timeCal.get(Calendar.MINUTE),
				timeCal.get(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, 0);
		final Timestamp retValue = new Timestamp(cal.getTimeInMillis());
		return retValue;
	}	// getDayTime

	/**
	 * Is the _1 in the Range of _2
	 * 
	 * <pre>
	 * 		Time_1         +--x--+
	 * 		Time_2   +a+      +---b---+   +c+
	 * </pre>
	 * 
	 * The function returns true for b and false for a/b.
	 * 
	 * @param start_1 start (1)
	 * @param end_1 not included end (1)
	 * @param start_2 start (2)
	 * @param end_2 not included (2)
	 * @return true if in range
	 */
	static public boolean inRange(Timestamp start_1, Timestamp end_1, Timestamp start_2, Timestamp end_2)
	{
		// validity check
		if (end_1.before(start_1))
			throw new UnsupportedOperationException("TimeUtil.inRange End_1=" + end_1 + " before Start_1=" + start_1);
		if (end_2.before(start_2))
			throw new UnsupportedOperationException("TimeUtil.inRange End_2=" + end_2 + " before Start_2=" + start_2);
		// case a
		if (!end_2.after(start_1))		// end not including
		{
			// log.debug( "TimeUtil.InRange - No", start_1 + "->" + end_1 + " <??> " + start_2 + "->" + end_2);
			return false;
		}
		// case c
		if (!start_2.before(end_1))		// end not including
		{
			// log.debug( "TimeUtil.InRange - No", start_1 + "->" + end_1 + " <??> " + start_2 + "->" + end_2);
			return false;
		}
		// log.debug( "TimeUtil.InRange - Yes", start_1 + "->" + end_1 + " <??> " + start_2 + "->" + end_2);
		return true;
	}	// inRange

	/**
	 * Is start..end on one of the days ?
	 * 
	 * @param start start day
	 * @param end end day (not including)
	 * @param OnMonday true if OK
	 * @param OnTuesday true if OK
	 * @param OnWednesday true if OK
	 * @param OnThursday true if OK
	 * @param OnFriday true if OK
	 * @param OnSaturday true if OK
	 * @param OnSunday true if OK
	 * @return true if on one of the days
	 */
	static public boolean inRange(Timestamp start, Timestamp end,
			boolean OnMonday, boolean OnTuesday, boolean OnWednesday,
			boolean OnThursday, boolean OnFriday, boolean OnSaturday, boolean OnSunday)
	{
		// are there restrictions?
		if (OnSaturday && OnSunday && OnMonday && OnTuesday && OnWednesday && OnThursday && OnFriday)
			return false;

		GregorianCalendar calStart = new GregorianCalendar();
		calStart.setTimeInMillis(start.getTime());
		int dayStart = calStart.get(Calendar.DAY_OF_WEEK);
		//
		GregorianCalendar calEnd = new GregorianCalendar();
		calEnd.setTimeInMillis(end.getTime());
		calEnd.add(Calendar.DAY_OF_YEAR, -1);	// not including
		int dayEnd = calEnd.get(Calendar.DAY_OF_WEEK);

		// On same day
		if (calStart.get(Calendar.YEAR) == calEnd.get(Calendar.YEAR)
				&& calStart.get(Calendar.MONTH) == calEnd.get(Calendar.MONTH)
				&& calStart.get(Calendar.DAY_OF_MONTH) == calEnd.get(Calendar.DAY_OF_YEAR))
		{
			if ((!OnSaturday && dayStart == Calendar.SATURDAY)
					|| (!OnSunday && dayStart == Calendar.SUNDAY)
					|| (!OnMonday && dayStart == Calendar.MONDAY)
					|| (!OnTuesday && dayStart == Calendar.TUESDAY)
					|| (!OnWednesday && dayStart == Calendar.WEDNESDAY)
					|| (!OnThursday && dayStart == Calendar.THURSDAY)
					|| (!OnFriday && dayStart == Calendar.FRIDAY))
			{
				// log.debug( "TimeUtil.InRange - SameDay - Yes", start + "->" + end + " - "
				// + OnMonday+"-"+OnTuesday+"-"+OnWednesday+"-"+OnThursday+"-"+OnFriday+"="+OnSaturday+"-"+OnSunday);
				return true;
			}
			// log.debug( "TimeUtil.InRange - SameDay - No", start + "->" + end + " - "
			// + OnMonday+"-"+OnTuesday+"-"+OnWednesday+"-"+OnThursday+"-"+OnFriday+"="+OnSaturday+"-"+OnSunday);
			return false;
		}
		//
		// log.debug( "TimeUtil.inRange - WeekDay Start=" + dayStart + ", Incl.End=" + dayEnd);

		// Calendar.SUNDAY=1 ... SATURDAY=7
		BitSet days = new BitSet(8);
		// Set covered days in BitArray
		if (dayEnd <= dayStart)
			dayEnd += 7;
		for (int i = dayStart; i < dayEnd; i++)
		{
			int index = i;
			if (index > 7)
				index -= 7;
			days.set(index);
			// System.out.println("Set index=" + index + " i=" + i);
		}

		// for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++)
		// System.out.println("Result i=" + i + " - " + days.get(i));

		// Compare days to availability
		if ((!OnSaturday && days.get(Calendar.SATURDAY))
				|| (!OnSunday && days.get(Calendar.SUNDAY))
				|| (!OnMonday && days.get(Calendar.MONDAY))
				|| (!OnTuesday && days.get(Calendar.TUESDAY))
				|| (!OnWednesday && days.get(Calendar.WEDNESDAY))
				|| (!OnThursday && days.get(Calendar.THURSDAY))
				|| (!OnFriday && days.get(Calendar.FRIDAY)))
		{
			// log.debug( "MAssignment.InRange - Yes", start + "->" + end + " - "
			// + OnMonday+"-"+OnTuesday+"-"+OnWednesday+"-"+OnThursday+"-"+OnFriday+"="+OnSaturday+"-"+OnSunday);
			return true;
		}

		// log.debug( "MAssignment.InRange - No", start + "->" + end + " - "
		// + OnMonday+"-"+OnTuesday+"-"+OnWednesday+"-"+OnThursday+"-"+OnFriday+"="+OnSaturday+"-"+OnSunday);
		return false;
	}	// isRange

	/**
	 * Is it the same day
	 * 
	 * @param one day
	 * @param two compared day
	 * @return true if the same day
	 */
	static public boolean isSameDay(final Date one, final Date two)
	{
		GregorianCalendar calOne = new GregorianCalendar();
		if (one != null)
			calOne.setTimeInMillis(one.getTime());
		GregorianCalendar calTwo = new GregorianCalendar();
		if (two != null)
			calTwo.setTimeInMillis(two.getTime());
		if (calOne.get(Calendar.YEAR) == calTwo.get(Calendar.YEAR)
				&& calOne.get(Calendar.MONTH) == calTwo.get(Calendar.MONTH)
				&& calOne.get(Calendar.DAY_OF_MONTH) == calTwo.get(Calendar.DAY_OF_MONTH))
			return true;
		return false;
	}	// isSameDay

	/**
	 * Is it the same hour
	 * 
	 * @param one day/time
	 * @param two compared day/time
	 * @return true if the same day
	 */
	static public boolean isSameHour(Timestamp one, Timestamp two)
	{
		GregorianCalendar calOne = new GregorianCalendar();
		if (one != null)
			calOne.setTimeInMillis(one.getTime());
		GregorianCalendar calTwo = new GregorianCalendar();
		if (two != null)
			calTwo.setTimeInMillis(two.getTime());
		if (calOne.get(Calendar.YEAR) == calTwo.get(Calendar.YEAR)
				&& calOne.get(Calendar.MONTH) == calTwo.get(Calendar.MONTH)
				&& calOne.get(Calendar.DAY_OF_MONTH) == calTwo.get(Calendar.DAY_OF_MONTH)
				&& calOne.get(Calendar.HOUR_OF_DAY) == calTwo.get(Calendar.HOUR_OF_DAY))
			return true;
		return false;
	}	// isSameHour

	/**
	 * Is all day
	 * 
	 * @param start start date
	 * @param end end date
	 * @return true if all day (00:00-00:00 next day)
	 */
	static public boolean isAllDay(Timestamp start, Timestamp end)
	{
		GregorianCalendar calStart = new GregorianCalendar();
		calStart.setTimeInMillis(start.getTime());
		GregorianCalendar calEnd = new GregorianCalendar();
		calEnd.setTimeInMillis(end.getTime());
		if (calStart.get(Calendar.HOUR_OF_DAY) == calEnd.get(Calendar.HOUR_OF_DAY)
				&& calStart.get(Calendar.MINUTE) == calEnd.get(Calendar.MINUTE)
				&& calStart.get(Calendar.SECOND) == calEnd.get(Calendar.SECOND)
				&& calStart.get(Calendar.MILLISECOND) == calEnd.get(Calendar.MILLISECOND)
				&& calStart.get(Calendar.HOUR_OF_DAY) == 0
				&& calStart.get(Calendar.MINUTE) == 0
				&& calStart.get(Calendar.SECOND) == 0
				&& calStart.get(Calendar.MILLISECOND) == 0
				&& start.before(end))
			return true;
		//
		return false;
	}	// isAllDay

	/**
	 * Calculate the number of hours between start and end.
	 * 
	 * @param start start date
	 * @param end end date
	 * @return number of hours (0 = same)
	 */
	public static long getHoursBetween(Date date1, Date date2)
	{

		final int MILLI_TO_HOUR = 1000 * 60 * 60;
		return (date2.getTime() - date1.getTime()) / MILLI_TO_HOUR;
	}

	/**
	 * Calculate the number of days between start and end.
	 * 
	 * @param start start date
	 * @param end end date
	 * @return number of days (0 = same)
	 */
	static public int getDaysBetween(Date start, Date end)
	{
		boolean negative = false;
		if (end.before(start))
		{
			negative = true;
			Date temp = start;
			start = end;
			end = temp;
		}
		//
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(start);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		GregorianCalendar calEnd = new GregorianCalendar();
		calEnd.setTime(end);
		calEnd.set(Calendar.HOUR_OF_DAY, 0);
		calEnd.set(Calendar.MINUTE, 0);
		calEnd.set(Calendar.SECOND, 0);
		calEnd.set(Calendar.MILLISECOND, 0);

		// System.out.println("Start=" + start + ", End=" + end + ", dayStart=" + cal.get(Calendar.DAY_OF_YEAR) + ", dayEnd=" + calEnd.get(Calendar.DAY_OF_YEAR));

		// in same year
		if (cal.get(Calendar.YEAR) == calEnd.get(Calendar.YEAR))
		{
			if (negative)
				return (calEnd.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR)) * -1;
			return calEnd.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR);
		}

		// not very efficient, but correct
		int counter = 0;
		while (calEnd.after(cal))
		{
			cal.add(Calendar.DAY_OF_YEAR, 1);
			counter++;
		}
		if (negative)
			return counter * -1;
		return counter;
	}	// getDaysBetween

	/**
	 * Return Day + offset (truncates)
	 * 
	 * @param day Day
	 * @param offset day offset
	 * @return Day + offset at 00:00
	 */
	static public Timestamp addYears(Timestamp day, int offset)
	{
		if (offset == 0)
		{
			return day;
		}
		if (day == null)
		{
			day = SystemTime.asTimestamp();
		}
		//
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		cal.add(Calendar.YEAR, offset);			// may have a problem with negative (before 1/1)
		return new Timestamp(cal.getTimeInMillis());
	}	// addMonths

	/**
	 * Return Day + offset (truncates)
	 * 
	 * @param day Day
	 * @param offset day offset
	 * @return Day + offset at 00:00
	 */
	static public Timestamp addMonths(Date day, final int offset)
	{
		if (offset == 0)
		{
			return asTimestamp(day);
		}
		if (day == null)
		{
			day = SystemTime.asTimestamp();
		}
		//
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		cal.add(Calendar.MONTH, offset);			// may have a problem with negative (before 1/1)
		return new Timestamp(cal.getTimeInMillis());
	}	// addMonths

	/**
	 * Return Day + offset (truncates)
	 * 
	 * @param day Day
	 * @param offset day offset
	 * @return Day + offset at 00:00
	 */
	static public Timestamp addWeeks(Timestamp day, int offset)
	{
		if (offset == 0)
		{
			return day;
		}
		if (day == null)
		{
			day = SystemTime.asTimestamp();
		}
		//
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		cal.add(Calendar.WEEK_OF_YEAR, offset);			// may have a problem with negative (before 1/1)
		return new Timestamp(cal.getTimeInMillis());
	}	// addDays

	/**
	 * Return Day + offset (truncates)
	 * 
	 * @param day Day
	 * @param offset day offset
	 * @return Day + offset at 00:00
	 */
	static public Timestamp addDays(Date day, final int offset)
	{
		if (offset == 0)
		{
			return asTimestamp(day);
		}
		if (day == null)
		{
			day = new Timestamp(System.currentTimeMillis());
		}
		//
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		if (offset == 0)
			return new Timestamp(cal.getTimeInMillis());
		cal.add(Calendar.DAY_OF_YEAR, offset);			// may have a problem with negative (before 1/1)
		return new Timestamp(cal.getTimeInMillis());
	}	// addDays

	/**
	 * Return DateTime + offset in minutes
	 * 
	 * @param dateTime Date and Time
	 * @param offset minute offset
	 * @return dateTime + offset in minutes
	 */
	static public Timestamp addMinutess(Timestamp dateTime, int offset)
	{
		if (dateTime == null)
			dateTime = new Timestamp(System.currentTimeMillis());
		if (offset == 0)
			return dateTime;
		//
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(dateTime);
		cal.add(Calendar.MINUTE, offset);			// may have a problem with negative
		return new Timestamp(cal.getTimeInMillis());
	}	// addMinutes

	/**
	 * Return DateTime + offset in hours
	 * 
	 * @param dateTime Date and Time
	 * @param offset minute offset
	 * @return dateTime + offset in hours
	 */
	static public Timestamp addHours(Date dateTime, final int offset)
	{
		if (dateTime == null)
			dateTime = new Timestamp(System.currentTimeMillis());
		if (offset == 0)
			return asTimestamp(dateTime);
		//
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(dateTime);
		cal.add(Calendar.HOUR, offset);			// may have a problem with negative
		return new Timestamp(cal.getTimeInMillis());
	}	// addHours

	/**************************************************************************
	 * Format Elapsed Time
	 * 
	 * @param start start time or null for now
	 * @param end end time or null for now
	 * @return formatted time string 1'23:59:59.999
	 */
	public static String formatElapsed(Timestamp start, Timestamp end)
	{
		long startTime = 0;
		if (start == null)
			startTime = System.currentTimeMillis();
		else
			startTime = start.getTime();
		//
		long endTime = 0;
		if (end == null)
			endTime = System.currentTimeMillis();
		else
			endTime = end.getTime();
		return formatElapsed(endTime - startTime);
	}	// formatElapsed

	/**
	 * Format Elapsed Time until now
	 * 
	 * @param start start time
	 * @return formatted time string 1'23:59:59.999
	 */
	public static String formatElapsed(Timestamp start)
	{
		if (start == null)
			return "NoStartTime";
		long startTime = start.getTime();
		long endTime = System.currentTimeMillis();
		return formatElapsed(endTime - startTime);
	}	// formatElapsed

	/**
	 * Format Elapsed Time
	 *
	 * @param elapsedMS time in ms
	 * @return formatted time string 1'23:59:59.999 - d'hh:mm:ss.xxx
	 */
	public static String formatElapsed(long elapsedMS)
	{
		if (elapsedMS == 0)
			return "0";
		StringBuffer sb = new StringBuffer();
		if (elapsedMS < 0)
		{
			elapsedMS = -elapsedMS;
			sb.append("-");
		}
		//
		long miliSeconds = elapsedMS % 1000;
		elapsedMS = elapsedMS / 1000;
		long seconds = elapsedMS % 60;
		elapsedMS = elapsedMS / 60;
		long minutes = elapsedMS % 60;
		elapsedMS = elapsedMS / 60;
		long hours = elapsedMS % 24;
		long days = elapsedMS / 24;
		//
		if (days != 0)
			sb.append(days).append("'");
		// hh
		if (hours != 0)
			sb.append(get2digits(hours)).append(":");
		else if (days != 0)
			sb.append("00:");
		// mm
		if (minutes != 0)
			sb.append(get2digits(minutes)).append(":");
		else if (hours != 0 || days != 0)
			sb.append("00:");
		// ss
		sb.append(get2digits(seconds))
				.append(".").append(miliSeconds);
		return sb.toString();
	}	// formatElapsed

	/**
	 * Get Minimum of 2 digits
	 *
	 * @param no number
	 * @return String
	 */
	private static String get2digits(long no)
	{
		String s = String.valueOf(no);
		if (s.length() > 1)
			return s;
		return "0" + s;
	}	// get2digits

	/**
	 * Is it valid today?
	 *
	 * @param validFrom valid from
	 * @param validTo valid to
	 * @return true if walid
	 */
	public static boolean isValid(Timestamp validFrom, Timestamp validTo)
	{
		return isValid(validFrom, validTo, new Timestamp(System.currentTimeMillis()));
	}	// isValid

	/**
	 * Is it valid on test date.
	 * 
	 * If <code>testDate</code> is null, true will be returned.
	 *
	 * @param validFrom valid from
	 * @param validTo valid to
	 * @param testDate Date
	 * @return true if valid
	 * @see #isBetween(Date, Date, Date)
	 */
	public static boolean isValid(final Timestamp validFrom, final Timestamp validTo, final Timestamp testDate)
	{
		if (testDate == null)
		{
			return true;
		}

		return isBetween(testDate, validFrom, validTo);
	}	// isValid

	/**
	 * Checks if given <code>date</code> is between <code>dateFrom</code> and <code>dateTo</code> inclusivelly.
	 * 
	 * If <code>dateFrom</code> or <code>dateTo</code> are <code>null</code> it will be considered as infinity.
	 * 
	 * @param date
	 * @param dateFrom
	 * @param dateTo
	 * @return true if date is between given dates (inclusively)
	 */
	public static boolean isBetween(final Date date, final Date dateFrom, final Date dateTo)
	{
		Check.assumeNotNull(date, "date not null");

		if (dateFrom != null && date.before(dateFrom))
		{
			return false;
		}

		if (dateTo != null && date.after(dateTo))
		{
			return false;
		}

		return true;
	}

	/**
	 * Max date
	 *
	 * @param ts1 p1
	 * @param ts2 p2
	 * @return max time
	 */
	public static <T extends java.util.Date> T max(final T ts1, final T ts2)
	{
		if (ts1 == null)
			return ts2;
		if (ts2 == null)
			return ts1;

		if (ts2.after(ts1))
			return ts2;
		return ts1;
	}	// max

	/**
	 * Gets Minimum date.
	 * 
	 * If one of the dates is null, then the not null one will be returned.
	 * 
	 * If both dates are null then null will be returned.
	 * 
	 * @param date1
	 * @param date2
	 * @return minimum date or null
	 */
	public static <T extends java.util.Date> T min(final T date1, final T date2)
	{
		if (date1 == date2)
		{
			return date1;
		}
		else if (date1 == null)
		{
			return date2;
		}
		else if (date2 == null)
		{
			return date1;
		}
		else if (date1.compareTo(date2) <= 0)
		{
			return date1;
		}
		else
		{
			return date2;
		}
	}

	/** Truncate Second - S */
	public static final String TRUNC_SECOND = "S";
	/** Truncate Minute - M */
	public static final String TRUNC_MINUTE = "M";
	/** Truncate Hour - H */
	public static final String TRUNC_HOUR = "H";
	/** Truncate Day - D */
	public static final String TRUNC_DAY = "D";
	/** Truncate Week - W (Monday is always considered the first day of the week) */
	public static final String TRUNC_WEEK = "W";
	/** Truncate Month - MM */
	public static final String TRUNC_MONTH = "MM";
	/** Truncate Quarter - Q */
	public static final String TRUNC_QUARTER = "Q";
	/** Truncate Year - Y */
	public static final String TRUNC_YEAR = "Y";

	/**
	 * Get truncated day/time
	 * 
	 * @param dayTime day
	 * @param trunc how to truncate TRUNC_*
	 * @return next day with 00:00
	 */
	// metas: changed dayTime type from Timestamp to Date
	public static Timestamp trunc(final Date dayTime, final String trunc)
	{
		return new Timestamp(truncToMillis(dayTime, trunc));
	}

	public static long truncToMillis(Date dayTime, final String trunc)
	{
		if (dayTime == null)
			dayTime = new Timestamp(System.currentTimeMillis());
		// note-ts: not using a locale because this method may be used during early startup
		// (and I don't see what for we need a locale)
		// GregorianCalendar cal = new GregorianCalendar(Env.getLanguage(Env.getCtx()).getLocale());
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(dayTime.getTime());
		cal.set(Calendar.MILLISECOND, 0);

		// S - Second
		if (TRUNC_SECOND.equals(trunc))
		{
			return cal.getTimeInMillis();
		}
		cal.set(Calendar.SECOND, 0);

		// M - Minute
		if (TRUNC_MINUTE.equals(trunc))
		{
			return cal.getTimeInMillis();
		}
		cal.set(Calendar.MINUTE, 0);

		// H - Hour
		if (TRUNC_HOUR.equals(trunc))
		{
			return cal.getTimeInMillis();
		}
		cal.set(Calendar.HOUR_OF_DAY, 0);
		// D
		if (trunc == null || trunc.equals(TRUNC_DAY))
			return cal.getTimeInMillis();
		// W
		if (trunc.equals(TRUNC_WEEK))
		{
			cal.setFirstDayOfWeek(Calendar.MONDAY);
			cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
			return cal.getTimeInMillis();
		}
		// MM
		cal.set(Calendar.DAY_OF_MONTH, 1);
		if (trunc.equals(TRUNC_MONTH))
			return cal.getTimeInMillis();
		// Q
		if (trunc.equals(TRUNC_QUARTER))
		{
			int mm = cal.get(Calendar.MONTH);
			if (mm < 4)
				mm = 1;
			else if (mm < 7)
				mm = 4;
			else if (mm < 10)
				mm = 7;
			else
				mm = 10;
			cal.set(Calendar.MONTH, mm);
			return cal.getTimeInMillis();
		}
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return cal.getTimeInMillis();
	}	// trunc

	public static final Timestamp truncToDay(final Date dayTime)
	{
		return dayTime == null ? null : trunc(dayTime, TRUNC_DAY);
	}

	/**
	 * Returns the day border by combining the date part from dateTime and time part form timeSlot. If timeSlot is null, then first milli of the day will be used (if end == false) or last milli of the
	 * day (if end == true).
	 * 
	 * @param dateTime
	 * @param timeSlot
	 * @param end
	 * @return
	 */
	public static Timestamp getDayBorder(Timestamp dateTime, Timestamp timeSlot, boolean end)
	{
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(dateTime.getTime());
		dateTime.setNanos(0);

		if (timeSlot != null)
		{
			timeSlot.setNanos(0);
			GregorianCalendar gcTS = new GregorianCalendar();
			gcTS.setTimeInMillis(timeSlot.getTime());

			gc.set(Calendar.HOUR_OF_DAY, gcTS.get(Calendar.HOUR_OF_DAY));
			gc.set(Calendar.MINUTE, gcTS.get(Calendar.MINUTE));
			gc.set(Calendar.SECOND, gcTS.get(Calendar.SECOND));
			gc.set(Calendar.MILLISECOND, gcTS.get(Calendar.MILLISECOND));
		}
		else if (end)
		{
			gc.set(Calendar.HOUR_OF_DAY, 23);
			gc.set(Calendar.MINUTE, 59);
			gc.set(Calendar.SECOND, 59);
			gc.set(Calendar.MILLISECOND, 999);
		}
		else
		{
			gc.set(Calendar.MILLISECOND, 0);
			gc.set(Calendar.SECOND, 0);
			gc.set(Calendar.MINUTE, 0);
			gc.set(Calendar.HOUR_OF_DAY, 0);
		}
		return new Timestamp(gc.getTimeInMillis());
	}

	/** @return date as timestamp or null if the date is null */
	public static Timestamp asTimestamp(Date date)
	{
		if (date instanceof Timestamp)
			return (Timestamp)date;
		return date == null ? null : new Timestamp(date.getTime());
	}

	/**
	 * Get last date in year
	 * 
	 * @param day day
	 * @return year last day with 00:00
	 */
	// metas
	static public Timestamp getYearLastDay(Date day)
	{
		if (day == null)
			day = new Timestamp(System.currentTimeMillis());
		GregorianCalendar cal = new GregorianCalendar(Env.getLanguage(Env.getCtx()).getLocale());
		cal.setTimeInMillis(day.getTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		//
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		return new Timestamp(cal.getTimeInMillis());
	}	// getYearLastDay

	/**
	 * Extract the year from a given date.
	 * 
	 * @param date
	 * @return the year as int
	 */
	static public int getYearFromTimestamp(final Date date)
	{
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		final int year = calendar.get(Calendar.YEAR);
		return year;
	}

	static public String formatDate(final Timestamp date, final String pattern)
	{
		final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		final String s = sdf.format(date);

		return s;
	}

	public static Calendar asCalendar(final Date date)
	{
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * Truncates given dates by using the <code>trunc</code> method and then compares them.
	 * 
	 * @param date1
	 * @param date2
	 * @param trunc see TRUNC_* constants
	 * @return true if the dates are equal after truncating them by <code>trunc</code> method.
	 */
	public static boolean equals(final Date date1, final Date date2, final String trunc)
	{
		if (date1 == date2)
		{
			return true;
		}
		if (date1 == null || date2 == null)
		{
			return false;
		}

		if (trunc == null)
		{
			return date1.equals(date2);
		}

		final Timestamp date1Trunc = trunc(date1, trunc);
		final Timestamp date2Trunc = trunc(date2, trunc);
		return date1Trunc.equals(date2Trunc);
	}

	/** @return copy of given timestamp or null if the given timestamp was null */
	public static final Timestamp copyOf(final Timestamp timestamp)
	{
		return timestamp == null ? null : new Timestamp(timestamp.getTime());
	}

	/**
	 * Get the week of year number for the given Date
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekNumber(final Date date)
	{
		final Calendar cal = asCalendar(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		cal.setFirstDayOfWeek(Calendar.MONDAY);

		// FIXME: This shall be taken from Locale, but Locale it is not reliable (doesn't always work the same way)
		cal.setMinimalDaysInFirstWeek(4);

		final int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
		return weekOfYear;
	}

	/**
	 * Get the day of the week for the given date.
	 * First day of the week is considered Monday.
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(final Date date)
	{
		final int dayOfWeek = asCalendar(date).get(Calendar.DAY_OF_WEEK);

		if (dayOfWeek == 1)
		{
			return 7;
		}
		return dayOfWeek - 1;
	}

}	// TimeUtil

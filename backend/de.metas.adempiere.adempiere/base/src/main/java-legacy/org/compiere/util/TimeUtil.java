package org.compiere.util;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Range;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.organization.IOrgDAO;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static de.metas.common.util.CoalesceUtil.coalesceNotNull;
import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Time Utilities
 *
 * @author Jorg Janke
 * @version $Id: TimeUtil.java,v 1.3 2006/07/30 00:54:35 jjanke Exp $
 */
public class TimeUtil
{
	private static final LocalDate DATE_1970_01_01 = LocalDate.of(1970, Month.JANUARY, 1);

	/**
	 * Get earliest time of a day (truncate)
	 *
	 * @param time day and time
	 * @return day with 00:00
	 * @deprecated the return value of this method is {@code instanceof Date}, but it's not equal to "real" {@link Date} instances of the same time.
	 * Hint: you can use {@link #asDate(Object)} to get a "real" date
	 */
	@Deprecated
	public static Timestamp getDay(final long time)
	{
		final long timeToUse = time > 0 ? time : SystemTime.millis();

		// note-ts: not using a locale because this method may be used during early startup
		// (and I don't see what for we need a locale)
		// GregorianCalendar cal = new GregorianCalendar(Language.getLoginLanguage().getLocale());
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(timeToUse);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new Timestamp(cal.getTimeInMillis());
	}    // getDay

	/**
	 * @return instant at midnight of the given time zone
	 */
	public static Instant getDay(
			@NonNull final Instant instant,
			@NonNull final ZoneId timeZone)
	{
		return instant
				.atZone(timeZone)
				.toLocalDate()
				.atStartOfDay(timeZone)
				.toInstant();
	}

	/**
	 * Get earliest time of a day (truncate)
	 *
	 * @param dayTime day and time
	 * @return day with 00:00
	 * @deprecated the return value of this method is {@code instanceof Date}, but it's not equal to "real" {@link Date} instances of the same time.
	 * Hint: you can use {@link #asDate(Object)} to get a "real" date
	 */
	@Deprecated
	static public Timestamp getDay(@Nullable final Date dayTime)
	{
		if (dayTime == null)
		{
			return getDay(System.currentTimeMillis());
		}
		return getDay(dayTime.getTime());
	}    // getDay

	/**
	 * Get earliest time of a day (truncate)
	 *
	 * @param year  year (if two digits: < 50 is 2000; > 50 is 1900)
	 * @param month month 1..12
	 * @param day   day 1..31
	 * @return timestamp ** not too reliable
	 * @deprecated the return value of this method is {@code instanceof Date}, but it's not equal to "real" {@link Date} instances of the same time.
	 * Hint: you can use {@link #asDate(Object)} to get a "real" date
	 */
	@Deprecated
	public static Timestamp getDay(final int year, final int month, final int day)
	{
		final int hour = 0;
		final int minute = 0;
		final int second = 0;
		return getDay(year, month, day, hour, minute, second);
	}

	/**
	 * @deprecated the return value of this method is {@code instanceof Date}, but it's not equal to "real" {@link Date} instances of the same time.
	 * Hint: you can use {@link #asDate(Object)} to get a "real" date
	 */
	@Deprecated
	public static Timestamp getDay(
			final int year,
			final int month,
			final int day,
			final int hour,
			final int minute,
			final int second)
	{
		final int yearToUse;
		if (year < 50)
		{
			yearToUse = year + 2000;
		}
		else if (year < 100)
		{
			yearToUse = year + 1900;
		}
		else
		{
			yearToUse = year;
		}

		Check.errorIf(month < 1 || month > 12, "Invalid Month: {}", month);
		Check.errorIf(day < 1 || day > 31, "Invalid Day: {}", day);

		final GregorianCalendar cal = new GregorianCalendar(yearToUse, month - 1, day, hour, minute, second);
		return new Timestamp(cal.getTimeInMillis());
	}    // getDay

	/**
	 * Get today (truncate)
	 *
	 * @return day with 00:00
	 */
	static public Calendar getToday()
	{
		final GregorianCalendar cal = new GregorianCalendar();

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}    // getToday

	/**
	 * Get earliest time of next day
	 *
	 * @param day day
	 * @return next day with 00:00
	 * @deprecated the return value of this method is {@code instanceof Date}, but it's not equal to "real" {@link Date} instances of the same time.
	 * Hint: you can use {@link #asDate(Object)} to get a "real" date
	 */
	@Deprecated
	static public Timestamp getNextDay(@Nullable final Timestamp day)
	{
		final Timestamp dayToUse = day != null ? day : SystemTime.asDayTimestamp();

		final GregorianCalendar cal = new GregorianCalendar();

		cal.setTimeInMillis(dayToUse.getTime());
		cal.add(Calendar.DAY_OF_YEAR, +1);    // next
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return new Timestamp(cal.getTimeInMillis());
	}    // getNextDay

	/**
	 * Get earliest time of prev day
	 *
	 * @param day day
	 * @return next day with 00:00
	 * @deprecated the return value of this method is {@code instanceof Date}, but it's not equal to "real" {@link Date} instances of the same time.
	 * Hint: you can use {@link #asDate(Object)} to get a "real" date
	 */
	@Deprecated
	static public Timestamp getPrevDay(@Nullable final Timestamp day)
	{
		final Timestamp dayToUse = day != null ? day : SystemTime.asDayTimestamp();

		final GregorianCalendar cal = new GregorianCalendar();

		cal.setTimeInMillis(dayToUse.getTime());
		cal.add(Calendar.DAY_OF_YEAR, -1);    // prev
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return new Timestamp(cal.getTimeInMillis());
	}    // getPrevDay

	/**
	 * Get last date in month
	 *
	 * @param day day
	 * @return last day with 00:00
	 * @deprecated the return value of this method is {@code instanceof Date}, but it's not equal to "real" {@link Date} instances of the same time.
	 * Hint: you can use {@link #asDate(Object)} to get a "real" date
	 */
	@Deprecated
	static public Timestamp getMonthLastDay(@Nullable final Timestamp day)
	{
		final Timestamp dayToUse = day != null ? day : SystemTime.asDayTimestamp();

		final GregorianCalendar cal = new GregorianCalendar();

		cal.setTimeInMillis(dayToUse.getTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		//
		cal.add(Calendar.MONTH, 1);            // next
		cal.set(Calendar.DAY_OF_MONTH, 1);    // first
		cal.add(Calendar.DAY_OF_YEAR, -1);    // previous

		return new Timestamp(cal.getTimeInMillis());
	}    // getNextDay

	/**
	 * Get 15'th day in month
	 *
	 * @param day may be <code>null</code>, in which case the current time is used.
	 * @return 15'th with 00:00
	 * @deprecated the return value of this method is {@code instanceof Date}, but it's not equal to "real" {@link Date} instances of the same time.
	 * Hint: you can use {@link #asDate(Object)} to get a "real" date
	 */
	@Deprecated
	static public Timestamp getMonthMiddleDay(@Nullable final Timestamp day)
	{
		final Timestamp dateToUse = day == null ? SystemTime.asDayTimestamp() : day;

		final GregorianCalendar cal = new GregorianCalendar();

		cal.setTimeInMillis(dateToUse.getTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		cal.set(Calendar.DAY_OF_MONTH, 16);    // 15'th
		cal.add(Calendar.DAY_OF_YEAR, -1);    // previous

		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * Compose <code>day</code> (year, month, day) and <code>time</code> (hour, minute) and return the resulting date+time.
	 * <p>
	 * Milliseconds will be set to zero.
	 *
	 * @param day  day part
	 * @param time time part
	 * @return day + time.
	 * @deprecated the return value of this method is {@code instanceof Date}, but it's not equal to "real" {@link Date} instances of the same time.
	 * Hint: you can use {@link #asDate(Object)} to get a "real" date
	 */
	@Deprecated
	public static Timestamp getDayTime(
			@NonNull final Date day,
			@NonNull final Date time)
	{
		final GregorianCalendar dayCal = new GregorianCalendar();
		dayCal.setTime(day);

		final GregorianCalendar timeCal = new GregorianCalendar();
		timeCal.setTime(time);

		final GregorianCalendar cal = new GregorianCalendar();
		cal.set(dayCal.get(Calendar.YEAR),
				dayCal.get(Calendar.MONTH),
				dayCal.get(Calendar.DAY_OF_MONTH),
				timeCal.get(Calendar.HOUR_OF_DAY),
				timeCal.get(Calendar.MINUTE),
				timeCal.get(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, 0);
		return new Timestamp(cal.getTimeInMillis());
	}    // getDayTime

	/**
	 * Is the _1 in the Range of _2
	 *
	 * <pre>
	 * 		Time_1         +--x--+
	 * 		Time_2   +a+      +---b---+   +c+
	 * </pre>
	 * <p>
	 * The function returns true for b and false for a/b.
	 *
	 * @param start_1 start (1)
	 * @param end_1   not included end (1)
	 * @param start_2 start (2)
	 * @param end_2   not included (2)
	 * @return true if in range
	 */
	static public boolean inRange(
			@NonNull final Timestamp start_1,
			@NonNull final Timestamp end_1,
			@NonNull final Timestamp start_2,
			@NonNull final Timestamp end_2)
	{
		// validity check
		if (end_1.before(start_1))
		{
			throw new UnsupportedOperationException("TimeUtil.inRange End_1=" + end_1 + " before Start_1=" + start_1);
		}
		if (end_2.before(start_2))
		{
			throw new UnsupportedOperationException("TimeUtil.inRange End_2=" + end_2 + " before Start_2=" + start_2);
		}
		// case a
		if (!end_2.after(start_1))        // end not including
		{
			// log.debug( "TimeUtil.InRange - No", start_1 + "->" + end_1 + " <??> " + start_2 + "->" + end_2);
			return false;
		}
		// case c
		if (!start_2.before(end_1))        // end not including
		{
			// log.debug( "TimeUtil.InRange - No", start_1 + "->" + end_1 + " <??> " + start_2 + "->" + end_2);
			return false;
		}
		// log.debug( "TimeUtil.InRange - Yes", start_1 + "->" + end_1 + " <??> " + start_2 + "->" + end_2);
		return true;
	}    // inRange

	/**
	 * Is start..end on one of the days ?
	 *
	 * @param start       start day
	 * @param end         end day (not including)
	 * @param OnMonday    true if OK
	 * @param OnTuesday   true if OK
	 * @param OnWednesday true if OK
	 * @param OnThursday  true if OK
	 * @param OnFriday    true if OK
	 * @param OnSaturday  true if OK
	 * @param OnSunday    true if OK
	 * @return true if on one of the days
	 */
	static public boolean inRange(
			@NonNull final Timestamp start,
			@NonNull final Timestamp end,
			final boolean OnMonday,
			final boolean OnTuesday,
			final boolean OnWednesday,
			final boolean OnThursday,
			final boolean OnFriday,
			final boolean OnSaturday,
			final boolean OnSunday)
	{
		// are there restrictions?
		if (OnSaturday && OnSunday && OnMonday && OnTuesday && OnWednesday && OnThursday && OnFriday)
		{
			return false;
		}

		final GregorianCalendar calStart = new GregorianCalendar();
		calStart.setTimeInMillis(start.getTime());
		final int dayStart = calStart.get(Calendar.DAY_OF_WEEK);
		//
		final GregorianCalendar calEnd = new GregorianCalendar();
		calEnd.setTimeInMillis(end.getTime());
		calEnd.add(Calendar.DAY_OF_YEAR, -1);    // not including
		int dayEnd = calEnd.get(Calendar.DAY_OF_WEEK);

		// On same day
		if (calStart.get(Calendar.YEAR) == calEnd.get(Calendar.YEAR)
				&& calStart.get(Calendar.MONTH) == calEnd.get(Calendar.MONTH)
				&& calStart.get(Calendar.DAY_OF_MONTH) == calEnd.get(Calendar.DAY_OF_YEAR))
		{
			if (!OnSaturday && dayStart == Calendar.SATURDAY
					|| !OnSunday && dayStart == Calendar.SUNDAY
					|| !OnMonday && dayStart == Calendar.MONDAY
					|| !OnTuesday && dayStart == Calendar.TUESDAY
					|| !OnWednesday && dayStart == Calendar.WEDNESDAY
					|| !OnThursday && dayStart == Calendar.THURSDAY
					|| !OnFriday && dayStart == Calendar.FRIDAY)
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
		final BitSet days = new BitSet(8);
		// Set covered days in BitArray
		if (dayEnd <= dayStart)
		{
			dayEnd += 7;
		}
		for (int i = dayStart; i < dayEnd; i++)
		{
			int index = i;
			if (index > 7)
			{
				index -= 7;
			}
			days.set(index);
			// System.out.println("Set index=" + index + " i=" + i);
		}

		// for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++)
		// System.out.println("Result i=" + i + " - " + days.get(i));

		// Compare days to availability
		if (!OnSaturday && days.get(Calendar.SATURDAY)
				|| !OnSunday && days.get(Calendar.SUNDAY)
				|| !OnMonday && days.get(Calendar.MONDAY)
				|| !OnTuesday && days.get(Calendar.TUESDAY)
				|| !OnWednesday && days.get(Calendar.WEDNESDAY)
				|| !OnThursday && days.get(Calendar.THURSDAY)
				|| !OnFriday && days.get(Calendar.FRIDAY))
		{
			// log.debug( "MAssignment.InRange - Yes", start + "->" + end + " - "
			// + OnMonday+"-"+OnTuesday+"-"+OnWednesday+"-"+OnThursday+"-"+OnFriday+"="+OnSaturday+"-"+OnSunday);
			return true;
		}

		// log.debug( "MAssignment.InRange - No", start + "->" + end + " - "
		// + OnMonday+"-"+OnTuesday+"-"+OnWednesday+"-"+OnThursday+"-"+OnFriday+"="+OnSaturday+"-"+OnSunday);
		return false;
	}    // isRange

	/**
	 * Is it the same day
	 *
	 * @param one day
	 * @param two compared day
	 * @return true if the same day
	 */
	static public boolean isSameDay(final Date one, final Date two)
	{
		final GregorianCalendar calOne = new GregorianCalendar();
		if (one != null)
		{
			calOne.setTimeInMillis(one.getTime());
		}
		final GregorianCalendar calTwo = new GregorianCalendar();
		if (two != null)
		{
			calTwo.setTimeInMillis(two.getTime());
		}
		if (calOne.get(Calendar.YEAR) == calTwo.get(Calendar.YEAR)
				&& calOne.get(Calendar.MONTH) == calTwo.get(Calendar.MONTH)
				&& calOne.get(Calendar.DAY_OF_MONTH) == calTwo.get(Calendar.DAY_OF_MONTH))
		{
			return true;
		}
		return false;
	}    // isSameDay

	/**
	 * Is it the same hour
	 *
	 * @param one day/time
	 * @param two compared day/time
	 * @return true if the same day
	 */
	static public boolean isSameHour(final Timestamp one, final Timestamp two)
	{
		final GregorianCalendar calOne = new GregorianCalendar();
		if (one != null)
		{
			calOne.setTimeInMillis(one.getTime());
		}
		final GregorianCalendar calTwo = new GregorianCalendar();
		if (two != null)
		{
			calTwo.setTimeInMillis(two.getTime());
		}
		if (calOne.get(Calendar.YEAR) == calTwo.get(Calendar.YEAR)
				&& calOne.get(Calendar.MONTH) == calTwo.get(Calendar.MONTH)
				&& calOne.get(Calendar.DAY_OF_MONTH) == calTwo.get(Calendar.DAY_OF_MONTH)
				&& calOne.get(Calendar.HOUR_OF_DAY) == calTwo.get(Calendar.HOUR_OF_DAY))
		{
			return true;
		}
		return false;
	}    // isSameHour

	/**
	 * If is the dates are form the same year, returns true
	 */
	static public boolean isSameYear(final Timestamp one, final Timestamp two)
	{
		final int year1 = getYearFromTimestamp(one);
		final int year2 = getYearFromTimestamp(two);

		return year1 == year2;
	}

	/**
	 * Is all day
	 *
	 * @param start start date
	 * @param end   end date
	 * @return true if all day (00:00-00:00 next day)
	 */
	static public boolean isAllDay(final Timestamp start, final Timestamp end)
	{
		final GregorianCalendar calStart = new GregorianCalendar();
		calStart.setTimeInMillis(start.getTime());
		final GregorianCalendar calEnd = new GregorianCalendar();
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
		{
			return true;
		}
		//
		return false;
	}    // isAllDay

	/**
	 * Calculate the number of hours between start and end.
	 *
	 * @param date1 start date
	 * @param date2 end date
	 * @return number of hours (0 = same)
	 */
	public static long getHoursBetween(final Date date1, final Date date2)
	{

		final int MILLI_TO_HOUR = 1000 * 60 * 60;
		return (date2.getTime() - date1.getTime()) / MILLI_TO_HOUR;
	}

	public static int getDaysBetween(@NonNull final Instant start, @NonNull final Instant end)
	{
		// Thanks to http://mattgreencroft.blogspot.com/2014/12/java-8-time-choosing-right-object.html
		final LocalDate d1 = LocalDateTime.ofInstant(start, SystemTime.zoneId()).toLocalDate();
		final LocalDate d2 = LocalDateTime.ofInstant(end, SystemTime.zoneId()).toLocalDate();
		return Period.between(d1, d2).getDays();
	}

	public static int getDaysBetween(@NonNull final LocalDateAndOrgId start, @NonNull final LocalDateAndOrgId end)
	{
		return (int)LocalDateAndOrgId.daysBetween(start, end);
	}
	
	/**
	 * Calculate the number of days between start and end.
	 *
	 * @param start start date
	 * @param end   end date
	 * @return number of days (0 = same)
	 */
	public static int getDaysBetween(@NonNull Date start, @NonNull Date end)
	{
		boolean negative = false;
		if (end.before(start))
		{
			negative = true;
			final Date temp = start;
			start = end;
			end = temp;
		}
		//
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(start);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		final GregorianCalendar calEnd = new GregorianCalendar();
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
			{
				return (calEnd.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR)) * -1;
			}
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
		{
			return counter * -1;
		}
		return counter;
	}    // getDaysBetween

	/**
	 * Return Day + offset (truncates)
	 *
	 * @param day    Day
	 * @param offset day offset
	 * @return Day + offset at 00:00
	 */
	static public Timestamp addYears(Timestamp day, final int offset)
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

		cal.add(Calendar.YEAR, offset);            // may have a problem with negative (before 1/1)
		return new Timestamp(cal.getTimeInMillis());
	}    // addMonths

	/**
	 * Return Day + offset (truncates)
	 *
	 * @param day    Day
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

		cal.add(Calendar.MONTH, offset);            // may have a problem with negative (before 1/1)
		return new Timestamp(cal.getTimeInMillis());
	}    // addMonths

	/**
	 * Return Day + offset (truncates)
	 *
	 * @param day    Day
	 * @param offset day offset
	 * @return Day + offset at 00:00
	 */
	static public Timestamp addWeeks(Timestamp day, final int offset)
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

		cal.add(Calendar.WEEK_OF_YEAR, offset);            // may have a problem with negative (before 1/1)
		return new Timestamp(cal.getTimeInMillis());
	}    // addDays

	/**
	 * Return Day + offset (truncates)
	 *
	 * @param day    Day
	 * @param offset day offset
	 * @return day + offset at 00:00
	 */
	static public Timestamp addDays(Date day, final int offset)
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

		cal.add(Calendar.DAY_OF_YEAR, offset);            // may have a problem with negative (before 1/1)
		return new Timestamp(cal.getTimeInMillis());
	}    // addDays

	/**
	 * Similar to {@link #addDays(Date, int)}, but the given {@code day} may not be {@code null},
	 * and the return value has the same hours, minutes, records and milliseconds as the given day (i.e. it's not 00:00).
	 *
	 * @param offset day offset
	 * @return day + offset
	 */
	static public Timestamp addDaysExact(@NonNull final Date day, final int offset)
	{
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(day);
		cal.add(Calendar.DAY_OF_YEAR, offset);
		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * Return DateTime + offset in minutes
	 *
	 * @param dateTime Date and Time
	 * @param offset   minute offset
	 * @return dateTime + offset in minutes; never returns {@code null}
	 */
	public static Date addMinutes(final Date dateTime, final int offset)
	{
		final Date dateTimeToUse = dateTime == null ? SystemTime.asDate() : dateTime;

		if (offset == 0)
		{
			return dateTimeToUse;
		}

		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(dateTimeToUse);
		cal.add(Calendar.MINUTE, offset);            // may have a problem with negative
		return new Date(cal.getTimeInMillis());
	}

	/**
	 * Like {@link #addMinutes(Date, int)}, but takes and returns a {@link Timestamp} and not a {@link Date}.
	 */
	public static Timestamp addMinutes(final Timestamp dateTime, final int offset)
	{
		return new Timestamp(addMinutes((Date)dateTime, offset).getTime());
	}

	/**
	 * Return DateTime + offset in millis
	 *
	 * @param dateTime Date and Time
	 * @param offset   minute offset
	 * @return dateTime + offset in millis
	 */
	static public Timestamp addMillis(Timestamp dateTime, final int offset)
	{
		if (dateTime == null)
		{
			dateTime = new Timestamp(System.currentTimeMillis());
		}
		if (offset == 0)
		{
			return dateTime;
		}
		//
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(dateTime);
		cal.add(Calendar.MILLISECOND, offset);            // may have a problem with negative
		return new Timestamp(cal.getTimeInMillis());
	}    // addMillis

	/**
	 * Return DateTime + offset in hours
	 *
	 * @param dateTime Date and Time
	 * @param offset   minute offset
	 * @return dateTime + offset in hours
	 */
	static public Timestamp addHours(Date dateTime, final int offset)
	{
		if (dateTime == null)
		{
			dateTime = new Timestamp(Instant.now().toEpochMilli());
		}
		if (offset == 0)
		{
			return asTimestamp(dateTime);
		}
		//
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(dateTime);
		cal.add(Calendar.HOUR, offset);            // may have a problem with negative
		return new Timestamp(cal.getTimeInMillis());
	}    // addHours

	/**
	 * Format Elapsed Time
	 *
	 * @param start start time or null for now
	 * @param end   end time or null for now
	 */
	public static String formatElapsed(final Timestamp start, final Timestamp end)
	{
		if (start == null || end == null)
		{
			return formatElapsed(0);
		}

		final long startTime = start.getTime();
		final long endTime = end.getTime();
		return formatElapsed(endTime - startTime);
	}

	/**
	 * Format Elapsed Time until now
	 */
	public static String formatElapsed(final Timestamp start)
	{
		if (start == null)
		{
			return "NoStartTime";
		}
		final long startTime = start.getTime();
		final long endTime = System.currentTimeMillis();
		return formatElapsed(endTime - startTime);
	}

	public static String formatElapsed(final long elapsedMillis)
	{
		return formatElapsed(Duration.ofMillis(elapsedMillis));
	}

	// copy-paste of com.google.common.base.Stopwatch.toString(). Too bad that functionality is not exposed as a regular method call
	public static String formatElapsed(@NonNull final Duration duration)
	{
		final long nanos = duration.toNanos();

		final TimeUnit unit = chooseUnit(nanos);
		final double value = (double)nanos / NANOSECONDS.convert(1, unit);

		return String.format(Locale.ROOT, "%.4g", value) + " " + abbreviate(unit);
	}

	// copy-paste of com.google.common.base.Stopwatch.chooseUnit(long)
	private static TimeUnit chooseUnit(final long nanos)
	{
		if (DAYS.convert(nanos, NANOSECONDS) > 0)
		{
			return DAYS;
		}
		if (HOURS.convert(nanos, NANOSECONDS) > 0)
		{
			return HOURS;
		}
		if (MINUTES.convert(nanos, NANOSECONDS) > 0)
		{
			return MINUTES;
		}
		if (SECONDS.convert(nanos, NANOSECONDS) > 0)
		{
			return SECONDS;
		}
		if (MILLISECONDS.convert(nanos, NANOSECONDS) > 0)
		{
			return MILLISECONDS;
		}
		if (MICROSECONDS.convert(nanos, NANOSECONDS) > 0)
		{
			return MICROSECONDS;
		}
		return NANOSECONDS;
	}

	// copy-paste of com.google.common.base.Stopwatch.abbreviate(TimeUnit)
	private static String abbreviate(final TimeUnit unit)
	{
		switch (unit)
		{
			case NANOSECONDS:
				return "ns";
			case MICROSECONDS:
				return "\u03bcs"; // Î¼s
			case MILLISECONDS:
				return "ms";
			case SECONDS:
				return "s";
			case MINUTES:
				return "min";
			case HOURS:
				return "h";
			case DAYS:
				return "d";
			default:
				throw new AssertionError();
		}
	}

	/**
	 * Is it valid today?
	 *
	 * @param validFrom valid from
	 * @param validTo   valid to
	 * @return true if walid
	 */
	public static boolean isValid(final Timestamp validFrom, final Timestamp validTo)
	{
		return isValid(validFrom, validTo, new Timestamp(System.currentTimeMillis()));
	}    // isValid

	/**
	 * Is it valid on test date.
	 * <p>
	 * If <code>testDate</code> is null, true will be returned.
	 *
	 * @param validFrom valid from
	 * @param validTo   valid to
	 * @param testDate  Date
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
	}    // isValid

	/**
	 * Checks if given <code>date</code> is between <code>dateFrom</code> and <code>dateTo</code> inclusivelly.
	 * <p>
	 * If <code>dateFrom</code> or <code>dateTo</code> are <code>null</code> it will be considered as infinity.
	 *
	 * @return true if date is between given dates (inclusively)
	 */
	public static boolean isBetween(final Date date, @Nullable final Date dateFrom, @Nullable final Date dateTo)
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

	public static boolean isBetween(
			@NonNull final Instant date,
			@Nullable final Instant dateFrom,
			@Nullable final Instant dateTo)
	{
		if (dateFrom != null && date.isBefore(dateFrom))
		{
			return false;
		}

		if (dateTo != null && date.isAfter(dateTo))
		{
			return false;
		}

		return true;
	}

	/**
	 * Truncate Second - S
	 */
	public static final String TRUNC_SECOND = "S";
	/**
	 * Truncate Minute - M
	 */
	public static final String TRUNC_MINUTE = "M";
	/**
	 * Truncate Hour - H
	 */
	public static final String TRUNC_HOUR = "H";
	/**
	 * Truncate Day - D
	 */
	public static final String TRUNC_DAY = "D";
	/**
	 * Truncate Week - W (Monday is always considered the first day of the week)
	 */
	public static final String TRUNC_WEEK = "W";
	/**
	 * Truncate Month - MM
	 */
	public static final String TRUNC_MONTH = "MM";
	/**
	 * Truncate Quarter - Q
	 */
	public static final String TRUNC_QUARTER = "Q";
	/**
	 * Truncate Year - Y
	 */
	public static final String TRUNC_YEAR = "Y";

	/**
	 * Get truncated day/time
	 *
	 * @param dayTime day
	 * @param trunc   how to truncate TRUNC_*
	 * @return next day with 00:00
	 */
	// metas: changed dayTime type from Timestamp to Date
	public static Timestamp trunc(final Date dayTime, final String trunc)
	{
		return new Timestamp(truncToMillis(dayTime, trunc));
	}

	public static long truncToMillis(
			@Nullable final Date dayTime,
			final String trunc)
	{
		final Date dayTimeToUse = dayTime == null ? SystemTime.asTimestamp() : dayTime;
		return truncToMillisDayTimeNotNull(dayTimeToUse, trunc);
	}

	private static long truncToMillisDayTimeNotNull(
			@NonNull final Date dayTime,
			final String trunc)
	{
		// note-ts: not using a locale because this method may be used during early startup
		// (and I don't see what for we need a locale)
		// GregorianCalendar cal = new GregorianCalendar(Env.getLanguage(Env.getCtx()).getLocale());
		final GregorianCalendar cal = new GregorianCalendar();
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
		{
			return cal.getTimeInMillis();
		}
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
		{
			return cal.getTimeInMillis();
		}
		// Q
		if (trunc.equals(TRUNC_QUARTER))
		{
			int mm = cal.get(Calendar.MONTH);
			if (mm < 4)
			{
				mm = 1;
			}
			else if (mm < 7)
			{
				mm = 4;
			}
			else if (mm < 10)
			{
				mm = 7;
			}
			else
			{
				mm = 10;
			}
			cal.set(Calendar.MONTH, mm);
			return cal.getTimeInMillis();
		}
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return cal.getTimeInMillis();
	}    // trunc

	@Nullable
	public static Timestamp truncToDay(@Nullable final Date dayTime)
	{
		return dayTime == null ? null : trunc(dayTime, TRUNC_DAY);
	}

	/**
	 * Returns the day border by combining the date part from dateTime and time part form timeSlot. If timeSlot is null, then first milli of the day will be used (if end == false) or last milli of the
	 * day (if end == true).
	 */
	public static Timestamp getDayBorder(final Timestamp dateTime, final Timestamp timeSlot, final boolean end)
	{
		final GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(dateTime.getTime());
		dateTime.setNanos(0);

		if (timeSlot != null)
		{
			timeSlot.setNanos(0);
			final GregorianCalendar gcTS = new GregorianCalendar();
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

	public static Timestamp asTimestampNotNull(@NonNull final ZonedDateTime zdt)
	{
		return Timestamp.from(zdt.toInstant());
	}

	@Nullable
	public static Timestamp asTimestamp(@Nullable final Object obj)
	{
		return asTimestamp(obj, null);
	}

	public static Timestamp asTimestamp(@Nullable final Object obj, @Nullable final ZoneId zoneId)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof Timestamp)
		{
			return (Timestamp)obj;
		}
		else if (obj instanceof Date)
		{
			return new Timestamp(((Date)obj).getTime());
		}
		else if (obj instanceof LocalDateTime)
		{
			return Timestamp.valueOf((LocalDateTime)obj);
		}
		else if (obj instanceof ZonedDateTime)
		{
			return Timestamp.from(((ZonedDateTime)obj).toInstant());
		}
		else
		{
			final ZoneId zoneIdNonNull = CoalesceUtil.coalesceNotNull(zoneId, SystemTime.zoneId());
			return Timestamp.from(asInstant(obj, zoneIdNonNull));
		}
	}

	public static boolean isDateOrTimeObject(@Nullable final Object value)
	{
		return value != null && isDateOrTimeClass(value.getClass());
	}

	public static boolean isDateOrTimeClass(@NonNull final Class<?> clazz)
	{
		return java.util.Date.class.isAssignableFrom(clazz)
				|| Instant.class.isAssignableFrom(clazz)
				|| ZonedDateTime.class.isAssignableFrom(clazz)
				|| LocalDateTime.class.isAssignableFrom(clazz)
				|| LocalDate.class.isAssignableFrom(clazz)
				|| LocalTime.class.isAssignableFrom(clazz)
				|| XMLGregorianCalendar.class.isAssignableFrom(clazz);
	}

	/**
	 * @deprecated your method argument is already a {@link Timestamp}; you don't need to call this method.
	 */
	@Deprecated
	public static Timestamp asTimestamp(final Timestamp timestamp)
	{
		return timestamp;
	}

	@Deprecated
	public static Timestamp asTimestamp(final LocalDateAndOrgId localDateAndOrgId)
	{
		throw new AdempiereException("Converting from LocalDateAndOrgId to Timestamp without knowing the org timezone is not possible");
	}

	/**
	 * @return date as timestamp or null if the date is null
	 */
	@Nullable
	public static Timestamp asTimestamp(@Nullable final Date date)
	{
		if (date instanceof Timestamp)
		{
			return (Timestamp)date;
		}
		return date == null ? null : new Timestamp(date.getTime());
	}

	/**
	 * @return instant as timestamp or null if the instant is null; note: use {@link Timestamp#toInstant()} for the other direction.
	 */
	@Nullable
	public static Timestamp asTimestamp(@Nullable final Instant instant)
	{
		return instant != null ? asTimestampNotNull(instant) : null;
	}

	@NonNull
	public static Timestamp asTimestampNotNull(@NonNull final Instant instant)
	{
		return Timestamp.from(instant);
	}

	/**
	 * NOTE: please consider using {@link #asTimestamp(LocalDate, ZoneId)} with the respective org's time zone instead (see {@link de.metas.organization.IOrgDAO#getTimeZone(de.metas.organization.OrgId)}).
	 * Will be deprecated in future but atm we cannot because there are a lot of cases when we have to use it.
	 */
	@Nullable
	public static Timestamp asTimestamp(@Nullable final LocalDate localDate)
	{
		final ZoneId timezone = null;
		return asTimestamp(localDate, timezone);
	}

	@Nullable
	public static Timestamp asTimestamp(
			@Nullable final LocalDate localDate,
			@Nullable final ZoneId timezone)
	{
		if (localDate == null)
		{
			return null;
		}
		final Instant instant = localDate
				.atStartOfDay(coalesceNotNull(timezone, SystemTime.zoneId()))
				.toInstant();
		return Timestamp.from(instant);
	}

	public static Timestamp asTimestamp(
			@Nullable final LocalDate localDate,
			@Nullable final LocalTime localTime)
	{
		final ZoneId timezone = null;
		return asTimestamp(localDate, localTime, timezone);
	}

	public static Timestamp asTimestamp(
			@Nullable final LocalDate localDate,
			@Nullable final LocalTime localTime,
			@Nullable final ZoneId timezone)
	{
		final LocalDate localDateEff = localDate != null ? localDate : LocalDate.now();
		final ZoneId timezoneEff = coalesceNotNull(timezone, SystemTime.zoneId());

		final Instant instant;
		if (localTime == null)
		{
			instant = localDateEff.atStartOfDay(timezoneEff).toInstant();
		}
		else
		{
			instant = localDateEff.atTime(localTime).atZone(timezoneEff).toInstant();
		}

		return Timestamp.from(instant);
	}

	@Nullable
	public static Timestamp asTimestamp(@Nullable final LocalDateTime localDateTime)
	{
		if (localDateTime == null)
		{
			return null;
		}

		return Timestamp.valueOf(localDateTime);
	}

	/**
	 * Get last date in year
	 *
	 * @param day day
	 * @return year last day with 00:00
	 */
	// metas
	static public Timestamp getYearLastDay(@Nullable Date day)
	{
		if (day == null)
		{
			day = new Timestamp(System.currentTimeMillis());
		}
		final GregorianCalendar cal = new GregorianCalendar(Env.getLanguage(Env.getCtx()).getLocale());
		cal.setTimeInMillis(day.getTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		//
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		return new Timestamp(cal.getTimeInMillis());
	}    // getYearLastDay

	/**
	 * Get first date in year
	 *
	 * @param day day
	 * @return year first day with 00:00
	 */
	// metas
	static public Timestamp getYearFirstDay(@Nullable Date day)
	{
		if (day == null)
		{
			day = new Timestamp(System.currentTimeMillis());
		}
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(day.getTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		//
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return new Timestamp(cal.getTimeInMillis());
	}    // getYearFirstDay

	/**
	 * Extract the year from a given date.
	 *
	 * @return the year as int
	 */
	static public int getYearFromTimestamp(final Date date)
	{
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.YEAR);
	}

	public static String formatDate(final Timestamp date, final String pattern)
	{
		final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	@Deprecated
	public static Timestamp parseTimestamp(@NonNull final String date) {return parseLocalDateAsTimestamp(date);}

	@NonNull
	public static Timestamp parseLocalDateAsTimestamp(@NonNull final String localDateStr)
	{
		try
		{
			return Timestamp.valueOf(LocalDate.parse(localDateStr).atStartOfDay());
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Failed converting `" + localDateStr + "` to LocalDate and then to Timestamp");
		}
	}

	public static LocalDateTime parseLocalDateTime(@NonNull final String date)
	{
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDateTime.parse(date, formatter);
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

	/**
	 * @return copy of given timestamp or null if the given timestamp was null
	 */
	@Nullable
	public static Timestamp copyOf(@Nullable final Timestamp timestamp)
	{
		return timestamp == null ? null : new Timestamp(timestamp.getTime());
	}

	/**
	 * Get the week of year number for the given Date
	 * <p>
	 * The logic for calculating the week number is based on the ISO week date conventions.
	 * Please, check https://en.wikipedia.org/wiki/ISO_week_date for more details.
	 */
	public static int getWeekNumber(final Date date)
	{
		// make sure the timing is not taken into account. The Timestamp will be set on the first millisecond of the given date.
		final Calendar cal = asCalendar(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		// According to international standard ISO 8601, Monday is the first day of the week.
		cal.setFirstDayOfWeek(Calendar.MONDAY);

		// FIXME: This shall be taken from Locale, but Locale it is not reliable (doesn't always work the same way)
		// It is the first week with a majority (4 or more) of its days in January.
		cal.setMinimalDaysInFirstWeek(4);

		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * Get the day of the week for the given date.
	 * First day of the week is considered Monday, due to ISO 8601.
	 * Please, check https://en.wikipedia.org/wiki/ISO_week_date for more details.
	 */
	public static int getDayOfWeek(final Date date)
	{
		final int dayOfWeek = asCalendar(date).get(Calendar.DAY_OF_WEEK);

		// According to international standard ISO 8601, Monday is the first day of the week.
		// The Calendar considers it to be Sunday, so this adjustment is needed.
		// see java.util.Calendar.MONDAY
		if (dayOfWeek == 1)
		{
			return 7;
		}
		return dayOfWeek - 1;
	}

	@Deprecated
	@Nullable
	public static LocalDate asLocalDate(@Nullable final LocalDate localDate)
	{
		return localDate;
	}

	/**
	 * @deprecated Consider using {@link InstantAndOrgId#toLocalDate(Function)}.
	 */
	@Deprecated
	@Nullable
	public static LocalDate asLocalDate(@Nullable final Timestamp ts, @NonNull final OrgId orgId)
	{
		if (ts == null)
		{
			return null;
		}

		final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
		return InstantAndOrgId.ofTimestamp(ts, orgId).toLocalDate(orgDAO::getTimeZone);
	}

	@Nullable
	public static LocalDate asLocalDate(@Nullable final Timestamp ts)
	{
		return ts != null
				? asLocalDateNonNull(ts)
				: null;
	}

	@NonNull
	public static LocalDate asLocalDateNonNull(@NonNull final Timestamp ts)
	{
		return ts.toLocalDateTime().toLocalDate();
	}

	@Deprecated
	@Nullable
	public static LocalDate asLocalDate(@Nullable final java.util.Date date)
	{
		return date != null
				? date.toInstant().atZone(SystemTime.zoneId()).toLocalDate()
				: null;
	}

	@Deprecated
	@Nullable
	public static LocalDate asLocalDate(@Nullable final Instant instant)
	{
		return instant != null
				? instant.atZone(SystemTime.zoneId()).toLocalDate()
				: null;
	}

	public static LocalDate asLocalDate(@Nullable XMLGregorianCalendar calendar)
	{
		return calendar != null
				? calendar.toGregorianCalendar().toInstant().atZone(SystemTime.zoneId()).toLocalDate()
				: null;
	}

	@Nullable
	public static LocalDate asLocalDate(@Nullable final Object obj)
	{
		return asLocalDate(obj, null);
	}

	@Nullable
	public static LocalDate asLocalDate(@Nullable final Object obj, @Nullable final ZoneId zoneId)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof LocalDate)
		{
			return (LocalDate)obj;
		}
		else if (obj instanceof String)
		{
			return LocalDate.parse(obj.toString());
		}
		else if (obj instanceof LocalDateAndOrgId)
		{
			return ((LocalDateAndOrgId)obj).toLocalDate();
		}
		else
		{
			return asLocalDateTime(obj, zoneId).toLocalDate();
		}
	}

	@Nullable
	public static LocalDate asLocalDate(@Nullable final Timestamp timestamp, @NonNull final ZoneId zoneId)
	{
		return timestamp != null
				? timestamp.toInstant().atZone(zoneId).toLocalDate()
				: null;
	}

	@Nullable
	public static LocalDate asLocalDate(@Nullable final ZonedDateTime zonedDateTime, @NonNull final ZoneId zoneId)
	{
		return zonedDateTime != null
				? convertToTimeZone(zonedDateTime, zoneId).toLocalDate()
				: null;
	}

	@Nullable
	public static LocalDate asLocalDate(@Nullable final Instant instant, @NonNull final ZoneId zoneId)
	{
		return instant != null
				? instant.atZone(zoneId).toLocalDate()
				: null;
	}

	@Deprecated
	@Nullable
	public static LocalDate asLocalDate(@Nullable final ZonedDateTime zonedDateTime)
	{
		return zonedDateTime != null ? zonedDateTime.toLocalDate() : null;
	}

	@Nullable
	public static LocalTime asLocalTime(@Nullable final Object obj)
	{
		return asLocalTime(obj, null);
	}

	@Nullable
	public static LocalTime asLocalTime(@Nullable final Object obj, @Nullable final ZoneId zoneId)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof LocalTime)
		{
			return (LocalTime)obj;
		}
		else
		{
			return asLocalDateTime(obj, zoneId).toLocalTime();
		}
	}

	/**
	 * @deprecated your method argument is already a {@link LocalDateTime}; you don't need to call this method.
	 */
	@Deprecated
	public static LocalDateTime asLocalDateTime(final LocalDateTime localDateTime)
	{
		return localDateTime;
	}

	@Nullable
	public static LocalDateTime asLocalDateTime(@Nullable final Object obj)
	{
		return asLocalDateTime(obj, null);
	}

	@Nullable
	public static LocalDateTime asLocalDateTime(@Nullable final Object obj, @Nullable final ZoneId zoneId)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof LocalDateTime)
		{
			return (LocalDateTime)obj;
		}
		else if (obj instanceof LocalDate)
		{
			return ((LocalDate)obj).atStartOfDay();
		}
		else if (obj instanceof Timestamp)
		{
			return ((Timestamp)obj).toLocalDateTime();
		}
		else if (obj instanceof ZonedDateTime)
		{
			return ((ZonedDateTime)obj).toLocalDateTime();
		}
		else
		{
			final ZoneId zoneIdNonNull = CoalesceUtil.coalesceNotNull(zoneId, SystemTime.zoneId());

			return asInstant(obj, zoneIdNonNull)
					.atZone(zoneIdNonNull).toLocalDateTime();
		}
	}

	@Deprecated
	public static ZonedDateTime asZonedDateTime(final ZonedDateTime zonedDateTime)
	{
		return zonedDateTime;
	}

	/**
	 * @deprecated favor using {@link #asZonedDateTime(Object, ZoneId)}
	 */
	@Deprecated
	@Nullable
	public static ZonedDateTime asZonedDateTime(@Nullable final LocalDate localDate)
	{
		return localDate != null
				? localDate.atStartOfDay(SystemTime.zoneId())
				: null;
	}

	@Nullable
	public static ZonedDateTime asZonedDateTime(@Nullable final Instant instant)
	{
		return instant != null ? instant.atZone(SystemTime.zoneId()) : null;
	}

	/**
	 * @return timestamp converted to ZonedDateTime using system time zone.
	 * @deprecated Please consider using {@link #asZonedDateTime(Timestamp, ZoneId)}.
	 * <p>
	 * If you don't know the {@link ZoneId} then
	 * <ul>
	 *     <li>please consider using {@link de.metas.organization.InstantAndOrgId} and don't assume system time zone.
	 *     <li>or please consider using {@link Instant}
	 *     </ul>
	 *     <b>But please don't just assume the time zone is system time zone.</b>
	 */
	@Deprecated
	public static ZonedDateTime asZonedDateTime(@Nullable final Timestamp timestamp)
	{
		return timestamp != null ? timestamp.toInstant().atZone(SystemTime.zoneId()) : null;
	}

	/**
	 * @deprecated Consider using {@link InstantAndOrgId#toZonedDateTime(Function)}.
	 */
	@Deprecated
	@Nullable
	public static ZonedDateTime asZonedDateTime(@Nullable final Timestamp ts, @NonNull final OrgId orgId)
	{
		if (ts == null)
		{
			return null;
		}

		final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
		return InstantAndOrgId.ofTimestamp(ts, orgId).toZonedDateTime(orgDAO::getTimeZone);
	}

	public static ZonedDateTime asZonedDateTime(@Nullable final Timestamp timestamp, @NonNull final ZoneId zoneId)
	{
		return timestamp != null ? timestamp.toInstant().atZone(zoneId) : null;
	}

	/**
	 * @deprecated please use {@link #asZonedDateTime(Object, ZoneId)}. The server's timezone might not be the one you need.
	 */
	@Nullable
	@Deprecated
	public static ZonedDateTime asZonedDateTime(@Nullable final Object obj)
	{
		if (obj == null)
		{
			return null;
		}

		return asZonedDateTime(obj, SystemTime.zoneId());
	}

	@Nullable
	public static ZonedDateTime asZonedDateTime(@Nullable final Object obj, @NonNull final ZoneId zoneId)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof ZonedDateTime)
		{
			return convertToTimeZone((ZonedDateTime)obj, zoneId);
		}
		else
		{
			return asInstant(obj, zoneId).atZone(zoneId);
		}
	}

	@Nullable
	public static Date asDate(@Nullable final Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof Timestamp)
		{
			return new Date(((Timestamp)obj).getTime());
		}
		else if (obj instanceof Date)
		{
			return (Date)obj;
		}
		else
		{
			return Date.from(asInstant(obj));
		}
	}

	/**
	 * @deprecated your method argument is already an {@link Instant}; you don't need to call this method.
	 */
	@Deprecated
	@Nullable
	public static Instant asInstant(@Nullable final Instant instant)
	{
		return instant;
	}

	@Nullable
	public static Instant asInstant(@Nullable final Object obj)
	{
		return asInstant(obj, SystemTime.zoneId());
	}

	@Nullable
	public static Instant asInstant(@Nullable final Timestamp timestamp)
	{
		return timestamp != null ? timestamp.toInstant() : null;
	}

	@NonNull
	public static Instant asInstantNonNull(@NonNull final Timestamp timestamp)
	{
		return timestamp.toInstant();
	}

	/**
	 * @deprecated Consider using {@link LocalDateAndOrgId#toEndOfDayInstant(Function)}.
	 */
	@Deprecated
	@Nullable
	public static Instant asEndOfDayInstant(@Nullable final LocalDate localDate, @NonNull final OrgId orgId)
	{
		if (localDate == null)
		{
			return null;
		}

		final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
		return LocalDateAndOrgId.ofLocalDate(localDate, orgId).toEndOfDayInstant(orgDAO::getTimeZone);
	}

	@Nullable
	public static Instant asEndOfDayInstant(@Nullable final LocalDate localDate, @NonNull final ZoneId zoneId)
	{
		if (localDate == null)
		{
			return null;
		}
		final LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX);

		return asInstant(endOfDay, zoneId);
	}

	/**
	 * @deprecated Consider using {@link LocalDateAndOrgId#toInstant(Function)}.
	 */
	@Deprecated
	@Nullable
	public static Instant asInstant(
			@Nullable final LocalDate localDate,
			@NonNull final OrgId orgId)
	{
		if (localDate == null)
		{
			return null;
		}

		final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
		return LocalDateAndOrgId.ofLocalDate(localDate, orgId).toInstant(orgDAO::getTimeZone);
	}

	@SuppressWarnings("unused")
	@Deprecated
	public static Instant asInstant(
			@Nullable final LocalDateAndOrgId localDateAndOrgId,
			@NonNull final ZoneId zoneId)
	{
		throw new AdempiereException("Converting from localDateAndOrgId without knowing the Org's TimeZone is not possible");
	}

	@Nullable
	public static Instant asInstant(
			@Nullable final Object obj,
			@NonNull final ZoneId zoneId)
	{
		if (obj == null)
		{
			return null;
		}
		else if (String.valueOf(obj).equals(String.valueOf((Object)null)))
		{
			return null;
		}
		else if (obj instanceof InstantAndOrgId)
		{
			return ((InstantAndOrgId)obj).toInstant();
		}
		else if (obj instanceof Instant)
		{
			return (Instant)obj;
		}
		else if (obj instanceof Timestamp)
		{
			return ((Timestamp)obj).toInstant();
		}
		else if (obj instanceof Date)
		{
			return ((Date)obj).toInstant();
		}
		else if (obj instanceof LocalDateTime)
		{
			final LocalDateTime localDateTime = (LocalDateTime)obj;
			return localDateTime.atZone(zoneId).toInstant();
		}
		else if (obj instanceof LocalDate)
		{
			final LocalDate localDate = (LocalDate)obj;
			return localDate.atStartOfDay(zoneId).toInstant();
		}
		else if (obj instanceof LocalTime)
		{
			final LocalTime localTime = (LocalTime)obj;
			return localTime.atDate(DATE_1970_01_01).atZone(zoneId).toInstant();
		}
		else if (obj instanceof XMLGregorianCalendar)
		{
			return ((XMLGregorianCalendar)obj)
					.toGregorianCalendar()
					.toInstant();
		}
		else if (obj instanceof ZonedDateTime)
		{
			return ((ZonedDateTime)obj).toInstant();
		}
		else if (obj instanceof Integer)
		{
			final int millis = (Integer)obj;
			return Instant.ofEpochMilli(millis);
		}
		else if (obj instanceof Long)
		{
			final long millis = (Long)obj;
			return Instant.ofEpochMilli(millis);
		}
		else if (obj instanceof InstantAndOrgId)
		{
			return ((InstantAndOrgId)obj).toInstant();
		}
		else
		{
			throw new IllegalArgumentException("Cannot convert " + obj + " (" + obj.getClass() + ") to " + Instant.class);
		}
	}

	/**
	 * Gets minimum date.
	 * <p>
	 * If one of the dates is null, then the not null one will be returned.
	 * <p>
	 * If both dates are null then null will be returned.
	 *
	 * @return minimum date or null
	 */
	@Nullable
	public static <T extends Date> T min(@Nullable final T date1, @Nullable final T date2)
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

	@Nullable
	public static ZonedDateTime min(@Nullable final ZonedDateTime date1, @Nullable final ZonedDateTime date2) {return minOfNullables(date1, date2);}

	@Nullable
	@SuppressWarnings("rawtypes")
	public static <T extends Temporal & Comparable> T minOfNullables(@Nullable final T date1, @Nullable final T date2)
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
		else
		{
			return minNotNull(date1, date2);
		}
	}

	@SuppressWarnings("rawtypes")
	public static <T extends Temporal & Comparable> T minNotNull(@NonNull final T date1, @NonNull final T date2)
	{
		if (date1 == date2)
		{
			return date1;
		}
		else
		{
			//noinspection unchecked
			return date1.compareTo(date2) <= 0 ? date1 : date2;
		}
	}

	@Nullable
	public static Duration max(@Nullable final Duration duration1, @Nullable final Duration duration2)
	{
		if (duration1 == null)
		{
			return duration2;
		}
		else if (duration2 == null)
		{
			return duration1;
		}
		else
		{
			return maxNotNull(duration1, duration2);
		}
	}

	@NonNull
	public static Duration maxNotNull(@NonNull final Duration duration1, @NonNull final Duration duration2)
	{
		if (duration1 == duration2)
		{
			return duration1;
		}
		else if (duration1.compareTo(duration2) >= 0)
		{
			return duration1;
		}
		else
		{
			return duration2;
		}
	}

	@Nullable
	public static <T extends Date> T max(@Nullable final T ts1, @Nullable final T ts2)
	{
		if (ts1 == null)
		{
			return ts2;
		}
		if (ts2 == null)
		{
			return ts1;
		}

		if (ts2.after(ts1))
		{
			return ts2;
		}
		return ts1;
	}

	@Nullable
	public static Instant max(@Nullable final Instant instant1, @Nullable final Instant instant2) {return maxOfNullables(instant1, instant2);}

	public static LocalDate max(@NonNull final LocalDate d1, @NonNull final LocalDate d2) {return maxNotNull(d1, d2);}

	@SuppressWarnings("rawtypes")
	public static <T extends Temporal & Comparable> T maxNotNull(@NonNull final T date1, @NonNull final T date2)
	{
		if (date1 == date2)
		{
			return date1;
		}
		else
		{
			//noinspection unchecked
			return date1.compareTo(date2) >= 0 ? date1 : date2;
		}
	}

	@Nullable
	@SuppressWarnings("rawtypes")
	public static <T extends Temporal & Comparable> T maxOfNullables(@Nullable final T date1, @Nullable final T date2)
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
		else
		{
			return maxNotNull(date1, date2);
		}
	}

	public static boolean isLastDayOfMonth(@NonNull final LocalDate localDate)
	{
		final LocalDate lastDayOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth());
		return localDate.equals(lastDayOfMonth);
	}

	public static ZonedDateTime convertToTimeZone(@NonNull final ZonedDateTime date, @NonNull final ZoneId zoneId)
	{
		if (date.getZone().equals(zoneId))
		{
			return date;
		}
		else
		{
			return date.toInstant().atZone(zoneId);
		}
	}

	/**
	 * Counterpart of {@link #deserializeInstant(String)}.
	 */
	public static String serializeInstant(@NonNull final Instant instant)
	{
		return instant.getEpochSecond() + "." + Long.toString(instant.getNano());
	}

	/**
	 * Deserializes a string such as {@code "12345.234567"} into an {@link Instant}.
	 * <p>
	 * Notes:
	 * <li>I didn't want to use jackson because this is not inteded to serialize/deserialize a whole object-tree, but be called from very concrete business logic, so i want it to be more transparent how this is done.
	 * <li>{@link Instant#toString()} and {@link Instant#parse(CharSequence)} don't preserve the nanos, so they are even less accurate than a "normal" {@link Timestamp}.
	 */
	public static Instant deserializeInstant(@NonNull final String instant)
	{
		final String[] split = instant.split("\\.");
		if (split.length != 2)
		{
			throw new AdempiereException("The  instant string needs to contain two longs that are delimited by a dot").appendParametersToMessage().setParameter("instant-string", instant);
		}

		final long seconds;
		final long nanos;
		try
		{
			seconds = Long.parseLong(split[0]);
		}
		catch (final NumberFormatException e)
		{
			throw new AdempiereException("The 'seconds' part of the given instant string can't be parsed as long", e).appendParametersToMessage().setParameter("instant-string", instant);
		}

		try
		{
			nanos = Long.parseLong(split[1]);
		}
		catch (final NumberFormatException e)
		{
			throw new AdempiereException("The 'nanos' part of the given instant string can't be parsed as long", e).appendParametersToMessage().setParameter("instant-string", instant);
		}
		return Instant.ofEpochSecond(seconds, nanos);
	}

	@NonNull
	public static Duration toDuration(@NonNull final Stopwatch stopwatch)
	{
		return Duration.ofNanos(stopwatch.elapsed(TimeUnit.NANOSECONDS));
	}

	public static Range<Instant> toInstantsRange(
			@Nullable final java.sql.Timestamp from,
			@Nullable final java.sql.Timestamp to)
	{
		return toInstantsRange(
				from != null ? from.toInstant() : null,
				to != null ? to.toInstant() : null);
	}

	public static Range<Instant> toInstantsRange(
			@Nullable final Instant from,
			@Nullable final Instant to)
	{
		if (from == null)
		{
			return to == null ? Range.all() : Range.lessThan(to);
		}
		else
		{
			return to == null ? Range.atLeast(from) : Range.closedOpen(from, to);
		}
	}

	public static long getMillisBetween(@NonNull final Timestamp timestamp1, @NonNull final Timestamp timestamp2)
	{
		return timestamp2.getTime() - timestamp1.getTime();
	}

	@Nullable
	public static Instant asStartOfDayInstant(@Nullable final LocalDate localDate, @NonNull final ZoneId zoneId)
	{
		if (localDate == null)
		{
			return null;
		}
		final LocalDateTime startOfDay = localDate.atTime(LocalTime.MIN);

		return asInstant(startOfDay, zoneId);
	}

	@Nullable
	public static Instant asStartOfDayInstant(@Nullable final Instant instant, @NonNull final ZoneId zoneId)
	{
		if (instant == null)
		{
			return null;
		}

		final LocalDate localDate = asLocalDate(instant, zoneId);
		return asStartOfDayInstant(localDate, zoneId);
	}

	@Nullable
	public static Instant asEndOfDayInstant(@Nullable final Instant instant, @NonNull final ZoneId zoneId)
	{
		if (instant == null)
		{
			return null;
		}

		final LocalDate localDate = asLocalDate(instant, zoneId);
		return asEndOfDayInstant(localDate, zoneId);
	}

	public static boolean isOverlapping(
			@Nullable final Timestamp start1,
			@Nullable final Timestamp end1,
			@Nullable final Timestamp start2,
			@Nullable final Timestamp end2)
	{
		return isOverlapping(toInstantsRange(start1, end1), toInstantsRange(start2, end2));
	}

	public static boolean isOverlapping(@NonNull final Range<Instant> range1, @NonNull final Range<Instant> range2)
	{
		if (!range1.isConnected(range2))
		{
			return false;
		}

		return !range1.intersection(range2).isEmpty();
	}
}    // TimeUtil

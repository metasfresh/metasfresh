package org.compiere.util;

import de.metas.common.util.time.SystemTime;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.annotation.Nullable;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Teo Sarca
 */
public class TimeUtilTest
{
	private TimeZone jvmTimezoneBackup;

	@BeforeEach
	public void beforeEach()
	{
		SystemTime.resetTimeSource();
		jvmTimezoneBackup = TimeZone.getDefault();
	}

	@AfterEach
	public void afterEach()
	{
		SystemTime.resetTimeSource();
		TimeZone.setDefault(jvmTimezoneBackup);
	}

	private static Timestamp createTimestamp(final int year, int month, int day)
	{
		//noinspection deprecation
		return TimeUtil.getDay(year, month, day);
	}

	@Test
	public void testIsValid()
	{
		final Timestamp date_2011_05_10 = createTimestamp(2011, 5, 10);
		final Timestamp date_2011_05_20 = createTimestamp(2011, 5, 20);

		// Standard test
		assertIsValid(false, date_2011_05_10, date_2011_05_20, createTimestamp(2011, 5, 1));
		assertIsValid(true, date_2011_05_10, date_2011_05_20, createTimestamp(2011, 5, 10));
		assertIsValid(true, date_2011_05_10, date_2011_05_20, createTimestamp(2011, 5, 11));
		assertIsValid(true, date_2011_05_10, date_2011_05_20, createTimestamp(2011, 5, 20));
		assertIsValid(false, date_2011_05_10, date_2011_05_20, createTimestamp(2011, 5, 21));

		// Test for interval beginning
		assertIsValid(true, date_2011_05_10, date_2011_05_20, date_2011_05_10);

		// Test for interval ending
		assertIsValid(true, date_2011_05_10, date_2011_05_20, date_2011_05_20);

		// Test for null interval beginning
		assertIsValid(true, null, date_2011_05_20, createTimestamp(2011, 5, 1));
		assertIsValid(true, null, date_2011_05_20, createTimestamp(2011, 5, 10));
		assertIsValid(true, null, date_2011_05_20, createTimestamp(2011, 5, 11));
		assertIsValid(true, null, date_2011_05_20, createTimestamp(2011, 5, 20));
		assertIsValid(false, null, date_2011_05_20, createTimestamp(2011, 5, 21));

		// Test for null interval ending
		assertIsValid(false, date_2011_05_10, null, createTimestamp(2011, 5, 1));
		assertIsValid(true, date_2011_05_10, null, createTimestamp(2011, 5, 10));
		assertIsValid(true, date_2011_05_10, null, createTimestamp(2011, 5, 11));
		assertIsValid(true, date_2011_05_10, null, createTimestamp(2011, 5, 20));
		assertIsValid(true, date_2011_05_10, null, createTimestamp(2011, 5, 21));

	}

	private void assertIsValid(boolean isValid, Timestamp validFrom, Timestamp validTo, Timestamp now)
	{
		final String message = "Error for validFrom=" + validFrom + ", validTo=" + validTo + ", now=" + now;
		final boolean isValidActual = TimeUtil.isValid(validFrom, validTo, now);
		Assertions.assertEquals(isValid, isValidActual, message);
	}

	@Nested
	class max_with_Duration
	{
		@Test
		public void test()
		{
			final Duration min = Duration.ofMinutes(1);
			final Duration max = Duration.ofMinutes(2);

			//noinspection ConstantValue
			assertThat(TimeUtil.max((Duration)null, null)).isNull();

			assertThat(TimeUtil.max(min, null)).isSameAs(min);
			assertThat(TimeUtil.max(null, min)).isSameAs(min);
			assertThat(TimeUtil.max(min, min)).isSameAs(min);
			assertThat(TimeUtil.max(max, max)).isSameAs(max);
			assertThat(TimeUtil.max(min, max)).isSameAs(max);
			assertThat(TimeUtil.max(max, min)).isSameAs(max);
		}
	}

	@Nested
	class min_with_JUL_Date
	{
		@Test
		public void test()
		{
			final Timestamp date1 = createTimestamp(2014, 1, 1);
			final Timestamp date1_copy = createTimestamp(2014, 1, 1);
			final Timestamp date2 = createTimestamp(2014, 1, 2);

			// NULLs check
			assertThat(TimeUtil.min(null, (Date)null)).isSameAs(null);
			assertThat(TimeUtil.min(date1, null)).isSameAs(date1);
			assertThat(TimeUtil.min(null, date1)).isSameAs(date1);

			// Same (reference) value check
			assertThat(TimeUtil.min(date1, date1)).isSameAs(date1);

			// Same (value) check
			assertThat(TimeUtil.min(date1, date1_copy)).isSameAs(date1);

			assertThat(TimeUtil.min(date1, date2)).isSameAs(date1);
			assertThat(TimeUtil.min(date2, date1)).isSameAs(date1);
		}
	}

	@SuppressWarnings({ "unused", "JUnitMalformedDeclaration" })
	abstract static class MinMaxTemporalTest
	{
		abstract <DT extends Temporal & Comparable<?>> void test(DT minDate, DT maxDate);

		@Test
		void zonedDateTime()
		{
			final ZonedDateTime minDate = LocalDateTime.parse("2023-11-01T10:00").atZone(ZoneId.of("UTC-8"));
			final ZonedDateTime maxDate = LocalDateTime.parse("2023-11-01T11:00").atZone(ZoneId.of("UTC-8"));
			test(minDate, maxDate);
		}

		@Test
		void localDateTime()
		{
			final LocalDateTime minDate = LocalDateTime.parse("2023-11-01T10:00");
			final LocalDateTime maxDate = LocalDateTime.parse("2023-11-01T11:00");
			test(minDate, maxDate);
		}

		@Test
		void localDate()
		{
			final LocalDate minDate = LocalDate.parse("2023-11-01");
			final LocalDate maxDate = LocalDate.parse("2023-11-02");
			test(minDate, maxDate);
		}

		@Test
		void localTime()
		{
			final LocalTime minDate = LocalTime.parse("10:40");
			final LocalTime maxDate = LocalTime.parse("10:41");
			test(minDate, maxDate);
		}

		@Test
		void instant()
		{
			final Instant minDate = LocalDate.parse("2023-11-01").atStartOfDay().toInstant(ZoneOffset.UTC);
			final Instant maxDate = LocalDate.parse("2023-11-02").atStartOfDay().toInstant(ZoneOffset.UTC);
			test(minDate, maxDate);
		}

	}

	@Nested
	class min_with_ZonedDateTime
	{
		@Test
		void null_params()
		{
			final ZoneId zone = ZoneId.systemDefault();
			final ZonedDateTime date1 = LocalDate.of(2014, 1, 1).atStartOfDay().atZone(zone);

			assertThat(TimeUtil.min(null, (ZonedDateTime)null)).isNull();
			assertThat(TimeUtil.min(date1, null)).isSameAs(date1);
			assertThat(TimeUtil.min(null, date1)).isSameAs(date1);
		}

		@Test
		void same_values()
		{
			final ZoneId zone = ZoneId.systemDefault();
			final ZonedDateTime date1 = LocalDate.of(2014, 1, 1).atStartOfDay().atZone(zone);

			assertThat(TimeUtil.min(date1, date1)).isSameAs(date1);
		}

		@Test
		void equal_values()
		{
			final ZoneId zone = ZoneId.systemDefault();
			final ZonedDateTime date1 = LocalDate.of(2014, 1, 1).atStartOfDay().atZone(zone);
			final ZonedDateTime date1_copy = LocalDate.of(2014, 1, 1).atStartOfDay().atZone(zone);

			assertThat(TimeUtil.min(date1, date1_copy)).isEqualTo(date1);
		}

		@Test
		void standardCases()
		{
			final ZoneId zone = ZoneId.systemDefault();
			final ZonedDateTime date1 = LocalDate.of(2014, 1, 1).atStartOfDay().atZone(zone);
			final ZonedDateTime date2 = LocalDate.of(2014, 1, 2).atStartOfDay().atZone(zone);

			assertThat(TimeUtil.min(date1, date2)).isSameAs(date1);
			assertThat(TimeUtil.min(date2, date1)).isSameAs(date1);
		}
	}

	@Nested
	class minNotNull_with_Temporal extends MinMaxTemporalTest
	{
		@Override
		<DT extends Temporal & Comparable<?>> void test(DT minDate, DT maxDate)
		{
			assertThat(TimeUtil.minNotNull(minDate, minDate)).isSameAs(minDate);
			assertThat(TimeUtil.minNotNull(maxDate, maxDate)).isSameAs(maxDate);
			assertThat(TimeUtil.minNotNull(minDate, maxDate)).isSameAs(minDate);
			assertThat(TimeUtil.minNotNull(maxDate, minDate)).isSameAs(minDate);
		}
	}

	@Nested
	class minOfNullables_with_Temporal extends MinMaxTemporalTest
	{
		@Override
		<DT extends Temporal & Comparable<?>> void test(DT minDate, DT maxDate)
		{
			assertThat(TimeUtil.minOfNullables((DT)null, null)).isNull();
			assertThat(TimeUtil.minOfNullables(null, minDate)).isSameAs(minDate);
			assertThat(TimeUtil.minOfNullables(minDate, null)).isSameAs(minDate);

			assertThat(TimeUtil.minOfNullables(minDate, minDate)).isSameAs(minDate);
			assertThat(TimeUtil.minOfNullables(maxDate, maxDate)).isSameAs(maxDate);
			assertThat(TimeUtil.minOfNullables(minDate, maxDate)).isSameAs(minDate);
			assertThat(TimeUtil.minOfNullables(maxDate, minDate)).isSameAs(minDate);
		}
	}

	@Nested
	class maxNotNull_with_Temporal extends MinMaxTemporalTest
	{
		@Override
		<DT extends Temporal & Comparable<?>> void test(DT minDate, DT maxDate)
		{
			assertThat(TimeUtil.maxNotNull(minDate, minDate)).isSameAs(minDate);
			assertThat(TimeUtil.maxNotNull(maxDate, maxDate)).isSameAs(maxDate);
			assertThat(TimeUtil.maxNotNull(minDate, maxDate)).isSameAs(maxDate);
			assertThat(TimeUtil.maxNotNull(maxDate, minDate)).isSameAs(maxDate);
		}
	}

	@Nested
	class maxOfNullables_with_Temporal extends MinMaxTemporalTest
	{
		@Override
		<DT extends Temporal & Comparable<?>> void test(@Nullable DT minDate, @Nullable DT maxDate)
		{
			//noinspection ConstantValue
			assertThat(TimeUtil.maxOfNullables((DT)null, null)).isNull();
			assertThat(TimeUtil.maxOfNullables(minDate, null)).isSameAs(minDate);
			assertThat(TimeUtil.maxOfNullables(null, minDate)).isSameAs(minDate);

			assertThat(TimeUtil.maxOfNullables(minDate, minDate)).isSameAs(minDate);
			assertThat(TimeUtil.maxOfNullables(minDate, maxDate)).isSameAs(maxDate);
			assertThat(TimeUtil.maxOfNullables(maxDate, minDate)).isSameAs(maxDate);
		}
	}

	@Test
	public void test_isSameDay()
	{
		final Timestamp t1 = createTimestamp(1, 1, 1);
		final Timestamp t2 = createTimestamp(2, 2, 2);
		final Timestamp t3 = createTimestamp(3, 3, 3);

		final Timestamp t4 = createTimestamp(1, 1, 1);
		final Timestamp t5 = createTimestamp(2, 2, 2);

		assertSameDay(true, t1, t4);
		assertSameDay(true, t2, t5);
		assertSameDay(false, t3, t5);
	}

	private void assertSameDay(final boolean expected, Date date1, Date date2)
	{
		final boolean actual = TimeUtil.isSameDay(date1, date2);
		final String message = "Invalid isSameDay for date1=" + date1 + ", date2=" + date2;
		Assertions.assertEquals(expected, actual, message);
	}

	@Test
	public void test_formatElapsed()
	{
		assertFormatElapsed("1.000 s", 1000);
		assertFormatElapsed("1.234 s", 1234);
		assertFormatElapsed("1.000 h", 3601234);
		assertFormatElapsed("2.017 h", 7261234);
	}

	private void assertFormatElapsed(final String expected, final long elapsedMS)
	{
		final String actual = TimeUtil.formatElapsed(elapsedMS);
		Assertions.assertEquals(expected, actual, "Invalid formatElapsed for elapsedMS=" + elapsedMS);
	}

	@Test
	public void test_copyOf()
	{
		Assertions.assertNull(TimeUtil.copyOf(null)); // null test
		Assertions.assertEquals(123456, TimeUtil.copyOf(new Timestamp(123456)).getTime()); // straight forward test
	}

	@Test
	public void test_getWeekNumber()
	{
		final Date january1 = Timestamp.valueOf("2017-01-01 10:10:10.0");

		final int expected52 = 52;

		final int actual52 = TimeUtil.getWeekNumber(january1);

		Assertions.assertEquals(expected52, actual52);

		final Date january2 = Timestamp.valueOf("2017-01-02 10:10:10.0");

		final int expected1 = 1;

		final int actual1 = TimeUtil.getWeekNumber(january2);

		Assertions.assertEquals(expected1, actual1);

	}

	@Test
	public void test_getDayOfWeek()
	{
		final Timestamp january1 = Timestamp.valueOf("2017-01-01 10:10:10.0");

		// December 8, 2016 (Thu) : Day 343
		final int expected = 7;

		final int actual = TimeUtil.getDayOfWeek(january1);

		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void getDay()
	{
		test_getDay("2018-12-05T00:15:00+01:00", "+1", "2018-12-05");

		test_getDay("2018-12-05T23:59:59+01:00", "+1", "2018-12-05");

		test_getDay("2018-12-05T23:59:59+01:00", "+2", "2018-12-06");

		test_getDay("2018-12-05T00:59:59+01:00", "-1", "2018-12-04");
	}

	private void test_getDay(
			@NonNull final String zonedDateTime,
			@NonNull final String targetTimeZone,
			@NonNull final String expectedDateAtTimeZone)
	{
		final Instant input = ZonedDateTime.parse(zonedDateTime).toInstant();

		final ZoneId targetZoneId = ZoneId.of(targetTimeZone);

		final Instant expected = ZonedDateTime
				.of(
						LocalDateTime.parse(expectedDateAtTimeZone + "T00:00:00"),
						targetZoneId)
				.toInstant();

		assertThat(TimeUtil.getDay(input, targetZoneId)).isEqualTo(expected);
	}

	@Test
	public void test_isLastDayOfMonth()
	{
		assertLastDayOfMonth(false, LocalDate.of(2019, 1, 1));
		assertLastDayOfMonth(false, LocalDate.of(2019, 1, 30));
		assertLastDayOfMonth(true, LocalDate.of(2019, 1, 31));

		assertLastDayOfMonth(false, LocalDate.of(2019, 2, 27));
		assertLastDayOfMonth(true, LocalDate.of(2019, 2, 28));
	}

	private void assertLastDayOfMonth(final boolean expectation, final LocalDate date)
	{
		assertThat(TimeUtil.isLastDayOfMonth(date)).isEqualTo(expectation);
	}

	@Test
	public void test_isDateOrTimeObject()
	{
		assertThat(TimeUtil.isDateOrTimeObject(new java.util.Date())).isTrue();
		assertThat(TimeUtil.isDateOrTimeObject(new java.sql.Timestamp(System.currentTimeMillis()))).isTrue();
		assertThat(TimeUtil.isDateOrTimeObject(Instant.now())).isTrue();
		assertThat(TimeUtil.isDateOrTimeObject(ZonedDateTime.now())).isTrue();
		assertThat(TimeUtil.isDateOrTimeObject(LocalDateTime.now())).isTrue();
		assertThat(TimeUtil.isDateOrTimeObject(LocalDate.now())).isTrue();
		assertThat(TimeUtil.isDateOrTimeObject(LocalTime.now())).isTrue();

		assertThat(TimeUtil.isDateOrTimeObject(null)).isFalse();
		assertThat(TimeUtil.isDateOrTimeObject("aaa")).isFalse();
		assertThat(TimeUtil.isDateOrTimeObject(1)).isFalse();
		assertThat(TimeUtil.isDateOrTimeObject(new BigDecimal("1234"))).isFalse();
	}

	@Test
	public void test_isDateOrTimeClass()
	{
		assertThat(TimeUtil.isDateOrTimeClass(java.util.Date.class)).isTrue();
		assertThat(TimeUtil.isDateOrTimeClass(java.sql.Timestamp.class)).isTrue();
		assertThat(TimeUtil.isDateOrTimeClass(Instant.class)).isTrue();
		assertThat(TimeUtil.isDateOrTimeClass(ZonedDateTime.class)).isTrue();
		assertThat(TimeUtil.isDateOrTimeClass(LocalDateTime.class)).isTrue();
		assertThat(TimeUtil.isDateOrTimeClass(LocalDate.class)).isTrue();
		assertThat(TimeUtil.isDateOrTimeClass(LocalTime.class)).isTrue();
		assertThat(TimeUtil.isDateOrTimeClass(XMLGregorianCalendar.class)).isTrue();

		assertThat(TimeUtil.isDateOrTimeClass(String.class)).isFalse();
		assertThat(TimeUtil.isDateOrTimeClass(Integer.class)).isFalse();
		assertThat(TimeUtil.isDateOrTimeClass(BigDecimal.class)).isFalse();
	}

	@Nested
	class trunc
	{
		private Date truncAndCheckMillis(final Date date, final String trunc)
		{
			Date dateTrunc = TimeUtil.trunc(date, trunc);
			final long dateTruncMillis = TimeUtil.truncToMillis(date, trunc);
			Assertions.assertEquals(dateTruncMillis, dateTrunc.getTime(), "trunc() and truncToMillis() shall match for '" + date + "' but trunc date was '" + dateTrunc + "'");
			return dateTrunc;
		}

		@SuppressWarnings("SameParameterValue")
		private void test_trunc(final String expectedStr, final String dateStr, final String truncType) throws ParseException
		{
			final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			final Date expected = dateFormat.parse(expectedStr);
			final Date date = dateFormat.parse(dateStr);

			final Date actual = truncAndCheckMillis(date, truncType);
			final String message = "Invalid date for expectedStr=" + expectedStr + ", dateStr=" + dateStr + ", truncType=" + truncType;
			Assertions.assertEquals(expected, actual, message);
		}

		@Test
		public void toSecond()
		{
			final GregorianCalendar cal = new GregorianCalendar();
			cal.setTimeInMillis(SystemTime.millis());
			cal.set(Calendar.SECOND, 42);
			cal.set(Calendar.MILLISECOND, 13);
			final Date date = new Date(cal.getTimeInMillis());

			cal.set(Calendar.MILLISECOND, 0);
			final Date dateTruncExpected = new Date(cal.getTimeInMillis());

			final Date dateTruncActual = truncAndCheckMillis(date, TimeUtil.TRUNC_SECOND);

			Assertions.assertEquals(dateTruncExpected, dateTruncActual, "Date " + date + " was not correctly truncated to seconds");
		}

		@Test
		public void toMinute()
		{
			final GregorianCalendar cal = new GregorianCalendar();
			cal.setTimeInMillis(SystemTime.millis());
			cal.set(Calendar.MINUTE, 12);
			cal.set(Calendar.SECOND, 42);
			cal.set(Calendar.MILLISECOND, 13);
			final Date date = new Date(cal.getTimeInMillis());

			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			final Date dateTruncExpected = new Date(cal.getTimeInMillis());

			final Date dateTruncActual = truncAndCheckMillis(date, TimeUtil.TRUNC_MINUTE);

			Assertions.assertEquals(dateTruncExpected, dateTruncActual, "Date " + date + " was not correctly truncated to minutes");
		}

		@Test
		public void toHour()
		{
			final GregorianCalendar cal = new GregorianCalendar();
			cal.setTimeInMillis(SystemTime.millis());
			cal.set(Calendar.HOUR_OF_DAY, 14);
			cal.set(Calendar.MINUTE, 12);
			cal.set(Calendar.SECOND, 42);
			cal.set(Calendar.MILLISECOND, 13);
			final Date date = new Date(cal.getTimeInMillis());

			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			final Date dateTruncExpected = new Date(cal.getTimeInMillis());

			final Date dateTruncActual = truncAndCheckMillis(date, TimeUtil.TRUNC_HOUR);

			Assertions.assertEquals(dateTruncExpected, dateTruncActual, "Date " + date + " was not correctly truncated to hours");
		}

		@Test
		public void toDay()
		{
			final GregorianCalendar cal = new GregorianCalendar();
			cal.setTimeInMillis(SystemTime.millis());
			cal.set(Calendar.YEAR, 2013);
			cal.set(Calendar.MONTH, 4);
			cal.set(Calendar.DAY_OF_MONTH, 3);
			cal.set(Calendar.HOUR_OF_DAY, 14);
			cal.set(Calendar.MINUTE, 12);
			cal.set(Calendar.SECOND, 42);
			cal.set(Calendar.MILLISECOND, 13);
			final Date date = new Date(cal.getTimeInMillis());

			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			final Date dateTruncExpected = new Date(cal.getTimeInMillis());

			final Date dateTruncActual = truncAndCheckMillis(date, TimeUtil.TRUNC_DAY);

			Assertions.assertEquals(dateTruncExpected, dateTruncActual, "Date " + date + " was not correctly truncated to day");
		}

		@Test
		public void toWeek()
		{
			final GregorianCalendar cal = new GregorianCalendar();
			cal.setTimeInMillis(SystemTime.millis());
			cal.set(Calendar.YEAR, 2013);
			cal.set(Calendar.MONTH, Calendar.MARCH);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 14);
			cal.set(Calendar.MINUTE, 12);
			cal.set(Calendar.SECOND, 42);
			cal.set(Calendar.MILLISECOND, 13);
			final Date date = new Date(cal.getTimeInMillis());

			cal.setFirstDayOfWeek(Calendar.MONDAY);
			cal.set(Calendar.MONTH, Calendar.FEBRUARY);
			cal.set(Calendar.DAY_OF_MONTH, 25);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			final Date dateTruncExpected = new Date(cal.getTimeInMillis());

			final Date dateTruncActual = truncAndCheckMillis(date, TimeUtil.TRUNC_WEEK);

			Assertions.assertEquals(dateTruncExpected, dateTruncActual, "Date " + date + " was not correctly truncated to week");
		}

		/**
		 * Make sure {@link TimeUtil#TRUNC_WEEK} is compliant with postgresql's <code>date_trunc('week', ...)</code>.
		 */
		@Test
		public void toWeek_CheckIsCompliantWithPostgresql() throws Exception
		{
			test_trunc("2015-06-22", "2015-06-28", TimeUtil.TRUNC_WEEK);
			//
			test_trunc("2015-06-29", "2015-06-29", TimeUtil.TRUNC_WEEK);
			test_trunc("2015-06-29", "2015-06-30", TimeUtil.TRUNC_WEEK);
			test_trunc("2015-06-29", "2015-07-01", TimeUtil.TRUNC_WEEK);
			test_trunc("2015-06-29", "2015-07-02", TimeUtil.TRUNC_WEEK);
			test_trunc("2015-06-29", "2015-07-03", TimeUtil.TRUNC_WEEK);
			test_trunc("2015-06-29", "2015-07-04", TimeUtil.TRUNC_WEEK);
			test_trunc("2015-06-29", "2015-07-05", TimeUtil.TRUNC_WEEK);
			//
			test_trunc("2015-07-06", "2015-07-06", TimeUtil.TRUNC_WEEK);
		}

		@Test
		public void toMonth()
		{
			final GregorianCalendar cal = new GregorianCalendar();
			cal.setTimeInMillis(SystemTime.millis());
			cal.set(Calendar.YEAR, 2013);
			cal.set(Calendar.MONTH, Calendar.MARCH);
			cal.set(Calendar.DAY_OF_MONTH, 25);
			cal.set(Calendar.HOUR_OF_DAY, 14);
			cal.set(Calendar.MINUTE, 12);
			cal.set(Calendar.SECOND, 42);
			cal.set(Calendar.MILLISECOND, 13);
			final Date date = new Date(cal.getTimeInMillis());

			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			final Date dateTruncExpected = new Date(cal.getTimeInMillis());

			final Date dateTruncActual = truncAndCheckMillis(date, TimeUtil.TRUNC_MONTH);

			Assertions.assertEquals(dateTruncExpected, dateTruncActual, "Date " + date + " was not correctly truncated to month");
		}

		@Test
		public void toYear()
		{
			final GregorianCalendar cal = new GregorianCalendar();
			cal.setTimeInMillis(SystemTime.millis());
			cal.set(Calendar.YEAR, 2013);
			cal.set(Calendar.MONTH, Calendar.MARCH);
			cal.set(Calendar.DAY_OF_MONTH, 25);
			cal.set(Calendar.HOUR_OF_DAY, 14);
			cal.set(Calendar.MINUTE, 12);
			cal.set(Calendar.SECOND, 42);
			cal.set(Calendar.MILLISECOND, 13);
			final Date date = new Date(cal.getTimeInMillis());

			cal.set(Calendar.MONTH, Calendar.JANUARY);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			final Date dateTruncExpected = new Date(cal.getTimeInMillis());

			final Date dateTruncActual = truncAndCheckMillis(date, TimeUtil.TRUNC_YEAR);

			Assertions.assertEquals(dateTruncExpected, dateTruncActual, "Date " + date + " was not correctly truncated to year");
		}
	}

	@Nested
	class asDate
	{
		@Test
		void ofTimestamp()
		{
			final Date nowDate = new Date();
			final Timestamp nowTimestamp = new Timestamp(nowDate.getTime());
			assertThat(nowTimestamp).isNotEqualTo(nowDate); // guard, just to make sure that none magically fixed timestamp

			assertThat(TimeUtil.asDate(nowTimestamp)).isEqualTo(nowDate);
		}
	}

	@Nested
	class asTimestamp
	{
		/**
		 * Make sure the {@link TimeUtil#asTimestamp(Date)} returns null for null. We do this check because a lot of BL depends on that.
		 */
		@Test
		void nullParam() {assertThat(TimeUtil.asTimestamp((Date)null)).isNull();}

		@Test
		void ofLocalTime()
		{
			final LocalTime localTime = LocalTime.parse("15:15");
			assertThat(TimeUtil.asTimestamp(localTime))
					.isEqualTo(Timestamp.valueOf(LocalDate.parse("1970-01-01").atTime(localTime)));
		}

		@Test
		void ofZonedDateTime()
		{
			SystemTime.setFixedTimeSource("2020-04-29T13:14:00+05:00");

			final ZonedDateTime zonedDateTime = LocalDate.parse("2020-04-30")
					.atTime(LocalTime.of(23, 59, 59))
					.atZone(ZoneId.of("UTC-8"));

			final Timestamp timestamp = TimeUtil.asTimestamp(zonedDateTime);
			final LocalDate localDate = TimeUtil.asLocalDate(timestamp, ZoneId.of("UTC-8"));
			assertThat(localDate).isEqualTo("2020-04-30");
		}

		@Test
		void ofInstantAndOrgId()
		{
			final InstantAndOrgId instantAndOrgId = InstantAndOrgId.ofInstant(Instant.parse("2022-06-03T13:14:15.00Z"), OrgId.MAIN);
			assertThat(TimeUtil.asTimestamp(instantAndOrgId)).isEqualTo(instantAndOrgId.toTimestamp());
		}
	}

	@Nested
	class asLocalDate
	{
		@Test
		public void ofXMLGregorianCalendar() throws DatatypeConfigurationException
		{
			final Timestamp timestamp = Timestamp.valueOf("2018-10-04 15:43:10.1");

			final GregorianCalendar c = new GregorianCalendar();
			c.setTime(timestamp);
			final XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

			// invoke the method under test
			final LocalDate result = TimeUtil.asLocalDate(xmlGregorianCalendar);

			assertThat(result.getYear()).isEqualTo(2018);
			assertThat(result.getMonth()).isEqualTo(Month.OCTOBER);
			assertThat(result.getDayOfMonth()).isEqualTo(4);
		}

		@Test
		void ofLocalDateAndOrgId()
		{
			final LocalDateAndOrgId localDateAndOrgId = LocalDateAndOrgId.ofLocalDate(LocalDate.parse("2022-03-04"), OrgId.MAIN);
			assertThat(TimeUtil.asLocalDate(localDateAndOrgId)).isEqualTo("2022-03-04");
		}
	}

	@Nested
	public class isOverlapping
	{
		@Nullable
		Timestamp ts(@Nullable final String localDateStr)
		{
			return localDateStr != null
					? Timestamp.from(LocalDate.parse(localDateStr).atStartOfDay().atZone(SystemTime.zoneId()).toInstant())
					: null;
		}

		@Test
		void a__a__b________b()
		{
			assertThat(TimeUtil.isOverlapping(ts("2023-10-01"), ts("2023-10-02"), ts("2023-10-05"), ts("2023-10-10")))
					.isFalse();
		}

		@Test
		void a__ab__________b()
		{
			assertThat(TimeUtil.isOverlapping(ts("2023-10-01"), ts("2023-10-02"), ts("2023-10-02"), ts("2023-10-10")))
					.isFalse();
		}

		@Test
		void a__b__a________b()
		{
			assertThat(TimeUtil.isOverlapping(ts("2023-10-01"), ts("2023-10-05"), ts("2023-10-02"), ts("2023-10-10")))
					.isTrue();
		}

		@Test
		void b__a__a________b()
		{
			assertThat(TimeUtil.isOverlapping(ts("2023-10-01"), ts("2023-10-10"), ts("2023-10-02"), ts("2023-10-03")))
					.isTrue();
		}

		@Test
		void b_______a___b__a()
		{
			assertThat(TimeUtil.isOverlapping(ts("2023-10-01"), ts("2023-10-07"), ts("2023-10-05"), ts("2023-10-10")))
					.isTrue();
		}

		@Test
		void b__________ba__a()
		{
			assertThat(TimeUtil.isOverlapping(ts("2023-10-01"), ts("2023-10-07"), ts("2023-10-07"), ts("2023-10-10")))
					.isFalse();
		}

		@Test
		void b________b__a__a()
		{
			assertThat(TimeUtil.isOverlapping(ts("2023-10-01"), ts("2023-10-05"), ts("2023-10-07"), ts("2023-10-10")))
					.isFalse();
		}
	}

	@ParameterizedTest(name = "JVM.zoneId={0}")
	@ValueSource(strings = {
			"Pacific/Midway", // -11:00
			"US/Alaska", // -09:00,
			"America/Jamaica", // -05:00
			"Atlantic/Azores", // -01:00
			"UTC",
			"Europe/Berlin",  // +01:00
			"Europe/Bucharest", // +02:00
			"Asia/Kolkata", // +05:30
			"Asia/Tokyo", // +09:00
			"Pacific/Kiritimati", // +14:00
	})
	void parseLocalDateAsTimestamp_asLocalDate(final String timezone)
	{
		TimeZone.setDefault(TimeZone.getTimeZone(timezone));
		//System.out.println("JVM TimeZone: " + TimeZone.getDefault());
		assertThat(TimeZone.getDefault()).isEqualTo(TimeZone.getTimeZone(timezone));

		final Timestamp timestamp = TimeUtil.parseLocalDateAsTimestamp("2024-03-30");
		assertThat(TimeUtil.asLocalDateNonNull(timestamp)).isEqualTo("2024-03-30");
	}
}

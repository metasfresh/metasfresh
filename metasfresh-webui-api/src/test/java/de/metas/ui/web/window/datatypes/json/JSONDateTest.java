package de.metas.ui.web.window.datatypes.json;

import java.util.Date;
import java.util.TimeZone;
import java.util.function.Supplier;

import org.adempiere.util.time.FixedTimeSource;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.TimeUtil;
import org.junit.Assert;
import org.junit.Test;

import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class JSONDateTest
{
	// private final TimeZone testTimeZone = TimeZone.getTimeZone("Europe/Berlin");
	// private final TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");

	@Test
	public void testConvertDateTime()
	{
		testConvertDateTime( // winter
				"Europe/Berlin" // CET
				, "2017-02-24T00:02:03.004+01:00" // expected
				, "2017-02-24T01:02:03.004+02:00" // test
		);
		testConvertDateTime( // summer
				"Europe/Berlin" // CET
				, "2017-08-24T00:02:03.004+02:00" // expected
				, "2017-08-24T01:02:03.004+03:00" // test
		);

		testConvertDateTime(
				"Europe/Berlin" // CET
				, "2016-08-11T01:02:03.004+02:00" // expected
				, "2016-08-11T01:02:03.004+02:00" // test
		);
	}

	@Test
	public void testConvertDate()
	{
		testConvertDate(
				"Europe/Berlin" // CET
				, "2017-01-03T00:00:00.000+01:00" // expected
				, "2017-01-03T00:00:00.000+02:00" // test
		);
		testConvertDate(
				"Europe/Berlin" // CET
				, "2017-01-03T00:00:00.000+01:00" // expected
				, "2017-01-03T00:00:00.000Z" // test
		);
	}

	private static final void testConvertDateTime(final String systemTimeZoneStr, final String expectedDateStr, final String testDateStr)
	{
		final TimeZone timeZone = TimeZone.getTimeZone(systemTimeZoneStr);
		final String actualDateStr = withTimeZone(timeZone, () -> {
			final Date actualDate = JSONDate.fromJson(testDateStr, DocumentFieldWidgetType.DateTime);
			return JSONDate.toJson(actualDate);
		}).get();

		final String msg = "testConvertDateTime"
				+ "\n testDateStr: " + testDateStr
				+ "\n timeZoneStr: " + systemTimeZoneStr;
		Assert.assertEquals(msg, expectedDateStr, actualDateStr);
	}

	private static final void testConvertDate(final String systemTimeZoneStr, final String expectedDateStr, final String testDateStr)
	{
		final TimeZone timeZone = TimeZone.getTimeZone(systemTimeZoneStr);
		final String actualDateStr = withTimeZone(timeZone, () -> {
			final Date actualDate = JSONDate.fromJson(testDateStr, DocumentFieldWidgetType.Date);
			return JSONDate.toJson(actualDate);
		}).get();

		final String msg = "testConvertDate"
				+ "\n testDateStr: " + testDateStr
				+ "\n timeZoneStr: " + systemTimeZoneStr;
		Assert.assertEquals(msg, expectedDateStr, actualDateStr);
	}

	@Test
	public void test_getCurrentTimeZoneAsJson()
	{
		final TimeZone timeZone = TimeZone.getTimeZone("Europe/Berlin");

		{
			final Date winterDate = TimeUtil.getDay(2017, 02, 01);
			final String jsonWinterTimeZone = withFixedTimeSourceAndTimeZone(winterDate, timeZone, () -> JSONDate.getCurrentTimeZoneAsJson());
			Assert.assertEquals("Winter timezone", "+01:00", jsonWinterTimeZone);
		}

		final Date summerDate = TimeUtil.getDay(2017, 07, 01);
		final String jsonSummerTimeZone = withFixedTimeSourceAndTimeZone(summerDate, timeZone, () -> JSONDate.getCurrentTimeZoneAsJson());
		Assert.assertEquals("Summer timezone", "+02:00", jsonSummerTimeZone);
	}

	// @Test
	// public void test()
	// {
	//
	// withTimeZone(TimeZone.getTimeZone("Europe/Berlin"), () -> {
	// final String jsonDateStr = "2017-01-03T00:00:00.000-02:00";
	// final Date jsonDate = JSONDate.fromJson(jsonDateStr, DocumentFieldWidgetType.DateTime);
	//
	// final Calendar cal = Calendar.getInstance(utcTimeZone);
	// cal.setTimeInMillis(jsonDate.getTime());
	//
	// System.out.println("UTC timezone: " + utcTimeZone);
	// System.out.println("jsonDate str: " + jsonDateStr);
	// System.out.println("jsonDate: " + jsonDate);
	// System.out.println("jsonDate str (UTC): " + toUTCString(jsonDate));
	//
	// System.out.println("calendar: y=" + cal.get(Calendar.YEAR));
	// System.out.println("calendar: m=" + cal.get(Calendar.MONTH) + 1);
	// System.out.println("calendar: d=" + cal.get(Calendar.DAY_OF_YEAR));
	//
	// //
	// System.out.println("-------------------------------------------------");
	// {
	// final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(JSONDate.DATE_PATTEN);
	// final OffsetDateTime offsetDateTime = OffsetDateTime.parse(jsonDateStr, formatter);
	// final Date date = TimeUtil.getDay(offsetDateTime.getYear(), offsetDateTime.getMonthValue(), offsetDateTime.getDayOfMonth());
	// System.out.println("offsetDateTime: " + offsetDateTime);
	// System.out.println("date: " + date);
	// }
	//
	// return null;
	// })
	// .get();
	// }
	//
	// private final String toUTCString(final Date date)
	// {
	// final SimpleDateFormat df = new SimpleDateFormat(JSONDate.DATE_PATTEN);
	// df.setTimeZone(utcTimeZone);
	// return df.format(date);
	// }
	//
	// private static final Date createDate(final int year, final int month, final int day, final int hour, final int minute, final int second, final int millis)
	// {
	// final Calendar cal = Calendar.getInstance();
	// cal.set(year, month, day, hour, minute, second);
	// cal.set(Calendar.MILLISECOND, millis);
	// //cal.setTimeZone(testTimeZone);
	// final Date date = cal.getTime();
	// return date;
	// }
	//
	// private final Date fromJson(final String dateStr, final DocumentFieldWidgetType widgetType)
	// {
	// return withTestingTimeZone(() -> JSONDate.fromJson(dateStr, widgetType)).get();
	// }
	//
	// private final <V> Supplier<V> withTestingTimeZone(final Supplier<V> supplier)
	// {
	// return withTimeZone(testTimeZone, supplier);
	// }
	//
	// private static final <V> Supplier<V> withTimeZone(final String timeZoneStr, final Supplier<V> supplier)
	// {
	// return withTimeZone(TimeZone.getTimeZone(timeZoneStr), supplier);
	// }

	private static final <V> Supplier<V> withTimeZone(final TimeZone timeZone, final Supplier<V> supplier)
	{
		return () -> {
			final TimeZone timeZoneBackup = TimeZone.getDefault();
			try
			{
				TimeZone.setDefault(timeZone);

				return supplier.get();
			}
			finally
			{
				TimeZone.setDefault(timeZoneBackup);
			}
		};
	}

	private final <V> Supplier<V> withFixedTimeSource(final Date fixedDate, final Supplier<V> supplier)
	{
		return () -> {
			SystemTime.setTimeSource(new FixedTimeSource(fixedDate));
			try
			{
				return supplier.get();
			}
			finally
			{
				SystemTime.resetTimeSource();
			}
		};
	}

	private final <V> V withFixedTimeSourceAndTimeZone(final Date fixedDate, final TimeZone fixedTimeZone, final Supplier<V> supplier)
	{
		Supplier<V> supplierEffective = withFixedTimeSource(fixedDate, supplier);
		supplierEffective = withTimeZone(fixedTimeZone, supplierEffective);

		return supplierEffective.get();
	}

}

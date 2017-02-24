package de.metas.ui.web.window.datatypes.json;

import java.util.Calendar;
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
	private final TimeZone testTimeZone = TimeZone.getTimeZone("Europe/Berlin");

	@Test
	public void test_dates_convert()
	{
		final Date expectedDateAndTime = createDate(2016, Calendar.AUGUST, 11, 1, 2, 3, 4);
		final Date expectedDate = createDate(2016, Calendar.AUGUST, 11, 0, 0, 0, 0);

		final String dateStr = JSONDate.toJson(expectedDateAndTime, testTimeZone);
		Assert.assertEquals("2016-08-11T01:02:03.004+02:00", dateStr);

		Assert.assertEquals(expectedDateAndTime, fromJson(dateStr, null));
		Assert.assertEquals(expectedDateAndTime, fromJson(dateStr, DocumentFieldWidgetType.DateTime));
		Assert.assertEquals(expectedDate, fromJson(dateStr, DocumentFieldWidgetType.Date));
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
		String jsonSummerTimeZone = withFixedTimeSourceAndTimeZone(summerDate, timeZone, () -> JSONDate.getCurrentTimeZoneAsJson());
		Assert.assertEquals("Summer timezone", "+02:00", jsonSummerTimeZone);
	}

	private final Date createDate(final int year, final int month, final int day, final int hour, final int minute, final int second, final int millis)
	{
		final Calendar cal = Calendar.getInstance();
		cal.set(year, month, day, hour, minute, second);
		cal.set(Calendar.MILLISECOND, millis);
		cal.setTimeZone(testTimeZone);
		final Date date = cal.getTime();
		return date;
	}

	private final Date fromJson(final String dateStr, final DocumentFieldWidgetType widgetType)
	{
		return withTestingTimeZone(() -> JSONDate.fromJson(dateStr, widgetType)).get();
	}

	private final <V> Supplier<V> withTestingTimeZone(final Supplier<V> supplier)
	{
		return withTimeZone(testTimeZone, supplier);
	}

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

/*
 * #%L
 * de-metas-camel-edi
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.edi.esb.commons;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

public class Util_createCalendarDate_Test
{
	private static String originalTimeZone;

	@BeforeAll
	static void beforeAll()
	{
		originalTimeZone = TimeZone.getDefault().getID();
		TimeZone.setDefault(TimeZone.getTimeZone("CET"));
	}

	@AfterAll
	static void afterAll()
	{
		TimeZone.setDefault(TimeZone.getTimeZone(originalTimeZone));
	}


	@Test
	void createCalendarDate_WinterTime_EndOfDay()
	{
		final var result = Util.createCalendarDate("20201130000000", false, "yyyyMMddHHmmss", Util.TimeIfNotSpecified.END_OF_DAY);
		assertThat(result)
				.extracting(l -> l.getYear(), l -> l.getMonth(), l -> l.getDay(), l -> l.getHour(), l -> l.getMinute(), l -> l.getTimezone())
				.containsExactly(2020, 11, 30, 23, 59, 60);
	}

	@Test
	void createCalendarDate_WinterTime_StartOfDay()
	{
		final var result = Util.createCalendarDate("20201130000000", false, "yyyyMMddHHmmss", Util.TimeIfNotSpecified.START_OF_DAY);
		assertThat(result)
				.extracting(l -> l.getYear(), l -> l.getMonth(), l -> l.getDay(), l -> l.getHour(), l -> l.getMinute(), l -> l.getTimezone())
				.containsExactly(2020, 11, 30, 0, 0, 60);
	}

	@Test
	void createCalendarDate_SummerTime_EndOfDay()
	{
		final var result = Util.createCalendarDate("20210330000000", false, "yyyyMMddHHmmss", Util.TimeIfNotSpecified.END_OF_DAY);
		assertThat(result)
				.extracting(l -> l.getYear(), l -> l.getMonth(), l -> l.getDay(), l -> l.getHour(), l -> l.getMinute(), l -> l.getTimezone())
				.containsExactly(2021, 03, 30, 23, 59, 120);
	}

	@Test
	void createCalendarDate_SummerTime_StartOfDay()
	{
		final var result = Util.createCalendarDate("20210330000000", false, "yyyyMMddHHmmss", Util.TimeIfNotSpecified.START_OF_DAY);
		assertThat(result)
				.extracting(l -> l.getYear(), l -> l.getMonth(), l -> l.getDay(), l -> l.getHour(), l -> l.getMinute(), l -> l.getTimezone())
				.containsExactly(2021, 03, 30, 0, 0, 120);
	}
}

/*
 * #%L
 * procurement-webui-backend
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.procurement.webui.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.threeten.extra.YearWeek;

import java.time.LocalDate;
import java.time.temporal.IsoFields;

@UtilityClass
public class YearWeekUtil
{
	/**
	 * @param str ww.YYYY format
	 */
	public static YearWeek parse(@NonNull final String str)
	{
		final String[] parts = str.split("\\.");
		if (parts.length != 2)
		{
			throw new IllegalArgumentException("Invalid format: " + str);
		}

		try
		{
			final int weekNo = Integer.parseInt(parts[0]);
			final int year = Integer.parseInt(parts[1]);
			return YearWeek.of(year, weekNo);
		}
		catch (final Exception ex)
		{
			throw new IllegalArgumentException("Invalid format: " + str, ex);
		}
	}

	public static YearWeek ofLocalDate(@NonNull final LocalDate date)
	{
		return YearWeek.of(date.get(IsoFields.WEEK_BASED_YEAR), date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR));
	}

	public static String toJsonString(@NonNull final YearWeek yearWeek)
	{
		return (yearWeek.getWeek() <= 9 ? "0" : "") + yearWeek.getWeek()
				+ "."
				+ yearWeek.getYear();
	}

	public static String toDisplayName(@NonNull final YearWeek yearWeek)
	{
		return "KW" + (yearWeek.getWeek() <= 9 ? "0" : "") + yearWeek.getWeek();
	}
}

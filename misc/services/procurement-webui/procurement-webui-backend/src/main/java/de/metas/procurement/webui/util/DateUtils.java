package de.metas.procurement.webui.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

/*
 * #%L
 * de.metas.procurement.webui
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@UtilityClass
public final class DateUtils
{
	@Nullable
	public static LocalDate toLocalDate(@Nullable final java.sql.Date date)
	{
		return date != null
				? date.toLocalDate()
				: null;
	}

	@NonNull
	public static java.sql.Date toSqlDate(@NonNull final LocalDate date)
	{
		return java.sql.Date.valueOf(date);
	}

	public static boolean between(
			@NonNull final LocalDate date,
			@Nullable final LocalDate dateFrom,
			@Nullable final LocalDate dateTo)
	{
		if (dateFrom != null && dateFrom.compareTo(date) > 0)
		{
			return false;
		}

		return dateTo == null || date.compareTo(dateTo) <= 0;
	}

	public static String getDayName(@NonNull final LocalDate date, @NonNull final Locale locale)
	{
		return date.format(DateTimeFormatter.ofPattern("EEEE", locale));
	}

	public static ArrayList<LocalDate> getDaysList(final LocalDate startDate, final LocalDate endDate)
	{
		final ArrayList<LocalDate> result = new ArrayList<>();
		for (LocalDate date = startDate; date.compareTo(endDate) <= 0; date = date.plusDays(1))
		{
			result.add(date);
		}

		return result;
	}

}

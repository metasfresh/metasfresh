package de.metas.vertical.pharma.securpharm.client.schema;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-pharma.securpharm
 * %%
 * Copyright (C) 2019 metas GmbH
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

@ToString
@EqualsAndHashCode(exclude = { "yyMMdd" })
public final class JsonExpirationDate
{
	@JsonCreator
	public static JsonExpirationDate ofJson(@NonNull final String json)
	{
		if (json.contains("-"))
		{
			final LocalDate localDate = LocalDate.parse(json, FORMAT_yyyy_MM_dd);
			final String yyMMdd = toYYMMDD(localDate);
			return new JsonExpirationDate(yyMMdd, localDate);
		}
		else
		{
			final String yyMMdd = json;
			final LocalDate localDate = toLocalDate(yyMMdd);
			return new JsonExpirationDate(yyMMdd, localDate);
		}
	}

	public static JsonExpirationDate ofLocalDate(@NonNull final LocalDate date)
	{
		return new JsonExpirationDate(toYYMMDD(date), date);
	}

	public static JsonExpirationDate ofNullableLocalDate(@Nullable final LocalDate date)
	{
		return date != null ? ofLocalDate(date) : null;
	}

	private static final DateTimeFormatter FORMAT_yyMMdd = DateTimeFormatter.ofPattern("yyMMdd");
	private static final DateTimeFormatter FORMAT_yyMM = DateTimeFormatter.ofPattern("yyMM");
	private static final DateTimeFormatter FORMAT_yyyy_MM_dd = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private final String yyMMdd;
	private final LocalDate localDate;

	private JsonExpirationDate(@NonNull final String yyMMdd, @NonNull final LocalDate localDate)
	{
		this.yyMMdd = yyMMdd;
		this.localDate = localDate;
	}

	private static LocalDate toLocalDate(@NonNull final String yyMMdd)
	{
		if (yyMMdd.length() != 6)
		{
			throw new AdempiereException("Invalid expire date format: " + yyMMdd);
		}

		final String dd = yyMMdd.substring(4, 6);
		if (dd.endsWith("00")) // last day of month indicator
		{
			final String yyMM = yyMMdd.substring(0, 4);
			return YearMonth.parse(yyMM, FORMAT_yyMM).atEndOfMonth();
		}
		else
		{
			return LocalDate.parse(yyMMdd, FORMAT_yyMMdd);
		}
	}

	private static String toYYMMDD(@NonNull final LocalDate localDate)
	{
		if (TimeUtil.isLastDayOfMonth(localDate))
		{
			return FORMAT_yyMM.format(localDate) + "00";
		}
		else
		{
			return FORMAT_yyMMdd.format(localDate);
		}
	}

	@JsonValue
	public String toJson()
	{
		return toYYMMDDString();
	}

	public String toYYMMDDString()
	{
		return yyMMdd;
	}

	public LocalDate toLocalDate()
	{
		return localDate;
	}

	public Timestamp toTimestamp()
	{
		return TimeUtil.asTimestamp(toLocalDate());
	}
}

package de.metas.vertical.pharma.securpharm.model.schema;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import org.adempiere.exceptions.AdempiereException;

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
@EqualsAndHashCode
public final class ExpirationDate
{
	@JsonValue
	public static ExpirationDate ofString(@NonNull final String yyMMdd)
	{
		return new ExpirationDate(yyMMdd, toLocalDate(yyMMdd));
	}

	public static ExpirationDate ofLocalDate(@NonNull final LocalDate date)
	{
		return new ExpirationDate(toYYMMDD(date), date);
	}

	private static final DateTimeFormatter FORMAT_yyMMdd = DateTimeFormatter.ofPattern("yyMMdd");
	private static final DateTimeFormatter FORMAT_yyMM = DateTimeFormatter.ofPattern("yyMM");

	private final String yyMMdd;
	private final LocalDate localDate;

	private ExpirationDate(@NonNull final String yyMMdd, @NonNull final LocalDate localDate)
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
		if (dd.endsWith("00"))
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
		final LocalDate lastDayOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth());
		if (localDate.equals(lastDayOfMonth))
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
}

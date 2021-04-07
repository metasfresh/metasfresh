/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs;

import de.metas.common.util.CoalesceUtil;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@UtilityClass
public class DataTableUtil
{
	/**
	 * Used in case no RecordIdentifier is given.
	 */
	private static int recordIdentifierFallback = 0;

	public String extractRecordIdentifier(
			@NonNull final Map<String, String> dataTableRow,
			@NonNull final String fallbackPrefix)
	{
		return CoalesceUtil.coalesceSuppliers(() -> dataTableRow.get(StepDefConstants.TABLECOLUMN_IDENTIFIER), () -> DataTableUtil.createFallbackRecordIdentifier(fallbackPrefix));
	}

	private String createFallbackRecordIdentifier(@NonNull final String prefix)
	{
		return prefix + '_' + (++recordIdentifierFallback);
	}

	public int extractIntForColumnName(
			@NonNull final Map<String, String> dataTableRow,
			@NonNull final String columnName)
	{
		final String string = extractStringForColumnName(dataTableRow, columnName);
		try
		{
			return Integer.parseInt(string);
		}
		catch (final NumberFormatException e)
		{
			throw new AdempiereException("Can't parse value=" + string + " of columnName=" + columnName, e).appendParametersToMessage()
					.setParameter("dataTableRow", dataTableRow);
		}
	}

	@Nullable
	public String extractStringOrNullForColumnName(@NonNull final Map<String, String> dataTableRow, @NonNull final String columnName)
	{
		if (!dataTableRow.containsKey(columnName))
		{
			throw new AdempiereException("Missing column for columnName=" + columnName).appendParametersToMessage()
					.setParameter("dataTableRow", dataTableRow);
		}
		return dataTableRow.get(columnName);
	}

	@NonNull
	public String extractStringForColumnName(@NonNull final Map<String, String> dataTableRow, @NonNull final String columnName)
	{
		final String string = dataTableRow.get(columnName);
		if (Check.isBlank(string))
		{
			throw new AdempiereException("Missing value for columnName=" + columnName).appendParametersToMessage()
					.setParameter("dataTableRow", dataTableRow);
		}
		return string;
	}

	public int extractIntForIndex(
			@NonNull final List<String> dataTableRow,
			final int index)
	{
		final String string = extractStringForIndex(dataTableRow, index);
		try
		{
			return Integer.parseInt(string);
		}
		catch (final NumberFormatException e)
		{
			throw new AdempiereException("Can't parse value=" + string + " of index=" + index, e).appendParametersToMessage()
					.setParameter("dataTableRow", dataTableRow);
		}
	}

	public static Instant extractInstantForColumnName(final Map<String, String> dataTableRow, final String columnName)
	{
		final String string = extractStringForColumnName(dataTableRow, columnName);
		try
		{
			return Instant.parse(string);
		}
		catch (final DateTimeParseException e)
		{
			throw new AdempiereException("Can't parse value=" + string + " of columnName=" + columnName, e).appendParametersToMessage()
					.setParameter("dataTableRow", dataTableRow);
		}
	}

	public Instant extractInstantForIndex(final List<String> dataTableRow, final int index)
	{
		final String string = extractStringForIndex(dataTableRow, index);
		try
		{
			return Instant.parse(string);
		}
		catch (final DateTimeParseException e)
		{
			throw new AdempiereException("Can't parse value=" + string + " of index=" + index, e).appendParametersToMessage()
					.setParameter("dataTableRow", dataTableRow);
		}
	}

	@Nullable
	public static ZonedDateTime extractZonedDateTimeOrNullForColumnName(final Map<String, String> dataTableRow, final String columnName)
	{
		final String string = extractStringOrNullForColumnName(dataTableRow, columnName);
		try
		{
			return Check.isBlank(string) ? null : ZonedDateTime.parse(string);
		}
		catch (final DateTimeParseException e)
		{
			throw new AdempiereException("Can't parse value=" + string + " of columnName=" + columnName, e).appendParametersToMessage()
					.setParameter("dataTableRow", dataTableRow);
		}
	}

	public static ZonedDateTime extractZonedDateTimeForColumnName(final Map<String, String> dataTableRow, final String columnName)
	{
		final String string = extractStringForColumnName(dataTableRow, columnName);
		try
		{
			return ZonedDateTime.parse(string);
		}
		catch (final DateTimeParseException e)
		{
			throw new AdempiereException("Can't parse value=" + string + " of columnName=" + columnName, e).appendParametersToMessage()
					.setParameter("dataTableRow", dataTableRow);
		}
	}

	public static Timestamp extractDateTimestampForColumnName(final Map<String, String> dataTableRow, final String columnName)
	{
		final String string = extractStringForColumnName(dataTableRow, columnName);
		try
		{
			return TimeUtil.parseTimestamp(string);
		}
		catch (final DateTimeParseException e)
		{
			throw new AdempiereException("Can't parse value=" + string + " of columnName=" + columnName, e).appendParametersToMessage()
					.setParameter("dataTableRow", dataTableRow);
		}
	}

	public BigDecimal extractBigDecimalForIndex(final List<String> dataTableRow, final int index)
	{
		final String string = extractStringForIndex(dataTableRow, index);
		try
		{
			return new BigDecimal(string);
		}
		catch (final NumberFormatException e)
		{
			throw new AdempiereException("Can't parse value=" + string + " of index=" + index, e).appendParametersToMessage()
					.setParameter("dataTableRow", dataTableRow);
		}
	}

	@Nullable
	public static BigDecimal extractBigDecimalOrNullForColumnName(final Map<String, String> dataTableRow, final String columnName)
	{

		final String string = extractStringOrNullForColumnName(dataTableRow, columnName);

		try
		{
			return Check.isBlank(string) ? null : new BigDecimal(string);
		}
		catch (final NumberFormatException e)
		{
			throw new AdempiereException("Can't parse value=" + string + " of columnName=" + columnName, e).appendParametersToMessage()
					.setParameter("dataTableRow", dataTableRow);
		}
	}
	public static BigDecimal extractBigDecimalForColumnName(final Map<String, String> dataTableRow, final String columnName)
	{
		final String string = extractStringForColumnName(dataTableRow, columnName);
		try
		{
			return new BigDecimal(string);
		}
		catch (final NumberFormatException e)
		{
			throw new AdempiereException("Can't parse value=" + string + " of columnName=" + columnName, e).appendParametersToMessage()
					.setParameter("dataTableRow", dataTableRow);
		}
	}

	public static boolean extractBooleanForColumnName(@NonNull final Map<String, String> dataTableRow, @NonNull final String columnName)
	{
		final String string = extractStringForColumnName(dataTableRow, columnName);

		final Boolean result = StringUtils.toBoolean(string, null);
		if (result == null)
		{
			throw new AdempiereException("Can't parse value=" + string + " of columnName=" + columnName).appendParametersToMessage()
					.setParameter("dataTableRow", dataTableRow);
		}
		return result;
	}

	public String extractStringForIndex(final List<String> dataTableRow, final int index)
	{
		final String string = dataTableRow.get(index);
		if (Check.isBlank(string))
		{
			throw new AdempiereException("missing value for index=" + index).appendParametersToMessage()
					.setParameter("dataTableRow", dataTableRow);
		}
		return string;
	}

	@Nullable
	public String extractValueOrNull(@Nullable final String value)
	{
		if (value == null || value.equals("null"))
			return null;

		return value;
	}
}

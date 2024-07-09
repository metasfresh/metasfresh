/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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
import de.metas.common.util.EmptyUtil;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

@UtilityClass
public class DataTableUtil
{
	/**
	 * Used in case no RecordIdentifier is given.
	 */
	private static int recordIdentifierFallback = 0;

	public static final String NULL_STRING = "null";

	/**
	 * @param fallbackPrefix if the given dataTableRow has no {@value StepDefConstants#TABLECOLUMN_IDENTIFIER} column,
	 *                       then use {@link #createFallbackRecordIdentifier(String)} with this prefix.
	 */
	public String extractRecordIdentifier(
			@NonNull final Map<String, String> dataTableRow,
			@NonNull final String fallbackPrefix)
	{
		return CoalesceUtil.coalesceSuppliersNotNull(
				() -> dataTableRow.get(TABLECOLUMN_IDENTIFIER),
				() -> createFallbackRecordIdentifier(fallbackPrefix));
	}

	public String extractRecordIdentifier(
			@NonNull final Map<String, String> dataTableRow,
			@NonNull final String columnNamePrefix,
			@NonNull final String fallbackPrefix)
	{
		return CoalesceUtil.coalesceSuppliersNotNull(
				() -> dataTableRow.get(TABLECOLUMN_IDENTIFIER),
				() -> dataTableRow.get(columnNamePrefix + "." + TABLECOLUMN_IDENTIFIER),
				() -> createFallbackRecordIdentifier(fallbackPrefix));
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
	public Integer extractIntegerOrNullForColumnName(
			@NonNull final Map<String, String> dataTableRow,
			@NonNull final String columnName)
	{
		final String string = extractStringOrNullForColumnName(dataTableRow, columnName);

		try
		{
			return Check.isBlank(string) ? null : Integer.parseInt(string);
		}
		catch (final NumberFormatException e)
		{
			throw new AdempiereException("Can't parse value=" + string + " of columnName=" + columnName, e).appendParametersToMessage()
					.setParameter("dataTableRow", dataTableRow);
		}
	}

	public int extractIntOrZeroForColumnName(
			@NonNull final Map<String, String> dataTableRow,
			@NonNull final String columnName)
	{
		return extractIntOrDefaultForColumnName(dataTableRow, columnName, 0);
	}

	public int extractIntOrMinusOneForColumnName(
			@NonNull final Map<String, String> dataTableRow,
			@NonNull final String columnName)
	{
		return extractIntOrDefaultForColumnName(dataTableRow, columnName, -1);
	}

	private int extractIntOrDefaultForColumnName(
			final @NonNull Map<String, String> dataTableRow,
			final @NonNull String columnName,
			final int defaultValue)
	{
		final String string = extractStringOrNullForColumnName(dataTableRow, columnName);
		if (EmptyUtil.isBlank(string))
		{
			return defaultValue;
		}
		return extractIntForColumnName(dataTableRow, columnName);
	}

	@Nullable
	public String extractStringOrNullForColumnName(@NonNull final Map<String, String> dataTableRow, @NonNull final String columnName)
	{
		// it's OK for "OPT." columns to be missing!
		// TODO: add some dedicated methods to distinguish between OPT and not-OPT that can be null
		// if (!dataTableRow.containsKey(columnName))
		// {
		// 	throw new AdempiereException("Missing column for columnName=" + columnName).appendParametersToMessage()
		// 			.setParameter("dataTableRow", dataTableRow);
		// }

		if (NULL_STRING.equals(dataTableRow.get(columnName)))
		{
			return null;
		}

		return dataTableRow.get(columnName);
	}

	@Nullable
	public String extractNullableStringForColumnName(@NonNull final Map<String, String> dataTableRow, @NonNull final String columnName)
	{
		return dataTableRow.get(columnName);
	}

	@Nullable
	public String nullToken2Null(@NonNull final String value)
	{
		return NULL_STRING.equals(value) ? null : value;
	}

	@NonNull
	public String extractStringForColumnName(@NonNull final Map<String, String> dataTableRow, @NonNull final String columnName)
	{
		final String string = dataTableRow.get(columnName);
		if (string == null || Check.isBlank(string))
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
	public static LocalDate extractLocalDateOrNullForColumnName(
			@NonNull final Map<String, String> dataTableRow,
			@NonNull final String columnName)
	{
		final String string = extractStringOrNullForColumnName(dataTableRow, columnName);
		try
		{
			return Check.isBlank(string) ? null : LocalDate.parse(string);
		}
		catch (final DateTimeParseException e)
		{
			throw new AdempiereException("Can't parse value=" + string + " of columnName=" + columnName, e).appendParametersToMessage()
					.setParameter("dataTableRow", dataTableRow);
		}
	}

	@NonNull
	public static LocalDate extractLocalDateForColumnName(
			@NonNull final Map<String, String> dataTableRow,
			@NonNull final String columnName)
	{
		final String string = extractStringForColumnName(dataTableRow, columnName);
		try
		{
			return LocalDate.parse(string);
		}
		catch (final DateTimeParseException e)
		{
			throw new AdempiereException("Can't parse value=" + string + " of columnName=" + columnName, e).appendParametersToMessage()
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

	@Deprecated
	public static Timestamp extractDateTimestampForColumnName(final Map<String, String> dataTableRow, final String columnName)
	{
		return extractLocalDateTimestampForColumnName(dataTableRow, columnName);
	}

	@Deprecated
	@Nullable
	public static Timestamp extractDateTimestampForColumnNameOrNull(final Map<String, String> dataTableRow, final String columnName)
	{
		return extractLocalDateTimestampForColumnNameOrNull(dataTableRow, columnName);
	}

	public static Timestamp extractLocalDateTimestampForColumnName(final Map<String, String> row, final String columnName)
	{
		final String valueStr = extractStringForColumnName(row, columnName);
		return parseLocalDateAsTimestamp(valueStr, columnName, row);
	}

	public static Timestamp extractLocalDateTimestampForColumnNameOrNull(final Map<String, String> row, final String columnName)
	{
		String valueStr = extractStringOrNullForColumnName(row, columnName);
		valueStr = StringUtils.trimBlankToNull(valueStr);
		return valueStr != null ? parseLocalDateAsTimestamp(valueStr, columnName, row) : null;
	}

	@NonNull
	private static Timestamp parseLocalDateAsTimestamp(final String valueStr, final String columnName, final Map<String, String> row)
	{
		try
		{
			return Timestamp.valueOf(LocalDate.parse(valueStr).atStartOfDay());
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Can't parse value=`" + valueStr + "` of columnName=" + columnName, e)
					.appendParametersToMessage()
					.setParameter("row", row);
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

	@NonNull
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

	@NonNull
	public static BigDecimal extractBigDecimalWithScaleForColumnName(final Map<String, String> dataTableRow, final String columnName)
	{
		final String string = extractStringForColumnName(dataTableRow, columnName);
		try
		{
			return new BigDecimal(string).setScale(2, RoundingMode.HALF_UP);
		}
		catch (final NumberFormatException e)
		{
			throw new AdempiereException("Can't parse value=" + string + " of columnName=" + columnName, e).appendParametersToMessage()
					.setParameter("dataTableRow", dataTableRow);
		}
	}

	public static boolean extractBooleanForColumnName(
			@NonNull final Map<String, String> dataTableRow,
			@NonNull final String columnName)
	{
		final Boolean result = extractBooleanForColumnNameOr(dataTableRow, columnName, null);
		if (result == null)
		{
			throw new AdempiereException("Can't parse value of columnName=" + columnName).appendParametersToMessage()
					.setParameter("dataTableRow", dataTableRow);
		}
		return result;
	}

	@Nullable
	public static Boolean extractBooleanForColumnNameOr(
			@NonNull final Map<String, String> dataTableRow,
			@NonNull final String columnName,
			@Nullable final Boolean defaultValue)
	{
		final String string = extractStringOrNullForColumnName(dataTableRow, columnName);
		return StringUtils.toBoolean(string, defaultValue);
	}

	@Nullable
	public static Boolean extractBooleanForColumnNameOrNull(
			@NonNull final Map<String, String> dataTableRow,
			@NonNull final String columnName)
	{
		final String string = extractStringOrNullForColumnName(dataTableRow, columnName);
		if (Check.isBlank(string))
		{
			return null;
		}

		return StringUtils.toBoolean(string);
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

	@Nullable
	public Double extractDoubleOrNullForColumnName(
			@NonNull final Map<String, String> dataTableRow,
			@NonNull final String columnName)
	{
		final String string = extractStringOrNullForColumnName(dataTableRow, columnName);

		try
		{
			return Check.isBlank(string) ? null : Double.parseDouble(string);
		}
		catch (final NumberFormatException e)
		{
			throw new AdempiereException("Can't parse value=" + string + " of columnName=" + columnName, e).appendParametersToMessage()
					.setParameter("dataTableRow", dataTableRow);
		}
	}
}

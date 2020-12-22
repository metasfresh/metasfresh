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

import de.metas.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@UtilityClass
public class DataTableUtil
{

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

	private String extractStringForColumnName(final Map<String, String> dataTableRow, final String columnName)
	{
		final String string = dataTableRow.get(columnName);
		if (Check.isBlank(string))
		{
			throw new AdempiereException("missing value for columnName=" + columnName).appendParametersToMessage()
					.setParameter("dataTableRow", dataTableRow);
		}
		return string;
	}

	public int extractIntForIndex(
			@NonNull final List<String> dataTableRow,
			@NonNull final int index)
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

}

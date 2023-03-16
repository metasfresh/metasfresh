package de.metas.impexp.parser.csv;

import com.google.common.collect.ImmutableList;
import de.metas.impexp.format.ImpFormat;
import de.metas.impexp.format.ImpFormatColumn;
import de.metas.impexp.parser.ErrorMessage;
import de.metas.impexp.parser.ImpDataCell;
import groovy.transform.ToString;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.ArrayList;
import java.util.List;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
final class FlexImpDataLineParser implements CsvImpDataLineParser
{
	private static final char DEFAULT_CELL_QUOTE = '"';

	private final char cellDelimiter;
	private final char cellQuote;
	private final ImmutableList<ImpFormatColumn> columns;

	public FlexImpDataLineParser(@NonNull final ImpFormat importFormat)
	{
		columns = importFormat.getColumns();
		cellDelimiter = importFormat.getFormatType().getCellDelimiterChar();
		cellQuote = DEFAULT_CELL_QUOTE;
	}

	@Override
	public List<ImpDataCell> parseDataCells(final String line)
	{
		final List<String> cellRawValues = splitLineByDelimiter(line, cellQuote, cellDelimiter);

		final int columnsCount = columns.size();
		final List<ImpDataCell> cells = new ArrayList<>(columnsCount);
		for (final ImpFormatColumn column : columns)
		{
			cells.add(createDateCell(column, cellRawValues));
		}

		return ImmutableList.copyOf(cells);
	}

	private ImpDataCell createDateCell(final ImpFormatColumn column, final List<String> cellRawValues)
	{
		try
		{
			final String rawValue = extractRawValue(column, cellRawValues);
			final Object value = column.parseCellValue(rawValue);
			return ImpDataCell.value(value);
		}
		catch (final Exception ex)
		{
			return ImpDataCell.error(ErrorMessage.of(ex));
		}
	}

	private static String extractRawValue(final ImpFormatColumn column, final List<String> cellRawValues)
	{
		if (column.isConstant())
		{
			return column.getConstantValue();
		}

		// NOTE: startNo is ONE based, so we have to convert it to 0-based index.
		final int cellIndex = column.getStartNo() - 1;
		if (cellIndex < 0)
		{
			throw new AdempiereException("Invalid StartNo for column + " + column.getName());
		}

		if (cellIndex >= cellRawValues.size())
		{
			return null;
		}

		return cellRawValues.get(cellIndex);
	}

	private static List<String> splitLineByDelimiter(
			final String line,
			final char cellQuote,
			final char cellDelimiter)
	{
		if (line == null || line.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<String> result = new ArrayList<>();

		StringBuilder currentValue = new StringBuilder();
		boolean inQuotes = false;
		boolean startCollectChar = false;
		boolean doubleQuotesInColumn = false;

		final char[] chars = line.toCharArray();

		for (final char ch : chars)
		{

			if (inQuotes)
			{
				startCollectChar = true;
				if (ch == cellQuote)
				{
					inQuotes = false;
					doubleQuotesInColumn = false;
				}
				else
				{

					// Fixed : allow "" in custom quote enclosed
					if (ch == '\"')
					{
						if (!doubleQuotesInColumn)
						{
							currentValue.append(ch);
							doubleQuotesInColumn = true;
						}
					}
					else
					{
						currentValue.append(ch);
					}

				}
			}
			else
			{
				if (ch == cellQuote)
				{

					inQuotes = true;

					// double quotes in column will hit this!
					if (startCollectChar)
					{
						currentValue.append('"');
					}

				}
				else if (ch == cellDelimiter)
				{

					result.add(currentValue.toString());

					currentValue = new StringBuilder();
					startCollectChar = false;

				}
				else
				{
					currentValue.append(ch);
				}
			}
		}

		result.add(currentValue.toString());

		return result;
	}
}

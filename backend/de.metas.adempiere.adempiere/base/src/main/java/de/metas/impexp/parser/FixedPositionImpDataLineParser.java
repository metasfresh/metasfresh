package de.metas.impexp.parser;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import de.metas.impexp.format.ImpFormat;
import de.metas.impexp.format.ImpFormatColumn;
import groovy.transform.ToString;
import lombok.NonNull;

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
final class FixedPositionImpDataLineParser implements ImpDataLineParser
{
	private final ImmutableList<ImpFormatColumn> columns;

	public FixedPositionImpDataLineParser(@NonNull final ImpFormat importFormat)
	{
		this.columns = importFormat.getColumns();
	}

	@Override
	public List<ImpDataCell> parseDataCells(final String line)
	{
		final int columnsCount = columns.size();

		final List<ImpDataCell> cells = new ArrayList<>(columnsCount);
		for (final ImpFormatColumn impFormatColumn : columns)
		{
			final ImpDataCell cell = parseDataCell(line, impFormatColumn);
			cells.add(cell);
		}	// for all columns

		return ImmutableList.copyOf(cells);
	}	// parseLine

	private ImpDataCell parseDataCell(final String line, final ImpFormatColumn column)
	{
		try
		{
			final String rawValue = extractCellRawValue(line, column);
			final Object value = column.parseCellValue(rawValue);
			return ImpDataCell.value(value);
		}
		catch (final Exception ex)
		{
			return ImpDataCell.error(ErrorMessage.of(ex));
		}
	}

	private static String extractCellRawValue(final String line, final ImpFormatColumn impFormatColumn)
	{
		if (impFormatColumn.isConstant())
		{
			return impFormatColumn.getConstantValue();
		}
		else
		{
			// check length
			if (impFormatColumn.getStartNo() > 0 && impFormatColumn.getEndNo() <= line.length())
			{
				return line.substring(impFormatColumn.getStartNo() - 1, impFormatColumn.getEndNo());
			}
			else
			{
				return null;
			}
		}
	}
}

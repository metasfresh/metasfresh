package de.metas.datev;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.ThreadLocalDecimalFormatter;
import org.compiere.util.TimeUtil;

import de.metas.data.export.api.IExportDataDestination;
import de.metas.data.export.api.IExportDataSource;
import de.metas.data.export.api.impl.AbstractExporter;
import de.metas.data.export.api.impl.CSVWriter;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-datev
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class DATEVCsvExporter extends AbstractExporter
{
	private final DATEVExportFormat exportFormat;

	@Builder
	private DATEVCsvExporter(
			@NonNull final DATEVExportFormat exportFormat,
			@NonNull final IExportDataSource dataSource)
	{
		this.exportFormat = exportFormat;
		setDataSource(dataSource);
	}

	@Override
	protected CSVWriter createDataDestination(final OutputStream out) throws UnsupportedEncodingException
	{
		final Properties config = new Properties(getConfig());
		config.setProperty(CSVWriter.CONFIG_Encoding, exportFormat.getCsvEncoding());
		config.setProperty(CSVWriter.CONFIG_FieldDelimiter, exportFormat.getCsvFieldDelimiter());
		config.setProperty(CSVWriter.CONFIG_FieldQuote, exportFormat.getCsvFieldQuote());

		final CSVWriter csvWriter = new CSVWriter(out, config);
		csvWriter.setHeader(getDataSource().getFieldNames());
		return csvWriter;
	}

	@Override
	protected void appendRow(final IExportDataDestination dataDestination, final List<Object> row) throws IOException
	{
		final CSVWriter csvWriter = CSVWriter.cast(dataDestination);
		final List<Object> rowFormatted = formatRow(row);
		csvWriter.appendLine(rowFormatted);
	}

	private List<Object> formatRow(final List<Object> row)
	{
		final List<DATEVExportFormatColumn> formatColumns = exportFormat.getColumns();
		final int rowSize = row.size();
		final List<Object> rowFormatted = new ArrayList<>(rowSize);
		for (int i = 0; i < rowSize; i++)
		{
			final Object cell = row.get(i);
			final DATEVExportFormatColumn columnFormat = formatColumns.get(i);
			final Object cellFormated = formatCell(cell, columnFormat);
			rowFormatted.add(cellFormated);
		}

		return rowFormatted;
	}

	private Object formatCell(final Object value, final DATEVExportFormatColumn columnFormat)
	{
		if (value == null)
		{
			return null;
		}

		if (columnFormat.getDateFormatter() != null)
		{
			return formatDateCell(value, columnFormat.getDateFormatter());
		}
		else if (columnFormat.getNumberFormatter() != null)
		{
			return formatNumberCell(value, columnFormat.getNumberFormatter());
		}
		else
		{
			return value;
		}
	}

	private static String formatDateCell(final Object value, final DateTimeFormatter dateFormatter)
	{
		if (value == null)
		{
			return null;
		}
		else if (value instanceof java.util.Date)
		{
			final java.util.Date date = (java.util.Date)value;
			return dateFormatter.format(TimeUtil.asLocalDate(date));
		}
		else if (value instanceof TemporalAccessor)
		{
			TemporalAccessor temporal = (TemporalAccessor)value;
			return dateFormatter.format(temporal);
		}
		else
		{
			throw new AdempiereException("Cannot convert/format value to Date: " + value + " (" + value.getClass() + ")");
		}
	}

	private static String formatNumberCell(final Object value, final ThreadLocalDecimalFormatter numberFormatter)
	{
		if (value == null)
		{
			return null;
		}

		return numberFormatter.format(value);
	}

}

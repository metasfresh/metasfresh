/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.impexp.spreadsheet.csv;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.impexp.spreadsheet.service.DataConsumer;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcCSVExporter implements DataConsumer<ResultSet>
{
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private static final AdMessageKey MSG_NoData = AdMessageKey.of("NoData");

	private final String adLanguage;
	private final boolean translateHeaders;
	private final String fieldDelimiter;

	private final boolean doNotQuoteRows;

	private ImmutableList<String> _columnHeaders;

	@Nullable
	private CSVWriter writer;

	@Builder
	private JdbcCSVExporter(
			@NonNull final String adLanguage,
			@Nullable final Boolean translateHeaders,
			@Nullable final String fieldDelimiter, 
			final boolean doNotQuoteRows)
	{
		this.adLanguage = adLanguage;
		this.translateHeaders = translateHeaders != null ? translateHeaders : true;
		this.fieldDelimiter = fieldDelimiter != null ? fieldDelimiter : CSVWriter.DEFAULT_FieldDelimiter;
		this.doNotQuoteRows = doNotQuoteRows;
	}

	@Override
	public void putHeader(@NonNull final List<String> columnHeaders)
	{
		if (this._columnHeaders != null)
		{
			throw new AdempiereException("headers were already set")
					.setParameter("columnHeaders previously set", this._columnHeaders)
					.setParameter("columnHeaders new", columnHeaders)
					.appendParametersToMessage();
		}

		if (columnHeaders.isEmpty())
		{
			throw new AdempiereException("columnHeaders shall not be empty");
		}

		this._columnHeaders = ImmutableList.copyOf(columnHeaders);
	}

	private ImmutableList<String> getColumnHeaders()
	{
		if (this._columnHeaders == null)
		{
			throw new AdempiereException("columnHeaders were not set");
		}
		return this._columnHeaders;
	}

	private ImmutableList<String> getColumnHeadersTranslatedIfNeeded()
	{
		final ImmutableList<String> columnHeaders = getColumnHeaders();
		if (!translateHeaders)
		{
			return columnHeaders;
		}

		return columnHeaders.stream()
				.map(columnHeader -> msgBL.translatable(columnHeader).translate(adLanguage))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public void putResult(@NonNull final ResultSet rs)
	{
		if (writer != null)
		{
			throw new AdempiereException("putResult method was called more than once");
		}

		try
		{
			final int columnCount = Math.min(
					getColumnHeaders().size(),
					rs.getMetaData().getColumnCount());

			writer = createWriter();

			// always show spreadsheet header, even if there are no rows
			writer.appendHeaderIfNeeded();

			while (rs.next())
			{
				final List<Object> row = retrieveRow(rs, columnCount);
				writer.appendRow(row);
			}
		}
		catch (final SQLException ex)
		{
			throw DBException.wrapIfNeeded(ex);
		}

		if (writer != null)
		{
			writer.close();
		}
	}

	private List<Object> retrieveRow(final ResultSet rs, final int columnCount) throws SQLException
	{
		final ArrayList<Object> row = new ArrayList<>(columnCount);
		for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++)
		{
			final Object value = rs.getObject(columnIndex);
			row.add(value);
		}

		return row;
	}

	private CSVWriter createWriter()
	{
		final File outputFile;
		try
		{
			outputFile = File.createTempFile("Report_", ".csv");
		}
		catch (final IOException ex)
		{
			throw new AdempiereException("Failed creating temporary CSV file", ex);
		}

		return CSVWriter.builder()
				.outputFile(outputFile)
				.header(getColumnHeadersTranslatedIfNeeded())
				.adLanguage(adLanguage)
				.fieldDelimiter(fieldDelimiter)
				.doNotQuoteRows(doNotQuoteRows)
				.build();
	}

	@Override
	public boolean isNoDataAddedYet()
	{
		return writer == null || writer.getLinesWrote() <= 0;
	}

	@Nullable
	public File getResultFile()
	{
		final File resultFile = writer != null ? writer.getOutputFile() : null;
		if (resultFile == null)
		{
			// shall not happen
			throw new AdempiereException(MSG_NoData);
		}
		return resultFile;
	}
}

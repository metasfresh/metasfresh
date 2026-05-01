package de.metas.datev;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaBL;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyBL;
import de.metas.datev.model.I_DATEV_Export;
import de.metas.money.CurrencyId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.util.TimeUtil;

import de.metas.data.export.api.IExportDataDestination;
import de.metas.data.export.api.IExportDataSource;
import de.metas.data.export.api.impl.AbstractExporter;
import de.metas.data.export.api.impl.CSVWriter;
import de.metas.util.ThreadLocalDecimalFormatter;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;

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
	private final IAcctSchemaBL acctSchemaBL = Services.get(IAcctSchemaBL.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

	private static final DateTimeFormatter EXTF_TS_FMT   = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	private static final DateTimeFormatter EXTF_DATE_FMT = DateTimeFormatter.ofPattern("yyyyMMdd");

	@Nullable
	private final I_DATEV_Export datevExport;

	private final DATEVExportFormat exportFormat;

	@Builder
	private DATEVCsvExporter(
			@NonNull final DATEVExportFormat exportFormat,
			@NonNull final IExportDataSource dataSource,
			@Nullable final I_DATEV_Export datevExport)
	{
		this.exportFormat = exportFormat;
		this.datevExport = datevExport;
		setDataSource(dataSource);
	}

	@Override
	protected CSVWriter createDataDestination(final OutputStream out) throws IOException
	{
		// 1. Write EXTF line (line 1) if a datevExport record was provided
		if (datevExport != null)
		{
			final String extfLine = buildExtfLine(datevExport, exportFormat);
			out.write(extfLine.getBytes(exportFormat.getCsvEncoding()));
			out.write('\r');
			out.write('\n');
		}

		// 2. Hand the same stream to CSVWriter — it will write the column-header line next
		final Properties config = new Properties(getConfig());
		config.setProperty(CSVWriter.CONFIG_Encoding, exportFormat.getCsvEncoding());
		config.setProperty(CSVWriter.CONFIG_FieldDelimiter, exportFormat.getCsvFieldDelimiter());
		config.setProperty(CSVWriter.CONFIG_FieldQuote, exportFormat.getCsvFieldQuote());
		config.setProperty(CSVWriter.CONFIG_AllowMultilineFields, "false");

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

	private String buildExtfLine(@NonNull final I_DATEV_Export datevExport, @NonNull final DATEVExportFormat exportFormat)
	{
		final String ts       = LocalDateTime.now().format(EXTF_TS_FMT);
		final String dateFrom = datevExport.getDateAcctFrom() != null
				? TimeUtil.asLocalDate(datevExport.getDateAcctFrom()).format(EXTF_DATE_FMT) : "";
		final String dateTo   = datevExport.getDateAcctTo() != null
				? TimeUtil.asLocalDate(datevExport.getDateAcctTo()).format(EXTF_DATE_FMT) : "";
		final String formatName = exportFormat.getName();

		final AcctSchema primaryAcctSchema = acctSchemaBL.getPrimaryAcctSchema(ClientId.ofRepoId(datevExport.getAD_Client_ID()));
		final CurrencyId acctSchemaCurrencyId = primaryAcctSchema.getCurrencyId();
		final CurrencyCode currencyCode = currencyBL.getCurrencyCodeById(acctSchemaCurrencyId);

		// TODO: get fiscal year start from C_Period/C_Year based on accounting schema and period
		final String fiscalYearStart = String.format("%04d0101", TimeUtil.asLocalDate(datevExport.getDateAcctFrom()).getYear());

		// TODO: add DATEV_Beraternr + DATEV_Mandantennr columns to I_DATEV_Export
		//       and replace the hardcoded values below
		final String beraternr   = "179155";
		final String mandantennr = "131";

		return "EXTF;510;21;"
				+ formatName
				+ ";7;"
				+ ts + "313"
				+ ";;"          // imported / exported (empty on export)
				+ ";FR;B.L;"    // source, initials, reserved
				+ ";" + beraternr
				+ ";" + mandantennr
				+ ";" + fiscalYearStart
				+ ";4"
				+ ";" + dateFrom
				+ ";" + dateTo
				+ ";metasfresh;MM;1;0;1;"
				+ currencyCode.toThreeLetterCode()
				+ ";;;;52.025;;;;";
	}

}

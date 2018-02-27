package de.metas.datev;

import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.ThreadLocalDecimalFormatter;
import org.compiere.model.POInfo;
import org.compiere.util.CCache;
import org.compiere.util.DisplayType;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import de.metas.datev.DATEVExportFormatColumn.DATEVExportFormatColumnBuilder;
import de.metas.datev.model.I_DATEV_ExportFormat;
import de.metas.datev.model.I_DATEV_ExportFormatColumn;
import de.metas.datev.model.I_DATEV_ExportLine;

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

@Component
public class DATEVExportFormatRepository
{
	private final CCache<Integer, DATEVExportFormat> exportFormatsById = CCache.newCache(I_DATEV_ExportFormat.Table_Name, 10, CCache.EXPIREMINUTES_Never);

	public DATEVExportFormat getById(final int datevExportFormatId)
	{
		return exportFormatsById.getOrLoad(datevExportFormatId, () -> retrieveById(datevExportFormatId));
	}

	private DATEVExportFormat retrieveById(final int datevExportFormatId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final I_DATEV_ExportFormat formatPO = queryBL
				.createQueryBuilder(I_DATEV_ExportFormat.class)
				.addEqualsFilter(I_DATEV_ExportFormat.COLUMN_DATEV_ExportFormat_ID, datevExportFormatId)
				.create()
				.firstOnlyNotNull(I_DATEV_ExportFormat.class);

		final POInfo exportLinePOInfo = POInfo.getPOInfo(I_DATEV_ExportLine.Table_Name);

		final List<DATEVExportFormatColumn> formatColumns = queryBL
				.createQueryBuilder(I_DATEV_ExportFormatColumn.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DATEV_ExportFormatColumn.COLUMN_DATEV_ExportFormat_ID, datevExportFormatId)
				.orderBy(I_DATEV_ExportFormatColumn.COLUMN_SeqNo)
				.create()
				.stream(I_DATEV_ExportFormatColumn.class)
				.map(formatColumnPO -> toDATEVExportFormatColumn(formatColumnPO, formatPO, exportLinePOInfo))
				.collect(ImmutableList.toImmutableList());

		return DATEVExportFormat.builder()
				.id(formatPO.getDATEV_ExportFormat_ID())
				.name(formatPO.getName())
				.csvEncoding(formatPO.getCSVEncoding())
				.csvFieldDelimiter(extractCsvFieldDelimiter(formatPO))
				.csvFieldQuote(extractCsvFieldQuote(formatPO))
				.columns(formatColumns)
				.build();
	}

	private DATEVExportFormatColumn toDATEVExportFormatColumn(final I_DATEV_ExportFormatColumn formatColumnPO,
			final I_DATEV_ExportFormat exportFormatPO,
			final POInfo exportLinePOInfo)
	{
		final int adColumnId = formatColumnPO.getAD_Column_ID();
		final int columnIndex = exportLinePOInfo.getColumnIndex(adColumnId);
		final String columnName = exportLinePOInfo.getColumnName(columnIndex);
		if (Check.isEmpty(columnName, true))
		{
			throw new AdempiereException("No column found for AD_Column_ID=" + adColumnId + " in " + exportLinePOInfo);
		}

		final DATEVExportFormatColumnBuilder columnBuilder = DATEVExportFormatColumn.builder()
				.columnName(columnName)
				.csvHeaderName(formatColumnPO.getName());

		final int displayType = exportLinePOInfo.getColumnDisplayType(columnIndex);
		if (DisplayType.isDate(displayType))
		{
			final String formatPatternStr = formatColumnPO.getFormatPattern();
			if (!Check.isEmpty(formatPatternStr, true))
			{
				columnBuilder.dateFormatter(DateTimeFormatter.ofPattern(formatPatternStr));
			}
		}
		else if (DisplayType.isNumeric(displayType))
		{
			columnBuilder.numberFormatter(extractNumberFormatter(formatColumnPO.getFormatPattern(), exportFormatPO));
		}

		return columnBuilder.build();
	}

	private static ThreadLocalDecimalFormatter extractNumberFormatter(final String formatPattern, final I_DATEV_ExportFormat formatPO)
	{
		final String formatPatternEffective = !Check.isEmpty(formatPattern, true) ? formatPattern : "#########.00####";
		final DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.getDefault(Locale.Category.FORMAT));

		final String decimalSeparator = formatPO.getDecimalSeparator();
		if (decimalSeparator != null && !decimalSeparator.isEmpty())
		{
			symbols.setDecimalSeparator(decimalSeparator.charAt(0));
		}

		final String groupingSeparator = formatPO.getNumberGroupingSeparator();
		if (groupingSeparator != null && !groupingSeparator.isEmpty())
		{
			symbols.setGroupingSeparator(groupingSeparator.charAt(0));
		}

		return ThreadLocalDecimalFormatter.ofPattern(formatPatternEffective, symbols);
	}

	private static String extractCsvFieldDelimiter(final I_DATEV_ExportFormat formatPO)
	{
		String delimiter = formatPO.getCSVFieldDelimiter();
		if (delimiter == null)
		{
			return "";
		}

		return delimiter
				.replace("\\t", "\t")
				.replace("\\r", "\r")
				.replace("\\n", "\n");
	}

	private static String extractCsvFieldQuote(final I_DATEV_ExportFormat formatPO)
	{
		final String quote = formatPO.getCSVFieldQuote();
		if (quote == null)
		{
			return "";
		}
		else if ("-".equals(quote))
		{
			return "";
		}
		else
		{
			return quote;
		}
	}
}

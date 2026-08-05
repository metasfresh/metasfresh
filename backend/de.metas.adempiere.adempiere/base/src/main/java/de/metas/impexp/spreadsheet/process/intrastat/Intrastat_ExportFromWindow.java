/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.impexp.spreadsheet.process.intrastat;

import de.metas.impexp.spreadsheet.csv.JdbcCSVExporter;
import de.metas.impexp.spreadsheet.service.SpreadsheetExporterService;
import de.metas.process.JavaProcess;
import de.metas.process.SpreadsheetExportOptions;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.compiere.util.Evaluatees;

import java.io.File;

/**
 * Selection-driven Intrastat CSV export invoked from the Intrastat preview window.
 *
 * <p>Reads the user's row selection via {@code T_Selection} filtered by the current
 * {@code AD_PInstance_ID} and writes the matching {@code Intrastat_Preview_V} rows to CSV
 * with the extended column set (10 AT RTIC columns + UOM + Currency).</p>
 *
 * <p>Selection semantics are the standard metasfresh {@code T_Selection} behavior:
 * checked rows if any, else whatever the WebUI populated as the currently-filtered set.
 * When {@code T_Selection} is empty for this {@code AD_PInstance_ID}, the SQL falls back
 * to the full view (belt-and-braces so an unexpected empty selection does not silently
 * produce an empty CSV).</p>
 *
 * <p>Number formatting for the 10 shared columns mirrors {@code report.Intrastat_Export}
 * so the extended CSV stays byte-compatible with the AT RTIC payload on those columns
 * (the two new columns UOM + Currency are text and appended at the end).</p>
 */
public class Intrastat_ExportFromWindow extends JavaProcess
{
	private final SpreadsheetExporterService spreadsheetExporterService =
			SpringContextHolder.instance.getBean(SpreadsheetExporterService.class);

	@Override
	protected String doIt()
	{
		final int adPInstanceId = getPinstanceId().getRepoId();
		final String sql = buildSql(adPInstanceId);

		final SpreadsheetExportOptions options = getProcessInfo().getSpreadsheetExportOptions();

		final JdbcCSVExporter csvExporter = JdbcCSVExporter.builder()
				.adLanguage(Env.getADLanguageOrBaseLanguage(getCtx()))
				.translateHeaders(options.isTranslateHeaders())
				.fieldDelimiter(options.getCsvFieldDelimiter())
				.fieldQualifier(options.getCsvFieldQualifier())
				.includeHeader(options.isIncludeCSVHeaderRow())
				.build();

		spreadsheetExporterService.processDataFromSQL(sql, Evaluatees.ofCtx(getCtx()), csvExporter);

		final File resultFile = csvExporter.getResultFile();
		getResult().setReportData(resultFile);

		return MSG_OK;
	}

	/**
	 * Build the SELECT for the extended 12-column CSV.
	 *
	 * <p>Uses the current {@code AD_PInstance_ID}'s {@code T_Selection} as the row filter.
	 * If that selection is empty (no {@code T_Selection} row for this instance) the entire
	 * {@code Intrastat_Preview_V} is exported instead.</p>
	 */
	private String buildSql(final int adPInstanceId)
	{
		return "SELECT pv.CNCode,"
				+ " p.Name                                                       AS \"GoodsDescription\","
				+ " pv.CountryDestinationConsignment,"
				+ " pv.CountryOfOrigin,"
				+ " pv.IntrastaNatureOfTransaction,"
				+ " TO_CHAR(pv.NetMass,            'FM9999999D000')               AS \"NetMass\","
				+ " TO_CHAR(pv.SupplementaryUnits, 'FM9999999D000')               AS \"SupplementaryUnits\","
				+ " TO_CHAR(pv.InvoiceValue,       'FM9999999D00')                AS \"InvoiceValue\","
				+ " TO_CHAR(pv.StatisticalValue,   'FM9999999D00')                AS \"StatisticalValue\","
				+ " CASE WHEN pv.IsSOTrx = 'Y' THEN pv.RecipientVATNo END         AS \"Recipient-VAT-No\","
				+ " uom.UOMSymbol                                                 AS \"UOM\","
				+ " cur.ISO_Code                                                  AS \"Currency\""
				+ " FROM Intrastat_Preview_V pv"
				+ " LEFT JOIN M_Product  p   ON p.M_Product_ID   = pv.M_Product_ID"
				+ " LEFT JOIN C_UOM      uom ON uom.C_UOM_ID     = pv.C_UOM_ID"
				+ " LEFT JOIN C_Currency cur ON cur.C_Currency_ID = pv.C_Currency_ID"
				+ " WHERE ("
				+ "    pv.Intrastat_Preview_V_ID IN"
				+ "      (SELECT T_Selection_ID FROM T_Selection WHERE AD_PInstance_ID = " + adPInstanceId + ")"
				+ "    OR NOT EXISTS"
				+ "      (SELECT 1 FROM T_Selection WHERE AD_PInstance_ID = " + adPInstanceId + ")"
				+ " )";
	}
}

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
import de.metas.process.PInstanceId;
import de.metas.process.SpreadsheetExportOptions;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.compiere.util.Evaluatees;

import java.io.File;

/**
 * Selection-driven Intrastat CSV export invoked from the Intrastat window
 * ({@code AD_Process 585647}, backed by {@code AD_Table Intrastat_Report_Detail_V}).
 * <p>
 * Behaviour by row selection ({@code T_Selection}, scoped to the current {@code AD_PInstance_ID}):
 * <ul>
 *   <li>Checked rows → export those.</li>
 *   <li>No row checked → the WebUI still populates {@code T_Selection} with the currently-filtered
 *       set, and those are exported.</li>
 *   <li>No {@code T_Selection} row at all (defensive fallback) → export the whole view rather than
 *       silently produce empty output.</li>
 * </ul>
 * <p>
 * Output format: CSV, no header row (matches the AT RTIC-file convention; the tax-authority upload
 * rejects a header row). Number formatting mirrors {@code report.Intrastat_Export}
 * ({@code TO_CHAR('FM9999999D000' / 'FM9999999D00')}). The two extra columns ({@code UOM},
 * {@code Currency}) are appended at the end.
 * <p>
 * Nature-of-transaction is hardcoded to {@code '11'} (standard sale/purchase) — same simplification
 * as {@code report.Intrastat_Export}. Return codes (21), processing under contract (41), etc. are
 * a known limitation.
 */
public class Intrastat_ExportFromWindow extends JavaProcess
{
	@NonNull private final SpreadsheetExporterService spreadsheetExporterService = SpringContextHolder.instance.getBean(SpreadsheetExporterService.class);

	private static final String SQL_TEMPLATE = String.join("\n",
			"SELECT d.CustomsTariff                                                            AS \"CNCode\",",
			"       p.Name                                                                     AS \"GoodsDescription\",",
			"       d.DeliveryCountry                                                          AS \"CountryDestinationConsignment\",",
			"       COALESCE(d.DeliveredFromCountry, d.OriginCountry, d.DeliveryCountry)       AS \"CountryOfOrigin\",",
			"       '11'                                                                       AS \"IntrastaNatureOfTransaction\",",
			"       TO_CHAR(d.Weight,       'FM9999999D000')                                   AS \"NetMass\",",
			"       TO_CHAR(d.MovementQty,  'FM9999999D000')                                   AS \"SupplementaryUnits\",",
			"       TO_CHAR(d.LineNetAmt,   'FM9999999D00')                                    AS \"InvoiceValue\",",
			"       TO_CHAR(d.LineNetAmt,   'FM9999999D00')                                    AS \"StatisticalValue\",",
			"       CASE WHEN d.IsSOTrx = 'Y' THEN bp.vataxid END                              AS \"Recipient-VAT-No\",",
			"       d.UOMSymbol                                                                AS \"UOM\",",
			"       d.CurSymbol                                                                AS \"Currency\"",
			"FROM  Intrastat_Report_Detail_V d",
			"LEFT JOIN M_Product  p  ON p.M_Product_ID   = d.M_Product_ID",
			"LEFT JOIN C_BPartner bp ON bp.C_BPartner_ID = d.C_BPartner_ID",
			"WHERE d.Intrastat_Report_Detail_V_ID IN",
			"      (SELECT T_Selection_ID FROM T_Selection WHERE AD_PInstance_ID = %1$d)",
			// Fallback: no T_Selection row was populated for this AD_PInstance_ID
			// (unusual — the WebUI normally seeds it). Export the whole view rather than
			// silently produce an empty file.
			"   OR NOT EXISTS",
			"      (SELECT 1 FROM T_Selection WHERE AD_PInstance_ID = %1$d)");

	@Override
	protected String doIt()
	{
		final PInstanceId pinstanceId = getPinstanceId();
		final String sql = String.format(SQL_TEMPLATE, pinstanceId.getRepoId());
		final File csv = runCsvExport(sql);

		getResult().setReportData(csv);
		return MSG_OK;
	}

	private File runCsvExport(@NonNull final String sql)
	{
		final SpreadsheetExportOptions options = getProcessInfo().getSpreadsheetExportOptions();
		final JdbcCSVExporter exporter = JdbcCSVExporter.builder()
				.adLanguage(Env.getADLanguageOrBaseLanguage(getCtx()))
				.translateHeaders(false)
				.fieldDelimiter(options.getCsvFieldDelimiter())
				.fieldQualifier(options.getCsvFieldQualifier())
				.includeHeader(false)
				.build();

		spreadsheetExporterService.processDataFromSQL(sql, Evaluatees.ofCtx(getCtx()), exporter);
		return exporter.getResultFile();
	}
}

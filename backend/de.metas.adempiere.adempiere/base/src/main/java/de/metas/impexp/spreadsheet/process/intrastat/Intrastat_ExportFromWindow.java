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
 * Exports only what the user sees in the window — i.e. the rows the WebUI has populated into
 * {@code T_Selection} for the current {@code AD_PInstance_ID}:
 * <ul>
 *   <li>User ticked rows in the grid → export those.</li>
 *   <li>Nothing ticked → the WebUI still populates {@code T_Selection} with the currently-filtered
 *       set, and those are exported.</li>
 *   <li>{@code T_Selection} empty → export is empty (no defensive fallback to the whole view —
 *       exporting rows the user did not see would violate intent).</li>
 * </ul>
 * <p>
 * Output shape mirrors {@code report.Intrastat_Export} exactly — same 10 columns, same
 * {@code TO_CHAR} number formats, same direction-conditional logic for
 * {@code CountryOfOrigin} and {@code Recipient-VAT-No}, same row filters
 * ({@code IsPackagingMaterial='N'} — inherited from the view, {@code CustomsTariff IS NOT NULL},
 * {@code Product.IsStocked='Y'}), and the same aggregation granularity
 * (GROUP BY on the columns {@code Intrastat_Report_V} groups by). CSV, no header row.
 * <p>
 * {@code IntrastaNatureOfTransaction} is hardcoded to {@code '11'} — same simplification as
 * the report function's default parameter value.
 */
public class Intrastat_ExportFromWindow extends JavaProcess
{
	@NonNull private final SpreadsheetExporterService spreadsheetExporterService = SpringContextHolder.instance.getBean(SpreadsheetExporterService.class);

	private static final String SQL_TEMPLATE = String.join("\n",
			"SELECT d.CustomsTariff                                                            AS \"CNCode\",",
			"       p.Name                                                                     AS \"GoodsDescription\",",
			"       d.DeliveryCountry                                                          AS \"CountryDestinationConsignment\",",
			"       CASE",
			"           WHEN d.IsSOTrx = 'Y' THEN d.DeliveredFromCountry",
			"           ELSE COALESCE(d.OriginCountry, d.DeliveryCountry)",
			"       END                                                                        AS \"CountryOfOrigin\",",
			"       '11'                                                                       AS \"IntrastaNatureOfTransaction\",",
			"       TO_CHAR(SUM(d.Weight),       'FM9999999D000')                              AS \"NetMass\",",
			"       TO_CHAR(SUM(d.MovementQty),  'FM9999999D000')                              AS \"SupplementaryUnits\",",
			"       TO_CHAR(SUM(d.LineNetAmt),   'FM9999999D00')                               AS \"InvoiceValue\",",
			"       TO_CHAR(SUM(d.LineNetAmt),   'FM9999999D00')                               AS \"StatisticalValue\",",
			"       CASE WHEN d.IsSOTrx = 'Y' THEN bp.VATaxID END                              AS \"Recipient-VAT-No\"",
			"FROM  Intrastat_Report_Detail_V d",
			"LEFT JOIN M_Product  p  ON p.M_Product_ID   = d.M_Product_ID",
			"LEFT JOIN C_BPartner bp ON bp.C_BPartner_ID = d.C_BPartner_ID",
			"WHERE d.Intrastat_Report_Detail_V_ID IN",
			"      (SELECT T_Selection_ID FROM T_Selection WHERE AD_PInstance_ID = %1$d)",
			// Match report.Intrastat_Export's stricter filters — the view alone lets in null-
			// tariff rows (non-zero amount) and non-stocked products for its debugging use case.
			"  AND d.CustomsTariff IS NOT NULL",
			"  AND p.IsStocked = 'Y'",
			"GROUP BY d.CustomsTariff,",
			"         p.Name,",
			"         d.DeliveryCountry,",
			"         d.DeliveredFromCountry,",
			"         d.OriginCountry,",
			"         d.IsSOTrx,",
			"         bp.VATaxID,",
			"         d.C_Period_ID,",
			"         d.C_Year_ID,",
			"         d.AD_Org_ID");

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

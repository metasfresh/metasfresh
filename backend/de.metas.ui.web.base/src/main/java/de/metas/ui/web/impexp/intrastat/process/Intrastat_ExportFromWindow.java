/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.impexp.intrastat.process;

import de.metas.impexp.spreadsheet.csv.JdbcCSVExporter;
import de.metas.impexp.spreadsheet.service.SpreadsheetExporterService;
import de.metas.process.SpreadsheetExportOptions;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Evaluatees;

import java.io.File;
import java.util.Set;

/**
 * Selection-driven Intrastat CSV export invoked from the Intrastat window
 * ({@code AD_Process 585647}, backed by {@code AD_Table Intrastat_Report_Detail_V}).
 * <p>
 * Extends {@link ViewBasedProcessTemplate} so the WebUI's view / selection context is
 * available. The user's ticked rows (or the WebUI-populated filtered set if nothing is
 * ticked) are copied into {@code T_Selection} for this {@code AD_PInstance_ID}, then the
 * SQL below filters against that.
 * <p>
 * Output shape mirrors {@code report.Intrastat_Export} exactly — same 10 columns, same
 * {@code TO_CHAR} number formats, direction-conditional {@code CountryOfOrigin} and
 * {@code Recipient-VAT-No}, same row filters ({@code IsPackagingMaterial='N'} inherited
 * from the view, {@code CustomsTariff IS NOT NULL}, {@code Product.IsStocked='Y'}), and
 * the same aggregation granularity (GROUP BY on the columns {@code Intrastat_Report_V}
 * groups by). CSV, no header row. {@code IntrastaNatureOfTransaction} is hardcoded to
 * {@code '11'} — same simplification as the report function's default parameter value.
 */
public class Intrastat_ExportFromWindow extends ViewBasedProcessTemplate
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
			"JOIN M_Product  p  ON p.M_Product_ID   = d.M_Product_ID AND p.IsStocked = 'Y'",
			"LEFT JOIN C_BPartner bp ON bp.C_BPartner_ID = d.C_BPartner_ID",
			"WHERE d.Intrastat_Report_Detail_V_ID IN",
			"      (SELECT T_Selection_ID FROM T_Selection WHERE AD_PInstance_ID = %1$d)",
			"  AND d.CustomsTariff IS NOT NULL",
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
		final Set<Integer> selectedIds = getSelectedRowIds().toIntSet();
		DB.createT_Selection(getPinstanceId(), selectedIds, ITrx.TRXNAME_None);

		final String sql = String.format(SQL_TEMPLATE, getPinstanceId().getRepoId());
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

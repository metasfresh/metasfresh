/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.process;

import de.metas.impexp.spreadsheet.excel.JdbcExcelExporter;
import de.metas.impexp.spreadsheet.service.SpreadsheetExporterService;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.RunOutOfTrx;
import lombok.NonNull;
import org.apache.poi.ss.usermodel.Font;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.SQLValueStringResult;

import java.io.File;
import java.util.List;

public class M_HU_PI_Item_Product_Consolidate_Report extends JavaProcess
{
	@NonNull private final SpreadsheetExporterService spreadsheetExporterService =
			SpringContextHolder.instance.getBean(SpreadsheetExporterService.class);

	@Param(parameterName = "IsNormalizeGTIN")
	private boolean p_IsNormalizeGTIN;

	@Param(parameterName = "IsConsolidate")
	private boolean p_IsConsolidate;

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		// Step 1: Execute consolidation function, capture RAISE NOTICE via SQLWarning
		final String consolidateSql = "SELECT m_hu_pi_item_product_consolidate(?, ?, ?)";
		final SQLValueStringResult result = DB.getSQLValueStringWithWarningEx(
				null, // trxName — RunOutOfTrx, no transaction
				consolidateSql,
				p_IsNormalizeGTIN,
				p_IsConsolidate,
				getPinstanceId().getRepoId());

		// Log each RAISE NOTICE message (backup table names, counts)
		final List<String> warningMessages = result.getWarningMessages();
		if (warningMessages != null)
		{
			for (final String warning : warningMessages)
			{
				addLog(warning);
			}
		}

		// Log summary
		final String summary = result.getReturnedValue();
		if (summary != null)
		{
			addLog(summary);
		}

		// Step 2: Generate Excel report of remaining conflicts
		final String adLanguage = Env.getAD_Language(getCtx());
		final String reportSql = "SELECT * FROM m_hu_pi_item_product_consolidate_report("
				+ DB.TO_STRING(adLanguage)
				+ ")";

		final JdbcExcelExporter exporter = JdbcExcelExporter.builder()
				.ctx(getCtx())
				.build();
		exporter.setFontCharset(Font.ANSI_CHARSET);

		spreadsheetExporterService.processDataFromSQL(reportSql, exporter);

		if (!exporter.isNoDataAddedYet())
		{
			final File tempFile = exporter.getResultFile();
			getResult().setReportData(tempFile);
		}

		return MSG_OK;
	}
}

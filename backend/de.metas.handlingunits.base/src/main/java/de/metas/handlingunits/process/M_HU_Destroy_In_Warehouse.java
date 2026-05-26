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
import org.compiere.util.SQLValueStringResult;

import java.io.File;
import java.util.List;

public class M_HU_Destroy_In_Warehouse extends JavaProcess
{
	@NonNull private final SpreadsheetExporterService spreadsheetExporterService =
			SpringContextHolder.instance.getBean(SpreadsheetExporterService.class);

	@Param(parameterName = "M_Warehouse_ID", mandatory = true)
	private int p_M_Warehouse_ID;

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		// Step 1: Execute the destroy function; capture RAISE NOTICE messages via SQLWarning
		final String destroySql = "SELECT m_hu_destroy_in_warehouse(?::numeric, ?::numeric)";
		final SQLValueStringResult result = DB.getSQLValueStringWithWarningEx(
				null,
				destroySql,
				p_M_Warehouse_ID,
				getPinstanceId().getRepoId());

		// Log each RAISE NOTICE (backup table names, counts)
		final List<String> warnings = result.getWarningMessages();
		if (warnings != null)
		{
			warnings.forEach(this::addLog);
		}

		// Log summary returned by the function
		final String summary = result.getReturnedValue();
		if (summary != null)
		{
			addLog(summary);
		}

		// Step 2: Generate Excel from the report capture table created by the function
		final String reportTable = "backup.m_hu_destroy_pi_" + getPinstanceId().getRepoId();
		final String reportSql = "SELECT * FROM " + reportTable;

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

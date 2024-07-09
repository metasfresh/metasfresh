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

package de.metas.impexp.spreadsheet.process;

import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.impexp.spreadsheet.csv.JdbcCSVExporter;
import de.metas.impexp.spreadsheet.excel.JdbcExcelExporter;
import de.metas.impexp.spreadsheet.service.SpreadsheetExporterService;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.SpreadsheetExportOptions;
import de.metas.process.SpreadsheetFormat;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_DatevAcctExport;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;

import java.io.File;
import java.util.ArrayList;

public class ExportToSpreadsheetProcess extends JavaProcess
{
	final SpreadsheetExporterService spreadsheetExporterService = SpringContextHolder.instance.getBean(SpreadsheetExporterService.class);
	final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	@Param(parameterName = "SpreadsheetFormat")
	private	String  p_SpreadsheetFormat;

	@Override
	protected String doIt()
	{
		final String sql = getSql();
		final Evaluatee evalCtx = getEvalContext();

		final File resultFile;

		final SpreadsheetExportOptions spreadsheetExportOptions = getProcessInfo().getSpreadsheetExportOptions();
		final SpreadsheetFormat spreadsheetFormat = CoalesceUtil.coalesceNotNull(
				SpreadsheetFormat.ofNullableCode(p_SpreadsheetFormat),
				spreadsheetExportOptions.getFormat());

		if (spreadsheetFormat == SpreadsheetFormat.Excel)
		{
			final JdbcExcelExporter jdbcExcelExporter = JdbcExcelExporter.builder()
					.ctx(getCtx())
					.translateHeaders(spreadsheetExportOptions.isTranslateHeaders())
					.applyFormatting(spreadsheetExportOptions.isExcelApplyFormatting())
					.build();

			spreadsheetExporterService.processDataFromSQL(sql, evalCtx, jdbcExcelExporter);

			resultFile = jdbcExcelExporter.getResultFile();
		}
		else if (spreadsheetFormat == SpreadsheetFormat.CSV)
		{
			final JdbcCSVExporter jdbcCSVExporter = JdbcCSVExporter.builder()
					.adLanguage(Env.getAD_Language(getCtx()))
					.translateHeaders(spreadsheetExportOptions.isTranslateHeaders())
					.fieldDelimiter(spreadsheetExportOptions.getCsvFieldDelimiter())
					.doNotQuoteRows(spreadsheetExportOptions.isDoNotQuoteRows())
					.build();

			spreadsheetExporterService.processDataFromSQL(sql, evalCtx, jdbcCSVExporter);

			resultFile = jdbcCSVExporter.getResultFile();
		}
		else
		{
			throw new AdempiereException("Unknown spreadsheet format: " + spreadsheetFormat);
		}

		updateDatevAcctExport();

		getResult().setReportData(resultFile);

		return MSG_OK;
	}

	private void updateDatevAcctExport()
	{
		final String tableName = getTableName();
		if (I_DatevAcctExport.Table_Name.equals(tableName))
		{
			final int recordId = getRecord_ID();
			final I_DatevAcctExport datevAcctExport = InterfaceWrapperHelper.create(getCtx(), recordId, I_DatevAcctExport.class, getTrxName());
			final AdProcessId adProcessId = getProcessInfo().getAdProcessId();
			final I_AD_Process processRecord = adProcessDAO.getById(adProcessId);
			if (processRecord.isUpdateExportDate())
			{
				datevAcctExport.setExportDate(SystemTime.asTimestamp());
			}
			datevAcctExport.setExportBy_ID(getAD_User_ID());
			InterfaceWrapperHelper.saveRecord(datevAcctExport);
		}
	}

	private String getSql()
	{
		return getProcessInfo().getSQLStatement().orElseThrow(() -> new FillMandatoryException("SQLStatement"));
	}

	private Evaluatee getEvalContext()
	{
		final ArrayList<Evaluatee> contexts = new ArrayList<>();

		//
		// 1: Add process parameters
		contexts.add(Evaluatees.ofRangeAwareParams(getParameterAsIParams()));

		//
		// 2: underlying record
		final String recordTableName = getTableName();
		final int recordId = getRecord_ID();
		if (recordTableName != null && recordId > 0)
		{
			final TableRecordReference recordRef = TableRecordReference.of(recordTableName, recordId);
			final Evaluatee evalCtx = Evaluatees.ofTableRecordReference(recordRef);
			if (evalCtx != null)
			{
				contexts.add(evalCtx);
			}
		}

		//
		// 3: global context
		contexts.add(Evaluatees.ofCtx(getCtx()));

		return Evaluatees.compose(contexts);
	}
}

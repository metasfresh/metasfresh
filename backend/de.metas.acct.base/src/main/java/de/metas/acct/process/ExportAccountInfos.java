/**
 *
 */
package de.metas.acct.process;

import de.metas.impexp.spreadsheet.excel.JdbcExcelExporter;
import de.metas.impexp.spreadsheet.service.SpreadsheetExporterService;
import de.metas.process.JavaProcess;
import de.metas.util.FileUtil;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * #%L
 * de.metas.fresh.base
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

/**
 * @author metas-dev <dev@metasfresh.com>
 */
public class ExportAccountInfos extends JavaProcess
{
	final SpreadsheetExporterService spreadsheetExporterService = SpringContextHolder.instance.getBean(SpreadsheetExporterService.class);

	@Override
	protected String doIt()
	{
		final String sql = getSql("1100");
		final Evaluatee evalCtx = getEvalContext();

		final JdbcExcelExporter jdbcExcelExporter = JdbcExcelExporter.builder()
				.ctx(getCtx())
				.translateHeaders(true)
				.applyFormatting(true)
				.build();

		spreadsheetExporterService.processDataFromSQL(sql, evalCtx, jdbcExcelExporter);

		final File resultFile = jdbcExcelExporter.getResultFile();

		final List<File> files = new ArrayList<>(); // this is the list with file for all needed accounts
		files.add(resultFile);

		try
		{
			final File zipFile = FileUtil.zip(files);
			getResult().setReportData(zipFile);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

		return MSG_OK;
	}

	private String getSql(@NonNull final String account)
	{

		final StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM report.AccountSheet_Report")
				.append("(")
				.append("p_Account_ID := ")
				.append(account)
				.append(", ")
				.append("p_C_AcctSchema_ID := @C_AcctSchema_ID@, ")
				.append("p_DateAcctFrom := '@DateAcctFrom@', ")
				.append("p_DateAcctTo := '@DateAcctTo@')");

		return sb.toString();
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

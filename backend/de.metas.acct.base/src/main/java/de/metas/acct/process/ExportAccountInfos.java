/**
 *
 */
package de.metas.acct.process;

import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IFactAcctDAO;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.elementvalue.ElementValue;
import de.metas.elementvalue.ElementValueService;
import de.metas.impexp.spreadsheet.excel.JdbcExcelExporter;
import de.metas.impexp.spreadsheet.service.SpreadsheetExporterService;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.FileUtil;
import de.metas.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.TimeUtil;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
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
	final ElementValueService elementValueService = SpringContextHolder.instance.getBean(ElementValueService.class);
	final IFactAcctDAO factAcctDAO = Services.get(IFactAcctDAO.class);

	@Param(parameterName = "C_AcctSchema_ID", mandatory = true)
	private int p_C_AcctSchema_ID;

	@Param(parameterName = "DateAcctFrom", mandatory = true)
	private Timestamp p_DateAcctFrom;

	@Param(parameterName = "DateAcctTo", mandatory = true)
	private Timestamp p_DateAcctTo;

	@Param(parameterName = "Function", mandatory = true)
	private String p_Function;

	@Override
	protected String doIt()
	{
		final Evaluatee evalCtx = getEvalContext();
		final String fileNameSuffix = TimeUtil.asLocalDate(p_DateAcctFrom).toString() + "_" + TimeUtil.asLocalDate(p_DateAcctTo).toString();

		final List<ElementValueId> accountIds = factAcctDAO.retrieveAccountsForTimeFrame(AcctSchemaId.ofRepoId(p_C_AcctSchema_ID), p_DateAcctFrom, p_DateAcctTo);
		final List<File> files = new ArrayList<>();

		for (final ElementValueId accountId : accountIds)
		{
			final String sql = getSql(accountId);

			final ElementValue ev = elementValueService.getById(accountId);
			final String fileName = ev.getValue() + "_" + fileNameSuffix;

			final JdbcExcelExporter jdbcExcelExporter = JdbcExcelExporter.builder()
					.ctx(getCtx())
					.translateHeaders(true)
					.applyFormatting(true)
					.fileName(fileName)
					.build();

			spreadsheetExporterService.processDataFromSQL(sql, evalCtx, jdbcExcelExporter);

			final File resultFile = jdbcExcelExporter.getResultFile();

			files.add(resultFile);
		}

		try
		{
			final String zipFileName = TimeUtil.asLocalDate(p_DateAcctFrom).toString() + "_" + TimeUtil.asLocalDate(p_DateAcctTo).toString() + "_";
			final File zipFile = FileUtil.zip(files, zipFileName);
			getResult().setReportData(zipFile);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

		return MSG_OK;
	}

	private String getSql(final ElementValueId accountId)
	{

		final StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ")
				.append(p_Function)
				.append("(")
				.append("p_Account_ID := ")
				.append(accountId.getRepoId())
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

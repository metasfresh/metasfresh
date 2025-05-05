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
import de.metas.organization.IOrgDAO;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.FileUtil;
import de.metas.util.Services;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.compiere.util.Evaluatees;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.compiere.util.TimeUtil.asLocalDate;

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
	final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@Param(parameterName = "C_AcctSchema_ID", mandatory = true)
	private AcctSchemaId p_C_AcctSchema_ID;

	@Param(parameterName = "DateAcctFrom", mandatory = true)
	private Instant p_DateAcctFrom;

	@Param(parameterName = "DateAcctTo", mandatory = true)
	private Instant p_DateAcctTo;

	@Param(parameterName = "Function", mandatory = true)
	private String p_Function;

	@Override
	protected String doIt() throws IOException
	{

		final String fileNameSuffix = getDateAcctFrom() + "_" + getDateAcctTo();

		final List<ElementValueId> accountIds = factAcctDAO.retrieveAccountsForTimeFrame(p_C_AcctSchema_ID, p_DateAcctFrom, p_DateAcctTo);
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
					.fileNamePrefix(fileName)
					.build();

			spreadsheetExporterService.processDataFromSQL(sql, Evaluatees.empty(), jdbcExcelExporter);

			final File resultFile = jdbcExcelExporter.getResultFile();

			files.add(resultFile);
		}

		final String zipFileName = getDateAcctFrom() + "_" + getDateAcctTo() + ".zip";
		final File zipFile = FileUtil.zip(files);
		getResult().setReportData(zipFile, zipFileName);

		return MSG_OK;
	}

	private String getSql(final ElementValueId accountId)
	{
		return "SELECT * FROM "
				+ p_Function
				+ "("
				+ "p_Account_ID := " + accountId.getRepoId()
				+ ", "
				+ "p_C_AcctSchema_ID := " + p_C_AcctSchema_ID.getRepoId()
				+ ", "
				+ "p_DateAcctFrom := '" + getDateAcctFrom()
				+ "', "
				+ "p_DateAcctTo := '" + getDateAcctTo()
				+ "')";
	}

	@Nullable
	private LocalDate getDateAcctFrom()
	{
		final ZoneId zoneId = orgDAO.getTimeZone(Env.getOrgId());
		return asLocalDate(p_DateAcctFrom, zoneId);
	}

	@Nullable
	private LocalDate getDateAcctTo()
	{
		final ZoneId zoneId = orgDAO.getTimeZone(Env.getOrgId());
		return asLocalDate(p_DateAcctTo, zoneId);
	}
}

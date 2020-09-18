/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.impexp.export.process;

import de.metas.impexp.export.DataConsumer;
import de.metas.impexp.export.ResultSetToTableExporterService;
import de.metas.process.JavaProcess;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class ExportToTableProcess extends JavaProcess
{
	final ResultSetToTableExporterService excelExporterService = SpringContextHolder.instance.getBean(ResultSetToTableExporterService.class);

	@Override
	protected String doIt()
	{
		final String sql = getSql();
		final Evaluatee evalCtx = getEvalContext();

		final DataConsumer<ResultSet> jdbcExcelExporter = createDataConsumer();
		excelExporterService.processDataFromSQL(sql, evalCtx, jdbcExcelExporter);

		final File tempFile = jdbcExcelExporter.getResultFile();
		getResult().setReportData(tempFile);

		return MSG_OK;
	}

	protected abstract DataConsumer<ResultSet> createDataConsumer();

	private String getSql()
	{
		return getProcessInfo().getSQLStatement().orElseThrow(() -> new FillMandatoryException("SQLStatement"));
	}

	private Evaluatee getEvalContext()
	{
		final List<Evaluatee> contexts = new ArrayList<>();

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

/**
 *
 */
package de.metas.adempiere.report.jasper;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.X_AD_Process;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import de.metas.logging.LogManager;
import de.metas.process.ProcessParams;
import de.metas.report.engine.ReportContext;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * report-service
 * %%
 * Copyright (C) 2019 metas GmbH
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
 *
 */
@Repository
public class JsonDataSourceRepository
{
	private static final String SYSCONFIG_RESTAPI_URL = "API_URL";
	private final transient Logger log = LogManager.getLogger(JsonDataSourceRepository.class);

	public String getAPI_URL()
	{
		final String url = Services.get(ISysConfigBL.class).getValue(SYSCONFIG_RESTAPI_URL, "");
		if (Check.isEmpty(url, true) || "-".equals(url))
		{
			log.warn("{} is not configured. Jasper Json reports will not work", SYSCONFIG_RESTAPI_URL);
			return null;
		}

		return url.trim();
	}

	public String retrieveSQLValue(@NonNull final ReportContext reportContext)
	{
		//
		// Get SQL
		final String sql = reportContext.getSQLStatement();
		if (!X_AD_Process.TYPE_JasperReportsJSON.equals(reportContext.getType()) || Check.isEmpty(sql, true))
		{
			return null;
		}

		// Parse the SQL Statement
		final IStringExpression sqlExpression = Services.get(IExpressionFactory.class).compile(sql, IStringExpression.class);
		final Evaluatee evalCtx = getEvalContext(reportContext);
		final String sqlFinal = sqlExpression.evaluate(evalCtx, OnVariableNotFound.Fail);

		return DB.getSQLValueStringEx(ITrx.TRXNAME_ThreadInherited, sqlFinal);
	}


	public Evaluatee getEvalContext(@NonNull final ReportContext reportContext)
	{
		final List<Evaluatee> contexts = new ArrayList<>();

		//
		// 1: Add process parameters
		contexts.add(Evaluatees.ofRangeAwareParams(new ProcessParams(reportContext.getProcessInfoParameters())));

		//
		// 2: underlying record
		final String recordTableName = reportContext.getTableNameOrNull();
		final int recordId = reportContext.getRecord_ID();
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
		contexts.add(Evaluatees.ofCtx(Env.getCtx()));

		return Evaluatees.compose(contexts);
	}
}

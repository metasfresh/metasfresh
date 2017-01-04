package de.metas.migration.async;

import java.util.Properties;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.api.IParams;
import org.compiere.util.DB;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;

import com.google.common.annotations.VisibleForTesting;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;

/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Processor used to execute a given SQL code and if there were some records updated,
 * then re-enqueue a new workpackage to execute it again, until nothing left.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ExecuteSQLWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	static final String PARAM_Code = "Code";
	static final String PARAM_ReEnqueue = "ReEnqueue";
	static final boolean DEFAULT_ReEnqueue = true;

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName)
	{
		final ILoggable loggable = Loggables.get();
		final IParams params = getParameters();

		//
		// Extract param: SQL code to execute (and normalize it)
		final String sqlRaw = params.getParameterAsString(PARAM_Code);
		Check.assumeNotEmpty(sqlRaw, "Missing parameter: {}", PARAM_Code);
		final String sql = parseSql(sqlRaw, workPackage);
		loggable.addLog("SQL to execute: {0}", sql);

		//
		// Extract param: ReEnqueue
		final boolean isReEnqueue = params.hasParameter(PARAM_ReEnqueue) ? params.getParameterAsBool(PARAM_ReEnqueue) : DEFAULT_ReEnqueue;

		//
		// Execute the SQL update
		final int updateCount = executeSql(sql, localTrxName);
		loggable.addLog("Updated {0} records", updateCount);

		//
		// Re-enqueue the Workpackage if there was something updated and if we are asked to do so
		if (updateCount > 0)
		{
			if (isReEnqueue)
			{
				final Properties ctx = InterfaceWrapperHelper.getCtx(workPackage);
				final I_C_Queue_WorkPackage nextWorkpackage = Services.get(IWorkPackageQueueFactory.class)
						.getQueueForEnqueuing(ctx, getClass())
						.newBlock()
						.setContext(ctx)
						.newWorkpackage()
						.bindToTrxName(localTrxName)
						//
						// Workpackage Parameters
						.parameters()
						.setParameter(PARAM_Code, sql)
						.end()
						//
						// Build & enqueue
						.build();

				loggable.addLog("New workpackage enqueued: {0}", nextWorkpackage);
			}
			else
			{
				loggable.addLog("No new workpackages will be reenqueued because parameter {0} is false", PARAM_ReEnqueue);
			}
		}
		else
		{
			loggable.addLog("No new workpackages will be reenqueued because there was nothing updated in this run");
		}

		return Result.SUCCESS;
	}

	@VisibleForTesting
	int executeSql(final String sql, final String trxName)
	{
		return DB.executeUpdateEx(sql, trxName);
	}

	private static final String parseSql(final String sqlRaw, final I_C_Queue_WorkPackage workpackage)
	{
		//
		// Normalize
		String sql = sqlRaw.trim()
				.replaceAll("--.*[\r\n\t]", "") // remove one-line-comments (comments within /* and */ are OK)
				.replaceAll("[\r\n\t]", " "); // replace line-breaks with spaces

		//
		// Parse context variables
		{
			final IStringExpression sqlExpression = Services.get(IExpressionFactory.class).compile(sql, IStringExpression.class);
			final Evaluatee evalCtx = Evaluatees.compose(InterfaceWrapperHelper.getEvaluatee(workpackage));

			sql = sqlExpression.evaluate(evalCtx, OnVariableNotFound.Fail);
		}

		return sql;
	}
}

package org.adempiere.ad.migration.executor.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Check;

import org.adempiere.ad.migration.executor.IMigrationExecutor.Action;
import org.adempiere.ad.migration.executor.IMigrationExecutorContext;
import org.adempiere.ad.migration.executor.MigrationExecutorException;
import org.adempiere.ad.migration.executor.impl.AbstractMigrationStepExecutor.ExecutionResult;
import org.adempiere.ad.migration.model.I_AD_MigrationStep;
import org.adempiere.ad.migration.model.X_AD_MigrationStep;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TrxRunnable2;

class MigrationStepExecutorRunnable implements TrxRunnable2
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	private final IMigrationExecutorContext migrationCtx;
	private final I_AD_MigrationStep step;
	private final Action action;

	private Throwable exception;
	private ExecutionResult executionResult = null;

	public MigrationStepExecutorRunnable(final IMigrationExecutorContext migrationCtx, final I_AD_MigrationStep step, final Action action)
	{
		this.migrationCtx = migrationCtx;
		this.step = step;
		this.action = action;
	}

	public ExecutionResult getExecutionResult()
	{
		return executionResult;
	}

	@Override
	public void run(String trxName)
	{
		final CompositeMigrationStepExecutor executor = new CompositeMigrationStepExecutor(migrationCtx, step);

		if (action == Action.Apply)
		{
			try
			{
				executionResult = executor.apply(trxName);
			}
			catch (MigrationExecutorException e)
			{
				if (e.isFatal())
				{
					throw e;
				}
				executionResult = ExecutionResult.Skipped;
				logger.info(e.getLocalizedMessage(), e);
			}
		}
		else if (action == Action.Rollback)
		{
			executionResult = executor.rollback(trxName);
		}
		else
		{
			throw new IllegalStateException("Unknown action: " + action);
		}
	}

	@Override
	public boolean doCatch(Throwable e) throws Throwable
	{
		exception = e;
		throw e;
	}

	@Override
	public void doFinally()
	{
		if (ExecutionResult.Ignored == executionResult)
		{
			// do nothing
			return;
		}

		setExecutionStatus();
		InterfaceWrapperHelper.save(step);
	}

	/**
	 * After step execution, sets ErrorMsg, StatusCode, Apply
	 */
	private void setExecutionStatus()
	{
		// Success
		if (exception == null)
		{
			Check.assumeNotNull(executionResult, "executionResult not null");

			step.setErrorMsg(null);
			if (executionResult == ExecutionResult.Executed)
			{
				if (action == Action.Apply)
				{
					step.setStatusCode(X_AD_MigrationStep.STATUSCODE_Applied);
				}
				else if (action == Action.Rollback)
				{
					step.setStatusCode(X_AD_MigrationStep.STATUSCODE_Unapplied);
				}
				else
				{
					throw new AdempiereException("Unknown action: " + action);
				}
			}
			else if (executionResult == ExecutionResult.Skipped)
			{
				step.setStatusCode(X_AD_MigrationStep.STATUSCODE_Unapplied);
			}
			else
			{
				throw new AdempiereException("Unknown execution result: " + executionResult);
			}
		}
		// Error / Warning
		else
		{
			logger.error("Action " + action + " of " + step + " failed.", exception);

			step.setErrorMsg(exception.getLocalizedMessage());
			if (action == Action.Apply)
			{
				step.setStatusCode(X_AD_MigrationStep.STATUSCODE_Failed);
				step.setApply(X_AD_MigrationStep.APPLY_Apply);
			}
		}

		if (X_AD_MigrationStep.STATUSCODE_Applied.equals(step.getStatusCode()))
		{
			step.setApply(X_AD_MigrationStep.APPLY_Rollback);
		}
		else if (X_AD_MigrationStep.STATUSCODE_Unapplied.equals(step.getStatusCode()))
		{
			step.setApply(X_AD_MigrationStep.APPLY_Apply);
		}
	}
}

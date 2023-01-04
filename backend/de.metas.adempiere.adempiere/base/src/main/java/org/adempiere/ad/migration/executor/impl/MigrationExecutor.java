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


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.adempiere.ad.migration.executor.IMigrationExecutor;
import org.adempiere.ad.migration.executor.IMigrationExecutorContext;
import org.adempiere.ad.migration.executor.IPostponedExecutable;
import org.adempiere.ad.migration.executor.MigrationExecutorException;
import org.adempiere.ad.migration.model.I_AD_Migration;
import org.adempiere.ad.migration.model.I_AD_MigrationStep;
import org.adempiere.ad.migration.model.X_AD_Migration;
import org.adempiere.ad.migration.service.IMigrationBL;
import org.adempiere.ad.migration.service.IMigrationDAO;
import org.adempiere.ad.migration.util.MigrationStepSeqNoComparator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.DB;
import org.compiere.util.Trx;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;

class MigrationExecutor implements IMigrationExecutor
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	private static final String LOCALTRXNAME_PREFIX = "MigrationExecutor";

	private final IMigrationExecutorContext migrationCtx;
	private final I_AD_Migration migration;
	private List<I_AD_MigrationStep> migrationSteps;

	private CommitLevel commitLevel = CommitLevel.Batch;

	private Action action;
	private List<Exception> executionErrors;

	public MigrationExecutor(final IMigrationExecutorContext ctx, int migrationId)
	{
		super();

		if (ctx == null)
		{
			throw new IllegalArgumentException("ctx is null");
		}
		if (migrationId <= 0)
		{
			throw new IllegalArgumentException("migrationId <= 0");
		}

		this.migrationCtx = ctx;

		//
		// Load AD_Migration
		this.migration = InterfaceWrapperHelper.create(ctx.getCtx(), migrationId, I_AD_Migration.class, ITrx.TRXNAME_None); // out of trx
		if (migration == null)
		{
			throw new IllegalArgumentException("No migration found for AD_Migration_ID=" + migrationId);
		}
	}

	@Override
	public I_AD_Migration getAD_Migration()
	{
		return migration;
	}

	@Override
	public CommitLevel getCommitLevel()
	{
		return commitLevel;
	}

	@Override
	public void setCommitLevel(CommitLevel commitLevel)
	{
		this.commitLevel = commitLevel;
	}

	@Override
	public List<I_AD_MigrationStep> getMigrationSteps()
	{
		if (migrationSteps == null)
		{
			migrationSteps = Services.get(IMigrationDAO.class).retrieveSteps(migration, true);
		}
		return migrationSteps;
	}

	@Override
	public void setMigrationSteps(List<I_AD_MigrationStep> steps)
	{
		if (steps == null)
		{
			migrationSteps = Collections.emptyList();
		}
		else
		{
			migrationSteps = new ArrayList<>(steps);
		}
	}

	@Override
	public void execute()
	{
		final I_AD_Migration migration = getAD_Migration();

		final Action action;
		final String actionStr = migration.getApply();
		if (actionStr == null || X_AD_Migration.APPLY_Apply.equals(actionStr))
		{
			action = Action.Apply;
		}
		else if ("N".equals(actionStr))
		{
			// NOTE: there are some cases when this column is set to No.
			action = Action.Apply;
		}
		else if (X_AD_Migration.APPLY_Rollback.equals(actionStr))
		{
			action = Action.Rollback;
		}
		else
		{
			throw new IllegalStateException("Unknown action " + actionStr + " for " + Services.get(IMigrationBL.class).getSummary(migration));
		}

		execute(action);

		executePostponedRequests();
	}

	@Override
	public void execute(final Action action)
	{
		DB.saveConstraints();
		try
		{
			DB.getConstraints().addAllowedTrxNamePrefix(LOCALTRXNAME_PREFIX);

			execute0(action);
		}
		finally
		{
			DB.restoreConstraints();
		}
	}

	private void execute0(final Action action)
	{
		if (action == null)
		{
			throw new IllegalArgumentException("action is null");
		}

		this.action = action;
		this.executionErrors = new ArrayList<>();
		final List<I_AD_MigrationStep> steps = getMigrationSteps();

		log("Executing " + action + " on " + steps.size() + " steps", null, false);

		//
		// Make sure steps are in proper order
		Comparator<I_AD_MigrationStep> stepsCmp = MigrationStepSeqNoComparator.instance;
		if (action == Action.Rollback)
		{
			stepsCmp = Collections.reverseOrder(stepsCmp);
		}
		Collections.sort(steps, stepsCmp);

		final String trxName;
		final Trx localTrx;
		if (commitLevel == CommitLevel.Batch)
		{
			trxName = Trx.createTrxName("MigrationExecutor");
			localTrx = Trx.get(trxName, true);

			if (migration.isDeferredConstraints())
			{
				disableConstraints(trxName);
			}
			else
			{
				enableConstraints(trxName);
			}
		}
		else if (commitLevel == CommitLevel.Step)
		{
			// we will execute each step in a generated transaction
			trxName = null;
			localTrx = null;
		}
		else
		{
			throw new AdempiereException("Not supported commit level: " + commitLevel);
		}

		try
		{
			this.executionErrors = new ArrayList<>();
			for (final I_AD_MigrationStep step : steps)
			{
				if (!step.isActive())
				{
					log("Skipped inactive step " + step.getSeqNo(), "SKIP", false);
					continue;
				}

				//
				// Make sure our step is in the right transaction
				if (!Objects.equals(trxName, InterfaceWrapperHelper.getTrxName(step)))
				{
					InterfaceWrapperHelper.refresh(step, trxName);
				}

				try
				{
					final MigrationStepExecutorRunnable executorRunnable = new MigrationStepExecutorRunnable(migrationCtx, step, action);
					Services.get(ITrxManager.class).run(trxName, executorRunnable);
				}
				catch (Exception e)
				{
					executionErrors.add(e);

					// Abort on first error
					final boolean fatal = MigrationExecutorException.isFatal(e);
					if (fatal && migrationCtx.isFailOnFirstError())
					{
						throw e instanceof MigrationExecutorException ? (MigrationExecutorException)e : new MigrationExecutorException(e);
					}
				}
			}
		}
		finally
		{
			// If is a locally managed trx
			if (localTrx != null)
			{
				if (executionErrors != null && !executionErrors.isEmpty())
				{
					localTrx.rollback();
				}
				else
				{
					localTrx.commit();
				}
				localTrx.close();
			}

			// Make sure we have updated migration status before throwing further
			updateMigrationStatus();
		}
	}

	private void executePostponedRequests()
	{
		final List<IPostponedExecutable> executables = migrationCtx.popPostponedExecutables();
		for (IPostponedExecutable executable : executables)
		{
			executable.execute();
		}
	}

	private void disableConstraints(final String trxName)
	{
		final String sql = "SET CONSTRAINTS ALL DEFERRED";
		DB.executeUpdateAndThrowExceptionOnFail(sql, trxName);

		logger.info("Constraints deferred");
	}

	private void enableConstraints(final String trxName)
	{
		final String sql = "SET CONSTRAINTS ALL IMMEDIATE";
		DB.executeUpdateAndThrowExceptionOnFail(sql, trxName);

		logger.info("Constraints immediate");
	}

	private void updateMigrationStatus()
	{
		Services.get(IMigrationBL.class).updateStatus(migration);
	}

	@Override
	public String getStatusDescription()
	{
		final String statusCode = migration.getStatusCode();

		if (action == Action.Apply)
		{
			if (X_AD_Migration.STATUSCODE_Applied.equals(statusCode))
			{
				return "Migration successful";
			}
			else if (X_AD_Migration.STATUSCODE_PartiallyApplied.equals(statusCode))
			{
				return "Migration partially applied. Please review migration steps for errors.";
			}
			else if (X_AD_Migration.STATUSCODE_Failed.equals(statusCode))
			{
				return "Migration failed. Please review migration steps for errors.";
			}
		}
		else if (action == Action.Rollback)
		{
			if (X_AD_Migration.STATUSCODE_Unapplied.equals(statusCode))
			{
				return "Rollback successful.";
			}
			else if (X_AD_Migration.STATUSCODE_PartiallyApplied.equals(statusCode))
			{
				return "Migration partially rollback. Please review migration steps for errors.";
			}
			else
			{
				return "Rollback failed. Please review migration steps for errors.";
			}
		}

		//
		// Default
		return "@Action@=" + action + ", @StatusCode@=" + statusCode;
	}

	@Override
	public List<Exception> getExecutionErrors()
	{
		if (executionErrors == null)
		{
			return Collections.emptyList();
		}
		else
		{
			return new ArrayList<>(executionErrors);
		}
	}

	private final void log(String msg, String resolution, boolean isError)
	{
		if (isError && !logger.isErrorEnabled())
		{
			return;
		}
		if (!isError && !logger.isInfoEnabled())
		{
			return;
		}

		final StringBuffer sb = new StringBuffer();
		sb.append(Services.get(IMigrationBL.class).getSummary(migration));

		if (!Check.isEmpty(msg, true))
		{
			sb.append(": ").append(msg.trim());
		}

		if (resolution != null)
		{
			sb.append(" [").append(resolution).append("]");
		}

		if (isError)
		{
			logger.error(sb.toString());
		}
		else
		{
			logger.info(sb.toString());
		}
	}
}

/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/

package org.compiere.process;

import java.util.Arrays;

import org.adempiere.ad.migration.executor.IMigrationExecutor;
import org.adempiere.ad.migration.executor.IMigrationExecutor.Action;
import org.adempiere.ad.migration.executor.IMigrationExecutorContext;
import org.adempiere.ad.migration.executor.IMigrationExecutorProvider;
import org.adempiere.ad.migration.model.I_AD_MigrationStep;
import org.adempiere.ad.migration.model.X_AD_MigrationStep;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.process.SvrProcess;

/**
 * 
 * Process to apply a single migration step
 * 
 * @author Paul Bowden, Adaxa Pty Ltd
 * @author Teo Sarca
 * 
 */
public class MigrationStepApply extends SvrProcess
{

	private I_AD_MigrationStep migrationStep;
	private Action action = null;

	@Override
	protected void prepare()
	{
		if (I_AD_MigrationStep.Table_Name.equals(getTableName()) && getRecord_ID() > 0)
		{
			migrationStep = InterfaceWrapperHelper.create(getCtx(), getRecord_ID(), I_AD_MigrationStep.class, null);
		}
	}

	public Action getAction()
	{
		return action;
	}

	public void setAction(Action action)
	{
		this.action = action;
	}

	public I_AD_MigrationStep getMigrationStep()
	{
		return migrationStep;
	}

	@Override
	protected String doIt() throws Exception
	{
		if (migrationStep == null || migrationStep.getAD_MigrationStep_ID() <= 0)
		{
			throw new AdempiereException("@NotFound@ AD_MigrationStep_ID@");
		}

		final IMigrationExecutorProvider executorProvider = Services.get(IMigrationExecutorProvider.class);
		final IMigrationExecutorContext migrationCtx = executorProvider.createInitialContext(getCtx());

		final IMigrationExecutor executor = executorProvider.newMigrationExecutor(migrationCtx, migrationStep.getAD_Migration_ID());
		executor.setMigrationSteps(Arrays.asList(migrationStep));

		if (action == null)
		{
			action = getAction(migrationStep);
		}

		executor.execute(action);

		return "Executed: " + action;
	}

	protected Action getAction(I_AD_MigrationStep step)
	{
		if (X_AD_MigrationStep.STATUSCODE_Applied.equals(migrationStep.getStatusCode()))
		{
			return IMigrationExecutor.Action.Rollback;
		}
		else
		{
			return IMigrationExecutor.Action.Apply;
		}
	}
}

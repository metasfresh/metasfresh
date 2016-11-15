package de.metas.adempiere.process;

/*
 * #%L
 * de.metas.adempiere.adempiere.serverRoot.base
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


import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.MScheduler;
import org.compiere.model.MTable;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.server.AdempiereServer;
import org.compiere.server.AdempiereServerMgr;

public class ManageScheduler extends SvrProcess
{
	private static final String PARAM_ACTION = "Action";

	private static final String PARAM_RETRY_IF_STOP_FAILS = "RetryIfStopFails";

	private static final String ACTION_RESTART = "RESTART";

	private static final String ACTION_STOP = "STOP";

	private static final String ACTION_START = "START";

	private int p_AD_Scheduler_ID = -1;

	private boolean retryIfStopFails = false;

	private String action;

	@Override
	protected void prepare()
	{
		if (getTable_ID() == MTable.getTable_ID(MScheduler.Table_Name))
		{
			p_AD_Scheduler_ID = getRecord_ID();
		}

		final ProcessInfoParameter[] para = getParametersAsArray();

		for (int i = 0; i < para.length; i++)
		{
			final String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
			{
				;
			}
			else if (name.equals(PARAM_RETRY_IF_STOP_FAILS))
			{
				retryIfStopFails = "Y".equals(para[i].getParameter());
			}
			else if (name.equals(PARAM_ACTION))
			{
				action = (String)para[i].getParameter();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		if (p_AD_Scheduler_ID <= 0)
			throw new FillMandatoryException("AD_Scheduler_ID");

		final MScheduler schedulerModel = new MScheduler(getCtx(), p_AD_Scheduler_ID, null);

		if (isStop() && !stop(schedulerModel))
		{
			return "@Error@";
		}

		if (isStart() && !start(schedulerModel))
		{
			return "@Error@";
		}
		return "@Success@";
	}

	private boolean isStart()
	{
		return ACTION_START.equals(action) || ACTION_RESTART.equals(action);
	}

	private boolean isStop()
	{
		return ACTION_STOP.equals(action) || ACTION_RESTART.equals(action);
	}

	private boolean start(final MScheduler schedulerModel)
	{
		final AdempiereServerMgr adempiereServerMgr = AdempiereServerMgr.get();
		
		final AdempiereServer scheduler = adempiereServerMgr.getServer(schedulerModel.getServerID());
		if (scheduler == null)
		{
			adempiereServerMgr.add(schedulerModel);
			addLog("Created server scheduler for " + schedulerModel.getName());
		}

		adempiereServerMgr.start(schedulerModel.getServerID());
		addLog("Started " + schedulerModel.getName());
		return true;
	}

	private boolean stop(final MScheduler schedulerModel) throws InterruptedException
	{
		final AdempiereServerMgr adempiereServerMgr = AdempiereServerMgr.get(); // adempiereAlreadyStarted == true
		
		// making sure that the scheduler exists on the server
		final AdempiereServer scheduler = adempiereServerMgr.getServer(schedulerModel.getServerID());
		if (scheduler == null)
		{
			addLog("Scheduler " + schedulerModel.getName() + " is not registered on the server");
			return false;
		}

		addLog("Stopping scheduler " + schedulerModel.getName());

		if (!adempiereServerMgr.stop(schedulerModel.getServerID()))
		{
			if (retryIfStopFails)
			{
				addLog("Scheduler " + schedulerModel.getName() + " seems to be running right now");
				do
				{
					addLog("Will retry in 5 seconds...");
					Thread.sleep(5000);
				}
				while (!adempiereServerMgr.stop(schedulerModel.getServerID()));
			}
			else
			{
				addLog("Failed to stop scheduler " + schedulerModel.getName());
				return false;
			}
		}
		addLog("Sucessfully stopped scheduler " + schedulerModel.getName());
		return true;
	}
}

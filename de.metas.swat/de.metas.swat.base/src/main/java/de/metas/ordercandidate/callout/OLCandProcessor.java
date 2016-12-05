package de.metas.ordercandidate.callout;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.AWindow;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.GridTab.DataNewCopyMode;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.I_AD_RelationType;
import org.compiere.model.I_AD_Scheduler;
import org.compiere.model.MQuery;
import org.compiere.model.MRelationType;
import org.compiere.model.MSchedulerPara;
import org.compiere.model.X_AD_Scheduler;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.model.I_C_OLCandProcessor;
import de.metas.ordercandidate.process.ProcessOLCands;
import de.metas.process.IADProcessDAO;
import de.metas.relation.grid.ModelRelationTarget;
import de.metas.relation.grid.VRelationTarget;

public class OLCandProcessor extends CalloutEngine
{

	private static final String MSG_MISSING_RELTYPE = "OLCandProcessor.MissingRelationType";

	public String assignOLCands(
			final Properties ctx,
			final int WindowNo,
			final GridTab mTab,
			final GridField mField,
			final Object value)
	{
		if (isCalloutActive())
		{
			return "";
		}

		final I_C_OLCandProcessor processor = InterfaceWrapperHelper.create(mTab, I_C_OLCandProcessor.class);

		if (processor.getC_OLCandProcessor_ID() <= 0)
		{
			return "";
		}

		final int sourceWindowId = mTab.getAD_Window_ID();
		final String sourceTabName = mTab.getName();
		
		final IOLCandBL olCandBL = Services.get(IOLCandBL.class);
		
		final ModelRelationTarget model = olCandBL.mkModelRelationTarget(processor, sourceWindowId, sourceTabName, "");
		final VRelationTarget relationTarget;

		// TODO find out if we are dealing with ZK
		relationTarget = new VRelationTarget(WindowNo, model);

		relationTarget.showWindow();

		return "";
	}

	public String adSchedulerOLCandProcessor(
			final Properties ctx,
			final int WindowNo,
			final GridTab mTab,
			final GridField mField,
			final Object value)
	{
		if (isCalloutActive())
		{
			return "";
		}

		final I_C_OLCandProcessor processor = InterfaceWrapperHelper.create(mTab, I_C_OLCandProcessor.class);
		if (processor.getC_OLCandProcessor_ID() <= 0)
		{
			return "";
		}

		final IOLCandBL olCandBL = Services.get(IOLCandBL.class);
		final I_AD_RelationType relType = MRelationType.retrieveForInternalName(ctx, olCandBL.mkRelationTypeInternalName(processor), null);

		if (relType == null)
		{
			ADialog.error(WindowNo, null, MSG_MISSING_RELTYPE);
			return Msg.getMsg(ctx, MSG_MISSING_RELTYPE);
		}

		if (openSchedulerWindow(ctx, processor))
		{
			return "";
		}

		return "Fehler";
	}

	private boolean openSchedulerWindow(final Properties ctx, final I_C_OLCandProcessor processor)
	{
		final String trxName = null;
		final int AD_SCHEDULER_WINDOW_ID = 305;

		final AWindow schedulerFrame = new AWindow();

		final int schedulerId = processor.getAD_Scheduler_ID();

		final MQuery query = new MQuery(I_AD_Scheduler.Table_Name);
		if (schedulerId > 0)
		{
			query.addRestriction(I_AD_Scheduler.COLUMNNAME_AD_Scheduler_ID + "=" + schedulerId);
		}
		final boolean success = schedulerFrame.initWindow(AD_SCHEDULER_WINDOW_ID, query);
		if (!success)
		{
			return false;
		}

		schedulerFrame.pack();

		final GridTab tab = schedulerFrame.getAPanel().getCurrentTab();
		if (schedulerId <= 0)
		{
			final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class); 

			final int adProcessId = adProcessDAO.retriveProcessIdByClassIfUnique(ctx, ProcessOLCands.class);
			final I_AD_Process_Para processPara = adProcessDAO.retriveProcessParameter(ctx, adProcessId, ProcessOLCands.PARAM_C_OLCandProcessor_ID);

			tab.dataNew(DataNewCopyMode.NoCopy);
			tab.setValue(I_AD_Scheduler.COLUMNNAME_Name, Msg.translate(ctx, I_C_OLCandProcessor.COLUMNNAME_C_OLCandProcessor_ID) + " \"" + processor.getName() + "\"");
			tab.setValue(I_AD_Scheduler.COLUMNNAME_SchedulerProcessType, X_AD_Scheduler.SCHEDULERPROCESSTYPE_Process);
			tab.setValue(I_AD_Scheduler.COLUMNNAME_AD_Process_ID, adProcessId);
			tab.setValue(I_AD_Scheduler.COLUMNNAME_FrequencyType, X_AD_Scheduler.FREQUENCYTYPE_Day);
			tab.setValue(I_AD_Scheduler.COLUMNNAME_Frequency, 1);
			tab.setValue(I_AD_Scheduler.COLUMNNAME_Supervisor_ID, Env.getAD_User_ID(ctx));
			tab.setValue(I_AD_Scheduler.COLUMNNAME_MonthDay, 1);
			if (tab.getValue(I_AD_Scheduler.COLUMNNAME_Supervisor_ID) == null)
			{
				tab.setValue(I_AD_Scheduler.COLUMNNAME_Supervisor_ID, 0);
			}
			if (!tab.dataSave(true))
			{
				return false;
			}

			final int schedulerIdToUse = (Integer)tab.getValue(I_AD_Scheduler.COLUMNNAME_AD_Scheduler_ID);

			processor.setAD_Scheduler_ID(schedulerIdToUse);

			final MSchedulerPara newPara = new MSchedulerPara(ctx, 0, trxName);
			newPara.setAD_Scheduler_ID(schedulerIdToUse);
			newPara.setAD_Process_Para(processPara);
			newPara.setParameterDefault(Integer.toString(processor.getC_OLCandProcessor_ID()));
			newPara.saveEx();
		}

		AEnv.showCenterScreen(schedulerFrame);

		return success;
	}
}

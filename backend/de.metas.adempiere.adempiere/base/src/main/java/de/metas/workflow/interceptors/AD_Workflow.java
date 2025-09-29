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

package de.metas.workflow.interceptors;

import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.workflow.service.IADWorkflowBL;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.MMenu;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.slf4j.Logger;

@Interceptor(I_AD_Workflow.class)
public class AD_Workflow
{
	private final Logger logger = LogManager.getLogger(getClass());

	@Init
	public void init()
	{
		CopyRecordFactory.enableForTableName(I_AD_Workflow.Table_Name);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_AD_Workflow workflow, final ModelChangeType changeType)
	{
		final String errorMsg = Services.get(IADWorkflowBL.class).validateAndGetErrorMsg(workflow);

		// NOTE: don't prevent workflow from saving, just let the IsValid flag to be reset
		// Don't log warning if new, because new WFs are not valid (no start node can be set because there is none).
		if (!Check.isEmpty(errorMsg, true) && !changeType.isNew())
		{
			logger.warn("Workflow {} is marked as invalid because {}", workflow, errorMsg);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(final I_AD_Workflow workflow, final ModelChangeType changeType)
	{
		if (changeType.isNew())
		{
			// nothing
		}
		// Menu/Workflow update
		else if (InterfaceWrapperHelper.isValueChanged(
				workflow,
				I_AD_Workflow.COLUMNNAME_IsActive,
				I_AD_Workflow.COLUMNNAME_Name,
				I_AD_Workflow.COLUMNNAME_Description,
				I_AD_Workflow.COLUMNNAME_Help))
		{
			final MMenu[] menues = MMenu.get(Env.getCtx(), "AD_Workflow_ID=" + workflow.getAD_Workflow_ID(), ITrx.TRXNAME_ThreadInherited);
			for (final MMenu menue : menues)
			{
				menue.setIsActive(workflow.isActive());
				menue.setName(workflow.getName());
				menue.setDescription(workflow.getDescription());
				menue.saveEx();
			}
			// TODO: teo_sarca: why do we need to sync node name with workflow name? - see BF 2665963
			//			X_AD_WF_Node[] nodes = MWindow.getWFNodes(getCtx(), "AD_Workflow_ID=" + getAD_Workflow_ID(), get_TrxName());
			//			for (int i = 0; i < nodes.length; i++)
			//			{
			//				boolean changed = false;
			//				if (nodes[i].isActive() != isActive())
			//				{
			//					nodes[i].setIsActive(isActive());
			//					changed = true;
			//				}
			//				if (nodes[i].isCentrallyMaintained())
			//				{
			//					nodes[i].setName(getName());
			//					nodes[i].setDescription(getDescription());
			//					nodes[i].setHelp(getHelp());
			//					changed = true;
			//				}
			//				if (changed)
			//					nodes[i].saveEx();
			//			}
		}
	}

}

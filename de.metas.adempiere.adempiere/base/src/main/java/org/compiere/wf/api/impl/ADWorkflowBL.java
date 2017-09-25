package org.compiere.wf.api.impl;

import java.math.BigDecimal;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Date;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.X_AD_Workflow;
import org.compiere.util.Env;
import org.compiere.wf.MWorkflow;
import org.compiere.wf.api.IADWorkflowBL;
import org.compiere.wf.api.IADWorkflowDAO;
import org.compiere.wf.exceptions.WorkflowNotValidException;

import lombok.NonNull;

public class ADWorkflowBL implements IADWorkflowBL
{
	@Override
	public boolean isValidFromTo(final I_AD_Workflow workflow, final Date date)
	{
		final Date validFrom = workflow.getValidFrom();

		if (validFrom != null && date.before(validFrom))
		{
			return false;
		}

		final Date validTo = workflow.getValidTo();
		if (validTo != null && date.after(validTo))
		{
			return false;
		}
		return true;
	}

	@Override
	public boolean isValidFromTo(final I_AD_WF_Node node, final Date date)
	{
		final Date validFrom = node.getValidFrom();
		if (validFrom != null && date.before(validFrom))
		{
			return false;
		}

		final Date validTo = node.getValidTo();
		if (validTo != null && date.after(validTo))
		{
			return false;
		}
		return true;
	}

	@Override
	public void validate(final I_AD_Workflow workflow)
	{
		final StringBuilder errors = new StringBuilder();
		//
		if (workflow.getAD_WF_Node_ID() == 0)
			errors.append(" - No Start Node");
		//
		if (X_AD_Workflow.WORKFLOWTYPE_DocumentValue.equals(workflow.getWorkflowType())
				&& (workflow.getDocValueLogic() == null || workflow.getDocValueLogic().length() == 0))
			errors.append(" - No Document Value Logic");
		//

		//
		if (workflow.getWorkflowType().equals(MWorkflow.WORKFLOWTYPE_Manufacturing))
		{
			workflow.setAD_Table_ID(0);
		}

		// final
		final boolean valid = errors.length() == 0;
		workflow.setIsValid(valid);
		if (!valid)
		{
			throw new WorkflowNotValidException(workflow, errors.toString());
		}
	}

	@Override
	public String getWorkflowName(int AD_Workflow_ID)
	{
		if (AD_Workflow_ID > 0)
		{
			final I_AD_Workflow workflow = InterfaceWrapperHelper.create(Env.getCtx(), AD_Workflow_ID, I_AD_Workflow.class, ITrx.TRXNAME_None);
			return workflow.getName();
		}

		return null;
	}

	@Override
	public I_AD_Workflow createWorkflowForDocument(@NonNull final I_AD_Table document)
	{
		// Services
		final IADWorkflowDAO workflowDAO = Services.get(IADWorkflowDAO.class);

		final I_AD_Workflow workflow = InterfaceWrapperHelper.newInstance(I_AD_Workflow.class);

		final String workflowValue = "Process_" + getDocumentName(document);

		// Document specific details
		workflow.setValue(workflowValue);
		workflow.setName(workflowValue);
		workflow.setAD_Table_ID(document.getAD_Table_ID());

		// Mandatory fields
		workflow.setWorkflowType(X_AD_Workflow.WORKFLOWTYPE_DocumentProcess);
		workflow.setAccessLevel(X_AD_Workflow.ACCESSLEVEL_Organization);
		workflow.setEntityType(X_AD_Workflow.ENTITYTYPE_Dictionary);
		workflow.setPublishStatus(X_AD_Workflow.PUBLISHSTATUS_Released);
		workflow.setVersion(0);
		workflow.setAuthor(Adempiere.getName());

		// Simulation details
		workflow.setDuration(1);
		workflow.setCost(BigDecimal.ZERO);
		workflow.setWorkingTime(0);
		workflow.setWaitingTime(0);

		// Other general details

		// The suggested workflow node will be one with the action X_AD_WF_Node.ACTION_WaitSleep, if exists.
		// Otherwise it will be not set, which is acceptable, since it's not a mandatory field.
		final int waitSleepWorkflowNodeId = workflowDAO.retrieveWaitSleepWorkflowNodeID(workflow);
		workflow.setAD_WF_Node_ID(waitSleepWorkflowNodeId);

		workflow.setDurationUnit(X_AD_Workflow.DURATIONUNIT_Day);

		InterfaceWrapperHelper.save(workflow);

		return workflow;
	}

	private String getDocumentName(@NonNull final I_AD_Table document)
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		return adTableDAO.retrieveTableName(document.getAD_Table_ID());
	}

}

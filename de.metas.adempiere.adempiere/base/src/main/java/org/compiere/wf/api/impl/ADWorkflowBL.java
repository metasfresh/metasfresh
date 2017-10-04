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
import org.compiere.model.I_AD_WF_NodeNext;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.X_AD_WF_Node;
import org.compiere.model.X_AD_Workflow;
import org.compiere.util.Env;
import org.compiere.wf.ADWorkflowConstants;
import org.compiere.wf.MWorkflow;
import org.compiere.wf.api.IADWorkflowBL;
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

		// General details
		workflow.setDuration(1);
		workflow.setCost(BigDecimal.ZERO);
		workflow.setWorkingTime(0);
		workflow.setWaitingTime(0);
		workflow.setDurationUnit(X_AD_Workflow.DURATIONUNIT_Day);

		InterfaceWrapperHelper.save(workflow);

		createAndLinkWorkflowNodesForDocumentWorkflow(workflow);

		return workflow;
	}

	private String getDocumentName(@NonNull final I_AD_Table document)
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		return adTableDAO.retrieveTableName(document.getAD_Table_ID());
	}

	private void createAndLinkWorkflowNodesForDocumentWorkflow(@NonNull I_AD_Workflow documentWorkflow)
	{
		// Create start Workflow Node
		final I_AD_WF_Node startWorkflowNode = createWorkflowNode(documentWorkflow, ADWorkflowConstants.WF_NODE_Start_Name, X_AD_WF_Node.ACTION_WaitSleep, null);

		// set this node as the start node in the workflow
		documentWorkflow.setAD_WF_Node_ID(startWorkflowNode.getAD_WF_Node_ID());
		InterfaceWrapperHelper.save(documentWorkflow);
		
		// Create DocAuto Workflow Node
		final I_AD_WF_Node docAutoWorkflowNode = createWorkflowNode(documentWorkflow, ADWorkflowConstants.WF_NODE_DocAuto_Name, X_AD_WF_Node.ACTION_DocumentAction, X_AD_WF_Node.DOCACTION_None);

		// Create DocPrepare Workflow Node
		final I_AD_WF_Node docPrepareWorkflowNode = createWorkflowNode(documentWorkflow, ADWorkflowConstants.WF_NODE_DocPrepare_Name, X_AD_WF_Node.ACTION_DocumentAction, X_AD_WF_Node.DOCACTION_Prepare);

		// Create DocComplete Workflow Node
		final I_AD_WF_Node docCompleteWorkflowNode = createWorkflowNode(documentWorkflow, ADWorkflowConstants.WF_NODE_DocComplete_Name, X_AD_WF_Node.ACTION_DocumentAction, X_AD_WF_Node.DOCACTION_Complete);

		// Link workflow nodes Start and DocPrepare
		final I_AD_WF_NodeNext startToDocPrepare = linkNextNode(startWorkflowNode, docPrepareWorkflowNode, 10);
		startToDocPrepare.setDescription("(Standard Approval)");
		startToDocPrepare.setIsStdUserWorkflow(true);
		InterfaceWrapperHelper.save(startToDocPrepare);

		// Link workflow nodes Start and DocAuto
		linkNextNode(startWorkflowNode, docAutoWorkflowNode, 100);

		// Link workflos nodes Prepare and Complete
		linkNextNode(docPrepareWorkflowNode, docCompleteWorkflowNode, 100);

	}

	private I_AD_WF_Node createWorkflowNode(@NonNull final I_AD_Workflow workflow, @NonNull final String name, final String action, final String docAction)
	{
		final I_AD_WF_Node workflowNode = InterfaceWrapperHelper.newInstance(I_AD_WF_Node.class);

		// Specific details
		workflowNode.setAD_Workflow(workflow);
		workflowNode.setName(name);
		workflowNode.setValue(name);
		workflowNode.setAction(action);
		workflowNode.setDocAction(docAction);

		// General details
		workflowNode.setDescription("(Standard Node)");
		workflowNode.setEntityType(workflow.getEntityType());

		workflowNode.setSplitElement(X_AD_WF_Node.JOINELEMENT_XOR);
		workflowNode.setJoinElement(X_AD_WF_Node.JOINELEMENT_XOR);

		InterfaceWrapperHelper.save(workflowNode);

		return workflowNode;

	}

	private I_AD_WF_NodeNext linkNextNode(@NonNull final I_AD_WF_Node workflowSourceNode, @NonNull final I_AD_WF_Node workflowTargetNode, int seqNo)
	{
		final I_AD_WF_NodeNext workflowNodeNext = InterfaceWrapperHelper.newInstance(I_AD_WF_NodeNext.class);

		// Specific Details
		workflowNodeNext.setAD_WF_Node_ID(workflowSourceNode.getAD_WF_Node_ID());
		workflowNodeNext.setAD_WF_Next_ID(workflowTargetNode.getAD_WF_Node_ID());
		workflowNodeNext.setSeqNo(seqNo);
		workflowNodeNext.setEntityType(workflowSourceNode.getEntityType());
		workflowNodeNext.setDescription("Standard Transition");

		InterfaceWrapperHelper.save(workflowNodeNext);

		return workflowNodeNext;
	}

}

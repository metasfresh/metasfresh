/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.workflow.service.impl;

import com.google.common.collect.ImmutableList;
import de.metas.copy_with_details.GeneralCopyRecordSupport;
import de.metas.copy_with_details.template.CopyTemplate;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.workflow.WFNodeId;
import de.metas.workflow.WorkflowId;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.PO;
import org.eevolution.model.I_PP_WF_Node_Product;

public class AD_WF_Node_CopyRecordSupport extends GeneralCopyRecordSupport
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	protected void onRecordAndChildrenCopied(final PO to, final PO from, final CopyTemplate template)
	{
		final I_AD_WF_Node toWFNode = InterfaceWrapperHelper.create(to, I_AD_WF_Node.class);
		final I_AD_WF_Node fromWFNode = InterfaceWrapperHelper.create(from, I_AD_WF_Node.class);
		cloneWFNodeProductsForWFNode(toWFNode, fromWFNode);

		ClonedWFNodesInfo.getOrCreate(getTargetWorkflow())
				.addOriginalToClonedWFStepMapping(WFNodeId.ofRepoId(fromWFNode.getAD_WF_Node_ID()),
												  WFNodeId.ofRepoId(toWFNode.getAD_WF_Node_ID()));
	}

	private I_AD_Workflow getTargetWorkflow()
	{
		return Check.assumeNotNull(getParentModel(I_AD_Workflow.class), "target workflow is not null");
	}

	private void cloneWFNodeProductsForWFNode(@NonNull final I_AD_WF_Node toWFNode, @NonNull final I_AD_WF_Node fromWFNode)
	{
		getWFNodeProductsByWorkflowIdAndWFNodeId(WorkflowId.ofRepoId(fromWFNode.getAD_Workflow_ID()), WFNodeId.ofRepoId(fromWFNode.getAD_WF_Node_ID()))
				.forEach(wfNodeProduct -> cloneWFNodeProductAndSetNewWorkflowAndWFNode(wfNodeProduct, WFNodeId.ofRepoId(toWFNode.getAD_WF_Node_ID()), WorkflowId.ofRepoId(toWFNode.getAD_Workflow_ID())));
	}

	public ImmutableList<I_PP_WF_Node_Product> getWFNodeProductsByWorkflowIdAndWFNodeId(
			@NonNull final WorkflowId fromWorkflowId,
			@NonNull final WFNodeId fromWFNodeId)
	{
		return queryBL.createQueryBuilder(I_PP_WF_Node_Product.class)
				.addEqualsFilter(I_PP_WF_Node_Product.COLUMNNAME_AD_Workflow_ID, fromWorkflowId.getRepoId())
				.addEqualsFilter(I_PP_WF_Node_Product.COLUMNNAME_AD_WF_Node_ID, fromWFNodeId.getRepoId())
				.create()
				.stream()
				.collect(ImmutableList.toImmutableList());
	}

	private void cloneWFNodeProductAndSetNewWorkflowAndWFNode(
			final @NonNull I_PP_WF_Node_Product wfNodeProduct,
			final @NonNull WFNodeId toWFNodeId,
			final @NonNull WorkflowId toWorkflowId)
	{
		final I_PP_WF_Node_Product newWFNodeProduct = InterfaceWrapperHelper.newInstance(I_PP_WF_Node_Product.class);
		InterfaceWrapperHelper.copyValues(wfNodeProduct, newWFNodeProduct);
		newWFNodeProduct.setAD_WF_Node_ID(toWFNodeId.getRepoId());
		newWFNodeProduct.setAD_Workflow_ID(toWorkflowId.getRepoId());
		InterfaceWrapperHelper.saveRecord(newWFNodeProduct);
	}
}

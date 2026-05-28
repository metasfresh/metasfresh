/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2026 metas GmbH
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
package de.metas.material.planning.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/**
 * Validates that {@code AD_Workflow.AD_WF_Node_ID} (the configured first / start node of a workflow)
 * always points at a node that the routing-loader will accept: active, belonging to the same workflow,
 * and having a non-null {@code S_Resource_ID}.
 * <p>
 * Companion to {@link AD_WF_Node} (which prevents the first node from being deactivated / deleted /
 * having its resource removed from the other side). Together they keep
 * {@code PPRoutingRepository.toRouting()} from blowing up with the noisy 6 KB save-status message.
 */
@Interceptor(I_AD_Workflow.class)
@Component
public class AD_Workflow
{
	@ModelChange(
			timings = ModelValidator.TYPE_BEFORE_CHANGE,
			ifColumnsChanged = I_AD_Workflow.COLUMNNAME_AD_WF_Node_ID)
	public void validateFirstNode(final I_AD_Workflow workflow)
	{
		final int wfNodeId = workflow.getAD_WF_Node_ID();
		if (wfNodeId <= 0)
		{
			// Clearing the first node is not blocked here — the loader handles wfNodeId<=0 with its own
			// specific exception (AD_Workflow_StartNode_NotSet). We only validate that, when a value is
			// being set, it is a usable target.
			return;
		}

		final I_AD_WF_Node targetNode = InterfaceWrapperHelper.load(wfNodeId, I_AD_WF_Node.class);
		if (targetNode == null)
		{
			throw new AdempiereException("@PPRouting_FirstNodeInvalid@ - target AD_WF_Node_ID=" + wfNodeId + " not found");
		}

		if (!targetNode.isActive())
		{
			throw new AdempiereException("@PPRouting_FirstNodeInvalid@ - target AD_WF_Node_ID=" + wfNodeId + " is inactive");
		}

		if (targetNode.getAD_Workflow_ID() != workflow.getAD_Workflow_ID())
		{
			throw new AdempiereException("@PPRouting_FirstNodeInvalid@ - target AD_WF_Node_ID=" + wfNodeId
					+ " belongs to a different workflow (AD_Workflow_ID=" + targetNode.getAD_Workflow_ID()
					+ "), expected AD_Workflow_ID=" + workflow.getAD_Workflow_ID());
		}

		if (targetNode.getS_Resource_ID() <= 0)
		{
			throw new AdempiereException("@PPRouting_FirstNodeInvalid@ - target AD_WF_Node_ID=" + wfNodeId
					+ " has no S_Resource_ID; the routing-loader filters such nodes out");
		}
	}
}

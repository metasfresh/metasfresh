/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.workflow;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.workflow.WorkflowId;
import lombok.NonNull;
import org.compiere.model.I_AD_Workflow;

import java.util.HashSet;
import java.util.Set;

public class AD_Workflow_StepDefData extends StepDefData<I_AD_Workflow>
		implements StepDefDataGetIdAware<PPRoutingId, I_AD_Workflow>
{
	/**
	 * Every {@code AD_Workflow} that {@code AD_Workflow_StepDef} itself created (directly or via {@code clone
	 * AD_Workflow:}) -- as opposed to one merely registered here via {@code load AD_Workflow:} (masterdata).
	 * {@code AD_WF_Node_StepDef}'s own teardown reads this (through {@link #isCreated(WorkflowId)}) to scope its
	 * first-node-pointer clearing to workflows the fixture owns, never a loaded one.
	 */
	private final Set<WorkflowId> createdWorkflowIds = new HashSet<>();

	public AD_Workflow_StepDefData()
	{
		super(I_AD_Workflow.class);
	}

	@Override
	public PPRoutingId extractIdFromRecord(final I_AD_Workflow record)
	{
		return PPRoutingId.ofRepoId(record.getAD_Workflow_ID());
	}

	public void markCreated(@NonNull final WorkflowId workflowId)
	{
		createdWorkflowIds.add(workflowId);
	}

	public boolean isCreated(@NonNull final WorkflowId workflowId)
	{
		return createdWorkflowIds.contains(workflowId);
	}
}

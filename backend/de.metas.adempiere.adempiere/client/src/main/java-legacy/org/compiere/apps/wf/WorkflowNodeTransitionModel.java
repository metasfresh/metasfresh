/*
 * #%L
 * de.metas.adempiere.adempiere.client
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

package org.compiere.apps.wf;

import de.metas.workflow.WFNodeId;
import de.metas.workflow.WFNodeSplitType;
import de.metas.workflow.WFNodeTransition;
import de.metas.workflow.WFNodeTransitionId;
import lombok.NonNull;
import org.adempiere.service.ClientId;

class WorkflowNodeTransitionModel
{
	@NonNull
	private final WorkflowModel workflowModel;
	@NonNull
	private final WFNodeId fromNodeId;
	@NonNull
	private final WFNodeTransition transition;

	WorkflowNodeTransitionModel(
			@NonNull final WorkflowModel workflowModel,
			@NonNull final WFNodeId fromNodeId,
			@NonNull final WFNodeTransition transition)
	{
		this.workflowModel = workflowModel;
		this.fromNodeId = fromNodeId;
		this.transition = transition;
	}

	public @NonNull WFNodeTransitionId getId() {return transition.getId();}

	public @NonNull ClientId getClientId() {return transition.getClientId();}

	public @NonNull WFNodeId getFromNodeId() { return fromNodeId; }

	public @NonNull WFNodeId getNextNodeId() {return transition.getNextNodeId();}

	public @NonNull WorkflowNodeModel getNextNode()
	{
		return workflowModel.getNodeById(transition.getNextNodeId());
	}

	public String getDescription() {return transition.getDescription();}

	public int getSeqNo() {return transition.getSeqNo();}

	public WFNodeSplitType getFromSplitType() {return transition.getFromSplitType();}

	public boolean isUnconditional() {return transition.isUnconditional();}
}

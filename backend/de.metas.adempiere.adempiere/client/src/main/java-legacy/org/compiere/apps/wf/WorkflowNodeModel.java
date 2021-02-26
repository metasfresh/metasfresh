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

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import de.metas.i18n.ITranslatableString;
import de.metas.util.Services;
import de.metas.workflow.WFNode;
import de.metas.workflow.WFNodeAction;
import de.metas.workflow.WFNodeId;
import de.metas.workflow.WFNodeJoinType;
import de.metas.workflow.WorkflowId;
import de.metas.workflow.service.IADWorkflowDAO;
import de.metas.workflow.service.WFNodeLayoutChangeRequest;
import lombok.NonNull;
import org.adempiere.service.ClientId;

class WorkflowNodeModel
{
	private final IADWorkflowDAO workflowDAO = Services.get(IADWorkflowDAO.class);

	@NonNull
	private final WorkflowModel workflowModel;

	@NonNull
	private final WFNode node;

	private Integer xPosition;
	private Integer yPosition;

	public WorkflowNodeModel(
			@NonNull final WorkflowModel workflowModel,
			@NonNull final WFNode node)
	{
		this.workflowModel = workflowModel;
		this.node = node;
	}

	@Override
	public String toString()
	{
		final ImmutableList<String> nextNodeNames = getTransitions(ClientId.SYSTEM)
				.stream()
				.map(transition -> transition.getNextNode().getName().getDefaultValue())
				.collect(ImmutableList.toImmutableList());

		return MoreObjects.toStringHelper(this)
				.add("name", getName().getDefaultValue())
				.add("next", nextNodeNames)
				.toString();
	}

	public WFNode unbox() { return node; }

	public @NonNull WFNodeId getId() {return node.getId();}

	public @NonNull WorkflowId getWorkflowId() { return workflowModel.getId(); }

	public @NonNull ClientId getClientId() {return node.getClientId();}

	public @NonNull WFNodeAction getAction() {return node.getAction();}

	public @NonNull ITranslatableString getName() {return node.getName();}

	@NonNull
	public ITranslatableString getDescription() {return node.getDescription();}

	@NonNull
	public ITranslatableString getHelp() {return node.getHelp();}

	public @NonNull WFNodeJoinType getJoinType() {return node.getJoinType();}

	public int getXPosition() {return xPosition != null ? xPosition : node.getXPosition();}

	public void setXPosition(final int x) { this.xPosition = x; }

	public int getYPosition() {return yPosition != null ? yPosition : node.getYPosition();}

	public void setYPosition(final int y) { this.yPosition = y; }

	public @NonNull ImmutableList<WorkflowNodeTransitionModel> getTransitions(@NonNull final ClientId clientId)
	{
		return node.getTransitions(clientId)
				.stream()
				.map(transition -> new WorkflowNodeTransitionModel(workflowModel, node.getId(), transition))
				.collect(ImmutableList.toImmutableList());
	}

	public void saveEx()
	{
		workflowDAO.changeNodeLayout(WFNodeLayoutChangeRequest.builder()
				.nodeId(getId())
				.xPosition(getXPosition())
				.yPosition(getYPosition())
				.build());
	}
}

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

import com.google.common.collect.ImmutableList;
import de.metas.i18n.ITranslatableString;
import de.metas.workflow.WFNode;
import de.metas.workflow.WFNodeId;
import de.metas.workflow.Workflow;
import de.metas.workflow.WorkflowId;
import lombok.NonNull;
import org.adempiere.service.ClientId;

import java.util.LinkedHashMap;
import java.util.List;

class WorkflowModel
{
	private final Workflow workflow;

	private final LinkedHashMap<WFNodeId, WorkflowNodeModel> nodesById = new LinkedHashMap<>();

	WorkflowModel(@NonNull final Workflow workflow)
	{
		this.workflow = workflow;
	}

	public @NonNull WorkflowId getId() { return workflow.getId(); }

	public @NonNull ITranslatableString getName() {return workflow.getName();}

	public @NonNull ITranslatableString getDescription() {return workflow.getDescription();}

	public @NonNull ITranslatableString getHelp() {return workflow.getHelp();}

	public WorkflowNodeModel getNodeById(final WFNodeId nodeId)
	{
		return nodesById.computeIfAbsent(nodeId, k -> {
			final WFNode node = workflow.getNodeById(nodeId);
			return new WorkflowNodeModel(this, node);
		});
	}

	public List<WorkflowNodeModel> getNodesInOrder(final @NonNull ClientId clientId)
	{
		return workflow.getNodesInOrder(clientId)
				.stream()
				.map(node -> getNodeById(node.getId()))
				.collect(ImmutableList.toImmutableList());
	}
}

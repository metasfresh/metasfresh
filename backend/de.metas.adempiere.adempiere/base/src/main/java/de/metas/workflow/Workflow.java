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

package de.metas.workflow;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class Workflow
{
	private static final Logger log = LogManager.getLogger(Workflow.class);

	@NonNull WorkflowId id;

	@NonNull ClientId clientId;

	@NonNull ITranslatableString name;
	@NonNull ITranslatableString description;
	@NonNull ITranslatableString help;

	int priority;
	@Nullable Instant validFrom;
	@Nullable Instant validTo;

	@NonNull WFResponsibleId responsibleId;

	@NonNull WFNodeId firstNodeId;
	@NonNull ImmutableList<WFNode> nodes;

	@Nullable String docValueWorkflowTriggerLogic;

	@NonNull
	public WFNode getNodeById(@NonNull final WFNodeId nodeId)
	{
		for (final WFNode node : getNodes())
		{
			if (node.getId().equals(nodeId))
			{
				return node;
			}
		}

		throw new AdempiereException("Node " + nodeId + " not found");
	}

	/**
	 * @return Nodes in sequence
	 */
	public List<WFNode> getNodesInOrder(@NonNull final ClientId clientId)
	{
		final ArrayList<WFNode> list = new ArrayList<>();
		addNodesSF(list, firstNodeId, clientId);    //	start with first
		//	Remaining Nodes
		if (getNodes().size() != list.size())
		{
			//	Add Stand alone
			for (final WFNode node : getNodes())
			{
				if (!node.isActive())
				{
					continue;
				}

				final ClientId nodeClientId = node.getClientId();
				if (nodeClientId.isSystem()
						|| ClientId.equals(nodeClientId, clientId))
				{
					boolean found = false;
					for (final WFNode existing : list)
					{
						if (existing.getId().equals(node.getId()))
						{
							found = true;
							break;
						}
					}
					if (!found)
					{
						log.warn("Added Node w/o transition: {}", node);
						list.add(node);
					}
				}
			}
		}

		return list;
	}    //	getNodesInOrder

	/**
	 * Add Nodes recursively (sibling first) to Ordered List
	 */
	private void addNodesSF(
			final ArrayList<WFNode> list,
			@NonNull final WFNodeId nodeId,
			@NonNull final ClientId clientId)
	{
		final ArrayList<WFNode> tmplist = new ArrayList<>();
		final WFNode node = getNodeById(nodeId);

		final ClientId nodeClientId = node.getClientId();

		if (nodeClientId.isSystem() || ClientId.equals(nodeClientId, clientId))
		{
			if (!list.contains(node))
			{
				list.add(node);
			}

			for (final WFNodeTransition transition : node.getTransitions(clientId))
			{
				final WFNode child = getNodeById(transition.getNextNodeId());
				if (!child.isActive())
				{
					continue;
				}

				final ClientId childClientId = child.getClientId();

				if (childClientId.isSystem() || ClientId.equals(childClientId, clientId))
				{
					if (!list.contains(child))
					{
						list.add(child);
						tmplist.add(child);
					}
				}
			}

			//	Remainder Nodes not connected
			for (final WFNode mwfNode : tmplist)
			{
				addNodesSF(list, mwfNode.getId(), clientId);
			}
		}
	}

	public ImmutableList<WFNodeTransition> getTransitionsFromNode(
			@NonNull final WFNodeId nodeId,
			@NonNull final ClientId clientId)
	{
		for (final WFNode node : getNodesInOrder(clientId))
		{
			if (node.getId().equals(nodeId))
			{
				return node.getTransitions(clientId);
			}
		}

		return ImmutableList.of();
	}    //	getNext

}

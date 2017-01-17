package de.metas.ui.web.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;

import de.metas.logging.LogManager;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.menu.MenuNode.MenuNodeFilter.MenuNodeFilterResolution;
import de.metas.ui.web.menu.MenuNode.MenuNodeType;
import de.metas.ui.web.menu.exception.NoMenuNodesFoundException;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class MenuTree
{
	public static final MenuTree of(final MenuNode rootNode)
	{
		return new MenuTree(rootNode);
	}

	private static final Logger logger = LogManager.getLogger(MenuTree.class);

	private final MenuNode rootNode;
	private final Map<String, MenuNode> nodesById;
	private final ListMultimap<ArrayKey, MenuNode> nodesByTypeAndElementId;

	private MenuTree(final MenuNode rootNode)
	{
		super();
		Preconditions.checkNotNull(rootNode, "rootNode");
		this.rootNode = rootNode;

		final ImmutableMap.Builder<String, MenuNode> nodesByIdBuilder = ImmutableMap.builder();
		final ImmutableListMultimap.Builder<ArrayKey, MenuNode> nodesByTypeAndElementIdBuilder = ImmutableListMultimap.builder();
		rootNode.iterate(node -> {
			nodesByIdBuilder.put(node.getId(), node);
			nodesByTypeAndElementIdBuilder.put(mkTypeAndElementIdKey(node.getType(), node.getElementId()), node);
		});
		nodesById = nodesByIdBuilder.build();
		nodesByTypeAndElementId = nodesByTypeAndElementIdBuilder.build();
	}

	private static final ArrayKey mkTypeAndElementIdKey(final MenuNodeType type, final int elementId)
	{
		return Util.mkKey(type, elementId);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("rootNode", rootNode)
				.toString();
	}

	public MenuNode getRootNode()
	{
		return rootNode;
	}

	public MenuNode getNodeById(final String nodeId)
	{
		final MenuNode node = nodesById.get(nodeId);
		if (node == null)
		{
			throw new NoMenuNodesFoundException("No menu node found for nodeId=" + nodeId);
		}
		return node;
	}

	public MenuNode getFirstNodeByElementId(final MenuNodeType type, final int elementId)
	{
		final ArrayKey key = mkTypeAndElementIdKey(type, elementId);
		final List<MenuNode> nodes = nodesByTypeAndElementId.get(key);
		if (nodes == null || nodes.isEmpty())
		{
			throw new NoMenuNodesFoundException("No menu node found for type=" + type + " and elementId=" + elementId);
		}
		return nodes.get(0);
	}

	public List<MenuNode> getPath(final String nodeId)
	{
		final MenuNode node = getNodeById(nodeId);
		return getPath(node);
	}

	public List<MenuNode> getPath(final MenuNodeType type, final int elementId)
	{
		final MenuNode node = getFirstNodeByElementId(type, elementId);
		return getPath(node);
	}

	private List<MenuNode> getPath(final MenuNode node)
	{
		Preconditions.checkNotNull(node, "node not null");
		final List<MenuNode> path = new ArrayList<>();

		MenuNode n = node;
		while (n != null)
		{
			path.add(0, n);
			n = n.getParent();
		}

		return path;
	}

	/**
	 * Filters this node and its children recursively.
	 * 
	 * @param nameQuery
	 * @param includeLeafsIfGroupAccepted
	 *            <ul>
	 *            <li><code>false</code> populate groups only with the leafs that match (default)
	 *            <li><code>true</code> if groups that were matched shall be populated with it's leafs, even if those leafs are not matching
	 *            </ul>
	 * @return a new copy with all matching nodes.
	 */
	public MenuNode filter(final String nameQuery, final boolean includeLeafsIfGroupAccepted)
	{
		if (Check.isEmpty(nameQuery, true))
		{
			throw new IllegalArgumentException("Invalid name query '" + nameQuery + "'");
		}

		final String nameQueryLC = nameQuery.toLowerCase();
		logger.trace("Filtering using nameQueryLC={}", nameQueryLC);

		return getRootNode()
				.deepCopy(node -> {
					if (node.isRoot())
					{
						logger.trace("Filter: accept root node: {}", node);
						return MenuNodeFilterResolution.Accept;
					}

					final boolean matches = matchesNameQuery(node, nameQueryLC);
					if (matches)
					{
						logger.trace("Filter: accept node because matches: {}", node);
						return MenuNodeFilterResolution.Accept;
					}

					if (node.isGroupingNode())
					{
						logger.trace("Filter: accept node (if has children!) because does matches and it's a groupping node: {}", node);
						return MenuNodeFilterResolution.AcceptIfHasChildren;
					}
					else if (includeLeafsIfGroupAccepted)
					{
						logger.trace("Filter: accept node if parent is accepted because does not match and we were asked to populate matching groups with its leafs: {}", node);
						return MenuNodeFilterResolution.AcceptIfParentIsAccepted;
					}
					else
					{
						logger.trace("Filter: reject node because does not match and it's leaf node: {}", node);
						return MenuNodeFilterResolution.Reject;
					}
				});
	}

	private static final boolean matchesNameQuery(final MenuNode node, final String nameQueryLC)
	{
		return node.getCaption().toLowerCase().indexOf(nameQueryLC) >= 0;
	}
}

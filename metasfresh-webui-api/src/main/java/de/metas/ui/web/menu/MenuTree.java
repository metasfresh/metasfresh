package de.metas.ui.web.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;

import de.metas.ui.web.menu.MenuNode.MenuNodeType;

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

	private final MenuNode rootNode;
	private final Map<Integer, MenuNode> nodesById;
	private final ListMultimap<ArrayKey, MenuNode> nodesByTypeAndElementId;

	private MenuTree(final MenuNode rootNode)
	{
		super();
		Preconditions.checkNotNull(rootNode, "rootNode");
		this.rootNode = rootNode;

		final ImmutableMap.Builder<Integer, MenuNode> nodesByIdBuilder = ImmutableMap.builder();
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

	public MenuNode getNodeById(final int nodeId)
	{
		final MenuNode node = nodesById.get(nodeId);
		if (node == null)
		{
			throw new IllegalArgumentException("No menu node found for nodeId=" + nodeId);
		}
		return node;
	}

	public MenuNode getFirstNodeByElementId(final MenuNodeType type, final int elementId)
	{
		final ArrayKey key = mkTypeAndElementIdKey(type, elementId);
		final List<MenuNode> nodes = nodesByTypeAndElementId.get(key);
		if (nodes == null || nodes.isEmpty())
		{
			throw new IllegalArgumentException("No menu node found for type=" + type + " and elementId=" + elementId);
		}
		return nodes.get(0);
	}

	public List<MenuNode> getPath(final int nodeId)
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
}

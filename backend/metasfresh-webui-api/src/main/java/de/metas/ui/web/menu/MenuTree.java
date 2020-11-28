package de.metas.ui.web.menu;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

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
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class MenuTree
{
	public static final MenuTree of(final long version, final MenuNode rootNode)
	{
		return new MenuTree(version, rootNode);
	}

	private static final Logger logger = LogManager.getLogger(MenuTree.class);

	private final long version;

	private final MenuNode rootNode;
	private final Map<String, MenuNode> nodesById;

	private final ListMultimap<ArrayKey, MenuNode> nodesByTypeAndElementId;
	private final ListMultimap<String, MenuNode> nodesByMainTableName;

	private MenuTree(final long version, final MenuNode rootNode)
	{
		super();
		this.version = version;
		Preconditions.checkNotNull(rootNode, "rootNode");
		this.rootNode = rootNode;

		final ImmutableMap.Builder<String, MenuNode> nodesByIdBuilder = ImmutableMap.builder();
		final ImmutableListMultimap.Builder<ArrayKey, MenuNode> nodesByTypeAndElementIdBuilder = ImmutableListMultimap.builder();
		final ImmutableListMultimap.Builder<String, MenuNode> nodesByMainTableNameBuilder = ImmutableListMultimap.builder();
		rootNode.iterate(node -> {
			nodesByIdBuilder.put(node.getId(), node);
			nodesByTypeAndElementIdBuilder.put(mkTypeAndElementIdKey(node.getType(), node.getElementId()), node);

			final String mainTableName = node.getMainTableName();
			if (mainTableName != null)
			{
				nodesByMainTableNameBuilder.put(mainTableName, node);
			}
		});
		nodesById = nodesByIdBuilder.build();
		nodesByTypeAndElementId = nodesByTypeAndElementIdBuilder.build();
		nodesByMainTableName = nodesByMainTableNameBuilder.build();
	}

	private static final ArrayKey mkTypeAndElementIdKey(final MenuNodeType type, final DocumentId elementId)
	{
		return Util.mkKey(type, elementId);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("rootNode", rootNode)
				.add("version", version)
				.toString();
	}

	public long getVersion()
	{
		return version;
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

	public Stream<MenuNode> streamNodesByAD_Menu_ID(final int adMenuId)
	{
		return nodesById.values()
				.stream()
				.filter(node -> node.getAD_Menu_ID() == adMenuId);
	}

	private MenuNode getFirstNodeByElementIdOrNull(final MenuNodeType type, final DocumentId elementId)
	{
		final ArrayKey key = mkTypeAndElementIdKey(type, elementId);
		final List<MenuNode> nodes = nodesByTypeAndElementId.get(key);
		if (nodes == null || nodes.isEmpty())
		{
			return null;
		}

		return nodes.get(0);
	}

	public MenuNode getFirstNodeByElementId(final MenuNodeType type, final DocumentId elementId)
	{
		final MenuNode node = getFirstNodeByElementIdOrNull(type, elementId);
		if (node == null)
		{
			throw new NoMenuNodesFoundException("No menu node found for type=" + type + " and elementId=" + elementId);
		}
		return node;
	}

	public Optional<MenuNode> getNewRecordNodeForWindowId(final WindowId windowId)
	{
		final DocumentId elementId = windowId.toDocumentId();
		final ArrayKey key = mkTypeAndElementIdKey(MenuNodeType.NewRecord, elementId);
		final List<MenuNode> nodes = nodesByTypeAndElementId.get(key);
		if (nodes == null || nodes.isEmpty())
		{
			return Optional.empty();
		}

		final MenuNode newRecordNode = nodes.get(0);
		return Optional.of(newRecordNode);
	}

	public Optional<MenuNode> getNewRecordNodeForTableName(final String tableName)
	{
		return nodesByMainTableName.get(tableName)
				.stream()
				.filter(node -> node.getType() == MenuNodeType.NewRecord)
				.findFirst();
	}

	public List<MenuNode> getPath(final String nodeId)
	{
		final MenuNode node = getNodeById(nodeId);
		return getPath(node);
	}

	public Optional<List<MenuNode>> getPath(final MenuNodeType type, final DocumentId elementId)
	{
		final MenuNode node = getFirstNodeByElementIdOrNull(type, elementId);
		if(node == null)
		{
			return Optional.empty();
		}
		
		final List<MenuNode> path = getPath(node);
		return Optional.of(path);
	}

	public MenuNode getTopLevelMenuGroupOrNull(final WindowId windowId)
	{
		final DocumentId elementId = windowId.toDocumentId();
		final MenuNode node = getFirstNodeByElementIdOrNull(MenuNodeType.Window, elementId);
		if (node == null)
		{
			return null;
		}
		final List<MenuNode> path = getPath(node);
		// NOTE: the top level menu group is at index "1" because on index "0" we have the menu root node.
		if (path.size() < 2)
		{
			return null;
		}
		return path.get(1);
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
		final String captionNorm = stripDiacritics(node.getCaption().toLowerCase());
		final String queryNorm = stripDiacritics(nameQueryLC);
		return captionNorm.indexOf(queryNorm) >= 0;
	}

	private static final String stripDiacritics(final String string)
	{
		String s = Normalizer.normalize(string, Normalizer.Form.NFD);
		s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		return s;
	}

	public MenuNode getRootNodeWithFavoritesOnly(@NonNull final MenuNodeFavoriteProvider menuNodeFavoriteProvider)
	{
		return getRootNode()
				.deepCopy(node -> {
					if (node.isRoot())
					{
						return MenuNodeFilterResolution.Accept;
					}

					if (node.isGroupingNode())
					{
						return MenuNodeFilterResolution.AcceptIfHasChildren;
					}

					if (menuNodeFavoriteProvider.isFavorite(node))
					{
						return MenuNodeFilterResolution.Accept;
					}

					return MenuNodeFilterResolution.Reject;
				});
	}
}

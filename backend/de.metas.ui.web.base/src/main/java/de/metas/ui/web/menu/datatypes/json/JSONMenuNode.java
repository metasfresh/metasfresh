package de.metas.ui.web.menu.datatypes.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import de.metas.ui.web.menu.MenuNode;
import de.metas.ui.web.menu.MenuNodeFavoriteProvider;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.MutableInt;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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

@Value
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class JSONMenuNode
{
	public static JSONMenuNode ofPath(
			final List<MenuNode> path,
			final boolean includeLastNode,
			final MenuNodeFavoriteProvider menuNodeFavoriteProvider)
	{
		if (path == null || path.isEmpty())
		{
			throw new AdempiereException("Empty path is not valid");
		}
		else if (path.size() == 1)
		{
			final MenuNode node = path.get(0);
			final boolean favorite = menuNodeFavoriteProvider.isFavorite(node);
			return new JSONMenuNode(node, favorite, null);
		}

		int lastIndex = path.size() - 1;
		if (!includeLastNode)
		{
			lastIndex--;
		}

		JSONMenuNode jsonChildNode = null;
		for (int i = lastIndex; i >= 0; i--)
		{
			final MenuNode node = path.get(i);
			if (node.isRoot())
			{
				continue;
			}

			final boolean favorite = menuNodeFavoriteProvider.isFavorite(node);
			jsonChildNode = new JSONMenuNode(node, favorite, jsonChildNode);
		}

		if (jsonChildNode == null)
		{
			throw new AdempiereException("Invalid path: " + path);
		}

		return jsonChildNode;
	}

	public static List<JSONMenuNode> ofList(
			final Collection<MenuNode> nodes,
			final MenuNodeFavoriteProvider menuNodeFavoriteProvider)
	{
		if (nodes.isEmpty())
		{
			return ImmutableList.of();
		}

		return nodes.stream()
				.map(node -> new JSONMenuNode(node, menuNodeFavoriteProvider.isFavorite(node), null))
				.collect(ImmutableList.toImmutableList());
	}

	@Nullable
	private static JSONMenuNode newInstanceOrNull(
			final MenuNode node,
			final int depth,
			final int childrenLimit,
			final MutableInt maxLeafNodes,
												  final MenuNodeFavoriteProvider menuNodeFavoriteProvider)
	{
		if (maxLeafNodes.getValue() <= 0)
		{
			return null;
		}

		final JSONMenuNode jsonNode = new JSONMenuNode(node, depth, childrenLimit, maxLeafNodes, menuNodeFavoriteProvider);

		// Avoid empty groups, makes no sense and looks ugly to show them to user.
		if (jsonNode.isEmptyGroup())
		{
			return null;
		}

		if (node.isEffectiveLeafNode())
		{
			maxLeafNodes.decrementAndGet();
		}

		return jsonNode;
	}

	public static Builder builder(final MenuNode node)
	{
		return new Builder(node);
	}

	@JsonProperty("nodeId") String nodeId;

	@JsonProperty("parentId")
	@JsonInclude(JsonInclude.Include.NON_NULL) String parentId;

	@JsonProperty("caption") String caption;

	@JsonProperty("captionBreadcrumb") String captionBreadcrumb;

	@JsonProperty("type") JSONMenuNodeType type;

	@JsonProperty("elementId")
	@JsonInclude(JsonInclude.Include.NON_NULL) String elementId;

	@JsonProperty("children")
	@JsonInclude(JsonInclude.Include.NON_EMPTY) List<JSONMenuNode> children;

	@JsonProperty("matched")
	@JsonInclude(JsonInclude.Include.NON_NULL) Boolean matched;

	@JsonProperty("favorite") boolean favorite;

	private JSONMenuNode(
			final MenuNode node,
			final int depth,
			final int childrenLimit,
			final MutableInt maxLeafNodes,
						 final MenuNodeFavoriteProvider menuNodeFavoriteProvider)
	{
		nodeId = node.getId();
		parentId = node.getParentId();
		caption = node.getCaption();
		captionBreadcrumb = node.getCaptionBreadcrumb();
		type = JSONMenuNodeType.ofNullable(node.getType());
		elementId = node.getElementId() != null ? node.getElementId().toJson() : null;
		matched = node.isMatchedByFilter() ? Boolean.TRUE : null;
		favorite = menuNodeFavoriteProvider.isFavorite(node);

		if (depth <= 0)
		{
			children = ImmutableList.of();
		}
		else
		{
			children = node.getChildren()
					.stream()
					.map(childNode -> newInstanceOrNull(childNode, depth - 1, childrenLimit, maxLeafNodes, menuNodeFavoriteProvider))
					.filter(Objects::nonNull)
					.limit(childrenLimit > 0 ? childrenLimit : Long.MAX_VALUE)
					.collect(ImmutableList.toImmutableList());
		}
	}

	/**
	 * Path constructor
	 */
	private JSONMenuNode(
			final MenuNode node,
			final boolean favorite,
			@Nullable final JSONMenuNode jsonChildNode)
	{
		nodeId = node.getId();
		parentId = null; // omit parentId when we are building the path!
		caption = node.getCaption();
		captionBreadcrumb = node.getCaptionBreadcrumb();
		type = JSONMenuNodeType.ofNullable(node.getType());
		elementId = node.getElementId() != null ? node.getElementId().toJson() : null;
		children = jsonChildNode == null ? ImmutableList.of() : ImmutableList.of(jsonChildNode);
		matched = node.isMatchedByFilter() ? Boolean.TRUE : null;
		this.favorite = favorite;
	}

	@JsonCreator
	private JSONMenuNode(
			@JsonProperty("nodeId") final String nodeId,
			@JsonProperty("parentId") final String parentId,
			@JsonProperty("caption") final String caption,
			@JsonProperty("captionBreadcrumb") final String captionBreadcrumb,
			@JsonProperty("type") final JSONMenuNodeType type,
			@JsonProperty("elementId") final String elementId,
			@JsonProperty("children") final List<JSONMenuNode> children,
			@JsonProperty("matched") final Boolean matchedByFilter,
			@JsonProperty("favorite") final boolean favorite)
	{
		this.nodeId = nodeId;
		this.parentId = parentId;
		this.caption = caption;
		this.captionBreadcrumb = captionBreadcrumb;
		this.type = type;
		this.elementId = elementId;
		this.children = children == null ? ImmutableList.of() : ImmutableList.copyOf(children);
		matched = matchedByFilter;
		this.favorite = favorite;
	}

	//
	// -----------------
	//

	public boolean isEmptyGroup()
	{
		return JSONMenuNodeType.group.equals(type) && (children == null || children.isEmpty());
	}

	public static final class Builder
	{
		private final MenuNode node;
		private int maxDepth = Integer.MAX_VALUE;
		private int maxChildrenPerNode = Integer.MAX_VALUE;
		private int maxLeafNodes = Integer.MAX_VALUE;
		private MenuNodeFavoriteProvider menuNodeFavoriteProvider;

		private Builder(final MenuNode node)
		{
			this.node = node;
		}

		@Nullable
		public JSONMenuNode build()
		{
			final MutableInt maxLeafNodes = new MutableInt(this.maxLeafNodes);
			return newInstanceOrNull(node, maxDepth, maxChildrenPerNode, maxLeafNodes, menuNodeFavoriteProvider);
		}

		public Builder setMaxDepth(final int maxDepth)
		{
			this.maxDepth = maxDepth > 0 ? maxDepth : Integer.MAX_VALUE;
			return this;
		}

		public Builder setMaxChildrenPerNode(final int maxChildrenPerNode)
		{
			this.maxChildrenPerNode = maxChildrenPerNode > 0 ? maxChildrenPerNode : Integer.MAX_VALUE;
			return this;
		}

		public Builder setMaxLeafNodes(final int maxLeafNodes)
		{
			this.maxLeafNodes = maxLeafNodes > 0 ? maxLeafNodes : Integer.MAX_VALUE;
			return this;
		}

		public Builder setIsFavoriteProvider(final MenuNodeFavoriteProvider menuNodeFavoriteProvider)
		{
			this.menuNodeFavoriteProvider = menuNodeFavoriteProvider;
			return this;
		}
	}
}

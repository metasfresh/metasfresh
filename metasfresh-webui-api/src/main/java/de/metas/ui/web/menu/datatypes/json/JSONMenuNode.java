package de.metas.ui.web.menu.datatypes.json;

import java.io.Serializable;
import java.util.List;

import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.lang.MutableInt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.menu.MenuNode;

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

@SuppressWarnings("serial")
public final class JSONMenuNode implements Serializable
{
	public static final JSONMenuNode ofPath(final List<MenuNode> path, final boolean skipRootNode, final boolean includeLastNode)
	{
		if (path == null || path.isEmpty())
		{
			throw new IllegalArgumentException("Invalid path");
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
			if(node.isRoot())
			{
				continue;
			}
			jsonChildNode = new JSONMenuNode(node, jsonChildNode);
		}
		
		if(jsonChildNode == null)
		{
			throw new IllegalArgumentException("Invalid path");
		}
		
		return jsonChildNode;
	}

	private static JSONMenuNode newInstanceOrNull(final MenuNode node, final int depth, final int childrenLimit, final MutableInt maxLeafNodes)
	{
		if (maxLeafNodes.getValue() <= 0)
		{
			return null;
		}
		
		if(node.isEffectiveLeafNode())
		{
			maxLeafNodes.decrementAndGet();
		}
		
		return new JSONMenuNode(node, depth, childrenLimit, maxLeafNodes);
	}

	public static final Builder builder(final MenuNode node)
	{
		return new Builder(node);
	}

	@JsonProperty("nodeId")
	private final String nodeId;
	
	@JsonProperty("parentId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String parentId;
	
	@JsonProperty("caption")
	private final String caption;
	
	@JsonProperty("captionBreadcrumb")
	private final String captionBreadcrumb;
	
	@JsonProperty("type")
	private final JSONMenuNodeType type;
	
	@JsonProperty("elementId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Integer elementId;

	@JsonProperty("children")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONMenuNode> children;

	@JsonProperty("matched")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Boolean matched;

	private JSONMenuNode(final MenuNode node, final int depth, final int childrenLimit, final MutableInt maxLeafNodes)
	{
		super();
		nodeId = node.getId();
		parentId = node.getParentId();
		caption = node.getCaption();
		captionBreadcrumb = node.getCaptionBreadcrumb();
		type = JSONMenuNodeType.fromNullable(node.getType());
		elementId = normalizeElementId(node.getElementId());
		matched = node.isMatchedByFilter() ? Boolean.TRUE : null;

		if (depth <= 0)
		{
			children = ImmutableList.of();
		}
		else
		{
			children = node.getChildren()
					.stream()
					.limit(childrenLimit > 0 ? childrenLimit : Long.MAX_VALUE)
					.map(childNode -> newInstanceOrNull(childNode, depth - 1, childrenLimit, maxLeafNodes))
					.filter(jsonNode -> jsonNode != null)
					.collect(GuavaCollectors.toImmutableList());
		}
	}

	/**
	 * Path constructor
	 *
	 * @param node
	 * @param jsonChildNode
	 */
	private JSONMenuNode(final MenuNode node, final JSONMenuNode jsonChildNode)
	{
		super();
		nodeId = node.getId();
		parentId = null; // omit parentId when we are building the path!
		caption = node.getCaption();
		captionBreadcrumb = node.getCaptionBreadcrumb();
		type = JSONMenuNodeType.fromNullable(node.getType());
		elementId = normalizeElementId(node.getElementId());
		children = jsonChildNode == null ? ImmutableList.of() : ImmutableList.of(jsonChildNode);
		matched = node.isMatchedByFilter() ? Boolean.TRUE : null;
	}

	private static final Integer normalizeElementId(final int elementId)
	{
		return elementId <= 0 ? null : elementId;
	}

	@JsonCreator
	private JSONMenuNode(
			@JsonProperty("nodeId") final String nodeId //
			, @JsonProperty("parentId") final String parentId //
			, @JsonProperty("caption") final String caption //
			, @JsonProperty("captionBreadcrumb") final String captionBreadcrumb //
			, @JsonProperty("type") final JSONMenuNodeType type //
			, @JsonProperty("elementId") final Integer elementId //
			, @JsonProperty("children") final List<JSONMenuNode> children //
			, @JsonProperty("matched") final Boolean matchedByFilter //
	)
	{
		super();
		this.nodeId = nodeId;
		this.parentId = parentId;
		this.caption = caption;
		this.captionBreadcrumb = captionBreadcrumb;
		this.type = type;
		this.elementId = elementId;
		this.children = children == null ? ImmutableList.of() : ImmutableList.copyOf(children);
		this.matched = matchedByFilter;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("nodeId", nodeId)
				.add("parentId", parentId)
				.add("caption", caption)
				.add("captionBreadcrumb", captionBreadcrumb)
				.add("type", type)
				.add("elementId", elementId)
				.add("matchedByFilter", matched)
				.toString();
	}

	public String getNodeId()
	{
		return nodeId;
	}

	public String getCaption()
	{
		return caption;
	}

	public String getCaptionBreadcrumb()
	{
		return captionBreadcrumb;
	}

	public JSONMenuNodeType getType()
	{
		return type;
	}

	public Integer getElementId()
	{
		return elementId;
	}

	public List<JSONMenuNode> getChildren()
	{
		return children;
	}
	
	@JsonIgnore
	public boolean isLeaf()
	{
		return children == null || children.isEmpty();
	}
	
	public Boolean getMatched()
	{
		return matched;
	}
	
	public static final class Builder
	{
		private final MenuNode node;
		private int maxDepth = Integer.MAX_VALUE;
		private int maxChildrenPerNode = Integer.MAX_VALUE;
		private int maxLeafNodes = Integer.MAX_VALUE;

		private Builder(final MenuNode node)
		{
			super();
			this.node = node;
		}

		public JSONMenuNode build()
		{
			final MutableInt maxLeafNodes = new MutableInt(this.maxLeafNodes);
			return JSONMenuNode.newInstanceOrNull(node, maxDepth, maxChildrenPerNode, maxLeafNodes);
		}
		
		public Builder setMaxDepth(int maxDepth)
		{
			this.maxDepth = maxDepth > 0 ? maxDepth : Integer.MAX_VALUE;
			return this;
		}
		
		public Builder setMaxChildrenPerNode(int maxChildrenPerNode)
		{
			this.maxChildrenPerNode = maxChildrenPerNode > 0 ? maxChildrenPerNode : Integer.MAX_VALUE;
			return this;
		}
		
		public Builder setMaxLeafNodes(int maxLeafNodes)
		{
			this.maxLeafNodes = maxLeafNodes > 0 ? maxLeafNodes : Integer.MAX_VALUE;
			return this;
		}
	}
}

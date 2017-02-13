package de.metas.ui.web.menu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.adempiere.util.Check;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import de.metas.ui.web.menu.MenuNode.MenuNodeFilter.MenuNodeFilterResolution;

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

public final class MenuNode
{
	public static final Builder builder()
	{
		return new Builder();
	}

	public static enum MenuNodeType
	{
		Group //
		, Window, NewRecord //
		, Process, Report //
	}
	
	@FunctionalInterface
	public static interface MenuNodeFilter
	{
		public static enum MenuNodeFilterResolution
		{
			Accept, Reject, AcceptIfHasChildren, AcceptIfParentIsAccepted
		}

		MenuNodeFilterResolution check(MenuNode node);
	}

	private final String id;
	private final String caption;
	private final String captionBreadcrumb;
	private final MenuNodeType type;
	private final int elementId;
	private final List<MenuNode> children;
	private MenuNode parent;
	
	//
	// Characteristics
	private final boolean matchedByFilter;

	private Integer _hashcode;

	private MenuNode(final Builder builder)
	{
		super();

		Check.assumeNotNull(builder.id, "Parameter ID is not null");
		id = builder.id;

		caption = builder.caption;
		captionBreadcrumb = builder.captionBreadcrumb;
		type = builder.type;
		elementId = builder.elementId;

		children = ImmutableList.copyOf(Iterables.concat(builder.childrenFirst, builder.childrenRest));
		for (final MenuNode child : children)
		{
			child.parent = this;
		}
		
		matchedByFilter = false;
		
		// Validate
		if(type != MenuNodeType.Group && !children.isEmpty())
		{
			throw new IllegalArgumentException("Only grouping nodes can have children");
		}
	}

	/** Copy constructor */
	private MenuNode(final MenuNode node, final List<MenuNode> children, final boolean matchedByFilter)
	{
		super();
		id = node.id;
		caption = node.caption;
		captionBreadcrumb = node.captionBreadcrumb;
		type = node.type;
		elementId = node.elementId;

		this.children = ImmutableList.copyOf(children);
		for (final MenuNode child : this.children)
		{
			child.parent = this;
		}
		
		this.matchedByFilter = matchedByFilter;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("id", id)
				.add("caption", caption)
				.add("type", type)
				.add("elementId", elementId)
				.add("children-count", children.size())
				.add("matchedByFilter", matchedByFilter ? Boolean.TRUE : null)
				.toString();
	}

	@Override
	public int hashCode()
	{
		if (_hashcode == null)
		{
			_hashcode = Objects.hash(id);
		}
		return _hashcode;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof MenuNode))
		{
			return false;
		}

		final MenuNode other = (MenuNode)obj;
		return id.equals(other.id);
	}

	public String getId()
	{
		return id;
	}

	public String getCaption()
	{
		return caption;
	}
	
	public String getCaptionBreadcrumb()
	{
		return captionBreadcrumb;
	}

	public MenuNode getParent()
	{
		return parent;
	}
	
	public String getParentId()
	{
		return parent == null ? null : parent.getId();
	}

	public List<MenuNode> getChildren()
	{
		return children;
	}

	public MenuNodeType getType()
	{
		return type;
	}

	public int getElementId()
	{
		return elementId;
	}

	public void iterate(final Consumer<MenuNode> consumer)
	{
		consumer.accept(this);
		for (final MenuNode child : children)
		{
			child.iterate(consumer);
		}
	}

	public MenuNode deepCopy(final MenuNodeFilter filter)
	{
		final IPair<MenuNode, MenuNodeFilterResolution> nodeAndResolution = deepCopy0(filter);
		return nodeAndResolution == null ? null : nodeAndResolution.getLeft();
	}

	private IPair<MenuNode, MenuNodeFilterResolution> deepCopy0(final MenuNodeFilter filter)
	{
		//
		// Get the resolution for this node:
		final MenuNodeFilterResolution resolution = filter.check(this);
		if (resolution == MenuNodeFilterResolution.Reject)
		{
			return null;
		}

		//
		// Check and copy it's children
		final List<MenuNode> childrenCopy = new ArrayList<>();
		int countAcceptedChildren = 0;
		for (final MenuNode child : children)
		{
			final IPair<MenuNode, MenuNodeFilterResolution> childCopyAndResolution = child.deepCopy0(filter);
			if(childCopyAndResolution == null)
			{
				continue;
			}
			
			final MenuNode childCopy = childCopyAndResolution.getLeft();
			if (childCopy == null)
			{
				continue;
			}
			
			childrenCopy.add(childCopy);
			
			final MenuNodeFilterResolution childResolution = childCopyAndResolution.getRight();
			switch(childResolution)
			{
				case Accept:
				case AcceptIfHasChildren:
					countAcceptedChildren++;
					break;
				case AcceptIfParentIsAccepted:
					// nothing to do
					break;
				default:
					throw new IllegalStateException("Invalid child resolution: " + childResolution); // shall not happen
			}
		}

		if (resolution == MenuNodeFilterResolution.AcceptIfHasChildren && countAcceptedChildren == 0)
		{
			return null;
		}
		
		final boolean matchedByFilter = resolution == MenuNodeFilterResolution.Accept;
		final MenuNode thisCopy = new MenuNode(this, childrenCopy, matchedByFilter);
		return ImmutablePair.of(thisCopy, resolution);
	}

	public boolean isRoot()
	{
		return parent == null;
	}

	public boolean isGroupingNode()
	{
		return type == MenuNodeType.Group;
	}
	
	/**
	 * Returns true if this node is effectively a leaf node.
	 * 
	 * An effectively leaf node it's a node which it's not a grouping node, or even if it's grouping node, it does no have any children.
	 * 
	 * @return
	 */
	public boolean isEffectiveLeafNode()
	{
		return children.isEmpty();
	}
	
	public boolean isMatchedByFilter()
	{
		return matchedByFilter;
	}

	public static final class Builder
	{
		private String id;

		private String caption;
		private String captionBreadcrumb;
		private MenuNodeType type;
		private int elementId;
		private final List<MenuNode> childrenFirst = new ArrayList<>();
		private final List<MenuNode> childrenRest = new ArrayList<>();

		private Builder()
		{
			super();
		}

		public MenuNode build()
		{
			return new MenuNode(this);
		}

		public Builder setId(final int id)
		{
			this.id = String.valueOf(id);
			return this;
		}

		public Builder setId(final String id)
		{
			this.id = id;
			return this;
		}

		public Builder setCaption(final String caption)
		{
			this.caption = caption;
			return this;
		}
		
		public Builder setCaptionBreadcrumb(String captionBreadcrumb)
		{
			this.captionBreadcrumb = captionBreadcrumb;
			return this;
		}

		public Builder setType(final MenuNodeType type, final int elementId)
		{
			this.type = type;
			this.elementId = elementId;
			return this;
		}

		public Builder addChildToFirstsList(final MenuNode child)
		{
			Preconditions.checkNotNull(child, "child");
			childrenFirst.add(child);
			return this;
		}

		public Builder addChild(final MenuNode child)
		{
			Preconditions.checkNotNull(child, "child");
			childrenRest.add(child);
			return this;
		}

		public Builder addChildren(final Collection<MenuNode> children)
		{
			if (children == null || children.isEmpty())
			{
				return this;
			}

			this.childrenRest.addAll(children);
			return this;
		}
	}
}

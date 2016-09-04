package de.metas.ui.web.menu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

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
		Group, Window, Process, Report,
	}

	private final int id;
	private final String caption;
	private final MenuNodeType type;
	private final int elementId;
	private final List<MenuNode> children;
	private MenuNode parent;

	private Integer _hashcode;

	private MenuNode(final Builder builder)
	{
		super();
		id = builder.id;
		caption = builder.caption;
		children = ImmutableList.copyOf(builder.children);
		for (final MenuNode child : children)
		{
			child.parent = this;
		}

		type = builder.type;
		elementId = builder.elementId;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("caption", caption)
				.add("type", type)
				.add("elementId", elementId)
				.add("children-count", children.size())
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
		return id == other.id;
	}

	public int getId()
	{
		return id;
	}

	public String getCaption()
	{
		return caption;
	}
	
	public MenuNode getParent()
	{
		return parent;
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

	public static final class Builder
	{
		private Integer id;

		private String caption;
		private MenuNodeType type;
		private int elementId;
		private final List<MenuNode> children = new ArrayList<>();

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
			this.id = id;
			return this;
		}

		public Builder setCaption(final String caption)
		{
			this.caption = caption;
			return this;
		}

		public Builder setType(final MenuNodeType type, final int elementId)
		{
			this.type = type;
			this.elementId = elementId;
			return this;
		}

		public Builder addChild(final MenuNode child)
		{
			Preconditions.checkNotNull(child, "child");
			children.add(child);
			return this;
		}

		public Builder addChildren(final Collection<MenuNode> children)
		{
			if (children == null || children.isEmpty())
			{
				return this;
			}

			this.children.addAll(children);
			return this;
		}

		public boolean hasChildren()
		{
			return !children.isEmpty();
		}
	}
}

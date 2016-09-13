package de.metas.ui.web.window_old.shared.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window_old.shared.ImageResource;

/*
 * #%L
 * metasfresh-webui
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
public final class MainMenuItem implements MenuItem, Serializable
{
	public static final MainMenuItem.Builder builder()
	{
		return new Builder();
	}

	public static final MainMenuItem cast(final MenuItem menuItem)
	{
		return (MainMenuItem)menuItem;
	}

	public static enum MenuItemType
	{
		Window, Process, Report,
	}

	@JsonProperty("caption")
	private final String caption;
	@JsonProperty("icon")
	private final ImageResource icon;
	@JsonProperty("children")
	private final List<MainMenuItem> children;

	@JsonProperty("type")
	private final MenuItemType type;
	@JsonProperty("elementId")
	private final int elementId;

	private MainMenuItem(final MainMenuItem.Builder builder)
	{
		super();
		caption = builder.caption;
		icon = builder.icon;
		children = ImmutableList.copyOf(builder.children);

		type = builder.type;
		elementId = builder.elementId;
	}

	@JsonCreator
	private MainMenuItem(
			@JsonProperty("caption") final String caption //
			, @JsonProperty("icon") final ImageResource icon //
			, @JsonProperty("children") final List<MainMenuItem> children //
			, @JsonProperty("type") final MenuItemType type //
			, @JsonProperty("elementId") final int elementId //
	)
	{
		super();
		this.caption = caption;
		this.icon = icon;
		this.children = children == null ? ImmutableList.of() : ImmutableList.copyOf(children);
		this.type = type;
		this.elementId = elementId;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("caption", caption)
				.add("type", type)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(caption, icon, children, type, elementId);
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
		if (!(obj instanceof MainMenuItem))
		{
			return false;
		}

		final MainMenuItem other = (MainMenuItem)obj;
		return Objects.equals(caption, other.caption)
				&& Objects.equals(icon, other.icon)
				&& Objects.equals(children, other.children)
				&& Objects.equals(type, other.type)
				&& Objects.equals(elementId, other.elementId);
	}

	@Override
	public String getCaption()
	{
		return caption;
	}

	@Override
	public List<MainMenuItem> getChildren()
	{
		return children;
	}

	@Override
	public ImageResource getIcon()
	{
		return icon;
	}

	public MenuItemType getType()
	{
		return type;
	}

	public int getElementId()
	{
		return elementId;
	}

	public static final class Builder
	{
		private String caption;
		private ImageResource icon;
		private final List<MainMenuItem> children = new ArrayList<>();

		private MenuItemType type;
		private int elementId;

		private Builder()
		{
			super();
		}

		public MainMenuItem build()
		{
			return new MainMenuItem(this);
		}

		public MainMenuItem.Builder setCaption(final String caption)
		{
			this.caption = caption;
			return this;
		}

		public MainMenuItem.Builder setIcon(final ImageResource icon)
		{
			this.icon = icon;
			return this;
		}

		public MainMenuItem.Builder setType(final MenuItemType type, final int elementId)
		{
			this.type = type;
			this.elementId = elementId;
			return this;
		}

		public MainMenuItem.Builder addChild(final MainMenuItem child)
		{
			children.add(child);
			return this;
		}

		public MainMenuItem.Builder addChildren(final Collection<MainMenuItem> children)
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
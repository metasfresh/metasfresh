package de.metas.ui.web.vaadin.components.menu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.vaadin.server.Resource;

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

public final class MainMenuItem implements MenuItem
{
	public static final MainMenuItem.Builder builder()
	{
		return new Builder();
	}

	public static final MainMenuItem cast(final MenuItem menuItem)
	{
		return (MainMenuItem)menuItem;
	}

	public enum MenuItemType
	{
		Window, Process, Report,
	}

	private final String caption;
	private final Resource icon;
	private final List<MenuItem> children;

	private final MenuItemType type;
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

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("caption", caption)
				.add("type", type)
				.toString();
	}

	@Override
	public String getCaption()
	{
		return caption;
	}

	@Override
	public Collection<MenuItem> getChildren()
	{
		return children;
	}

	@Override
	public Resource getIcon()
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
		private Resource icon;
		private final List<MenuItem> children = new ArrayList<>();

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

		public MainMenuItem.Builder setIcon(final Resource icon)
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

		public MainMenuItem.Builder addChild(final MenuItem child)
		{
			children.add(child);
			return this;
		}

		public boolean hasChildren()
		{
			return !children.isEmpty();
		}
	}
}
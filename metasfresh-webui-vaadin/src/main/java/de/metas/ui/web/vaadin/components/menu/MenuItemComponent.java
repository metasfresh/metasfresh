package de.metas.ui.web.vaadin.components.menu;

import com.vaadin.ui.Button;

import de.metas.ui.web.vaadin.theme.Theme;

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
class MenuItemComponent extends Button
{
	public static final String STYLE = MenuPanel.STYLE + "-item";
	private final MenuItem menuItem;

	public MenuItemComponent(final MenuItem menuItem, final MenuItemClickListener clickListener)
	{
		super();
		setPrimaryStyleName(STYLE);
		this.menuItem = menuItem;

		addClickListener(event -> clickListener.onMenuItemClicked(menuItem));

		updateFromItem();
	}

	private void updateFromItem()
	{
		setCaption(menuItem.getCaption());
		setIcon(menuItem.getIcon());
	}

	public MenuItem getMenuItem()
	{
		return menuItem;
	}

	public final void setHiddenByStyle(final boolean hidden)
	{
		Theme.setHidden(this, hidden);
	}
}
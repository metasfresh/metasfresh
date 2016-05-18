package de.metas.ui.web.vaadin.components.menu;

import java.util.List;

import com.google.common.base.Objects;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

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
public class MenuPanelPage extends HorizontalLayout
{
	// UI
	private TextField searchTextField;
	private final Label nothingFoundLabel;

	private final MenuItemClickListener clickListener;

	private boolean menuItemsStaled = false;
	private Supplier<List<MenuItem>> menuItemsSupplier = null;

	private MenuItemFilter filter = MenuItemFilter.ACCEPT_ALL;

	public MenuPanelPage(final MenuItemClickListener clickListener)
	{
		super();
		setPrimaryStyleName(MenuPanel.STYLE + "-container");

		this.clickListener = clickListener;

		nothingFoundLabel = new Label("No results found");
		nothingFoundLabel.setStyleName(MenuPanel.STYLE + "-nothingfound-label");
	}

	void setSearchTextField(final TextField searchTextField)
	{
		this.searchTextField = searchTextField;
	}

	public void setMenuItems(final Supplier<List<MenuItem>> menuItemsSupplier)
	{
		this.menuItemsStaled = true;
		this.menuItemsSupplier = menuItemsSupplier;
	}

	public void updateIfStaled()
	{
		if (!menuItemsStaled)
		{
			return;
		}

		final List<MenuItem> menuItems = menuItemsSupplier == null ? ImmutableList.of() : menuItemsSupplier.get();

		//
		if (searchTextField != null)
		{
			searchTextField.setValue("");
		}
		this.filter = MenuItemFilter.ACCEPT_ALL; // reset filter

		//
		removeAllComponents();
		if (menuItems != null && !menuItems.isEmpty())
		{
			for (final MenuItem groupItem : menuItems)
			{
				final MenuGroupPanel itemComp = new MenuGroupPanel(groupItem, clickListener);
				addComponent(itemComp);
			}
		}

		Theme.setHidden(nothingFoundLabel, true);
		addComponent(nothingFoundLabel);

		//
		menuItemsStaled = false;
		menuItemsSupplier = null;
	}

	public void setFilter(final MenuItemFilter filter)
	{
		if (Objects.equal(this.filter, filter))
		{
			return;
		}

		this.filter = filter;

		int countDisplayed = 0;
		for (final Component menuGroupComp : this)
		{
			if (menuGroupComp instanceof MenuGroupPanel)
			{
				final MenuGroupPanel menuGroupPanel = (MenuGroupPanel)menuGroupComp;
				menuGroupPanel.setFilter(filter);

				if (!menuGroupPanel.isHiddenByStyle())
				{
					countDisplayed++;
				}
			}
		}

		Theme.setHidden(nothingFoundLabel, countDisplayed > 0);
	}
}

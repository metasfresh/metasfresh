package de.metas.ui.web.vaadin;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;

import de.metas.ui.web.vaadin.components.menu.MainMenuItem;
import de.metas.ui.web.vaadin.components.menu.MainMenuItem.MenuItemType;
import de.metas.ui.web.vaadin.components.menu.MenuItem;
import de.metas.ui.web.vaadin.components.menu.UserMenuProvider;
import de.metas.ui.web.vaadin.components.navigator.MFView;
import de.metas.ui.web.vaadin.components.navigator.MFViewDisplay;
import de.metas.ui.web.vaadin.window.WindowViewProvider;

/*
 * #%L
 * test_vaadin
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
public class MainNavigationView extends CssLayout implements MFView
{
	private List<MenuItem> _rootMenuItems;

	public MainNavigationView()
	{
		super();
		setSizeFull();
	}

	@Override
	public void enter(final ViewChangeEvent event)
	{
		MFViewDisplay viewDisplay = MFViewDisplay.getMFViewDisplayOrNull(event);
		viewDisplay.setTitle("Main");
		viewDisplay.setMenuItems(() -> getMenuItems(), menuItem -> runMenuItem(menuItem));
		viewDisplay.showMenuPanel();
	}

	@Override
	public void exit(final ViewChangeEvent event)
	{
		// nothing
	}

	private List<MenuItem> getMenuItems()
	{
		if (_rootMenuItems == null)
		{
			_rootMenuItems = ImmutableList.copyOf(new UserMenuProvider().getMenuItems());
		}

		return _rootMenuItems;
	}

	private void runMenuItem(final MenuItem menuItem)
	{
		final MainMenuItem mainMenuItem = MainMenuItem.cast(menuItem);
		if (mainMenuItem.getType() == MenuItemType.Window)
		{
			final String viewNameAndParameters = WindowViewProvider.createViewNameAndParameters(mainMenuItem.getElementId());
			UI.getCurrent().getNavigator().navigateTo(viewNameAndParameters);
		}
		else
		{
			throw new UnsupportedOperationException("Menu item not supported: " + mainMenuItem);
		}

	}
}

package de.metas.ui.web.vaadin.components.menu;

import java.util.List;

import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.metas.ui.web.vaadin.components.menu.UserMenuProvider.MenuItem;
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
public class MenuPanel extends CustomComponent
{
	public static final String STYLE = "mf-menu";
	//
	// UI
	private final TextField searchTextField;
	private final HorizontalLayout menuGroupsContainer;
	private final Label nothingFoundLabel;
	private boolean hiddenByStyle = false;

	//
	// Listeners
	private final ForwardingMenuItemClickListener clickListener = new ForwardingMenuItemClickListener();

	public MenuPanel()
	{
		super();
		setPrimaryStyleName(STYLE);

		searchTextField = new TextField();
		searchTextField.setTextChangeEventMode(TextChangeEventMode.TIMEOUT);
		searchTextField.setTextChangeTimeout(500);
		searchTextField.addTextChangeListener(event -> filterMenuItems(event.getText()));

		menuGroupsContainer = new HorizontalLayout();
		menuGroupsContainer.setPrimaryStyleName(STYLE + "-container");

		nothingFoundLabel = new Label("No results found");
		nothingFoundLabel.setStyleName(STYLE + "-nothingfound-label");

		setCompositionRoot(new VerticalLayout(searchTextField, menuGroupsContainer));
	}

	public void setClickListener(final MenuItemClickListener clickListener)
	{
		this.clickListener.setDelegate(clickListener);
	}

	public void setMenuItems(final List<MenuItem> menuItems)
	{
		searchTextField.setValue("");
		menuGroupsContainer.removeAllComponents();

		if (menuItems != null && !menuItems.isEmpty())
		{
			for (final MenuItem groupItem : menuItems)
			{
				final MenuGroupPanel itemComp = new MenuGroupPanel(groupItem, clickListener);
				menuGroupsContainer.addComponent(itemComp);
			}
		}

		Theme.setHidden(nothingFoundLabel, true);
		menuGroupsContainer.addComponent(nothingFoundLabel);
	}

	private void filterMenuItems(final String searchText)
	{
		//
		// Create filter
		final MenuItemFilter filter;
		if (searchText == null || searchText.trim().isEmpty())
		{
			filter = MenuItemFilter.ACCEPT_ALL;
		}
		else
		{
			filter = new ContainsStringMenuItemFilter(searchText);
		}

		int countDisplayed = 0;
		for (final Component menuGroupComp : menuGroupsContainer)
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

	public final void setHiddenByStyle(final boolean hidden)
	{
		Theme.setHidden(this, hidden);
		this.hiddenByStyle = hidden;
	}

	public boolean isHiddenByStyle()
	{
		return hiddenByStyle;
	}
}

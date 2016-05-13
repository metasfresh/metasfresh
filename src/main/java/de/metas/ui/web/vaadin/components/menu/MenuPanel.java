package de.metas.ui.web.vaadin.components.menu;

import java.util.List;

import com.vaadin.ui.AbstractTextField.TextChangeEventMode;

import de.metas.ui.web.vaadin.components.menu.UserMenuProvider.MenuItem;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

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
	public static interface MenuItemClickListener
	{
		void onMenuItemClicked(final MenuItem menuItem);
	}

	//
	// UI
	private final TextField searchTextField;
	private final HorizontalLayout menuGroupsContainer;
	private final Label nothingFoundLabel;

	//
	// Listeners
	private static final MenuItemClickListener CLICKLISTENER_NULL = (menuItem) -> {
	};
	private MenuItemClickListener clickListener = CLICKLISTENER_NULL;

	public MenuPanel()
	{
		super();

		searchTextField = new TextField();
		searchTextField.setTextChangeEventMode(TextChangeEventMode.TIMEOUT);
		searchTextField.setTextChangeTimeout(500);
		searchTextField.addTextChangeListener(event -> filterMenuItems(event.getText()));

		menuGroupsContainer = new HorizontalLayout();
		menuGroupsContainer.setPrimaryStyleName("mf-menu");

		nothingFoundLabel = new Label("No results found");

		setCompositionRoot(new VerticalLayout(searchTextField, menuGroupsContainer));
	}

	public void setClickListener(final MenuItemClickListener clickListener)
	{
		this.clickListener = clickListener == null ? CLICKLISTENER_NULL : clickListener;
	}

	private void fireClickListener(final MenuItem menuItem)
	{
		clickListener.onMenuItemClicked(menuItem);
	}

	public void setMenuItems(final List<MenuItem> menuItems)
	{
		searchTextField.setValue("");
		menuGroupsContainer.removeAllComponents();

		if (menuItems != null && !menuItems.isEmpty())
		{
			for (final MenuItem groupItem : menuItems)
			{
				final MenuGroupPanel itemComp = new MenuGroupPanel(groupItem);
				menuGroupsContainer.addComponent(itemComp);
			}
		}

		nothingFoundLabel.setVisible(false);
		menuGroupsContainer.addComponent(nothingFoundLabel);
	}

	private void filterMenuItems(final String searchText)
	{
		//
		// Create filter
		final MenuItemFilter filter;
		if (searchText == null || searchText.trim().isEmpty())
		{
			filter = (menuItem) -> {
				return true;
			};
		}
		else
		{
			final String searchTextUC = searchText.toUpperCase();
			filter = (menuItem) -> {
				final String caption = menuItem.getCaption().toUpperCase();
				return caption.indexOf(searchTextUC) >= 0;
			};
		}

		int countDisplayed = 0;
		for (final Component menuGroupComp : menuGroupsContainer)
		{
			if (menuGroupComp instanceof MenuGroupPanel)
			{
				final MenuGroupPanel menuGroupPanel = (MenuGroupPanel)menuGroupComp;
				menuGroupPanel.setFilter(filter);

				if (menuGroupPanel.isVisible())
				{
					countDisplayed++;
				}
			}
		}

		nothingFoundLabel.setVisible(countDisplayed == 0);
	}

	private static interface MenuItemFilter
	{
		boolean accept(MenuItem menuItem);
	}

	private class MenuGroupPanel extends CssLayout
	{
		private final MenuItem groupItem;

		private final Label captionLabel;
		private final ComponentContainer content;

		private MenuItemFilter filter = null;

		public MenuGroupPanel(final MenuItem groupItem)
		{
			super();
			setPrimaryStyleName("mf-menu-group");
			this.groupItem = groupItem;

			captionLabel = new Label();
			captionLabel.setPrimaryStyleName("mf-menu-group-caption");

			content = new VerticalLayout();
			content.setPrimaryStyleName("mf-menu-group-content");

			addComponents(captionLabel, content);

			updateFromItem();
		}

		public void setFilter(final MenuItemFilter filter)
		{
			if (this.filter == filter)
			{
				return;
			}

			this.filter = filter;
			applyFilter();
		}

		private void applyFilter()
		{
			int countDisplayed = 0;
			for (final Component comp : content)
			{
				if (comp instanceof MenuItemComponent)
				{
					final MenuItemComponent menuItemComp = (MenuItemComponent)comp;

					final MenuItem menuItem = menuItemComp.getMenuItem();
					final boolean displayed = filter == null || filter.accept(menuItem);
					menuItemComp.setVisible(displayed);

					if (displayed)
					{
						countDisplayed++;
					}
				}
			}

			//
			setVisible(countDisplayed > 0);
		}

		private void updateFromItem()
		{
			captionLabel.setValue(groupItem.getCaption());

			content.removeAllComponents();
			for (final MenuItem menuItem : groupItem.getChildren())
			{
				final MenuItemComponent menuItemComp = new MenuItemComponent(menuItem);
				content.addComponent(menuItemComp);
			}

			applyFilter();
		}
	}

	private class MenuItemComponent extends Button
	{
		private final MenuItem menuItem;

		public MenuItemComponent(final MenuItem menuItem)
		{
			super();
			setPrimaryStyleName("mf-menu-item");
			this.menuItem = menuItem;

			addClickListener(event -> fireClickListener(menuItem));

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
	}
}

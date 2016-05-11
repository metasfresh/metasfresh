package de.metas.ui.web.vaadin.login;

import org.compiere.model.X_AD_Menu;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.vaadin.data.util.HierarchicalBeanContainer;
import de.metas.ui.web.vaadin.login.MenuTreeProvider.MenuTreeNode;
import de.metas.ui.web.vaadin.window.components.AWindow;

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
public class MainView extends HorizontalLayout
{
	private HierarchicalBeanContainer<MenuTreeNode> menuTreeContainer;
	private Filter menuTreeFilter = null;

	private CssLayout contentWrapper = new CssLayout();

	public MainView()
	{
		super();
		setSizeFull();

		//
		// Menu tree
		{
			final TextField searchText = new TextField();
			searchText.setImmediate(true);
			searchText.addTextChangeListener(event -> onSearchText(event.getText()));

			menuTreeContainer = new MenuTreeProvider().getMenuTree();
			final Tree menuTree = new Tree();
			menuTree.setContainerDataSource(menuTreeContainer);
			menuTree.setItemCaptionPropertyId(HierarchicalBeanContainer.PROPERTYID_Caption);
			menuTree.setItemIconPropertyId(HierarchicalBeanContainer.PROPERTYID_Icon);
			menuTree.addValueChangeListener(event -> onMenuItemSelected(findMenuTreeNodeById(event.getProperty().getValue())));

			final VerticalLayout menuTreeWrapper = new VerticalLayout(searchText, menuTree);
			addComponents(menuTreeWrapper);
		}

		//
		// Content
		{
			contentWrapper.setSizeFull();
			addComponent(contentWrapper);
		}
	}

	private void onSearchText(final String text)
	{
		if (Check.isEmpty(text, true))
		{
			setFilter(null);
		}
		else
		{
			String textNormalized = text.trim();
			if (textNormalized.startsWith("%"))
			{
				textNormalized = textNormalized.substring(1);
			}
			if (!textNormalized.endsWith("%"))
			{
				textNormalized = textNormalized.substring(0, textNormalized.length() - 1);
			}
			final Filter filter = new SimpleStringFilter(HierarchicalBeanContainer.PROPERTYID_Caption, textNormalized, true, false);
			setFilter(filter);
		}
	}

	private void setFilter(final Filter filter)
	{
		if (Check.equals(menuTreeFilter, filter))
		{
			return;
		}

		//
		// Remove old filter
		if (menuTreeFilter != null)
		{
			menuTreeContainer.removeContainerFilter(menuTreeFilter);
		}
		menuTreeFilter = null;

		//
		// Add the new filter
		if (filter != null)
		{
			menuTreeContainer.addContainerFilter(filter);
		}
		menuTreeFilter = filter;

	}
	
	private MenuTreeNode findMenuTreeNodeById(final Object menuId)
	{
		if (menuId == null)
		{
			return null;
		}
		final BeanItem<MenuTreeNode> menuItem = menuTreeContainer.getItem(menuId);
		if (menuItem == null)
		{
			return null;
		}
		final MenuTreeNode menuNode = menuItem.getBean();
		return menuNode;
	}

	private void onMenuItemSelected(final MenuTreeNode menuNode)
	{
		if(menuNode == null)
		{
			return;
		}
		
		final String action = menuNode.getAction();

		if (X_AD_Menu.ACTION_Window.equals(action))
		{
			final int adWindowId = menuNode.getAD_Window_ID();
			openWindow(adWindowId);
		}
		else
		{
			throw new UnsupportedOperationException("Action not supported: " + action);
		}
	}

	private void openWindow(final int adWindowId)
	{
		final AWindow windowComp = new AWindow(adWindowId);

		contentWrapper.removeAllComponents();
		contentWrapper.addComponent(windowComp);
	}

}

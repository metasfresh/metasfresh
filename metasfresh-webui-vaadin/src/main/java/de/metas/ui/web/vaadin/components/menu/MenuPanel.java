package de.metas.ui.web.vaadin.components.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Supplier;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.metas.ui.web.vaadin.theme.Theme;
import de.metas.ui.web.window.shared.menu.MenuItem;

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
	private final VerticalLayout content;
	private final TextField searchTextField;
	private final Button previousPageButton;
	private boolean hiddenByStyle = false;
	private MenuPanelPage rootPage;
	private List<MenuPanelPage> pages = new ArrayList<>();

	//
	// Listeners
	private final ForwardingMenuItemClickListener clickListener = new ForwardingMenuItemClickListener()
	{
		@Override
		public void onMenuItemClicked(final MenuItem menuItem)
		{
			final List<? extends MenuItem> menuItemChildren = menuItem.getChildren();
			if(menuItemChildren != null && !menuItemChildren.isEmpty())
			{
				navigateTo(menuItem);
			}
			else
			{
				super.onMenuItemClicked(menuItem);
			}
		};
	};

	public MenuPanel()
	{
		super();
		setPrimaryStyleName(STYLE);

		previousPageButton = new Button("Back");
		previousPageButton.addClickListener(event -> navigateBack());
		previousPageButton.setVisible(false);
		
		searchTextField = new TextField();
		searchTextField.setTextChangeEventMode(TextChangeEventMode.TIMEOUT);
		searchTextField.setTextChangeTimeout(500);
		searchTextField.addTextChangeListener(event -> filterMenuItems(event.getText()));
		
		final HorizontalLayout toolbarLane = new HorizontalLayout(previousPageButton, searchTextField);

		this.content = new VerticalLayout(toolbarLane);
		setCompositionRoot(content);

		rootPage = new MenuPanelPage(clickListener);
		rootPage.setSearchTextField(searchTextField);
		addNewPage(rootPage);
	}

	public void setClickListener(final MenuItemClickListener clickListener)
	{
		this.clickListener.setDelegate(clickListener);
	}

	private MenuPanelPage getCurrentPage()
	{
		return pages.get(pages.size() - 1);
	}

	private MenuPanelPage getPreviousPage()
	{
		final int countPages = pages.size();
		if (countPages > 1)
		{
			return pages.get(countPages - 2);
		}
		else
		{
			return null;
		}
	}

	private void removeAllPagesExceptRoot()
	{
		for (final Iterator<MenuPanelPage> it = pages.iterator(); it.hasNext();)
		{
			final MenuPanelPage page = it.next();
			if (page == rootPage)
			{
				continue;
			}
			
			it.remove();
			content.removeComponent(page);
		}
		
		rootPage.setVisible(true);
		
		previousPageButton.setVisible(false);
	}
	
	private void addNewPage(final MenuPanelPage newPage)
	{
		for (final MenuPanelPage page : pages)
		{
			page.setVisible(false);
		}
		
		pages.add(newPage);
		content.addComponent(newPage);
		
		if(!isHiddenByStyle())
		{
			newPage.updateIfStaled();
		}

		searchTextField.setValue(""); // reset search field
		previousPageButton.setVisible(true);
	}
	
	private void removePagesUntil(final MenuPanelPage lastPage)
	{
		boolean foundPage = false;
		for (final MenuPanelPage page : new ArrayList<>(pages))
		{
			if (!foundPage)
			{
				if (page == lastPage)
				{
					foundPage = true;
				}
			}
			else
			{
				pages.remove(page);
				content.removeComponent(page);
			}
		}
		
		if (!foundPage)
		{
			return;
		}
		
		if(!isHiddenByStyle())
		{
			lastPage.updateIfStaled();
		}
		lastPage.setVisible(true);
		
		searchTextField.setValue(""); // reset search field
		previousPageButton.setVisible(pages.size() > 1);
	}
	
	public void setRootMenuItems(final Supplier<List<? extends MenuItem>> menuItemsSupplier)
	{
		removeAllPagesExceptRoot();
		
		final MenuPanelPage page = getCurrentPage();
		page.setMenuItems(menuItemsSupplier);

		if (!isHiddenByStyle())
		{
			page.updateIfStaled();
		}
		
		searchTextField.setValue(""); // reset search field
	}
	
	protected void navigateTo(final MenuItem menuItem)
	{
		final MenuPanelPage menuItemPage = new MenuPanelPage(clickListener);
		menuItemPage.setSearchTextField(searchTextField);
		menuItemPage.setMenuItems(() -> menuItem.getChildren());

		if(!isHiddenByStyle())
		{
			menuItemPage.updateIfStaled();
		}

		addNewPage(menuItemPage);
	}

	private void navigateBack()
	{
		final MenuPanelPage previousPage = getPreviousPage();
		if(previousPage != null)
		{
			removePagesUntil(previousPage);
		}
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

		getCurrentPage().setFilter(filter);
	}

	public final void setHiddenByStyle(final boolean hidden)
	{
		if (this.hiddenByStyle == hidden)
		{
			return;
		}

		if (!hidden)
		{
			getCurrentPage().updateIfStaled();
		}

		Theme.setHidden(this, hidden);
		this.hiddenByStyle = hidden;
	}

	public boolean isHiddenByStyle()
	{
		return hiddenByStyle;
	}
}

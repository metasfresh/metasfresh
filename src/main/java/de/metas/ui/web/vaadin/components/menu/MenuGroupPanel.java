package de.metas.ui.web.vaadin.components.menu;

import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
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
class MenuGroupPanel extends CssLayout
{
	public static final String STYLE = MenuPanel.STYLE + "-group";

	private final MenuItem groupItem;
	private final MenuItemClickListener clickListener;

	private final Label captionLabel;
	private final ComponentContainer content;

	private MenuItemFilter filter = null;

	private boolean hiddenByStyle = false;

	public MenuGroupPanel(final MenuItem groupItem, final MenuItemClickListener clickListener)
	{
		super();
		setPrimaryStyleName(STYLE);
		this.groupItem = groupItem;
		this.clickListener = clickListener;

		captionLabel = new Label();
		captionLabel.setPrimaryStyleName(STYLE + "-caption");

		content = new VerticalLayout();
		content.setPrimaryStyleName(STYLE + "-content");

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
				menuItemComp.setHiddenByStyle(!displayed);

				if (displayed)
				{
					countDisplayed++;
				}
			}
		}

		//
		setHiddenByStyle(countDisplayed <= 0);
	}

	private void updateFromItem()
	{
		captionLabel.setValue(groupItem.getCaption());

		content.removeAllComponents();
		for (final MenuItem menuItem : groupItem.getChildren())
		{
			final MenuItemComponent menuItemComp = new MenuItemComponent(menuItem, clickListener);
			content.addComponent(menuItemComp);
		}

		applyFilter();
	}

	private final void setHiddenByStyle(final boolean hidden)
	{
		Theme.setHidden(this, hidden);
		this.hiddenByStyle = hidden;
	}

	public final boolean isHiddenByStyle()
	{
		return hiddenByStyle;
	}
}
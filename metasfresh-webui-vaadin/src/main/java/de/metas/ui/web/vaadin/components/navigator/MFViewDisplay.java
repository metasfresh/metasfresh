package de.metas.ui.web.vaadin.components.navigator;

import java.util.List;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.metas.ui.web.vaadin.components.menu.MenuItem;
import de.metas.ui.web.vaadin.components.menu.MenuItemClickListener;
import de.metas.ui.web.vaadin.components.menu.MenuPanel;

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
public class MFViewDisplay extends CustomComponent implements ViewDisplay
{
	public static MFViewDisplay getMFViewDisplayOrNull(final ViewChangeEvent event)
	{
		final ViewDisplay viewDisplay = event.getNavigator().getDisplay();
		if (viewDisplay instanceof MFViewDisplay)
		{
			return (MFViewDisplay)viewDisplay;
		}
		return null;
	}

	static final String STYLE = "mf-window";

	private Label titleLabel;
	private final ViewContainer viewContainer;

	private MenuPanel menuPanel;

	public MFViewDisplay(final UI ui)
	{
		super();

		final VerticalLayout content = new VerticalLayout();
		content.addStyleName(STYLE);

		//
		// Lane: title
		{
			final Button icon = new Button();
			icon.setPrimaryStyleName(STYLE + "-title-icon");
			icon.setIcon(FontAwesome.BARS);
			icon.addClickListener(event -> toggleMenuPanel());

			titleLabel = new Label();
			titleLabel.setPrimaryStyleName(STYLE + "-title-text");

			final HorizontalLayout panel = new HorizontalLayout(icon, titleLabel);
			panel.addStyleName(STYLE + "-title-lane");
			content.addComponent(panel);
		}
		
		//
		// Lane: menu
		{
			menuPanel = new MenuPanel();
			menuPanel.setHiddenByStyle(false);
			
			content.addComponent(menuPanel);
		}

		viewContainer = new ViewContainer();
		content.addComponent(viewContainer);

		setCompositionRoot(content);

		ui.setContent(this);
	}

	@Override
	public void showView(final View view)
	{
		if (!(view instanceof Component))
		{
			throw new IllegalArgumentException("View is not a component: " + view);
		}

		final Component viewComp = (Component)view;
		viewContainer.setContent(viewComp);
		
		setTitle("-");
		hideMenuPanel();
		setMenuItems(() -> ImmutableList.of(), MenuItemClickListener.NULL);
	}

	private static final class ViewContainer extends CustomComponent
	{
		public ViewContainer()
		{
			super();
		}

		public void setContent(final Component content)
		{
			super.setCompositionRoot(content);
		}
	}
	
	public void setTitle(final String title)
	{
		titleLabel.setValue(title);
	}
	
	public void setMenuItems(final Supplier<List<MenuItem>> menuItemsSupplier, final MenuItemClickListener clickListener)
	{
		menuPanel.setRootMenuItems(menuItemsSupplier);
		menuPanel.setClickListener(clickListener);
	}
	
	private void toggleMenuPanel()
	{
		menuPanel.setHiddenByStyle(!menuPanel.isHiddenByStyle());
	}

	public void showMenuPanel()
	{
		menuPanel.setHiddenByStyle(false);
	}

	public void hideMenuPanel()
	{
		menuPanel.setHiddenByStyle(true);
	}

}

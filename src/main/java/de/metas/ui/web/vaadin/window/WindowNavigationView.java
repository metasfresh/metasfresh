package de.metas.ui.web.vaadin.window;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;

import de.metas.ui.web.vaadin.components.menu.UserMenuProvider.MenuItem;
import de.metas.ui.web.vaadin.components.menu.UserMenuProvider.MenuItemImpl;
import de.metas.ui.web.vaadin.components.menu.UserMenuProvider.MenuItemType;
import de.metas.ui.web.vaadin.components.navigator.MFView;
import de.metas.ui.web.vaadin.components.navigator.MFViewDisplay;
import de.metas.ui.web.vaadin.window.descriptor.legacy.VOPropertyDescriptorProvider;
import de.metas.ui.web.vaadin.window.model.action.Action;
import de.metas.ui.web.vaadin.window.model.action.ActionGroup;
import de.metas.ui.web.vaadin.window.view.ActionsView;

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
public class WindowNavigationView extends CustomComponent implements MFView, ActionsView
{
	private final int windowId;
	private MFViewDisplay viewDisplay;
	private WindowPresenter windowPresenter;
	
	private List<Action> actions = null;

	public WindowNavigationView(final int windowId)
	{
		super();
		this.windowId = windowId;
	}
	
	private MFViewDisplay getViewDisplay()
	{
		return viewDisplay;
	}
	
	private WindowPresenter getWindowPresenter()
	{
		return windowPresenter;
	}

	@Override
	public void enter(final ViewChangeEvent event)
	{
		this.windowPresenter = new WindowPresenter();

		this.viewDisplay = MFViewDisplay.getMFViewDisplayOrNull(event);
		if (viewDisplay != null)
		{
			windowPresenter.addPropertyValueChangedListener(WindowConstants.PROPERTYNAME_WindowTitle, (propertyName, value) -> {
				viewDisplay.setTitle(value == null ? "" : value.toString());
			});
		}
		
		windowPresenter.addActionsView(this);

		final PropertyDescriptor rootPropertyDescriptor = new VOPropertyDescriptorProvider().provideForWindow(windowId);
		windowPresenter.setRootPropertyDescriptor(rootPropertyDescriptor);

		final Component viewComp = windowPresenter.getViewComponent();
		setCompositionRoot(viewComp);
	}
	
	@Override
	public void exit(final ViewChangeEvent event)
	{
		if (windowPresenter != null)
		{
			windowPresenter.dispose();
			windowPresenter = null;
		}
		
		viewDisplay = null;
		
		actions = null;
		
		setCompositionRoot(null);
	}

	@Override
	public void setActions(final List<Action> actions)
	{
		if (Objects.equal(this.actions, actions))
		{
			return;
		}
		this.actions = actions;
		
		final MFViewDisplay viewDisplay = getViewDisplay();
		if(viewDisplay == null)
		{
			return;
		}
		
		viewDisplay.setMenuItems(() -> createMenuItems(actions), menuItem -> onActionMenuItemClicked(menuItem));
	}

	private List<MenuItem> createMenuItems(final List<Action> actions)
	{
		if (actions == null || actions.isEmpty())
		{
			return ImmutableList.of();
		}
		
		final Map<ActionGroup, MenuItemImpl.Builder> groups = new LinkedHashMap<>();
		for (final Action action : actions)
		{
			final ActionGroup actionGroup = action.getActionGroup();
			MenuItemImpl.Builder menuItemGroupBuilder = groups.get(actionGroup);
			if(menuItemGroupBuilder == null)
			{
				menuItemGroupBuilder = MenuItemImpl.builder()
						.setCaption(actionGroup.getCaption());
				groups.put(actionGroup, menuItemGroupBuilder);
			}
			
			final ActionMenuItem menuItem = ActionMenuItem.of(action);
			menuItemGroupBuilder.addChild(menuItem);
		}
		
		final ImmutableList.Builder<MenuItem> menuItems = ImmutableList.builder();
		for (final MenuItemImpl.Builder menuItemGroupBuilder : groups.values())
		{
			final MenuItem menuItemGroup = menuItemGroupBuilder.build();
			menuItems.add(menuItemGroup);
		}
		
		return menuItems.build();
	}
	
	private void onActionMenuItemClicked(final MenuItem menuItem)
	{
		if (!(menuItem instanceof ActionMenuItem))
		{
			return;
		}

		final WindowPresenter windowPresenter = getWindowPresenter();
		if(windowPresenter == null)
		{
			return;
		}

		final ActionMenuItem actionMenuItem = (ActionMenuItem)menuItem;
		final Action action = actionMenuItem.getAction();

		windowPresenter.onActionClicked(action);
	}
	
	private static class ActionMenuItem implements MenuItem
	{
		public static final ActionMenuItem of(final Action action)
		{
			return new ActionMenuItem(action);
		}

		private final Action action;
		
		private ActionMenuItem(final Action action)
		{
			super();
			this.action = action;
		}

		@Override
		public String getCaption()
		{
			return action.getCaption();
		}

		@Override
		public MenuItemType getType()
		{
			return null;
		}

		@Override
		public int getElementId()
		{
			return 0;
		}

		@Override
		public Collection<MenuItem> getChildren()
		{
			return ImmutableList.of();
		}

		@Override
		public Resource getIcon()
		{
			return action.getIcon();
		}

		public Action getAction()
		{
			return action;
		}
	}

}

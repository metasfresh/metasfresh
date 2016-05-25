package de.metas.ui.web.vaadin.window;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;

import de.metas.ui.web.service.IImageProvider.IImageResource;
import de.metas.ui.web.service.impl.VaadinImageProvider.VaadinImageResource;
import de.metas.ui.web.vaadin.components.menu.MenuItem;
import de.metas.ui.web.vaadin.components.navigator.MFView;
import de.metas.ui.web.vaadin.components.navigator.MFViewDisplay;
import de.metas.ui.web.vaadin.window.view.ActionsView;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.descriptor.PropertyDescriptor;
import de.metas.ui.web.window.descriptor.legacy.VOPropertyDescriptorProvider;
import de.metas.ui.web.window.model.action.Action;
import de.metas.ui.web.window.model.action.ActionGroup;
import de.metas.ui.web.window.model.action.ActionsList;
import de.metas.ui.web.window.shared.datatype.PropertyPath;

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

	private ActionsList actions = null;
	private boolean disposed = false;

	public WindowNavigationView(final int windowId)
	{
		super();
		this.windowId = windowId;
	}

	public void dispose()
	{
		this.disposed = true;

		viewDisplay = null;

		if (windowPresenter != null)
		{
			windowPresenter.dispose();
			windowPresenter = null;
		}

		actions = null;
		setCompositionRoot(null);
	}

	private final void assertNotDisposed()
	{
		Preconditions.checkState(!disposed, "view is not disposed");
	}

	private MFViewDisplay getViewDisplay()
	{
		return viewDisplay;
	}

	private WindowPresenter getCreateWindowPresenter()
	{
		if (windowPresenter == null)
		{
			assertNotDisposed();

			windowPresenter = new WindowPresenter();
			windowPresenter.addPropertyValueChangedListener(
					PropertyPath.of(WindowConstants.PROPERTYNAME_WindowTitle) //
					, (propertyPath, value) -> {
						if (viewDisplay != null)
						{
							viewDisplay.setTitle(value == null ? "" : value.toString());
						}
					});

			final PropertyDescriptor rootPropertyDescriptor = new VOPropertyDescriptorProvider().provideForWindow(windowId);
			windowPresenter.setRootPropertyDescriptor(rootPropertyDescriptor);
		}

		return windowPresenter;
	}

	private WindowPresenter getWindowPresenter()
	{
		return windowPresenter;
	}

	@Override
	public void enter(final ViewChangeEvent event)
	{
		assertNotDisposed();

		this.viewDisplay = MFViewDisplay.getMFViewDisplayOrNull(event);
		if (viewDisplay != null)
		{
			viewDisplay.setMenuItemClickListener(menuItem -> onActionMenuItemClicked(menuItem));
		}

		final WindowPresenter windowPresenter = getCreateWindowPresenter();

		windowPresenter.removeActionsView(this);
		windowPresenter.addActionsView(this);

		final Component viewComp = windowPresenter.getViewComponent();
		if (viewComp != getCompositionRoot())
		{
			setCompositionRoot(viewComp);
		}
	}

	@Override
	public void exit(final ViewChangeEvent event)
	{
		final WindowPresenter windowPresenter = getWindowPresenter();
		if (windowPresenter != null)
		{
			windowPresenter.removeActionsView(this);
		}

		viewDisplay = null;
		actions = null;
		// setCompositionRoot(null);
	}

	@Override
	public void setActions(final ActionsList actions)
	{
		if (Objects.equal(this.actions, actions))
		{
			return;
		}
		this.actions = actions;

		final MFViewDisplay viewDisplay = getViewDisplay();
		if (viewDisplay == null)
		{
			return;
		}

		viewDisplay.setMenuItems(() -> createMenuItems(actions));
	}

	private List<MenuItem> createMenuItems(final ActionsList actions)
	{
		if (actions == null || actions.isEmpty())
		{
			return ImmutableList.of();
		}

		final Map<ActionGroup, List<MenuItem>> groups = new LinkedHashMap<>();
		for (final Action action : actions)
		{
			final ActionGroup actionGroup = action.getActionGroup();
			List<MenuItem> menuItems = groups.get(actionGroup);
			if (menuItems == null)
			{
				menuItems = new ArrayList<>();
				groups.put(actionGroup, menuItems);
			}

			final ActionMenuItem menuItem = new ActionMenuItem(action);
			menuItems.add(menuItem);
		}

		final ImmutableList.Builder<MenuItem> rootMenuItems = ImmutableList.builder();
		for (final Entry<ActionGroup, List<MenuItem>> e : groups.entrySet())
		{
			final ActionGroup actionGroup = e.getKey();
			final List<MenuItem> groupMenuItems = e.getValue();
			final MenuItem menuItemGroup = ActionGroupMenuItem.of(actionGroup, groupMenuItems);
			rootMenuItems.add(menuItemGroup);
		}

		return rootMenuItems.build();
	}

	private void onActionMenuItemClicked(final MenuItem menuItem)
	{
		if (!(menuItem instanceof ActionMenuItem))
		{
			return;
		}

		final WindowPresenter windowPresenter = getWindowPresenter();
		if (windowPresenter == null)
		{
			return;
		}

		final ActionMenuItem actionMenuItem = (ActionMenuItem)menuItem;
		final Action action = actionMenuItem.getAction();

		windowPresenter.onActionClicked(action.getActionId());
	}

	private static class ActionGroupMenuItem implements MenuItem
	{
		public static final ActionGroupMenuItem of(final ActionGroup actionGroup, final List<MenuItem> menuItems)
		{
			return new ActionGroupMenuItem(actionGroup, menuItems);
		}

		private final ActionGroup actionGroup;
		private final List<MenuItem> menuItems;

		private ActionGroupMenuItem(final ActionGroup actionGroup, final List<MenuItem> menuItems)
		{
			super();
			this.actionGroup = actionGroup;
			this.menuItems = ImmutableList.copyOf(menuItems);
		}

		@Override
		public String getCaption()
		{
			return actionGroup.getCaption();
		}

		@Override
		public List<MenuItem> getChildren()
		{
			return menuItems;
		}

		@Override
		public Resource getIcon()
		{
			return null;
		}

	}

	class ActionMenuItem implements MenuItem
	{
		private final Action action;
		private final Supplier<List<MenuItem>> childrenSupplier;

		ActionMenuItem(final Action action)
		{
			super();
			this.action = action;

			if (action.isProvidingChildActions())
			{
				childrenSupplier = () -> {
					final ActionsList childActions = getWindowPresenter().viewRequestChildActions(action.getActionId());
					return createMenuItems(childActions);
				};
			}
			else
			{
				childrenSupplier = null;
			}
		}

		@Override
		public String getCaption()
		{
			return action.getCaption();
		}

		@Override
		public List<MenuItem> getChildren()
		{
			if (childrenSupplier != null)
			{
				return childrenSupplier.get();
			}
			return ImmutableList.of();
		}

		@Override
		public Resource getIcon()
		{
			final IImageResource imageResource = action.getIcon();
			return VaadinImageResource.getResource(imageResource);
		}

		public Action getAction()
		{
			return action;
		}
	}

}

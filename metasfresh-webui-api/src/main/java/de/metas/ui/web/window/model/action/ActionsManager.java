package de.metas.ui.web.window.model.action;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import de.metas.ui.web.service.IImageProvider.IImageResource;
import de.metas.ui.web.window.model.action.Action.ActionEvent;

/*
 * #%L
 * metasfresh-webui-api
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

public class ActionsManager
{
	public static final ActionsManager newInstance()
	{
		return new ActionsManager();
	}

	private final Map<String, Action> _actions = new LinkedHashMap<>();
	private final Map<String, Action.Listener> _actionListeners = new HashMap<>();

	private final Map<String, ActionsManagerProvider> _actionsManagerProviders = new HashMap<>();
	private final Map<String, ActionsManager> _childActionsManagers = new HashMap<>();

	private ActionsManager()
	{
		super();
	}

	public ActionBuilder newAction()
	{
		return new ActionBuilder();
	}

	public ActionBuilder newActionWithListener(final ActionGroup actionGroup, final String actionId, final Action.Listener listener)
	{
		return newAction()
				.setActionGroup(actionGroup)
				.setActionIdAndUpdateFromAD_Messages(actionId)
				.setActionId(actionId)
				.setListener(listener);
	}

	public ActionsManager addActionWithManagerProvider(final ActionGroup actionGroup, final String actionId, final ActionsManagerProvider provider)
	{
		newAction()
				.setActionGroup(actionGroup)
				.setActionIdAndUpdateFromAD_Messages(actionId)
				.setChildrenActionsManagerProvider(provider)
				.buildAndAdd();
		return this;
	}

	public ActionsManager addAction(final Action action)
	{
		final String actionId = action.getActionId();
		_actions.put(actionId, action);

		return this;
	}

	public ActionsManager setListener(final String actionId, final Action.Listener actionListener)
	{
		_actionListeners.put(actionId, actionListener);
		return this;
	}

	public ActionsManager setProvider(final String actionId, final ActionsManagerProvider actionProvider)
	{
		_actionsManagerProviders.put(actionId, actionProvider);
		return this;
	}

	private Action getActionOrNull(final String actionId)
	{
		return _actions.get(actionId);
	}

	private Action getAction(final String actionId)
	{
		final Action action = getActionOrNull(actionId);
		if (action == null)
		{
			throw new IllegalArgumentException("No action found for ID=" + actionId);
		}
		return action;
	}

	public void executeAction(final String actionId, final Object target)
	{
		final ActionsManager actionManager = findActionsManager(actionId);
		if (actionManager == null)
		{
			throw new UnsupportedOperationException("Unsupported action: " + actionId);
		}

		actionManager.executeActionNow(actionId, target);
	}

	private void executeActionNow(final String actionId, final Object target)
	{
		final Action action = getAction(actionId);
		final Action.Listener actionListener = _actionListeners.get(actionId);
		if (actionListener != null)
		{
			actionListener.handleAction(ActionEvent.of(action, target));
		}
		else
		{
			throw new UnsupportedOperationException("Unsupported action: " + action);
		}
	}

	private ActionsManager findActionsManager(final String actionId)
	{
		if (getActionOrNull(actionId) != null)
		{
			return this;
		}

		for (final ActionsManager childActionManager : _childActionsManagers.values())
		{
			final ActionsManager actionManager = childActionManager.findActionsManager(actionId);
			if (actionManager != null)
			{
				return actionManager;
			}
		}

		return null;
	}

	public ActionsList getActionsList()
	{
		return ActionsList.of(ImmutableList.copyOf(_actions.values()));
	}

	public ActionsList getChildActions(final String parentActionId)
	{
		final ActionsManagerProvider provider = _actionsManagerProviders.get(parentActionId);
		if (provider == null)
		{
			throw new IllegalArgumentException("No children provider found for " + parentActionId);
		}

		final Action parentAction = getAction(parentActionId);

		final ActionsManager childActionsManager = provider.provideActionsManager(parentAction);
		_childActionsManagers.put(parentActionId, childActionsManager);

		return childActionsManager.getActionsList();
	}

	public final class ActionBuilder extends Action.Builder
	{
		private Action.Listener listener;
		private ActionsManagerProvider childrenActionsManagerProvider;

		private ActionBuilder()
		{
			super();
		}

		@Override
		@Deprecated
		public Action build()
		{
			throw new UnsupportedOperationException();
		}

		public ActionsManager buildAndAdd()
		{
			final ActionsManager manager = ActionsManager.this;
			final Action action = super.build();
			manager.addAction(action);
			if (listener != null)
			{
				manager.setListener(action.getActionId(), listener);
			}

			if (childrenActionsManagerProvider != null)
			{
				manager.setProvider(action.getActionId(), childrenActionsManagerProvider);
			}

			return manager;
		}

		@Override
		public ActionBuilder setActionGroup(final ActionGroup actionGroup)
		{
			super.setActionGroup(actionGroup);
			return this;
		}

		@Override
		public ActionBuilder setActionId(final String actionId)
		{
			super.setActionId(actionId);
			return this;
		}

		@Override
		public ActionBuilder setActionIdAndUpdateFromAD_Messages(final String adMessage)
		{
			super.setActionIdAndUpdateFromAD_Messages(adMessage);
			return this;
		}

		@Override
		public ActionBuilder setCaption(final String caption)
		{
			super.setCaption(caption);
			return this;
		}

		@Override
		public ActionBuilder setIcon(final IImageResource icon)
		{
			super.setIcon(icon);
			return this;
		}

		public ActionBuilder setListener(final Action.Listener listener)
		{
			this.listener = listener;
			return this;
		}

		public ActionBuilder setChildrenActionsManagerProvider(final ActionsManagerProvider childrenActionsManagerProvider)
		{
			this.childrenActionsManagerProvider = childrenActionsManagerProvider;
			setProvidingChildActions(childrenActionsManagerProvider != null);
			return this;
		}

		@Override
		public ActionBuilder setToolbarAction()
		{
			super.setToolbarAction();
			return this;
		}

		@Override
		public ActionBuilder setToolbarAction(final boolean toolbarAction)
		{
			super.setToolbarAction(toolbarAction);
			return this;
		}

	}
}

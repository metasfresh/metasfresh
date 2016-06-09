package de.metas.ui.web.window.model.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import de.metas.ui.web.window.shared.ImageResource;
import de.metas.ui.web.window.shared.action.Action;
import de.metas.ui.web.window.shared.action.Action.ActionEvent;
import de.metas.ui.web.window.shared.action.ActionGroup;
import de.metas.ui.web.window.shared.action.ActionsList;

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

	private final List<Action> actionsList = new ArrayList<>();
	private ActionsList _actionsListImmutable; // lazy/cached

	private final Map<String, ActionInstance> _actions = new HashMap<>();

	private ActionsManager()
	{
		super();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("actions", actionsList)
				.toString();
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
		Preconditions.checkNotNull(action, "action not null");

		if (actionsList.contains(action))
		{
			return this;
		}

		actionsList.add(action);

		final ActionInstance actionInstance = new ActionInstance(action);
		_actions.put(actionInstance.getActionId(), actionInstance);

		_actionsListImmutable = null; // reset cache

		return this;
	}

	private ActionInstance getActionInstance(final String actionId)
	{
		final ActionInstance actionInstance = getActionInstanceOrNull(actionId);
		if (actionInstance == null)
		{
			throw new IllegalArgumentException("No action found for ID=" + actionId);
		}
		return actionInstance;
	}

	private ActionInstance getActionInstanceOrNull(final String actionId)
	{
		return _actions.get(actionId);
	}

	public ActionsManager setListener(final String actionId, final Action.Listener actionListener)
	{
		getActionInstance(actionId).setListener(actionListener);
		return this;
	}

	public ActionsManager setProvider(final String actionId, final ActionsManagerProvider actionProvider)
	{
		getActionInstance(actionId).setProvider(actionProvider);
		return this;
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
		final ActionInstance actionInstance = getActionInstance(actionId);
		final Action.Listener actionListener = actionInstance.getListener();
		if (actionListener != null)
		{
			actionListener.handleAction(ActionEvent.of(actionInstance.getAction(), target));
		}
		else
		{
			throw new UnsupportedOperationException("Unsupported action: " + actionInstance);
		}
	}

	private ActionsManager findActionsManager(final String actionId)
	{
		if (getActionInstanceOrNull(actionId) != null)
		{
			return this;
		}

		for (final ActionInstance actionInstance : _actions.values())
		{
			final ActionsManager childActionManager = actionInstance.getChildActionsManager();
			if (childActionManager == null)
			{
				continue;
			}

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
		if (_actionsListImmutable == null)
		{
			_actionsListImmutable = ActionsList.of(actionsList);
		}
		return _actionsListImmutable;
	}

	public ActionsList getChildActions(final String parentActionId)
	{
		final ActionInstance actionInstance = getActionInstance(parentActionId);
		final ActionsManagerProvider provider = actionInstance.getProvider();
		if (provider == null)
		{
			throw new IllegalArgumentException("No children provider found for " + parentActionId);
		}

		final ActionInstance parentActionInstance = getActionInstance(parentActionId);

		final ActionsManager childActionsManager = provider.provideActionsManager(parentActionInstance.getAction());
		actionInstance.setChildActionsManager(childActionsManager);

		return childActionsManager.getActionsList();
	}

	private static final class ActionInstance
	{
		private final Action action;
		private Action.Listener listener;
		private ActionsManagerProvider provider;
		private ActionsManager childActionsManager;

		private ActionInstance(final Action action)
		{
			super();
			this.action = action;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("action", action)
					.add("listener", listener)
					.add("provider", provider)
					.toString();
		}

		public String getActionId()
		{
			return action.getActionId();
		}

		public Action getAction()
		{
			return action;
		}

		public void setListener(final Action.Listener listener)
		{
			this.listener = listener;
		}

		public Action.Listener getListener()
		{
			return listener;
		}

		public void setProvider(final ActionsManagerProvider provider)
		{
			this.provider = provider;
		}

		public ActionsManagerProvider getProvider()
		{
			return provider;
		}

		public void setChildActionsManager(final ActionsManager childActionsManager)
		{
			this.childActionsManager = childActionsManager;
		}

		public ActionsManager getChildActionsManager()
		{
			return childActionsManager;
		}
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
		public ActionBuilder setIcon(final ImageResource icon)
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

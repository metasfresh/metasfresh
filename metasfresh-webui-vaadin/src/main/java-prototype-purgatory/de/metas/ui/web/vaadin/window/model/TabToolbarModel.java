package de.metas.ui.web.vaadin.window.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.common.base.Preconditions;

/*
 * #%L
 * de.metas.ui.web.vaadin
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

public class TabToolbarModel
{
	public static final String ACTION_New = "New";
	//
	public static final String ACTION_PreviousRecord = "PreviousRecord";
	public static final String ACTION_NextRecord = "NextRecord";

	private final Set<String> enabledActions = new HashSet<>();

	private final Map<String, ActionExecutor> actionExecutors = new HashMap<>();

	private final CompositeTabToolbarListener listeners = new CompositeTabToolbarListener();

	public TabToolbarModel()
	{
		super();
	}

	public void addTabToolbarListener(final TabToolbarListener listener)
	{
		listeners.addTabToolbarListener(listener);
	}

	public void setActionExecutor(final String action, final ActionExecutor actionExecutor)
	{
		actionExecutors.put(action, actionExecutor);
	}

	public void setActionEnabled(final String action, final boolean enabled)
	{
		Preconditions.checkNotNull(action, "action is null");
		if (enabled)
		{
			if (enabledActions.add(action))
			{
				listeners.onActionEnabledChanged(action, true);
			}
		}
		else
		{
			if (enabledActions.remove(action))
			{
				listeners.onActionEnabledChanged(action, false);
			}
		}
	}

	public boolean isActionEnabled(final String action)
	{
		return enabledActions.contains(action);
	}

	public void executeAction(final String action)
	{
		final ActionExecutor actionExecutor = actionExecutors.get(action);
		if (actionExecutor == null)
		{
			throw new IllegalStateException("No action executor found for action: " + action);
		}

		actionExecutor.executeAction(action);
	}

	public static interface TabToolbarListener
	{
		void onActionEnabledChanged(final String action, final boolean enabled);
	}

	private static final class CompositeTabToolbarListener implements TabToolbarListener
	{
		private final CopyOnWriteArrayList<TabToolbarListener> listeners = new CopyOnWriteArrayList<>();

		public CompositeTabToolbarListener()
		{
			super();
		}

		public void addTabToolbarListener(final TabToolbarListener listener)
		{
			Preconditions.checkNotNull(listener);
			listeners.addIfAbsent(listener);
		}

		@Override
		public void onActionEnabledChanged(final String action, final boolean enabled)
		{
			for (final TabToolbarListener listener : listeners)
			{
				listener.onActionEnabledChanged(action, enabled);
			}
		}
	}

	public static interface ActionExecutor
	{
		void executeAction(final String action);
	}
}

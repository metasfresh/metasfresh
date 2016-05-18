package de.metas.ui.web.vaadin.window.model.action;

import java.util.List;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.vaadin.server.Resource;

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

public abstract class Action
{
	public static Action of(final ActionGroup actionGroup, final String actionId, final String caption, final Resource icon)
	{
		return new ActionImpl(actionGroup, actionId, caption, icon);
	}

	public static Action selfHandledAction(final ActionGroup actionGroup, final String actionId, final String caption, final Resource icon, final Action.Listener listener)
	{
		return new SelfHandledAction(actionGroup, actionId, caption, icon, listener);
	}
	
	public static Action actionWithChildrenProvider(final ActionGroup actionGroup, final String actionId, final String caption, final Resource icon, final Action.Provider childrenProvider)
	{
		return new ActionWithChildrenProvider(actionGroup, actionId, caption, icon, childrenProvider);
	}

	private final String actionId;
	private final ActionGroup actionGroup;
	private final String caption;
	private final Resource icon;

	protected Action(final ActionGroup actionGroup, final String actionId, final String caption, final Resource icon)
	{
		super();
		this.actionId = Preconditions.checkNotNull(actionId, "actionId");
		this.actionGroup = actionGroup == null ? ActionGroup.NONE : actionGroup;
		this.caption = Preconditions.checkNotNull(caption, "caption");
		this.icon = icon;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("caption", caption)
				.add("actionGroup", actionGroup)
				.toString();
	}

	@Override
	public final int hashCode()
	{
		return Objects.hashCode(actionId);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof Action))
		{
			return false;
		}

		final Action other = (Action)obj;
		return Objects.equal(actionId, other.actionId);
	}

	public ActionGroup getActionGroup()
	{
		return actionGroup;
	}

	public String getCaption()
	{
		return caption;
	}

	public Resource getIcon()
	{
		return icon;
	}

	public static interface Listener
	{
		void handleAction(Object target);
	}
	
	public static interface Provider
	{
		List<Action> provideActions();
	}

	private static final class ActionImpl extends Action
	{
		protected ActionImpl(final ActionGroup actionGroup, final String actionId, final String caption, final Resource icon)
		{
			super(actionGroup, actionId, caption, icon);
		}
	}

	private static final class SelfHandledAction extends Action implements Action.Listener
	{
		private final Action.Listener listener;

		protected SelfHandledAction(final ActionGroup actionGroup, final String actionId, final String caption, final Resource icon, final Action.Listener listener)
		{
			super(actionGroup, actionId, caption, icon);
			this.listener = Preconditions.checkNotNull(listener, "listener not null");
		}

		@Override
		public void handleAction(final Object target)
		{
			listener.handleAction(target);
		}
	}
	
	private static final class ActionWithChildrenProvider extends Action implements Action.Provider
	{
		private final Action.Provider childrenProvider;

		protected ActionWithChildrenProvider(ActionGroup actionGroup, String actionId, String caption, Resource icon, final Action.Provider childrenProvider)
		{
			super(actionGroup, actionId, caption, icon);
			this.childrenProvider = childrenProvider;
		}

		@Override
		public List<Action> provideActions()
		{
			return childrenProvider.provideActions();
		}
		
	}
}

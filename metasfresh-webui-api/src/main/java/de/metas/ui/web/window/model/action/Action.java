package de.metas.ui.web.window.model.action;

import java.util.List;

import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import de.metas.ui.web.service.IImageProvider;
import de.metas.ui.web.service.IImageProvider.IImageResource;

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
	public static Builder builder()
	{
		return new Builder();
	}
	
	private final String actionId;
	private final ActionGroup actionGroup;
	private final String caption;
	private final IImageResource icon;
	private final boolean toolbarAction;

	protected Action(final Builder builder)
	{
		super();
		this.actionId = Preconditions.checkNotNull(builder.actionId, "actionId");
		this.actionGroup = builder.actionGroup == null ? ActionGroup.NONE : builder.actionGroup;
		this.caption = Preconditions.checkNotNull(builder.caption, "caption");
		this.icon = builder.icon;
		this.toolbarAction = builder.toolbarAction;
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

	public IImageResource getIcon()
	{
		return icon;
	}
	
	public boolean isToolbarAction()
	{
		return toolbarAction;
	}

	public static class ActionEvent
	{
		public static final ActionEvent of(final Action action, final Object target)
		{
			return new ActionEvent(action, target);
		}

		private final Action action;
		private final Object target;

		private ActionEvent(Action action, Object target)
		{
			super();
			this.action = action;
			this.target = target;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("action", action)
					.add("target", target)
					.toString();
		}

		public Action getAction()
		{
			return action;
		}

		public Object getTarget()
		{
			return target;
		}
	}

	public static interface Listener
	{
		void handleAction(ActionEvent event);
	}

	public static interface Provider
	{
		List<Action> provideActions();
	}
	
	public static class Builder
	{
		private ActionGroup actionGroup = ActionGroup.NONE;
		private String actionId;
		private String caption;
		private IImageResource icon;
		private Action.Listener listener;
		private Action.Provider childrenProvider;
		
		private boolean toolbarAction = false;
		
		private Builder()
		{
			super();
		}
		
		public Action build()
		{
			if (childrenProvider != null)
			{
				return new ActionWithChildrenProvider(this);
			}
			else if (listener != null)
			{
				return new SelfHandledAction(this);
			}
			else
			{
				return new ActionImpl(this);
			}
		}
		
		public Builder setActionGroup(ActionGroup actionGroup)
		{
			this.actionGroup = actionGroup;
			return this;
		}
		
		public Builder setActionIdAndUpdateFromAD_Messages(final String adMessage)
		{
			setActionId(adMessage);
			
			String caption = Services.get(IMsgBL.class).getMsg(Env.getCtx(), adMessage);
			caption = Util.cleanAmp(caption);
			setCaption(caption);

			final IImageResource icon = Services.get(IImageProvider.class).getIconSmall(actionId);
			setIcon(icon);

			return this;
		}
		
		public Builder setActionId(String actionId)
		{
			this.actionId = actionId;
			return this;
		}
		
		public Builder setCaption(String caption)
		{
			this.caption = caption;
			return this;
		}
		
		public Builder setIcon(IImageResource icon)
		{
			this.icon = icon;
			return this;
		}
		
		public Builder setListener(Action.Listener listener)
		{
			this.listener = listener;
			return this;
		}
		
		public Builder setChildrenProvider(Action.Provider childrenProvider)
		{
			this.childrenProvider = childrenProvider;
			return this;
		}
		
		public Builder setToolbarAction(boolean toolbarAction)
		{
			this.toolbarAction = toolbarAction;
			return this;
		}
		public Builder setToolbarAction()
		{
			setToolbarAction(true);
			return this;
		}
	}

	private static final class ActionImpl extends Action
	{
		protected ActionImpl(final Builder builder)
		{
			super(builder);
		}
	}

	private static final class SelfHandledAction extends Action implements Action.Listener
	{
		private final Action.Listener listener;

		protected SelfHandledAction(final Builder builder)
		{
			super(builder);
			this.listener = Preconditions.checkNotNull(builder.listener, "listener not null");
		}

		@Override
		public void handleAction(final ActionEvent event)
		{
			listener.handleAction(event);
		}
	}

	private static final class ActionWithChildrenProvider extends Action implements Action.Provider
	{
		private final Action.Provider childrenProvider;

		protected ActionWithChildrenProvider(final Builder builder)
		{
			super(builder);
			this.childrenProvider = builder.childrenProvider;
		}

		@Override
		public List<Action> provideActions()
		{
			return childrenProvider.provideActions();
		}

	}
}

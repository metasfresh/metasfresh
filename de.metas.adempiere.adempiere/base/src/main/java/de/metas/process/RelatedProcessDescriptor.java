package de.metas.process;

import java.util.HashSet;

import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

import de.metas.security.IUserRolePermissions;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * A small immutable object which contains the AD_Process_ID and it's attached flags (is quick action, is the default quick action etc).
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Immutable
@Value
public final class RelatedProcessDescriptor
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final AdProcessId processId;

	private final int tableId;
	private final AdWindowId windowId;
	private final AdTabId tabId;

	public static enum DisplayPlace
	{
		SingleDocumentActionsMenu, //
		IncludedTabTopActionsMenu, //
		ViewActionsMenu, //
		ViewQuickActions, //
	}

	private final ImmutableSet<DisplayPlace> displayPlaces;

	private final boolean webuiDefaultQuickAction;

	private final String webuiShortcut;

	private RelatedProcessDescriptor(final Builder builder)
	{
		processId = builder.processId;
		Preconditions.checkArgument(processId != null, "adProcessId not set");

		tableId = builder.tableId > 0 ? builder.tableId : 0;
		windowId = builder.windowId;
		tabId = builder.tabId;

		displayPlaces = builder.getDisplayPlaces();
		webuiDefaultQuickAction = builder.webuiDefaultQuickAction && displayPlaces.contains(DisplayPlace.ViewQuickActions);

		webuiShortcut = builder.webuiShortcut;
	}

	public boolean isDisplayedOn(@NonNull final DisplayPlace displayPlace)
	{
		return getDisplayPlaces().contains(displayPlace);
	}

	public boolean isExecutionGranted(final IUserRolePermissions permissions)
	{
		return permissions.checkProcessAccessRW(processId.getRepoId());
	}

	//
	//
	//

	public static final class Builder
	{
		private AdProcessId processId;
		private int tableId;
		private AdWindowId windowId;
		private AdTabId tabId;

		private final HashSet<DisplayPlace> displayPlaces = new HashSet<>();
		private final ImmutableSet<DisplayPlace> DEFAULT_displayPlaces = ImmutableSet.of(DisplayPlace.SingleDocumentActionsMenu);

		private boolean webuiDefaultQuickAction;

		private String webuiShortcut;

		private Builder()
		{
		}

		public RelatedProcessDescriptor build()
		{
			return new RelatedProcessDescriptor(this);
		}

		public Builder processId(final AdProcessId adProcessId)
		{
			processId = adProcessId;
			return this;
		}

		public Builder tableId(final int adTableId)
		{
			tableId = adTableId;
			return this;
		}

		public Builder anyTable()
		{
			return tableId(0);
		}

		public Builder windowId(final AdWindowId windowId)
		{
			this.windowId = windowId;
			return this;
		}

		public Builder anyWindow()
		{
			return windowId(null);
		}

		public Builder tabId(final AdTabId tabId)
		{
			this.tabId = tabId;
			return this;
		}

		public Builder displayPlace(@NonNull final DisplayPlace displayPlace)
		{
			this.displayPlaces.add(displayPlace);
			return this;
		}

		public Builder displayPlaceIfTrue(final boolean cond, @NonNull final DisplayPlace displayPlace)
		{
			if (cond)
			{
				displayPlace(displayPlace);
			}
			return this;
		}

		private ImmutableSet<DisplayPlace> getDisplayPlaces()
		{
			return !displayPlaces.isEmpty()
					? ImmutableSet.copyOf(displayPlaces)
					: DEFAULT_displayPlaces;
		}

		public Builder webuiDefaultQuickAction(final boolean webuiDefaultQuickAction)
		{
			this.webuiDefaultQuickAction = webuiDefaultQuickAction;
			return this;
		}

		public Builder webuiDefaultQuickAction()
		{
			return webuiDefaultQuickAction(true);
		}

		public Builder webuiShortcut(final String webuiShortcut)
		{
			this.webuiShortcut = webuiShortcut;
			return this;
		}
	}
}

package de.metas.process;

import com.google.common.collect.ImmutableSet;
import de.metas.security.IUserRolePermissions;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.AdTableId;

import javax.annotation.Nullable;

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
 */
@Value
public class RelatedProcessDescriptor
{
	@NonNull AdProcessId processId;

	@Nullable
	AdTableId tableId;
	@Nullable
	AdWindowId windowId;
	@Nullable
	AdTabId tabId;

	public enum DisplayPlace
	{
		SingleDocumentActionsMenu,
		IncludedTabTopActionsMenu,
		ViewActionsMenu,
		ViewQuickActions,
	}

	@NonNull
	ImmutableSet<DisplayPlace> displayPlaces;
	private static final ImmutableSet<DisplayPlace> DEFAULT_displayPlaces = ImmutableSet.of(DisplayPlace.SingleDocumentActionsMenu);

	boolean webuiDefaultQuickAction;

	@Nullable
	String webuiShortcut;

	int sortNo;

	@Builder(toBuilder = true)
	private RelatedProcessDescriptor(
			@NonNull final AdProcessId processId,
			@Nullable final AdTableId tableId,
			@Nullable final AdWindowId windowId,
			@Nullable final AdTabId tabId,
			@Singular final ImmutableSet<DisplayPlace> displayPlaces,
			final boolean webuiDefaultQuickAction,
			@Nullable final String webuiShortcut,
			final int sortNo)
	{
		this.processId = processId;
		this.tableId = tableId;
		this.windowId = windowId;
		this.tabId = tabId;

		this.displayPlaces = displayPlaces != null && !displayPlaces.isEmpty()
				? displayPlaces
				: DEFAULT_displayPlaces;

		this.webuiDefaultQuickAction = webuiDefaultQuickAction && this.displayPlaces.contains(DisplayPlace.ViewQuickActions);

		this.webuiShortcut = webuiShortcut;

		this.sortNo = Math.max(sortNo, 0);
	}

	public static class RelatedProcessDescriptorBuilder
	{
		public RelatedProcessDescriptorBuilder anyTable()
		{
			return tableId(null);
		}

		public RelatedProcessDescriptorBuilder anyWindow()
		{
			return windowId(null);
		}

		public RelatedProcessDescriptorBuilder displayPlaceIfTrue(final boolean cond, @NonNull final DisplayPlace displayPlace)
		{
			if (cond)
			{
				displayPlace(displayPlace);
			}
			return this;
		}
	}

	public boolean isDisplayedOn(@NonNull final DisplayPlace displayPlace)
	{
		return getDisplayPlaces().contains(displayPlace);
	}

	public boolean isExecutionGranted(final IUserRolePermissions permissions)
	{
		return permissions.checkProcessAccessRW(processId.getRepoId());
	}
}
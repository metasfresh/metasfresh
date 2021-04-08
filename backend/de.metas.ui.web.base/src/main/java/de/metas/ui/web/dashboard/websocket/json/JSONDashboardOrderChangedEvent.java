/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.dashboard.websocket.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import de.metas.ui.web.dashboard.DashboardWidgetType;
import de.metas.ui.web.dashboard.UserDashboardId;
import de.metas.ui.web.dashboard.UserDashboardItemId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
@EqualsAndHashCode(callSuper = true)
public class JSONDashboardOrderChangedEvent extends JSONDashboardChangedEvent
{
	public static JSONDashboardOrderChangedEvent of(
			@NonNull final UserDashboardId dashboardId,
			@NonNull final DashboardWidgetType widgetType,
			@Nullable final Collection<UserDashboardItemId> orderedItemIds)
	{
		return new JSONDashboardOrderChangedEvent(dashboardId, widgetType, orderedItemIds);
	}

	DashboardWidgetType widgetType;
	List<Integer> orderedItemIds;

	private JSONDashboardOrderChangedEvent(
			@NonNull final UserDashboardId dashboardId,
			@NonNull final DashboardWidgetType widgetType,
			@Nullable final Collection<UserDashboardItemId> orderedItemIds)
	{
		super(ChangeType.dashboardChanged, dashboardId.getRepoId());
		this.widgetType = widgetType;
		this.orderedItemIds = orderedItemIds != null
				? UserDashboardItemId.toRepoIdsList(orderedItemIds)
				: null;
	}
}

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
import de.metas.ui.web.dashboard.UserDashboardId;
import de.metas.ui.web.dashboard.UserDashboardItemId;
import de.metas.ui.web.dashboard.json.JsonKPIDataResult;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
@EqualsAndHashCode(callSuper = true)
public class JSONDashboardItemDataChangedEvent extends JSONDashboardChangedEvent
{
	public static JSONDashboardItemDataChangedEvent of(
			@NonNull final UserDashboardId dashboardId,
			@NonNull final UserDashboardItemId itemId,
			@NonNull final JsonKPIDataResult data)
	{
		return new JSONDashboardItemDataChangedEvent(dashboardId, itemId, data);
	}

	int itemId;
	JsonKPIDataResult data;

	private JSONDashboardItemDataChangedEvent(
			final @NonNull UserDashboardId dashboardId,
			final @NonNull UserDashboardItemId itemId,
			final @NonNull JsonKPIDataResult data)
	{
		super(ChangeType.itemDataChanged, dashboardId.getRepoId());
		this.itemId = itemId.getRepoId();
		this.data = data;
	}
}

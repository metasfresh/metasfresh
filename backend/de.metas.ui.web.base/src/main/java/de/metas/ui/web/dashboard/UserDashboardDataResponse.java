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

package de.metas.ui.web.dashboard;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Map;
import java.util.Optional;

@EqualsAndHashCode
@ToString
public class UserDashboardDataResponse
{
	private final ImmutableMap<UserDashboardItemId, UserDashboardItemDataResponse> itemsById;

	public static UserDashboardDataResponse ofMap(@NonNull final Map<UserDashboardItemId, UserDashboardItemDataResponse> itemsById)
	{
		return new UserDashboardDataResponse(itemsById);
	}

	private UserDashboardDataResponse(@NonNull final Map<UserDashboardItemId, UserDashboardItemDataResponse> itemsById)
	{
		this.itemsById = ImmutableMap.copyOf(itemsById);
	}

	public ImmutableCollection<UserDashboardItemDataResponse> getItems()
	{
		return itemsById.values();
	}

	public Optional<UserDashboardItemDataResponse> getItemById(@NonNull final UserDashboardItemId itemId)
	{
		final UserDashboardItemDataResponse itemData = itemsById.get(itemId);
		return Optional.ofNullable(itemData);
	}
}

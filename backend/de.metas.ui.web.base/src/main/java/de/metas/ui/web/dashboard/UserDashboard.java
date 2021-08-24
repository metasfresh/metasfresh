package de.metas.ui.web.dashboard;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;

import javax.annotation.concurrent.Immutable;
import java.util.Collection;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Immutable
@ToString
public final class UserDashboard
{
	@Getter
	@NonNull private final UserDashboardId id;
	@NonNull private final ClientId adClientId;
	private final ImmutableMap<UserDashboardItemId, UserDashboardItem> _targetIndicatorItemsById;
	private final ImmutableMap<UserDashboardItemId, UserDashboardItem> _kpiItemsById;

	@Builder
	private UserDashboard(
			@NonNull final UserDashboardId id,
			@NonNull final ClientId adClientId,
			@NonNull @Singular final ImmutableList<UserDashboardItem> items)
	{
		this.id = id;
		this.adClientId = adClientId;
		this._targetIndicatorItemsById = items.stream()
				.filter(item -> item.getWidgetType() == DashboardWidgetType.TargetIndicator)
				.collect(ImmutableMap.toImmutableMap(UserDashboardItem::getId, item -> item));
		this._kpiItemsById = items.stream()
				.filter(item -> item.getWidgetType() == DashboardWidgetType.KPI)
				.collect(ImmutableMap.toImmutableMap(UserDashboardItem::getId, item -> item));
	}

	private ImmutableMap<UserDashboardItemId, UserDashboardItem> getItemsById(final DashboardWidgetType widgetType)
	{
		if (widgetType == DashboardWidgetType.TargetIndicator)
		{
			return _targetIndicatorItemsById;
		}
		else if (widgetType == DashboardWidgetType.KPI)
		{
			return _kpiItemsById;
		}
		else
		{
			throw new AdempiereException("Unknown widget type: " + widgetType);
		}
	}

	public ImmutableSet<UserDashboardItemId> getItemIds(final DashboardWidgetType dashboardWidgetType)
	{
		return getItemsById(dashboardWidgetType).keySet();
	}

	public Collection<UserDashboardItem> getItems(final DashboardWidgetType dashboardWidgetType)
	{
		return getItemsById(dashboardWidgetType).values();
	}

	public UserDashboardItem getItemById(
			@NonNull final DashboardWidgetType dashboardWidgetType,
			@NonNull final UserDashboardItemId itemId)
	{
		final UserDashboardItem item = getItemsById(dashboardWidgetType).get(itemId);
		if (item == null)
		{
			throw new EntityNotFoundException("No " + dashboardWidgetType + " item found")
					.setParameter("itemId", itemId);
		}
		return item;
	}

	public void assertItemIdExists(
			@NonNull final DashboardWidgetType dashboardWidgetType,
			@NonNull final UserDashboardItemId itemId)
	{
		getItemById(dashboardWidgetType, itemId); // will fail if itemId does not exist
	}
}

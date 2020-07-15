package de.metas.ui.web.dashboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.concurrent.Immutable;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.websocket.WebsocketTopicName;
import de.metas.ui.web.websocket.WebsocketTopicNames;
import de.metas.util.Check;

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
public final class UserDashboard
{
	public static Builder builder()
	{
		return new Builder();
	}

	public static final UserDashboard EMPTY = new UserDashboard();

	private final int id;
	private final int adClientId;
	private final Map<Integer, UserDashboardItem> _targetIndicatorItemsById;
	private final Map<Integer, UserDashboardItem> _kpiItemsById;

	private final WebsocketTopicName websocketEndpoint;

	private UserDashboard(final Builder builder)
	{
		id = builder.id;
		adClientId = builder.adClientId;
		_targetIndicatorItemsById = Maps.uniqueIndex(builder.targetIndicatorItems, UserDashboardItem::getId);
		_kpiItemsById = Maps.uniqueIndex(builder.kpiItems, UserDashboardItem::getId);

		websocketEndpoint = WebsocketTopicName.ofString(WebsocketTopicNames.TOPIC_Dashboard + "/" + id);
	}

	private UserDashboard()
	{
		id = -1;
		adClientId = -1;
		_targetIndicatorItemsById = ImmutableMap.of();
		_kpiItemsById = ImmutableMap.of();
		websocketEndpoint = null;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("id", id)
				.add("targetIndicatorItems", _targetIndicatorItemsById.isEmpty() ? null : _targetIndicatorItemsById)
				.add("kpiItemsById", _kpiItemsById.isEmpty() ? null : _kpiItemsById)
				.toString();
	}

	public int getId()
	{
		return id;
	}

	public int getAdClientId()
	{
		return adClientId;
	}

	private Map<Integer, UserDashboardItem> getItemsById(final DashboardWidgetType widgetType)
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

	public Set<Integer> getItemIds(final DashboardWidgetType dashboardWidgetType)
	{
		return getItemsById(dashboardWidgetType).keySet();
	}

	public Collection<UserDashboardItem> getItems(final DashboardWidgetType dashboardWidgetType)
	{
		return getItemsById(dashboardWidgetType).values();
	}

	public UserDashboardItem getItemById(final DashboardWidgetType dashboardWidgetType, final int itemId)
	{
		final UserDashboardItem item = getItemsById(dashboardWidgetType).get(itemId);
		if (item == null)
		{
			throw new EntityNotFoundException("No " + dashboardWidgetType + " item found")
					.setParameter("itemId", itemId);
		}
		return item;
	}

	public void assertItemIdExists(final DashboardWidgetType dashboardWidgetType, final int itemId)
	{
		getItemById(dashboardWidgetType, itemId); // will fail if itemId does not exist
	}

	public WebsocketTopicName getWebsocketEndpoint()
	{
		return websocketEndpoint;
	}

	//
	//
	//
	//
	//
	public static final class Builder
	{
		private Integer id;
		private Integer adClientId;
		private final List<UserDashboardItem> targetIndicatorItems = new ArrayList<>();
		private final List<UserDashboardItem> kpiItems = new ArrayList<>();

		private Builder()
		{
			super();
		}

		public UserDashboard build()
		{
			Check.assumeNotNull(id, "Parameter id is not null");
			return new UserDashboard(this);
		}

		public Builder setId(final int id)
		{
			this.id = id;
			return this;
		}

		public Builder setAdClientId(Integer adClientId)
		{
			this.adClientId = adClientId;
			return this;
		}

		public Builder addItem(final UserDashboardItem item)
		{
			Check.assumeNotNull(item, "Parameter item is not null");

			switch (item.getWidgetType())
			{
				case TargetIndicator:
					targetIndicatorItems.add(item);
					break;
				case KPI:
					kpiItems.add(item);
					break;
			}

			return this;
		}
	}
}

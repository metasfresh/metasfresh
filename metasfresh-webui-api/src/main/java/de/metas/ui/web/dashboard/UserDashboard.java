package de.metas.ui.web.dashboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.concurrent.Immutable;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Immutable
public final class UserDashboard
{
	public static final Builder builder()
	{
		return new Builder();
	}

	public static final UserDashboard EMPTY = new UserDashboard();

	private final int id;
	private final List<UserDashboardItem> targetIndicatorItems;
	private final Map<Integer, UserDashboardItem> kpiItemsById;

	private UserDashboard(final Builder builder)
	{
		super();
		id = builder.id;
		targetIndicatorItems = ImmutableList.copyOf(builder.targetIndicatorItems);
		kpiItemsById = Maps.uniqueIndex(builder.kpiItems, UserDashboardItem::getId);
	}

	private UserDashboard()
	{
		super();
		id = -1;
		targetIndicatorItems = ImmutableList.of();
		kpiItemsById = ImmutableMap.of();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("id", id)
				.add("targetIndicatorItems", targetIndicatorItems.isEmpty() ? null : targetIndicatorItems)
				.add("kpiItemsById", kpiItemsById.isEmpty() ? null : kpiItemsById)
				.toString();
	}

	public int getId()
	{
		return id;
	}
	
	public List<UserDashboardItem> getTargetIndicatorItems()
	{
		return targetIndicatorItems;
	}
	
	public UserDashboardItem getTargetIndicatorItemById(final int itemId)
	{
		final UserDashboardItem item = targetIndicatorItems.get(itemId);
		if(item == null)
		{
			throw new IllegalArgumentException("No target indicator item found for "+itemId);
		}
		return item;
	}


	public Collection<UserDashboardItem> getKPIItems()
	{
		return kpiItemsById.values();
	}

	public UserDashboardItem getKPIItemById(final int itemId)
	{
		final UserDashboardItem item = kpiItemsById.get(itemId);
		if(item == null)
		{
			throw new IllegalArgumentException("No KPI item found for "+itemId);
		}
		return item;
	}

	public static final class Builder
	{
		private Integer id;
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

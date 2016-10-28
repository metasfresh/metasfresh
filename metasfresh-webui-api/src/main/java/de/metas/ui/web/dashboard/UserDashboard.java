package de.metas.ui.web.dashboard;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.concurrent.Immutable;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

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
	private final List<UserDashboardItem> items;

	private UserDashboard(final Builder builder)
	{
		super();
		id = builder.id;
		items = ImmutableList.copyOf(builder.items);
	}

	private UserDashboard()
	{
		super();
		id = -1;
		items = ImmutableList.of();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("items", items)
				.toString();
	}

	public int getId()
	{
		return id;
	}

	public List<UserDashboardItem> getItems()
	{
		return items;
	}

	public static final class Builder
	{
		private Integer id;
		private final List<UserDashboardItem> items = new ArrayList<>();

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
			items.add(item);
			return this;
		}
	}
}

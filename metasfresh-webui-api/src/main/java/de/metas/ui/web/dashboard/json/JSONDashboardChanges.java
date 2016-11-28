package de.metas.ui.web.dashboard.json;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

@SuppressWarnings("serial")
public class JSONDashboardChanges implements Serializable
{
	@JsonProperty("dashboardItemIdsOrder")
	private final List<Integer> dashboardItemIdsOrder;

	@JsonCreator
	JSONDashboardChanges( //
			@JsonProperty("dashboardItemIdsOrder") final List<Integer> dashboardItemIdsOrder //
	)
	{
		super();
		this.dashboardItemIdsOrder = dashboardItemIdsOrder == null ? ImmutableList.of() : dashboardItemIdsOrder;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("dashboardItemIdsOrder", dashboardItemIdsOrder)
				.toString();
	}

	public List<Integer> getDashboardItemIdsOrder()
	{
		return dashboardItemIdsOrder;
	}
}

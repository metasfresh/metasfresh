package de.metas.ui.web.dashboard.json;

import java.io.Serializable;
import java.util.List;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.ui.web.dashboard.UserDashboard;
import de.metas.ui.web.window.datatypes.json.JSONFilteringOptions;

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
public class JSONDashboard implements Serializable
{
	public static final JSONDashboard of(final UserDashboard dashboard, final JSONFilteringOptions jsonOpts)
	{
		return new JSONDashboard(dashboard, jsonOpts);
	}

	@JsonProperty("items")
	private final List<JSONDashboardItem> items;

	private JSONDashboard(final UserDashboard dashboard, final JSONFilteringOptions jsonOpts)
	{
		super();

		items = dashboard.getItems()
				.stream()
				.map(item -> JSONDashboardItem.of(item, jsonOpts))
				.collect(GuavaCollectors.toImmutableList());
	}

	public List<JSONDashboardItem> getItems()
	{
		return items;
	}
}

package de.metas.ui.web.dashboard.json;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import de.metas.logging.LogManager;
import de.metas.ui.web.dashboard.UserDashboardItem;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutOptions;
import de.metas.util.GuavaCollectors;
import lombok.Value;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class JSONDashboard
{
	public static final JSONDashboard of(final Collection<UserDashboardItem> items, final String websocketEndpoint, final JSONDocumentLayoutOptions jsonOpts)
	{
		return new JSONDashboard(items, websocketEndpoint, jsonOpts);
	}

	private static final Logger logger = LogManager.getLogger(JSONDashboard.class);

	private final List<JSONDashboardItem> items;
	private final String websocketEndpoint;

	private JSONDashboard(final Collection<UserDashboardItem> items, final String websocketEndpoint, final JSONDocumentLayoutOptions jsonOpts)
	{
		super();

		this.items = items.stream()
				.map(item -> {
					try
					{
						return JSONDashboardItem.of(item, jsonOpts);
					}
					catch (final Exception ex)
					{
						logger.warn("Failed converting {} to JSON. Skipped", item, ex);
						return null;
					}
				})
				.filter(item -> item != null)
				.collect(GuavaCollectors.toImmutableList());
		
		this.websocketEndpoint = websocketEndpoint;
	}
}

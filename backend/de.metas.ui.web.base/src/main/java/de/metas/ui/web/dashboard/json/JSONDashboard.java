package de.metas.ui.web.dashboard.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import de.metas.ui.web.dashboard.UserDashboardItem;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutOptions;
import lombok.Value;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
	public static JSONDashboard of(
			final Collection<UserDashboardItem> items,
			@Nullable final String websocketEndpoint,
			final JSONDocumentLayoutOptions jsonOpts)
	{
		return new JSONDashboard(items, websocketEndpoint, jsonOpts);
	}

	private static final Logger logger = LogManager.getLogger(JSONDashboard.class);

	List<JSONDashboardItem> items;
	@Nullable String websocketEndpoint;

	private JSONDashboard(
			final Collection<UserDashboardItem> items,
			@Nullable final String websocketEndpoint,
			final JSONDocumentLayoutOptions jsonOpts)
	{
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
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
		
		this.websocketEndpoint = websocketEndpoint;
	}
}

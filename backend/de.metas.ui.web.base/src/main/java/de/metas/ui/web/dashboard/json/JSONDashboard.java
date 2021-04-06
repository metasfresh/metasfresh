package de.metas.ui.web.dashboard.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;
import java.util.List;

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
	public static final JSONDashboard EMPTY = new JSONDashboard();

	List<JSONDashboardItem> items;
	@Nullable String websocketEndpoint;

	@With
	@JsonInclude(JsonInclude.Include.NON_EMPTY) String noDashboardReason;

	@Builder
	private JSONDashboard(
			@NonNull final List<JSONDashboardItem> items,
			@Nullable final String websocketEndpoint,
			@Nullable final String noDashboardReason)
	{
		this.items = ImmutableList.copyOf(items);
		this.websocketEndpoint = websocketEndpoint;
		this.noDashboardReason = noDashboardReason;
	}

	private JSONDashboard()
	{
		this.items = ImmutableList.of();
		this.websocketEndpoint = null;
		this.noDashboardReason = null;
	}
}

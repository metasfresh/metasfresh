/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.dashboard.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Value;

@Builder
@Value
@JsonDeserialize(builder = JsonUserDashboardItemAddRequest.JsonUserDashboardItemAddRequestBuilder.class)
public class JsonUserDashboardItemAddRequest
{
	int kpiId;
	@Default
	int position = -1;

	//
	// Optional params
	String caption;
	JSONInterval interval;
	JSONWhen when;

	@AllArgsConstructor
	@Getter
	public static enum JSONInterval
	{
		week("P-7D");

		private final String esTimeRange;
	}

	@AllArgsConstructor
	@Getter
	public enum JSONWhen
	{
		now(null), lastWeek("P-7D");

		private final String esTimeRangeEnd;
	}

	@JsonPOJOBuilder(withPrefix = "")
	public static class JsonUserDashboardItemAddRequestBuilder
	{
	}

}

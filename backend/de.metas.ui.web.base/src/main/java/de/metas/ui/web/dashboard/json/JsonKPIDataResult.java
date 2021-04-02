/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import de.metas.ui.web.dashboard.KPIDataResult;
import de.metas.ui.web.dashboard.TimeRange;
import de.metas.ui.web.dashboard.UserDashboardItemId;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
@Builder
public class JsonKPIDataResult
{
	@JsonProperty("took") String took;

	@JsonProperty("itemId")
	@JsonInclude(JsonInclude.Include.NON_NULL) Integer itemId;

	@JsonProperty("range")
	@JsonInclude(JsonInclude.Include.NON_NULL) TimeRange range;

	@JsonProperty("datasets") List<JsonKPIDataSet> datasets;

	public static JsonKPIDataResult of(
			@NonNull final KPIDataResult result,
			@Nullable final UserDashboardItemId itemId,
			@NonNull final KPIJsonOptions jsonOpts)
	{
		return JsonKPIDataResult.builder()
				.took(result.getTook())
				.itemId(itemId != null ? itemId.getRepoId() : null)
				.range(result.getRange())
				.datasets(result.getDatasets()
						.stream()
						.map(dataSet -> JsonKPIDataSet.of(dataSet, jsonOpts))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

}

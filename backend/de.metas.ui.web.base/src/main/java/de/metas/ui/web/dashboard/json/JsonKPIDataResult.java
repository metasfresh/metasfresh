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
import com.google.common.collect.ImmutableList;
import de.metas.ui.web.dashboard.KPIDataResult;
import de.metas.ui.web.dashboard.TimeRange;
import de.metas.ui.web.dashboard.UserDashboardItemDataResponse;
import de.metas.ui.web.exceptions.WebuiError;
import de.metas.ui.web.exceptions.json.JsonWebuiError;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
@Builder
public class JsonKPIDataResult
{
	@With
	@JsonInclude(JsonInclude.Include.NON_NULL) Integer itemId;

	@JsonInclude(JsonInclude.Include.NON_NULL) TimeRange range;
	@JsonInclude(JsonInclude.Include.NON_NULL) List<JsonKPIDataSet> datasets;
	@JsonInclude(JsonInclude.Include.NON_EMPTY) String took;
	@JsonInclude(JsonInclude.Include.NON_NULL) JsonWebuiError error;

	public static JsonKPIDataResult of(
			@NonNull final UserDashboardItemDataResponse itemData,
			@NonNull final KPIJsonOptions jsonOpts)
	{
		if (itemData.getKpiData() != null)
		{
			final KPIDataResult kpiData = itemData.getKpiData();
			return of(kpiData, jsonOpts)
					.withItemId(itemData.getItemId().getRepoId());
		}
		else
		{
			final WebuiError error = itemData.getError();
			assert error != null;
			return builder()
					.itemId(itemData.getItemId().getRepoId())
					.error(JsonWebuiError.of(error, jsonOpts))
					.build();
		}
	}

	public static JsonKPIDataResult of(
			@NonNull final KPIDataResult kpiData,
			@NonNull final KPIJsonOptions jsonOpts)
	{
		return builder()
				.range(kpiData.getRange())
				.datasets(kpiData.getDatasets()
						.stream()
						.map(dataSet -> JsonKPIDataSet.of(dataSet, jsonOpts))
						.collect(ImmutableList.toImmutableList()))
				.took(kpiData.getTook())
				.build();

	}
}

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
import de.metas.ui.web.kpi.data.KPIDataSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
@Builder
public class JsonKPIDataSet
{
	@JsonProperty("name")
	@JsonInclude(JsonInclude.Include.NON_NULL) String name;

	@JsonProperty("unit")
	@JsonInclude(JsonInclude.Include.NON_NULL) String unit;

	@JsonProperty("values") List<JsonKPIDataSetValue> values;

	public static JsonKPIDataSet of(@NonNull final KPIDataSet dataSet, @NonNull final KPIJsonOptions jsonOpts)
	{
		return JsonKPIDataSet.builder()
				.name(dataSet.getName())
				.unit(dataSet.getUnit())
				.values(dataSet.getValues()
						.stream()
						.map(dataSetValue -> JsonKPIDataSetValue.of(dataSetValue, jsonOpts))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}
}

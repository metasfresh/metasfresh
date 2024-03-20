package de.metas.ui.web.dashboard.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableSet;
import de.metas.ui.web.dashboard.DashboardWidgetType;
import de.metas.ui.web.kpi.data.KPIDataResult;
import de.metas.ui.web.kpi.descriptor.KPI;
import de.metas.ui.web.kpi.descriptor.KPIChartType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
@Builder
@Value
public class JsonKPI
{
	int kpiId;
	@NonNull String caption;
	@NonNull KPIChartType chartType;
	@NonNull ImmutableSet<DashboardWidgetType> widgetTypes;

	// layout
	@JsonInclude(JsonInclude.Include.NON_NULL) JsonKPIFieldLayout groupByField;
	@NonNull List<JsonKPIFieldLayout> fields;

	@Nullable JsonKPIDataResult sampleData;

	public static JsonKPI of(
			@NonNull final KPI kpi,
			@Nullable final KPIDataResult sampleData,
			@NonNull final KPIJsonOptions jsonOpts)
	{
		return JsonKPI.builder()
				.kpiId(kpi.getId().getRepoId())
				.caption(kpi.getCaption(jsonOpts.getAdLanguage()))
				.chartType(kpi.getChartType())
				.widgetTypes(ImmutableSet.copyOf(kpi.getSupportedWidgetTypes()))
				.groupByField(JsonKPILayout.extractGroupByField(kpi, jsonOpts))
				.fields(JsonKPILayout.extractFields(kpi, jsonOpts))
				.sampleData(sampleData != null ? JsonKPIDataResult.of(sampleData, jsonOpts) : null)
				.build();
	}
}

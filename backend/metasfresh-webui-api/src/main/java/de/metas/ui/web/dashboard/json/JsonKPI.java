package de.metas.ui.web.dashboard.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.dashboard.DashboardWidgetType;
import de.metas.ui.web.dashboard.KPI;
import de.metas.ui.web.dashboard.KPIChartType;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import lombok.Builder;
import lombok.Value;

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
	public static final JsonKPI of(final KPI kpi, final JSONOptions jsonOpts)
	{
		return JsonKPI.builder()
				.kpiId(kpi.getId())
				.caption(kpi.getCaption(jsonOpts.getAdLanguage()))
				.chartType(kpi.getChartType())
				.widgetTypes(ImmutableSet.copyOf(kpi.getSupportedWidgetTypes()))
				.build();
	}

	private final int kpiId;
	private final String caption;
	private final KPIChartType chartType;
	private final ImmutableSet<DashboardWidgetType> widgetTypes;
}

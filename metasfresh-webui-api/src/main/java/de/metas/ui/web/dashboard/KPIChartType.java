package de.metas.ui.web.dashboard;

import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.base.model.X_WEBUI_KPI;

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

public enum KPIChartType
{
	AreaChart(X_WEBUI_KPI.CHARTTYPE_AreaChart) //
	, BarChart(X_WEBUI_KPI.CHARTTYPE_BarChart) //
	, PieChart(X_WEBUI_KPI.CHARTTYPE_PieChart) //
	, Metric(X_WEBUI_KPI.CHARTTYPE_Metric) //
	;
	private String code;

	KPIChartType(final String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}
	
	public String toJson()
	{
		return name();
	}

	public static KPIChartType forCode(final String code)
	{
		final KPIChartType type = code2type.get(code);
		if (type == null)
		{
			throw new IllegalArgumentException("No type found for: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, KPIChartType> code2type = ImmutableMap.<String, KPIChartType> builder()
			.put(AreaChart.getCode(), AreaChart)
			.put(BarChart.getCode(), BarChart)
			.put(PieChart.getCode(), PieChart)
			.put(Metric.getCode(), Metric)
			.build();

}

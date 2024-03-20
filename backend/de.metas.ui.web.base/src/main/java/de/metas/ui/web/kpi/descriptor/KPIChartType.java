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

package de.metas.ui.web.kpi.descriptor;

import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.ui.web.base.model.X_WEBUI_KPI;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum KPIChartType implements ReferenceListAwareEnum
{
	AreaChart(X_WEBUI_KPI.CHARTTYPE_AreaChart),
	BarChart(X_WEBUI_KPI.CHARTTYPE_BarChart),
	PieChart(X_WEBUI_KPI.CHARTTYPE_PieChart),
	Metric(X_WEBUI_KPI.CHARTTYPE_Metric),
	URLs(X_WEBUI_KPI.CHARTTYPE_URLs),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<KPIChartType> index = ReferenceListAwareEnums.index(values());

	@NonNull @Getter private final String code;

	@JsonValue
	public String toJson()
	{
		return name();
	}

	public static KPIChartType ofCode(@NonNull final String code) {return index.ofCode(code);}
}

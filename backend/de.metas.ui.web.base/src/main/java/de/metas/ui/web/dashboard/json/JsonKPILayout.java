package de.metas.ui.web.dashboard.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import de.metas.ui.web.kpi.descriptor.KPI;
import de.metas.ui.web.kpi.descriptor.KPIField;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JsonKPILayout
{
	public static JsonKPILayout of(final KPI kpi, final KPIJsonOptions jsonOpts)
	{
		return new JsonKPILayout(kpi, jsonOpts);
	}

	// private final int id; // don't exported because is useless and confusing

	// @JsonProperty("caption")
	// private final String caption; // don't exported because is useless and confusing; frontend shall display the dashboard item caption anyways

	@JsonProperty("description")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String description;

	@JsonProperty("chartType")
	private final String chartType;

	@JsonProperty("groupByField")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JsonKPIFieldLayout groupByField;

	@JsonProperty("fields")
	private final List<JsonKPIFieldLayout> fields;

	@JsonProperty("zoomToDetailsAvailable")
	private final boolean zoomToDetailsAvailable;

	public JsonKPILayout(final KPI kpi, final KPIJsonOptions jsonOpts)
	{
		// id = kpi.getId();
		// caption = kpi.getCaption(jsonOpts.getAdLanguage());
		description = Strings.emptyToNull(kpi.getDescription(jsonOpts.getAdLanguage()));
		chartType = kpi.getChartType().toJson();
		groupByField = extractGroupByField(kpi, jsonOpts);
		fields = extractFields(kpi, jsonOpts);
		zoomToDetailsAvailable = kpi.isZoomToDetailsAvailable();
	}

	@Nullable
	static JsonKPIFieldLayout extractGroupByField(final KPI kpi, final KPIJsonOptions jsonOpts)
	{
		final KPIField groupByField = kpi.getGroupByFieldOrNull();
		return groupByField != null ? JsonKPIFieldLayout.field(groupByField, jsonOpts) : null;
	}

	static ImmutableList<JsonKPIFieldLayout> extractFields(final KPI kpi, final KPIJsonOptions jsonOpts)
	{
		final ImmutableList.Builder<JsonKPIFieldLayout> jsonFields = ImmutableList.builder();
		final boolean hasCompareOffset = kpi.hasCompareOffset();
		for (final KPIField kpiField : kpi.getFields())
		{
			// Don't add the group by field to our fields list
			if (kpiField.isGroupBy())
			{
				continue;
			}

			jsonFields.add(JsonKPIFieldLayout.field(kpiField, jsonOpts));

			if (hasCompareOffset && !kpiField.isGroupBy())
			{
				jsonFields.add(JsonKPIFieldLayout.offsetField(kpiField, jsonOpts));
			}
		}
		return jsonFields.build();
	}

}

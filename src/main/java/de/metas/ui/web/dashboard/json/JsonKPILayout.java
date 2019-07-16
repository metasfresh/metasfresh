package de.metas.ui.web.dashboard.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.dashboard.KPI;
import de.metas.ui.web.dashboard.KPIField;
import de.metas.ui.web.window.datatypes.json.JSONOptions;

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
	public static final JsonKPILayout of(final KPI kpi, final JSONOptions jsonOpts)
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

	@JsonProperty("pollIntervalSec")
	private final int pollIntervalSec;

	@JsonProperty("groupByField")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JsonKPIFieldLayout groupByField;

	@JsonProperty("fields")
	private final List<JsonKPIFieldLayout> fields;

	public JsonKPILayout(final KPI kpi, final JSONOptions jsonOpts)
	{
		final String adLanguage = jsonOpts.getAdLanguage();

		// id = kpi.getId();
		// caption = kpi.getCaption(adLanguage);
		description = Strings.emptyToNull(kpi.getDescription(adLanguage));
		chartType = kpi.getChartType().toJson();

		pollIntervalSec = kpi.getPollIntervalSec();

		//
		// Group by field
		final KPIField groupByField = kpi.getGroupByFieldOrNull();
		this.groupByField = groupByField == null ? null : JsonKPIFieldLayout.field(groupByField, jsonOpts);

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
		fields = jsonFields.build();
	}
}

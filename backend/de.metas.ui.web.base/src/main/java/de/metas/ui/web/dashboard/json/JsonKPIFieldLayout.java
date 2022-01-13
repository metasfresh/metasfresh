package de.metas.ui.web.dashboard.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.ui.web.kpi.descriptor.KPIField;

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
public class JsonKPIFieldLayout
{
	public static JsonKPIFieldLayout field(final KPIField kpiField, final KPIJsonOptions jsonOpts)
	{
		final boolean isOffsetField = false;
		return new JsonKPIFieldLayout(kpiField, isOffsetField, jsonOpts);
	}

	public static JsonKPIFieldLayout offsetField(final KPIField kpiField, final KPIJsonOptions jsonOpts)
	{
		final boolean isOffsetField = true;
		return new JsonKPIFieldLayout(kpiField, isOffsetField, jsonOpts);
	}

	@JsonProperty("caption")
	private final String caption;

	@JsonProperty("description")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String description;

	@JsonProperty("unit")
	private final String unit;

	@JsonProperty("fieldName")
	private final String fieldName;

	// NOTE: not needed because we are providing a separate groupByField in JsonKPILayout
	// @JsonProperty("groupBy")
	// private final boolean groupBy;

	@JsonProperty("dataType")
	private final String dataType;

	@JsonProperty("color")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String color;

	public JsonKPIFieldLayout(final KPIField kpiField, final boolean isOffsetField, final KPIJsonOptions jsonOpts)
	{
		final String adLanguage = jsonOpts.getAdLanguage();

		// Caption
		if (isOffsetField)
		{
			caption = kpiField.getOffsetCaption(adLanguage);
		}
		else
		{
			caption = kpiField.getCaption(adLanguage);
		}

		// FieldName
		if (isOffsetField)
		{
			fieldName = kpiField.getOffsetFieldName();
		}
		else
		{
			fieldName = kpiField.getFieldName();
		}

		description = kpiField.getDescription(adLanguage);
		unit = kpiField.getUnit(adLanguage).orElse(null);

		// groupBy = kpiField.isGroupBy();
		dataType = kpiField.getValueType().toJson();

		color = kpiField.getColor();
	}

}

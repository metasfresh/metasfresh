package de.metas.ui.web.dashboard.json;

import java.util.List;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import de.metas.ui.web.dashboard.KPI;
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
@SuppressWarnings("unused")
public class JsonKPILayout
{
	public static final JsonKPILayout of(final KPI kpi, final JSONOptions jsonOpts)
	{
		return new JsonKPILayout(kpi, jsonOpts);
	}

	// private final int id; // don't exported because is useless and confusing
	private final String caption;
	private final String description;
	private final String chartType;
	private final List<JsonKPIFieldLayout> fields;

	public JsonKPILayout(final KPI kpi, final JSONOptions jsonOpts)
	{
		final String adLanguage = jsonOpts.getAD_Language();
		
		// id = kpi.getId();
		caption = kpi.getCaption(adLanguage);
		description = kpi.getDescription(adLanguage);
		chartType = kpi.getChartType().toJson();

		fields = kpi.getFields()
				.stream()
				.map(kpiField -> JsonKPIFieldLayout.of(kpiField, jsonOpts))
				.collect(GuavaCollectors.toImmutableList());
	}
}

package de.metas.ui.web.dashboard.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.ui.web.dashboard.KPI;
import de.metas.ui.web.dashboard.UserDashboardItem;
import de.metas.ui.web.window.datatypes.json.JSONOptions;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

@SuppressWarnings("serial")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JSONDashboardItem implements Serializable
{
	/* package */static final JSONDashboardItem of(final UserDashboardItem dashboardItem, final JSONOptions jsonOpts)
	{
		return new JSONDashboardItem(dashboardItem, jsonOpts);
	}

	@JsonProperty("id")
	private final int id;
	@JsonProperty("caption")
	private final String caption;
	@JsonProperty("seqNo")
	private final int seqNo;

	@JsonProperty("url")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String url;

	@JsonProperty("kpi")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JsonKPILayout kpi;

	private JSONDashboardItem(final UserDashboardItem dashboardItem, final JSONOptions jsonOpts)
	{
		super();
		id = dashboardItem.getId();
		caption = dashboardItem.getCaption(jsonOpts.getAD_Language());
		url = dashboardItem.getUrl();
		seqNo = dashboardItem.getSeqNo();

		final KPI kpi = dashboardItem.getKPI();
		this.kpi = kpi == null ? null : JsonKPILayout.of(kpi, jsonOpts);
	}

	public int getId()
	{
		return id;
	}

	public String getCaption()
	{
		return caption;
	}

	public String getUrl()
	{
		return url;
	}

	public int getSeqNo()
	{
		return seqNo;
	}

	public JsonKPILayout getKPI()
	{
		return kpi;
	}
}

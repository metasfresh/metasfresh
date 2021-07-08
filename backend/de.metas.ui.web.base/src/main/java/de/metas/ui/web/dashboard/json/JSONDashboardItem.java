package de.metas.ui.web.dashboard.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.ui.web.dashboard.KPI;
import de.metas.ui.web.dashboard.UserDashboardItem;
import de.metas.ui.web.dashboard.UserDashboardItemDataResponse;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class JSONDashboardItem
{
	public static JSONDashboardItem of(
			@NonNull final UserDashboardItem item,
			@Nullable final UserDashboardItemDataResponse itemData,
			@NonNull final KPIJsonOptions jsonOpts)
	{
		return new JSONDashboardItem(item, itemData, jsonOpts);
	}

	int id;
	String caption;
	int seqNo;
	@JsonInclude(JsonInclude.Include.NON_EMPTY) String url;
	@JsonInclude(JsonInclude.Include.NON_NULL) JsonKPILayout kpi;
	@JsonInclude(JsonInclude.Include.NON_NULL) JsonKPIDataResult data;

	private JSONDashboardItem(
			@NonNull final UserDashboardItem item,
			@Nullable final UserDashboardItemDataResponse itemData,
			@NonNull final KPIJsonOptions jsonOpts)
	{
		this.id = item.getId().getRepoId();
		this.caption = extractCaption(item, item.getKPI(), jsonOpts);
		this.seqNo = item.getSeqNo();
		this.url = item.getUrl();
		this.kpi = JsonKPILayout.of(item.getKPI(), jsonOpts);
		this.data = itemData != null ? JsonKPIDataResult.of(itemData, jsonOpts) : null;
	}

	private static String extractCaption(final @NonNull UserDashboardItem item, @NonNull final KPI kpi, final @NonNull KPIJsonOptions jsonOpts)
	{
		String caption = item.getCaption(jsonOpts.getAdLanguage());
		if (jsonOpts.isDebugShowColumnNamesForCaption())
		{
			caption = caption + " (" + item.getId() + ", kpiId=" + kpi.getId() + ")";
		}

		return caption;
	}
}

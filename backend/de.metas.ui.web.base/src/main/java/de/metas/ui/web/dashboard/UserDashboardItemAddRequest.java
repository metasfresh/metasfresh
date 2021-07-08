package de.metas.ui.web.dashboard;

import de.metas.ui.web.dashboard.json.JsonUserDashboardItemAddRequest;
import lombok.Builder;
import lombok.Builder.Default;
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

@Builder
@Value
public class UserDashboardItemAddRequest
{
	public static final UserDashboardItemAddRequest of(final JsonUserDashboardItemAddRequest json, final DashboardWidgetType widgetType, final String adLanguage)
	{
		final UserDashboardItemChangeRequest changeRequest = UserDashboardItemChangeRequest.builder()
				.itemId(null) // new
				.widgetType(widgetType)
				.adLanguage(adLanguage)
				.caption(json.getCaption())
				.interval(json.getInterval())
				.when(json.getWhen())
				.build();

		return UserDashboardItemAddRequest.builder()
				.widgetType(widgetType)
				.kpiId(json.getKpiId())
				.position(json.getPosition())
				.changeRequest(!changeRequest.isEmpty() ? changeRequest : null)
				.build();
	}
	
	DashboardWidgetType widgetType;

	int kpiId;
	@Default int position = -1;

	UserDashboardItemChangeRequest changeRequest;
}

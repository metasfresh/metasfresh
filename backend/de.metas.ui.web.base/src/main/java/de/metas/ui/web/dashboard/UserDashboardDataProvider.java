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

package de.metas.ui.web.dashboard;

import lombok.Builder;
import lombok.NonNull;

public class UserDashboardDataProvider
{
	private final UserDashboardRepository userDashboardRepository;
	private final KPIDataProvider kpiDataProvider;

	private final UserDashboardId dashboardId;

	@Builder
	private UserDashboardDataProvider(
			@NonNull final UserDashboardRepository userDashboardRepository,
			@NonNull final KPIDataProvider kpiDataProvider,
			@NonNull final UserDashboardId dashboardId)
	{
		this.userDashboardRepository = userDashboardRepository;
		this.kpiDataProvider = kpiDataProvider;

		this.dashboardId = dashboardId;
	}

	public KPIDataResult getItemData(@NonNull final UserDashboardItemDataRequest request)
	{
		final UserDashboardItem dashboardItem = getDashboardItem(request.getWidgetType(), request.getItemId());
		final KPIId kpiId = dashboardItem.getKPIId();
		final TimeRange timeRange = dashboardItem.getTimeRangeDefaults()
				.createTimeRange(request.getFrom(), request.getTo());

		return kpiDataProvider.getKPIData(KPIDataRequest.builder()
				.kpiId(kpiId)
				.timeRange(timeRange)
				.build());
	}

	private UserDashboardItem getDashboardItem(
			@NonNull final DashboardWidgetType widgetType,
			@NonNull final UserDashboardItemId itemId)
	{
		return getDashboard().getItemById(widgetType, itemId);
	}

	private UserDashboard getDashboard()
	{
		return userDashboardRepository.getUserDashboardById(dashboardId);
	}
}

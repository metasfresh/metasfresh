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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.ui.web.exceptions.WebuiError;
import de.metas.ui.web.kpi.KPITimeRangeDefaults;
import de.metas.ui.web.kpi.data.KPIDataContext;
import de.metas.ui.web.kpi.data.KPIDataProvider;
import de.metas.ui.web.kpi.data.KPIDataRequest;
import de.metas.ui.web.kpi.data.KPIDataResult;
import de.metas.ui.web.kpi.data.KPIZoomIntoDetailsInfo;
import de.metas.ui.web.kpi.descriptor.KPIId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserDashboardDataProvider
{
	private final Logger logger = LogManager.getLogger(UserDashboardDataProvider.class);

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

	public UserDashboardDataResponse getAllItems(@NonNull final UserDashboardDataRequest request)
	{
		final KPIDataContext context = request.getContext();
		final List<DashboardWidgetType> widgetTypes = request.getWidgetType() != null
				? ImmutableList.of(request.getWidgetType())
				: Arrays.asList(DashboardWidgetType.values());

		final ImmutableMap.Builder<UserDashboardItemId, UserDashboardItemDataResponse> itemDataById = ImmutableMap.builder();
		final UserDashboard dashboard = getDashboard();
		for (final DashboardWidgetType widgetType : widgetTypes)
		{
			for (final UserDashboardItem dashboardItem : dashboard.getItems(widgetType))
			{
				final UserDashboardItemDataResponse itemData = getItemData(dashboardItem, context);
				itemDataById.put(dashboardItem.getId(), itemData);
			}
		}

		return UserDashboardDataResponse.ofMap(itemDataById.build());
	}

	public UserDashboardItemDataResponse getItemData(@NonNull final UserDashboardItemDataRequest request)
	{
		final UserDashboard dashboard = getDashboard();
		final UserDashboardItem dashboardItem = dashboard.getItemById(request.getWidgetType(), request.getItemId());
		final KPIDataContext context = request.getContext();
		return getItemData(dashboardItem, context);
	}

	private UserDashboardItemDataResponse getItemData(
			@NonNull final UserDashboardItem item,
			@NonNull final KPIDataContext context)
	{
		KPIDataRequest request = null;
		try
		{
			request = toKPIDataRequest(item, context);
			final KPIDataResult kpiData = kpiDataProvider.getKPIData(request);
			return UserDashboardItemDataResponse.ok(dashboardId, item.getId(), kpiData);
		}
		catch (@NonNull final Exception ex)
		{
			logger.warn("Failed computing KPI data for request={}, item={}, context={}.", request, item, context, ex);

			final ITranslatableString errorMessage = AdempiereException.isUserValidationError(ex)
					? AdempiereException.extractMessageTrl(ex)
					: TranslatableStrings.adMessage(KPIDataProvider.MSG_FailedLoadingKPI);

			return UserDashboardItemDataResponse.error(dashboardId, item.getId(), WebuiError.of(ex, errorMessage));
		}
	}

	private static KPIDataRequest toKPIDataRequest(
			final @NonNull UserDashboardItem item,
			final @NonNull KPIDataContext context)
	{
		final KPIId kpiId = item.getKPIId();
		final KPITimeRangeDefaults timeRangeDefaults = item.getTimeRangeDefaults();
		return KPIDataRequest.builder()
				.kpiId(kpiId)
				.timeRangeDefaults(timeRangeDefaults)
				.context(context)
				.build();
	}

	private UserDashboard getDashboard()
	{
		return userDashboardRepository.getUserDashboardById(dashboardId);
	}

	public Optional<KPIZoomIntoDetailsInfo> getZoomIntoDetailsInfo(@NonNull final UserDashboardItemDataRequest request)
	{
		final UserDashboard dashboard = getDashboard();
		final UserDashboardItem dashboardItem = dashboard.getItemById(request.getWidgetType(), request.getItemId());
		final KPIDataContext context = request.getContext();

		return kpiDataProvider.getZoomIntoDetailsInfo(toKPIDataRequest(dashboardItem, context));
	}
}

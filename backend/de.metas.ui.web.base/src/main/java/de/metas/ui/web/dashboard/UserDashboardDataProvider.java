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
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.ui.web.exceptions.WebuiError;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Trace;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class UserDashboardDataProvider
{
	private final Logger logger = LogManager.getLogger(UserDashboardDataProvider.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private static final AdMessageKey MSG_FailedLoadingKPI = AdMessageKey.of("webui.dashboard.KPILoadError");

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
		final Instant from = request.getFrom();
		final Instant to = request.getTo();
		final Duration maxStaleAccepted = request.getMaxStaleAccepted();
		final List<DashboardWidgetType> widgetTypes = request.getWidgetType() != null
				? ImmutableList.of(request.getWidgetType())
				: Arrays.asList(DashboardWidgetType.values());

		final ImmutableMap.Builder<UserDashboardItemId, UserDashboardItemDataResponse> itemDataById = ImmutableMap.builder();
		final UserDashboard dashboard = getDashboard();
		for (final DashboardWidgetType widgetType : widgetTypes)
		{
			for (final UserDashboardItem dashboardItem : dashboard.getItems(widgetType))
			{
				final UserDashboardItemDataResponse itemData = getItemData(dashboardItem, from, to, maxStaleAccepted);
				itemDataById.put(dashboardItem.getId(), itemData);
			}
		}

		return UserDashboardDataResponse.ofMap(itemDataById.build());
	}

	public UserDashboardItemDataResponse getItemData(@NonNull final UserDashboardItemDataRequest request)
	{
		final UserDashboard dashboard = getDashboard();
		final Instant from = request.getFrom();
		final Instant to = request.getTo();
		final Duration maxStaleAccepted = request.getMaxStaleAccepted();
		final UserDashboardItem dashboardItem = dashboard.getItemById(request.getWidgetType(), request.getItemId());
		return getItemData(dashboardItem, from, to, maxStaleAccepted);
	}

	private UserDashboardItemDataResponse getItemData(
			@NonNull final UserDashboardItem item,
			@Nullable final Instant from,
			@Nullable final Instant to,
			@NonNull final Duration maxStaleAccepted)
	{
		final KPIId kpiId = item.getKPIId();
		final KPITimeRangeDefaults timeRangeDefaults = item.getTimeRangeDefaults();

		final KPIDataRequest request = KPIDataRequest.builder()
				.kpiId(kpiId)
				.timeRangeDefaults(timeRangeDefaults)
				.from(from)
				.to(to)
				.maxStaleAccepted(maxStaleAccepted)
				.build();

		try
		{
			final KPIDataResult kpiData = kpiDataProvider.getKPIData(request);
			return UserDashboardItemDataResponse.ok(item.getId(), kpiData);
		}
		catch (@NonNull final Exception ex)
		{
			logger.warn("Failed computing KPI data for {}.", request, ex);
			final ITranslatableString errorMessage = AdempiereException.isUserValidationError(ex)
					? AdempiereException.extractMessageTrl(ex)
					: msgBL.getTranslatableMsgText(MSG_FailedLoadingKPI);

 			return UserDashboardItemDataResponse.error(item.getId(), WebuiError.of(ex, errorMessage));
		}
	}

	private UserDashboard getDashboard()
	{
		return userDashboardRepository.getUserDashboardById(dashboardId);
	}
}

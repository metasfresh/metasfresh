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

import de.metas.cache.CCache;
import de.metas.elasticsearch.IESSystem;
import de.metas.ui.web.dashboard.UserDashboardRepository.UserDashboardKey;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public class UserDashboardDataService
{
	private final UserDashboardRepository userDashboardRepository;
	private final KPIDataProvider kpiDataProvider;

	private final CCache<UserDashboardId, UserDashboardDataProvider> providers = CCache.<UserDashboardId, UserDashboardDataProvider>builder()
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.build();

	public UserDashboardDataService(
			@NonNull final KPIRepository kpiRepository,
			@NonNull final UserDashboardRepository userDashboardRepository)
	{
		this.userDashboardRepository = userDashboardRepository;

		final IESSystem esSystem = Services.get(IESSystem.class);
		this.kpiDataProvider = KPIDataProvider.builder()
				.esSystem(esSystem)
				.kpiRepository(kpiRepository)
				.build();
	}

	public Optional<UserDashboardDataProvider> getData(@NonNull final UserDashboardKey userDashboardKey)
	{
		return userDashboardRepository.getUserDashboardId(userDashboardKey)
				.map(this::getData);
	}

	public UserDashboardDataProvider getData(@NonNull final UserDashboardId dashboardId)
	{
		return providers.getOrLoad(dashboardId, this::createDashboardDataProvider);
	}

	private UserDashboardDataProvider createDashboardDataProvider(@NonNull final UserDashboardId dashboardId)
	{
		return UserDashboardDataProvider.builder()
				.userDashboardRepository(userDashboardRepository)
				.kpiDataProvider(kpiDataProvider)
				.dashboardId(dashboardId)
				.build();
	}

	public KPIDataResult getKPIData(@NonNull final KPIId kpiId)
	{
		return kpiDataProvider.getKPIData(KPIDataRequest.builder()
				.kpiId(kpiId)
				.timeRangeDefaults(KPITimeRangeDefaults.DEFAULT)
				.maxStaleAccepted(Duration.ofDays(100))
				.build());
	}

}

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
import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.elasticsearch.IESSystem;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.ui.web.dashboard.UserDashboardRepository.UserDashboardKey;
import de.metas.ui.web.kpi.KPITimeRangeDefaults;
import de.metas.ui.web.kpi.data.KPIDataContext;
import de.metas.ui.web.kpi.data.KPIDataProvider;
import de.metas.ui.web.kpi.data.KPIDataRequest;
import de.metas.ui.web.kpi.data.KPIDataResult;
import de.metas.ui.web.kpi.data.KPIPermissionsProvider;
import de.metas.ui.web.kpi.descriptor.KPIId;
import de.metas.ui.web.kpi.descriptor.KPIRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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

		this.kpiDataProvider = KPIDataProvider.builder()
				.kpiRepository(kpiRepository)
				.esSystem(Services.get(IESSystem.class))
				.sysConfigBL(Services.get(ISysConfigBL.class))
				.kpiPermissionsProvider(new KPIPermissionsProvider(Services.get(IUserRolePermissionsDAO.class)))
				.build();
	}

	@PostConstruct
	void postConstruct()
	{
		CacheMgt.get().addCacheResetListener(this::onCacheResetRequest);
	}

	private long onCacheResetRequest(final CacheInvalidateMultiRequest multiRequest)
	{
		if (multiRequest.isResetAll())
		{
			kpiDataProvider.cacheReset();
		}

		return 1;
	}

	public Optional<UserDashboardId> getUserDashboardId(@NonNull final UserDashboardKey userDashboardKey)
	{
		return userDashboardRepository.getUserDashboardId(userDashboardKey);
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

	public KPIDataResult getKPIData(@NonNull final KPIId kpiId, @NonNull final KPIDataContext kpiDataContext)
	{
		return kpiDataProvider.getKPIData(KPIDataRequest.builder()
				.kpiId(kpiId)
				.timeRangeDefaults(KPITimeRangeDefaults.DEFAULT)
				.context(kpiDataContext)
				.maxStaleAccepted(Duration.ofDays(100))
				.build());
	}

}

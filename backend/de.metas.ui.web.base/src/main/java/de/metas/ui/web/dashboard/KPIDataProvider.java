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

import de.metas.elasticsearch.IESSystem;
import lombok.Builder;
import lombok.NonNull;

public class KPIDataProvider
{
	private final IESSystem esSystem;
	private final KPIRepository kpiRepository;

	@Builder
	private KPIDataProvider(
			@NonNull final IESSystem esSystem,
			@NonNull final KPIRepository kpiRepository)
	{
		this.esSystem = esSystem;
		this.kpiRepository = kpiRepository;
	}

	public KPIDataResult getKPIData(@NonNull final KPIDataRequest request)
	{
		final KPI kpi = kpiRepository.getKPI(request.getKpiId());

		return KPIDataLoader.newInstance(esSystem.elasticsearchClient(), kpi)
				.setTimeRange(request.getTimeRange())
				.retrieveData();
	}
}

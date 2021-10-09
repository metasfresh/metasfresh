/*
 * #%L
 * de.metas.util.web
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

package de.metas.util.web.audit;

import com.google.common.collect.ImmutableList;
import de.metas.audit.apirequest.request.ApiRequestAudit;
import de.metas.audit.apirequest.request.ApiRequestAuditRepository;
import lombok.NonNull;
import org.adempiere.ad.housekeeping.spi.IStartupHouseKeepingTask;
import org.springframework.stereotype.Component;

@Component
public class ApiAuditHouseKeepingTask implements IStartupHouseKeepingTask
{
	private final ApiRequestAuditRepository apiRequestAuditRepository;
	private final ApiRequestReplayService apiRequestReplayService;

	public ApiAuditHouseKeepingTask(
			@NonNull final ApiRequestAuditRepository apiRequestAuditRepository,
			@NonNull final ApiRequestReplayService apiRequestReplayService)
	{
		this.apiRequestAuditRepository = apiRequestAuditRepository;
		this.apiRequestReplayService = apiRequestReplayService;
	}

	@Override
	public void executeTask()
	{
		final ImmutableList<ApiRequestAudit> timeWiseSortedApiRequests = apiRequestAuditRepository.getAllFailingTimeWiseSorted();

		apiRequestReplayService.replayApiRequests(timeWiseSortedApiRequests);
	}
}

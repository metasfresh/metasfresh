/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.audit.apirequest;

import de.metas.audit.apirequest.config.ApiAuditConfig;
import de.metas.audit.apirequest.config.ApiAuditConfigId;
import de.metas.audit.apirequest.config.ApiAuditConfigRepository;
import de.metas.audit.apirequest.request.ApiRequestAudit;
import de.metas.audit.apirequest.request.ApiRequestAuditRepository;
import de.metas.audit.apirequest.request.log.ApiAuditRequestLogDAO;
import de.metas.audit.apirequest.response.ApiResponseAudit;
import de.metas.audit.apirequest.response.ApiResponseAuditRepository;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class ApiAuditCleanUpService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final ApiRequestAuditRepository apiRequestAuditRepository;
	private final ApiResponseAuditRepository apiResponseAuditRepository;
	private final ApiAuditRequestLogDAO apiAuditRequestLogDAO;
	private final ApiAuditConfigRepository apiAuditConfigRepository;

	public ApiAuditCleanUpService(
			@NonNull final ApiRequestAuditRepository apiRequestAuditRepository,
			@NonNull final ApiResponseAuditRepository apiResponseAuditRepository,
			@NonNull final ApiAuditRequestLogDAO apiAuditRequestLogDAO,
			@NonNull final ApiAuditConfigRepository apiAuditConfigRepository)
	{
		this.apiRequestAuditRepository = apiRequestAuditRepository;
		this.apiResponseAuditRepository = apiResponseAuditRepository;
		this.apiAuditRequestLogDAO = apiAuditRequestLogDAO;
		this.apiAuditConfigRepository = apiAuditConfigRepository;
	}

	public void deleteProcessedRequests()
	{
		final Stream<ApiRequestAudit> processedApiRequests = apiRequestAuditRepository.getAllProcessedRequests();

		if (Check.isEmpty(processedApiRequests))
		{
			return;
		}

		final ApiAuditConfigShortTimeIndex apiAuditConfigShortTimeIndex = new ApiAuditConfigShortTimeIndex(apiAuditConfigRepository);

		processedApiRequests
				.filter(request -> isReadyForCleanup(request, apiAuditConfigShortTimeIndex))
				.forEach(this::deleteProcessedRequestInTrx);
	}

	private void deleteProcessedRequestInTrx(@NonNull final ApiRequestAudit apiRequestAudit)
	{
		trxManager.runInNewTrx(() -> deleteProcessedRequest(apiRequestAudit));
	}

	private void deleteProcessedRequest(@NonNull final ApiRequestAudit apiRequestAudit)
	{
		apiAuditRequestLogDAO.deleteLogsByApiRequestId(apiRequestAudit.getIdNotNull());

		apiResponseAuditRepository.getByRequestId(apiRequestAudit.getIdNotNull())
				.stream()
				.map(ApiResponseAudit::getIdNotNull)
				.forEach(apiResponseAuditRepository::delete);

		apiRequestAuditRepository.deleteRequestAudit(apiRequestAudit.getIdNotNull());
	}

	private boolean isReadyForCleanup(
			@NonNull final ApiRequestAudit apiRequestAudit,
			@NonNull final ApiAuditConfigShortTimeIndex apiAuditConfigIndex)
	{
		final ApiAuditConfig apiAuditConfig = apiAuditConfigIndex.getConfig(apiRequestAudit.getApiAuditConfigId());

		final long daysSinceLastUpdate = (Instant.now().getEpochSecond() - apiRequestAudit.getTime().getEpochSecond()) / (60 * 60 * 24);

		return daysSinceLastUpdate > apiAuditConfig.getKeepRequestDays();
	}

	@Value
	private static class ApiAuditConfigShortTimeIndex
	{
		Map<ApiAuditConfigId, ApiAuditConfig> configId2Config = new HashMap<>();

		ApiAuditConfigRepository apiAuditConfigRepository;

		public ApiAuditConfigShortTimeIndex(final ApiAuditConfigRepository apiAuditConfigRepository)
		{
			this.apiAuditConfigRepository = apiAuditConfigRepository;
		}

		@NonNull
		public ApiAuditConfig getConfig(@NonNull final ApiAuditConfigId apiAuditConfigId)
		{
			return configId2Config.computeIfAbsent(apiAuditConfigId, apiAuditConfigRepository::getConfigById);
		}
	}
}

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
import de.metas.audit.apirequest.ApiAuditLoggable;
import de.metas.audit.apirequest.request.ApiRequestAudit;
import de.metas.audit.apirequest.request.Status;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.adempiere.util.lang.IAutoCloseable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ApiRequestReplayService
{
	private final ApiAuditService apiAuditService;

	public ApiRequestReplayService(final ApiAuditService apiAuditService)
	{
		this.apiAuditService = apiAuditService;
	}

	public void replayApiRequests(@NonNull final ImmutableList<ApiRequestAudit> apiRequestAuditTimeSortedList)
	{
		apiRequestAuditTimeSortedList.forEach(this::replayActionNoFailing);
	}

	private void replayActionNoFailing(@NonNull final ApiRequestAudit apiRequestAudit)
	{
		final ApiAuditLoggable loggable = apiAuditService.createLogger(apiRequestAudit.getIdNotNull(), apiRequestAudit.getUserId());

		try (final IAutoCloseable loggableRestorer = Loggables.temporarySetLoggable(loggable))
		{
			Loggables.addLog("Replaying request!");

			replayAction(apiRequestAudit);
		}
		catch (final Exception e)
		{
			loggable.addLog("Error while trying to replay the request:" + e.getMessage(), e);
		}
		finally
		{
			loggable.flush();
		}
	}

	private void replayAction(@NonNull final ApiRequestAudit apiRequestAudit)
	{
		try
		{
			final ApiResponse apiResponse = apiAuditService.executeHttpCall(apiRequestAudit);

			final Status requestStatus = apiResponse.getStatusCode() / 100 > HttpStatus.OK.series().value()
					? Status.ERROR
					: Status.PROCESSED;

			apiAuditService.updateRequestStatus(requestStatus, apiRequestAudit);

			apiAuditService.logResponse(apiResponse, apiRequestAudit.getIdNotNull(), apiRequestAudit.getOrgId());
		}
		catch (final Exception e)
		{
			apiAuditService.updateRequestStatus(Status.ERROR, apiRequestAudit);
			throw e;
		}
	}
}

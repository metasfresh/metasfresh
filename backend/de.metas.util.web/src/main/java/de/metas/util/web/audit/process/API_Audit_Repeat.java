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

package de.metas.util.web.audit.process;

import com.google.common.collect.ImmutableList;
import de.metas.audit.apirequest.request.ApiRequestAudit;
import de.metas.audit.apirequest.request.ApiRequestAuditRepository;
import de.metas.audit.apirequest.request.Status;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.web.audit.ApiRequestReplayService;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_API_Request_Audit;

import java.util.Comparator;

public class API_Audit_Repeat extends JavaProcess implements IProcessPrecondition
{
	private final static String ONLY_WITH_ERROR = "IsOnlyWithError";

	private final ApiRequestAuditRepository apiRequestAuditRepository = SpringContextHolder.instance.getBean(ApiRequestAuditRepository.class);
	private final ApiRequestReplayService apiRequestReplayService = SpringContextHolder.instance.getBean(ApiRequestReplayService.class);

	@Param(parameterName = ONLY_WITH_ERROR)
	private boolean isOnlyWithError;

	@Override
	protected String doIt() throws Exception
	{
		final ImmutableList<ApiRequestAudit> timeSortedApiRequests = retrieveSelectedRecordsQueryBuilder(I_API_Request_Audit.class)
				.create()
				.stream()
				.map(apiRequestAuditRepository::recordToRequestAudit)
				.filter(request -> !isOnlyWithError || Status.ERROR.equals(request.getStatus()))
				.sorted(Comparator.comparing(ApiRequestAudit::getTime))
				.collect(ImmutableList.toImmutableList());

		apiRequestReplayService.replayApiRequests(timeSortedApiRequests);

		return MSG_OK;
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.getSelectionSize().isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}
}

/*
 * #%L
 * de.metas.util.web
 * %%
 * Copyright (C) 2022 metas GmbH
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

import de.metas.audit.apirequest.request.ApiRequestAuditRepository;
import de.metas.audit.apirequest.request.Status;
import de.metas.audit.request.ApiRequestIterator;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import de.metas.util.web.audit.ApiRequestReplayService;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_API_Request_Audit;

import java.util.Iterator;

public class API_Audit_Repeat extends JavaProcess implements IProcessPrecondition
{
	private final ApiRequestReplayService apiRequestReplayService = SpringContextHolder.instance.getBean(ApiRequestReplayService.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final static String ONLY_WITH_ERROR = "IsOnlyWithError";
	@Param(parameterName = ONLY_WITH_ERROR)
	private boolean isOnlyWithError;

	@Override
	protected String doIt() throws Exception
	{
		final ApiRequestIterator apiRequestIterator = getSelectedRequests();

		apiRequestReplayService.replayApiRequests(apiRequestIterator);

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

	@NonNull
	private ApiRequestIterator getSelectedRequests()
	{
		final IQueryBuilder<I_API_Request_Audit> selectedApiRequestsQueryBuilder = retrieveActiveSelectedRecordsQueryBuilder(I_API_Request_Audit.class);

		if (isOnlyWithError)
		{
			selectedApiRequestsQueryBuilder.addEqualsFilter(I_API_Request_Audit.COLUMNNAME_Status, Status.ERROR.getCode());
		}

		final IQueryOrderBy orderBy = queryBL.createQueryOrderByBuilder(I_API_Request_Audit.class)
				.addColumnAscending(I_API_Request_Audit.COLUMNNAME_Time)
				.createQueryOrderBy();

		final Iterator<I_API_Request_Audit> timeSortedApiRequests = selectedApiRequestsQueryBuilder.create()
				.setOrderBy(orderBy)
				.iterate(I_API_Request_Audit.class);

		return ApiRequestIterator.of(timeSortedApiRequests, ApiRequestAuditRepository::recordToRequestAudit);
	}
}

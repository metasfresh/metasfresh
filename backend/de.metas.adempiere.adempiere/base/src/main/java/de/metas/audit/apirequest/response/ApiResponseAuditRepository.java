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

package de.metas.audit.apirequest.response;

import com.google.common.collect.ImmutableList;
import de.metas.audit.apirequest.request.ApiRequestAuditId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_API_Response_Audit;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ApiResponseAuditRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public ApiResponseAudit save(@NonNull final ApiResponseAudit apiResponseAudit)
	{
		final I_API_Response_Audit record = InterfaceWrapperHelper.loadOrNew(apiResponseAudit.getApiResponseAuditId(), I_API_Response_Audit.class);

		record.setAD_Org_ID(apiResponseAudit.getOrgId().getRepoId());
		record.setAPI_Request_Audit_ID(apiResponseAudit.getApiRequestAuditId().getRepoId());
		record.setBody(apiResponseAudit.getBody());
		record.setHttpCode(apiResponseAudit.getHttpCode());
		record.setTime(TimeUtil.asTimestamp(apiResponseAudit.getTime()));
		record.setHttpHeaders(apiResponseAudit.getHttpHeaders());

		saveRecord(record);

		return recordToResponseAudit(record);
	}

	@NonNull
	public ApiResponseAudit getById(@NonNull final ApiResponseAuditId apiResponseAuditId)
	{
		final I_API_Response_Audit record = InterfaceWrapperHelper.load(apiResponseAuditId, I_API_Response_Audit.class);

		return recordToResponseAudit(record);
	}

	@NonNull
	public List<ApiResponseAudit> getByRequestId(@NonNull final ApiRequestAuditId apiRequestAuditId)
	{
		return queryBL.createQueryBuilder(I_API_Response_Audit.class)
				.addEqualsFilter(I_API_Response_Audit.COLUMNNAME_API_Request_Audit_ID, apiRequestAuditId.getRepoId())
				.create()
				.stream()
				.map(this::recordToResponseAudit)
				.collect(ImmutableList.toImmutableList());
	}

	public void delete(@NonNull final ApiResponseAuditId apiResponseAuditId)
	{
		final I_API_Response_Audit record = InterfaceWrapperHelper.load(apiResponseAuditId, I_API_Response_Audit.class);

		InterfaceWrapperHelper.deleteRecord(record);
	}

	@NonNull
	private ApiResponseAudit recordToResponseAudit(@NonNull final I_API_Response_Audit record)
	{
		return ApiResponseAudit.builder()
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.apiRequestAuditId(ApiRequestAuditId.ofRepoId(record.getAPI_Request_Audit_ID()))
				.apiResponseAuditId(ApiResponseAuditId.ofRepoId(record.getAPI_Response_Audit_ID()))
				.body(record.getBody())
				.httpCode(record.getHttpCode())
				.time(TimeUtil.asInstant(record.getTime()))
				.build();
	}
}

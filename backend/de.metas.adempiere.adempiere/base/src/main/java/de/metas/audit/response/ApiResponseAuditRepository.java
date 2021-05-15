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

package de.metas.audit.response;

import de.metas.audit.request.ApiRequestAuditId;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_API_Response_Audit;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ApiResponseAuditRepository
{
	@NonNull
	public ApiResponseAudit save(@NonNull final ApiResponseAudit apiResponseAudit)
	{
		final I_API_Response_Audit record = InterfaceWrapperHelper.loadOrNew(apiResponseAudit.getApiResponseAuditId(), I_API_Response_Audit.class);

		record.setAD_Org_ID(apiResponseAudit.getOrgId().getRepoId());
		record.setAPI_Request_Audit_ID(apiResponseAudit.getApiRequestAuditId().getRepoId());
		record.setBody(apiResponseAudit.getBody());
		record.setHttpCode(apiResponseAudit.getHttpCode());
		record.setTime(TimeUtil.asTimestamp(apiResponseAudit.getTime()));

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

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

package de.metas.audit.apirequest.request;

import com.google.common.collect.ImmutableList;
import de.metas.audit.apirequest.HttpMethod;
import de.metas.audit.apirequest.config.ApiAuditConfigId;
import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_API_Request_Audit;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ApiRequestAuditRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public ApiRequestAudit save(@NonNull final ApiRequestAudit apiRequestAudit)
	{
		final I_API_Request_Audit record = InterfaceWrapperHelper.loadOrNew(apiRequestAudit.getApiRequestAuditId(), I_API_Request_Audit.class);

		record.setAD_Org_ID(apiRequestAudit.getOrgId().getRepoId());
		record.setAD_Role_ID(apiRequestAudit.getRoleId().getRepoId());
		record.setAD_User_ID(apiRequestAudit.getUserId().getRepoId());
		record.setAPI_Audit_Config_ID(apiRequestAudit.getApiAuditConfigId().getRepoId());
		record.setStatus(apiRequestAudit.getStatus().getCode());
		record.setIsErrorAcknowledged(apiRequestAudit.isErrorAcknowledged());
		record.setBody(apiRequestAudit.getBody());
		record.setMethod(apiRequestAudit.getMethod() != null ? apiRequestAudit.getMethod().getCode() : null);
		record.setPath(apiRequestAudit.getPath());
		record.setRemoteAddr(apiRequestAudit.getRemoteAddress());
		record.setRemoteHost(apiRequestAudit.getRemoteHost());
		record.setTime(TimeUtil.asTimestamp(apiRequestAudit.getTime()));
		record.setHttpHeaders(apiRequestAudit.getHttpHeaders());
		record.setRequestURI(apiRequestAudit.getRequestURI());

		saveRecord(record);

		return recordToRequestAudit(record);
	}

	@NonNull
	public ApiRequestAudit getById(@NonNull final ApiRequestAuditId apiRequestAuditId)
	{
		final I_API_Request_Audit record = InterfaceWrapperHelper.load(apiRequestAuditId, I_API_Request_Audit.class);

		return recordToRequestAudit(record);
	}

	@NonNull
	public ImmutableList<ApiRequestAudit> getAllFailingTimeWiseSorted()
	{
		final IQueryOrderBy orderBy = queryBL.createQueryOrderByBuilder(I_API_Request_Audit.class)
				.addColumnAscending(I_API_Request_Audit.COLUMNNAME_Time)
				.createQueryOrderBy();

		return queryBL.createQueryBuilder(I_API_Request_Audit.class)
				.addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_API_Request_Audit.COLUMNNAME_IsErrorAcknowledged, true)
				.addEqualsFilter(I_API_Request_Audit.COLUMNNAME_Status, Status.ERROR)
				.create()
				.setOrderBy(orderBy)
				.stream()
				.map(this::recordToRequestAudit)
				.collect(ImmutableList.toImmutableList());
	}

	public Stream<ApiRequestAudit> getAllProcessedRequests()
	{
		final ICompositeQueryFilter<I_API_Request_Audit> acknowledgedErrors = queryBL.createCompositeQueryFilter(I_API_Request_Audit.class)
				.addEqualsFilter(I_API_Request_Audit.COLUMNNAME_Status, Status.ERROR)
				.addEqualsFilter(I_API_Request_Audit.COLUMNNAME_IsErrorAcknowledged, true);

		final ICompositeQueryFilter<I_API_Request_Audit> processedOrAcknowledgedErrors = queryBL.createCompositeQueryFilter(I_API_Request_Audit.class)
				.setJoinOr()
				.addEqualsFilter(I_API_Request_Audit.COLUMNNAME_Status, Status.PROCESSED)
				.addFilter(acknowledgedErrors);

		return queryBL.createQueryBuilder(I_API_Request_Audit.class)
				.filter(processedOrAcknowledgedErrors)
				.create()
				.iterateAndStream()
				.map(this::recordToRequestAudit);
	}


	@NonNull
	public ApiRequestAudit recordToRequestAudit(@NonNull final I_API_Request_Audit record)
	{
		return ApiRequestAudit.builder()
				.apiRequestAuditId(ApiRequestAuditId.ofRepoId(record.getAPI_Request_Audit_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.roleId(RoleId.ofRepoId(record.getAD_Role_ID()))
				.userId(UserId.ofRepoId(record.getAD_User_ID()))
				.apiAuditConfigId(ApiAuditConfigId.ofRepoId(record.getAPI_Audit_Config_ID()))
				.status(Status.ofCode(record.getStatus()))
				.isErrorAcknowledged(record.isErrorAcknowledged())
				.body(record.getBody())
				.method(HttpMethod.ofNullableCode(record.getMethod()))
				.path(record.getPath())
				.remoteAddress(record.getRemoteAddr())
				.remoteHost(record.getRemoteHost())
				.time(TimeUtil.asInstantNonNull(record.getTime()))
				.httpHeaders(record.getHttpHeaders())
				.requestURI(record.getRequestURI())
				.build();
	}

	public void deleteRequestAudit(@NonNull final ApiRequestAuditId apiRequestAuditId)
	{
		final I_API_Request_Audit record = InterfaceWrapperHelper.load(apiRequestAuditId, I_API_Request_Audit.class);

		InterfaceWrapperHelper.deleteRecord(record);
	}
}

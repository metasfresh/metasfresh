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

package de.metas.audit.apirequest.config;

import com.google.common.collect.ImmutableList;
import de.metas.audit.apirequest.HttpMethod;
import de.metas.audit.config.ApiAuditConfigsMap;
import de.metas.cache.CCache;
import de.metas.organization.OrgId;
import de.metas.user.UserGroupId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_API_Audit_Config;
import org.springframework.stereotype.Repository;

@Repository
public class ApiAuditConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, ApiAuditConfigsMap> cache = CCache.<Integer, ApiAuditConfigsMap>builder()
			.tableName(I_API_Audit_Config.Table_Name)
			.build();

	public ImmutableList<ApiAuditConfig> getActiveConfigsByOrgId(@NonNull final OrgId orgId)
	{
		return getMap().getActiveConfigsByOrgId(orgId);
	}

	public ImmutableList<ApiAuditConfig> getAllConfigsByOrgId(@NonNull final OrgId orgId)
	{
		return queryBL.createQueryBuilder(I_API_Audit_Config.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_API_Audit_Config.COLUMNNAME_AD_Org_ID, orgId, OrgId.ANY)
				.create()
				.list()
				.stream()
				.map(ApiAuditConfigRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public ApiAuditConfig getConfigById(@NonNull final ApiAuditConfigId id)
	{
		return getMap().getConfigById(id);
	}

	private ApiAuditConfigsMap getMap()
	{
		return cache.getOrLoad(0, this::retrieveMap);
	}

	private ApiAuditConfigsMap retrieveMap()
	{
		return ApiAuditConfigsMap.ofList(
				queryBL.createQueryBuilder(I_API_Audit_Config.class)
						.create()
						.stream()
						.map(ApiAuditConfigRepository::fromRecord)
						.collect(ImmutableList.toImmutableList()));
	}

	@NonNull
	private static ApiAuditConfig fromRecord(@NonNull final I_API_Audit_Config record)
	{
		return ApiAuditConfig.builder()
				.apiAuditConfigId(ApiAuditConfigId.ofRepoId(record.getAPI_Audit_Config_ID()))
				.active(record.isActive())
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.seqNo(record.getSeqNo())
				.isBypassAudit(record.isBypassAudit())
				.forceProcessedAsync(record.isForceProcessedAsync())
				.keepRequestDays(record.getKeepRequestDays())
				.keepRequestBodyDays(record.getKeepRequestBodyDays())
				.keepResponseDays(record.getKeepResponseDays())
				.keepResponseBodyDays(record.getKeepResponseBodyDays())
				.keepErroredRequestDays(record.getKeepErroredRequestDays())
				.method(HttpMethod.ofNullableCode(record.getMethod()))
				.pathPrefix(record.getPathPrefix())
				.notifyUserInCharge(NotificationTriggerType.ofNullableCode(record.getNotifyUserInCharge()))
				.userGroupInChargeId(UserGroupId.ofRepoIdOrNull(record.getAD_UserGroup_InCharge_ID()))
				.performAuditAsync(!record.isSynchronousAuditLoggingEnabled())
				.wrapApiResponse(record.isWrapApiResponse())
				.build();
	}
}

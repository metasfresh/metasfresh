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

package de.metas.audit.config;

import com.google.common.collect.ImmutableList;
import de.metas.audit.HttpMethod;
import de.metas.organization.OrgId;
import de.metas.user.UserGroupId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_API_Audit_Config;
import org.springframework.stereotype.Repository;

@Repository
public class ApiAuditConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public ImmutableList<ApiAuditConfig> getAllConfigsByOrgId(@NonNull final OrgId orgId)
	{
		return queryBL.createQueryBuilder(I_API_Audit_Config.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_API_Audit_Config.COLUMNNAME_AD_Org_ID, orgId, OrgId.ANY)
				.create()
				.list()
				.stream()
				.map(this::buildConfigFromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public ApiAuditConfig getConfigById(@NonNull final ApiAuditConfigId apiAuditConfigId)
	{
		final I_API_Audit_Config config = InterfaceWrapperHelper.load(apiAuditConfigId, I_API_Audit_Config.class);

		return buildConfigFromRecord(config);
	}

	@NonNull
	private ApiAuditConfig buildConfigFromRecord(@NonNull final I_API_Audit_Config record)
	{
		return ApiAuditConfig.builder()
				.apiAuditConfigId(ApiAuditConfigId.ofRepoId(record.getAPI_Audit_Config_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.seqNo(record.getSeqNo())
				.isInvokerWaitsForResponse(record.isInvokerWaitsForResult())
				.keepRequestDays(record.getKeepRequestDays())
				.keepRequestBodyDays(record.getKeepRequestBodyDays())
				.keepResponseDays(record.getKeepResponseDays())
				.keepResponseBodyDays(record.getKeepResponseBodyDays())
				.method(HttpMethod.ofNullableCode(record.getMethod()))
				.pathPrefix(record.getPathPrefix())
				.notifyUserInCharge(NotificationTriggerType.ofNullableCode(record.getNotifyUserInCharge()))
				.userGroupInChargeId(UserGroupId.ofRepoIdOrNull(record.getAD_UserGroup_InCharge_ID()))
				.build();
	}
}

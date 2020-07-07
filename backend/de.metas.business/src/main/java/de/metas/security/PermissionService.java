package de.metas.security;

import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.getId;
import static org.adempiere.model.InterfaceWrapperHelper.getModelTableId;
import static org.adempiere.model.InterfaceWrapperHelper.getOrgId;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.isNew;

/*
 * #%L
 * de.metas.ordercandidate.rest-api-impl
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class PermissionService
{
	private final IUserRolePermissionsDAO userRolePermissionsRepo = Services.get(IUserRolePermissionsDAO.class);

	private final UserRolePermissionsKey userRolePermissionsKey;
	@Getter
	private final OrgId defaultOrgId;

	private final Set<PermissionRequest> permissionsGranted = new HashSet<>();

	@Builder
	private PermissionService(
			@NonNull final UserRolePermissionsKey userRolePermissionsKey,
			@NonNull final OrgId defaultOrgId)
	{
		this.userRolePermissionsKey = userRolePermissionsKey;
		this.defaultOrgId = defaultOrgId;
	}

	public void assertCanCreateOrUpdate(final Object record)
	{
		final OrgId orgId = getOrgId(record).orElse(OrgId.ANY);
		final int adTableId = getModelTableId(record);
		final int recordId;
		if (isNew(record))
		{
			recordId = -1;
		}
		else
		{
			recordId = getId(record);
		}

		assertPermission(PermissionRequest.builder()
				.orgId(orgId)
				.adTableId(adTableId)
				.recordId(recordId)
				.build());
	}

	public void assertCanCreateOrUpdateRecord(final OrgId orgId, final Class<?> modelClass)
	{
		assertPermission(PermissionRequest.builder()
				.orgId(orgId)
				.adTableId(getTableId(modelClass))
				.build());
	}

	public boolean canRunProcess(@NonNull final AdProcessId processId)
	{
		final PermissionRequest permissionRequest = PermissionRequest.builder()
				.orgId(OrgId.ANY)
				.processId(processId.getRepoId())
				.build();

		if (permissionsGranted.contains(permissionRequest))
		{
			return true;
		}

		final IUserRolePermissions userPermissions = userRolePermissionsRepo.getUserRolePermissions(userRolePermissionsKey);

		final boolean canAccessProcess = userPermissions.checkProcessAccessRW(processId.getRepoId());

		if (canAccessProcess)
		{
			permissionsGranted.add(permissionRequest);
		}

		return canAccessProcess;
	}

	private void assertPermission(@NonNull final PermissionRequest request)
	{
		if (permissionsGranted.contains(request))
		{
			return;
		}

		final IUserRolePermissions userPermissions = userRolePermissionsRepo.getUserRolePermissions(userRolePermissionsKey);

		final String errmsg;
		if (request.getRecordId() >= 0)
		{
			errmsg = userPermissions.checkCanUpdate(
					userPermissions.getClientId(),
					request.getOrgId(),
					request.getAdTableId(),
					request.getRecordId());
		}
		else
		{
			errmsg = userPermissions.checkCanCreateNewRecord(
					userPermissions.getClientId(),
					request.getOrgId(),
					request.getAdTableId());
		}

		if (errmsg != null)
		{
			throw new PermissionNotGrantedException(errmsg);
		}
		permissionsGranted.add(request);
	}

	@lombok.Value
	@lombok.Builder
	private static class PermissionRequest
	{
		@lombok.NonNull
		OrgId orgId;
		@lombok.Builder.Default
		int adTableId = -1;
		@lombok.Builder.Default
		int recordId = -1;
		@lombok.Builder.Default
		int processId = -1;
	}

}

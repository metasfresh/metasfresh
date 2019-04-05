package de.metas.security.process;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.security.Principal;
import de.metas.security.permissions.Access;
import de.metas.security.permissions.record_access.UserGroupRecordAccessGrantRequest;
import de.metas.security.permissions.record_access.UserGroupRecordAccessService;
import de.metas.user.UserGroupId;
import de.metas.user.UserId;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class GrantUserGroupRecordAccess extends JavaProcess implements IProcessPrecondition
{
	private final UserGroupRecordAccessService userGroupRecordAccessService = Adempiere.getBean(UserGroupRecordAccessService.class);

	@Param(parameterName = "AD_User_ID", mandatory = false)
	private UserId userId;

	@Param(parameterName = "AD_UserGroup_ID", mandatory = false)
	private UserGroupId userGroupId;

	@Param(parameterName = "Access", mandatory = true)
	private String permissionCode;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		userGroupRecordAccessService.grantAccess(UserGroupRecordAccessGrantRequest.builder()
				.recordRef(getRecordRef())
				.principal(getPrincipal())
				.permission(getPermissionToGrant())
				.build());

		return MSG_OK;
	}

	private TableRecordReference getRecordRef()
	{
		return TableRecordReference.of(getTableName(), getRecord_ID());
	}

	private Principal getPrincipal()
	{
		return Principal.builder()
				.userId(userId)
				.userGroupId(userGroupId)
				.build();
	}

	private Access getPermissionToGrant()
	{
		return Access.ofCode(permissionCode);
	}

}

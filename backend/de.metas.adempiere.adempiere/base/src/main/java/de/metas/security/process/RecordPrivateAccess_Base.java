package de.metas.security.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.Principal;
import de.metas.security.requests.CreateRecordPrivateAccessRequest;
import de.metas.security.requests.RemoveRecordPrivateAccessRequest;
import de.metas.user.UserGroupId;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;

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

abstract class RecordPrivateAccess_Base extends JavaProcess implements IProcessPrecondition
{
	private final IUserRolePermissionsDAO userRolePermissionsRepo = Services.get(IUserRolePermissionsDAO.class);

	@Param(parameterName = "PrincipalType", mandatory = true)
	private String principalTypeCode;

	@Param(parameterName = "AD_User_ID", mandatory = false)
	private UserId userId;

	@Param(parameterName = "AD_UserGroup_ID", mandatory = false)
	private UserGroupId userGroupId;

	@Override
	public final ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		return ProcessPreconditionsResolution.accept();
	}

	protected final void addToPrivateAccess()
	{
		userRolePermissionsRepo.createPrivateAccess(CreateRecordPrivateAccessRequest.builder()
				.recordRef(getRecordRef())
				.principal(getPrincipal())
				.build());
	}

	protected final void removeFromPrivateAccess()
	{
		userRolePermissionsRepo.deletePrivateAccess(RemoveRecordPrivateAccessRequest.builder()
				.recordRef(getRecordRef())
				.principal(getPrincipal())
				.build());
	}

	private Principal getPrincipal()
	{
		final PrincipalType principalType = PrincipalType.ofCode(principalTypeCode);
		if (PrincipalType.USER.equals(principalType))
		{
			return Principal.userId(userId);
		}
		else if (PrincipalType.USER_GROUP.equals(principalType))
		{
			return Principal.userGroupId(userGroupId);
		}
		else
		{
			throw new AdempiereException("@Unknown@ @PrincipalType@: " + principalType);
		}
	}

}

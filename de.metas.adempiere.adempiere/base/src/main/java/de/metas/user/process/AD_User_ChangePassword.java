package de.metas.user.process;

import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_User;
import org.compiere.util.Env;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.user.UserId;
import de.metas.user.api.ChangeUserPasswordRequest;
import de.metas.user.api.IUserBL;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import de.metas.util.hash.HashableString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Change the password of any user (except the login one).
 * 
 * For current logged in user, the {@link AD_User_ChangeMyPassword} shall be used.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class AD_User_ChangePassword extends JavaProcess implements IProcessPrecondition
{
	private final IUserBL usersService = Services.get(IUserBL.class);

	@Param(parameterName = "OldPassword", mandatory = false)
	private String oldPassword;
	@Param(parameterName = "NewPassword", mandatory = true)
	private String newPassword;
	@Param(parameterName = "NewPasswordRetype", mandatory = true)
	private String newPasswordRetype;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		if (!I_AD_User.Table_Name.equals(context.getTableName()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not running on AD_User table");
		}

		// Make sure it's NOT the current logged in user
		final int adUserId = context.getSingleSelectedRecordId();
		if (adUserId == Env.getAD_User_ID(Env.getCtx()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("current logged in user");
		}

		final I_AD_User user = Services.get(IUserDAO.class).retrieveUserOrNull(Env.getCtx(), adUserId);
		final de.metas.adempiere.model.I_AD_User userSystem = InterfaceWrapperHelper.create(user, de.metas.adempiere.model.I_AD_User.class);
		if (!userSystem.isSystemUser())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not a system user");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		if (!I_AD_User.Table_Name.equals(getTableName()))
		{
			throw new AdempiereException("Call it from User window");
		}

		final Properties ctx = getCtx(); // logged in context

		//
		// Get the AD_User_ID and make sure it's NOT the currently logged on.
		final UserId adUserId = UserId.ofRepoId(getRecord_ID());
		final UserId loggedUserId = Env.getLoggedUserId(ctx);
		if (UserId.equals(adUserId, loggedUserId))
		{
			throw new AdempiereException("Changing password for currently logged on user is not allowed by this process");
		}

		//
		// Actually change it's password
		usersService.changePassword(ChangeUserPasswordRequest.builder()
				.userId(adUserId)
				.oldPassword(HashableString.ofPlainValue(oldPassword))
				.newPassword(newPassword)
				.newPasswordRetype(newPasswordRetype)
				//
				.contextClientId(Env.getClientId(ctx))
				.contextUserId(loggedUserId)
				.contextDate(Env.getLocalDate(ctx))
				//
				.build());

		return MSG_OK;
	}

}

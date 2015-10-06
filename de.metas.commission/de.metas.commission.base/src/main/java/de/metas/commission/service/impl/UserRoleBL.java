package de.metas.commission.service.impl;

/*
 * #%L
 * de.metas.commission.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.ad.security.IRoleDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.user.api.IUserBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_AD_User_Roles;

import de.metas.commission.modelvalidator.C_Sponsor_SalesRep;
import de.metas.commission.service.IUserRoleBL;

public class UserRoleBL implements IUserRoleBL
{
	final public static String MSG_UserPortalRoleNotMatch = "AD_User_Rolle_Message";

	@Override
	public void validate(final I_AD_User_Roles userRole)
	{
		assertUserPortalRoleMatch(userRole);
	}

	/**
	 * allow only portal role or any other role is the bp is employee 
	 * @param userRole
	 */
	private void assertUserPortalRoleMatch(final I_AD_User_Roles userRole)
	{
		Check.assumeNotNull(userRole, "userRole not null");
		
		// NOTE: we need to enforce this role ONLY if record is created by user from an UI Window
		if (!InterfaceWrapperHelper.isUIAction(userRole))
		{
			return;
		}
		
		//
		// If portal role sysconfig is not set, do nothing and accept it
		final int portalRoleId = getPortalRoleId();
		if (portalRoleId <= 0)
		{
			return;
		}

		// User was not set yet
		if (userRole.getAD_User_ID() <= 0)
		{
			return;
		}

		// Role was not set
		if (userRole.getAD_Role_ID() <= 0)
		{
			return;
		}

		// Portal Role => allow it
		if (portalRoleId == userRole.getAD_Role_ID())
		{
			return;
		}

		final I_AD_User user = userRole.getAD_User();
		if (Services.get(IUserBL.class).isEmployee(user))
		{
			return;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(userRole);
		final String portalRoleName = Services.get(IRoleDAO.class).retrieveRoleName(ctx, portalRoleId);
		throw new AdempiereException(ctx, MSG_UserPortalRoleNotMatch, new Object[] { portalRoleName });
	}

	public int getPortalRoleId()
	{
		final int portalRoleId = Services.get(ISysConfigBL.class).getIntValue(C_Sponsor_SalesRep.SYS_CONFIG_PORTAL_ROLLE, -1);
		if (portalRoleId <= 0)
		{
			return -1;
		}

		return portalRoleId;
	}
}

package org.adempiere.ad.security.model.validator;

/*
 * #%L
 * ADempiere ERP - Base
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
import java.util.logging.Level;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.security.IRoleDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.MRoleOrgAccess;
import org.compiere.model.ModelValidator;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_AD_Role;

@Interceptor(I_AD_Org.class)
public class AD_Org
{
	public static final transient AD_Org instance = new AD_Org();

	private final transient CLogger logger = CLogger.getCLogger(getClass());

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void addAccessToRolesWithAutomaticMaintenance(final I_AD_Org org)
	{
		int orgAccessCreatedCounter = 0;
		final Properties ctx = InterfaceWrapperHelper.getCtx(org);
		for (final I_AD_Role role : Services.get(IRoleDAO.class).retrieveAllRolesWithAutoMaintenance(ctx))
		{
			MRoleOrgAccess orgAccess = new MRoleOrgAccess(org, role.getAD_Role_ID());
			if (orgAccess.save())
			{
				orgAccessCreatedCounter++;
			}
		}
		logger.log(Level.INFO, "{0} - created #{1}", new Object[] { org, orgAccessCreatedCounter });

		// Reset role permissions, just to make sure we are on the safe side
		if (orgAccessCreatedCounter > 0)
		{
			Env.resetUserRolePermissions();
		}
	}
}

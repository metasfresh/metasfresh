/**
 * 
 */
package de.metas.commission.callout;

/*
 * #%L
 * de.metas.commission.client
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

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_User_Roles;

import de.metas.commission.service.IUserRoleBL;

/**
 * @author cg
 * 
 */
public class AD_User_Roles extends CalloutEngine
{

	final public static String ROLLE_MSG = "AD_User_Rolle_Message";

	public String onRoleChange(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value,
			final Object oldValue)
	{
		final I_AD_User_Roles userRoles = InterfaceWrapperHelper.create(mTab, I_AD_User_Roles.class);
		Services.get(IUserRoleBL.class).validate(userRoles);
		return NO_ERROR;
	}
}

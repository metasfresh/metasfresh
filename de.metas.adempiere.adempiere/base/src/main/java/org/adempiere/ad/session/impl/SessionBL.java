package org.adempiere.ad.session.impl;

import org.adempiere.ad.security.IUserRolePermissions;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import org.adempiere.ad.session.ISessionBL;
import org.compiere.util.Env;

public class SessionBL implements ISessionBL
{
	private final InheritableThreadLocal<Boolean> disableChangeLogsThreadLocal = new InheritableThreadLocal<Boolean>()
	{
		@Override
		protected Boolean initialValue()
		{
			return Boolean.FALSE;
		};
	};

	@Override
	public void setDisableChangeLogsForThread(final boolean disable)
	{
		disableChangeLogsThreadLocal.set(disable);
	}
	
	@Override
	public boolean isChangeLogEnabled()
	{
		//
		// Check if it's disabled for current thread
		final Boolean disableChangeLogsThreadLocalValue = disableChangeLogsThreadLocal.get();
		if (Boolean.TRUE.equals(disableChangeLogsThreadLocalValue))
		{
			return false;
		}
		
		//
		// Check if role allows us to create the change log
		final IUserRolePermissions role = Env.getUserRolePermissions();
		if (role == null || !role.hasPermission(IUserRolePermissions.PERMISSION_ChangeLog))
		{
			return false;
		}
		
		return true; // enabled
	}
}

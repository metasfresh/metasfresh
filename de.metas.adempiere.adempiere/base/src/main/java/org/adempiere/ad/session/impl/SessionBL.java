package org.adempiere.ad.session.impl;

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
		final Boolean disableChangeLogsThreadLocalValue = disableChangeLogsThreadLocal.get();
		if (disableChangeLogsThreadLocalValue != null && disableChangeLogsThreadLocalValue.booleanValue() == true)
		{
			return false;
		}
		
		return true;
	}
}

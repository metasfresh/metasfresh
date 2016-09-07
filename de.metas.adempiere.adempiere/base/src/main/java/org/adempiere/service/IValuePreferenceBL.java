package org.adempiere.service;

import java.util.Collection;

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

import java.util.Properties;

import org.adempiere.util.ISingletonService;

public interface IValuePreferenceBL extends ISingletonService
{
	public interface IUserValuePreference
	{
		int AD_WINDOW_ID_NONE = 0;

		int getAD_Window_ID();

		String getName();

		String getValue();
	}

	public interface IUserValuePreferences
	{
		int getAD_Window_ID();

		String getValue(String name);
		
		Collection<IUserValuePreference> values();
	}

	IUserValuePreferences getWindowPreferences(Properties ctx, int adWindowId);

	Collection<IUserValuePreferences> getAllWindowPreferences(int AD_Client_ID, int AD_Org_ID, int AD_User_ID);

}

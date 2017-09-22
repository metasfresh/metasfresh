package de.metas.system.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.isValueChanged;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.service.ISystemBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_System;
import org.compiere.model.ModelValidator;
import org.compiere.util.Ini;

import lombok.NonNull;

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

@Interceptor(I_AD_System.class)
public class AD_System
{
	public static final AD_System INSTANCE = new AD_System();

	private AD_System()
	{
	};
	
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(@NonNull final I_AD_System system)
	{

		boolean userChange = Ini.isClient() &&
				(isValueChanged(system, I_AD_System.COLUMNNAME_Name)
						|| isValueChanged(system, I_AD_System.COLUMNNAME_UserName)
						|| isValueChanged(system, I_AD_System.COLUMNNAME_Password)
						|| isValueChanged(system, I_AD_System.COLUMNNAME_CustomPrefix));
		if (userChange)
		{
			String name = system.getName();
			if (name.equals("?") || name.length() < 2)
			{
				throw new AdempiereException("Define a unique System name (e.g. Company name) not " + name);
			}
			if (system.getUserName().equals("?") || system.getUserName().length() < 2)
			{
				throw new AdempiereException("Use the same EMail address as in the Adempiere Web Store");
			}
			if (system.getPassword().equals("?") || system.getPassword().length() < 2)
			{
				throw new AdempiereException("Use the same Password as in the Adempiere Web Store");
			}
		}

		Services.get(ISystemBL.class).setInfo(system);
	}
}

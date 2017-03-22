package org.adempiere.ad.security.model.validator;

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

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.security.IRoleDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Access;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.model.I_AD_Role;
import de.metas.process.IADProcessDAO;

@Interceptor(I_AD_Process.class)
public class AD_Process
{
	public static final transient AD_Process instance = new AD_Process();

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void addAccessToRolesWithAutomaticMaintenance(final I_AD_Process process)
	{
		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
		
		final Properties ctx = InterfaceWrapperHelper.getCtx(process);
		for (final I_AD_Role role : Services.get(IRoleDAO.class).retrieveAllRolesWithAutoMaintenance(ctx))
		{
			final I_AD_Process_Access pa = adProcessDAO.createProcessAccessDraft(ctx, process.getAD_Process_ID(), role);
			InterfaceWrapperHelper.save(pa);
		}

	}
}

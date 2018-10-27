package de.metas.adempiere.modelvalidator;

/*
 * #%L
 * de.metas.swat.base
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


import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import de.metas.adempiere.model.I_C_CountryArea_Assign;
import de.metas.adempiere.service.ICountryAreaBL;
import de.metas.util.Services;

//@Validator(I_C_CountryArea_Assign.class)
public final class C_CountryArea_Assign implements ModelValidator
{

	private int ad_Client_ID = -1;

	@Override
	public void initialize(ModelValidationEngine engine, MClient client)
	{
		if (client != null)
		{
			ad_Client_ID = client.getAD_Client_ID();
		}
		engine.addModelChange(I_C_CountryArea_Assign.Table_Name, this);
	}

	@Override
	public int getAD_Client_ID()
	{
		return ad_Client_ID;
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(PO po, int type)
	{
		final I_C_CountryArea_Assign assignment = InterfaceWrapperHelper.create(po, I_C_CountryArea_Assign.class);

		if (type == TYPE_BEFORE_NEW || type == TYPE_BEFORE_CHANGE)
		{
			Services.get(ICountryAreaBL.class).validate(assignment);
		}

		return null;
	}

	@Override
	public String docValidate(PO po, int timing)
	{
		return null;
	}
}

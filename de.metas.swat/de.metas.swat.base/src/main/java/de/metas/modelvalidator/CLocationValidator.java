package de.metas.modelvalidator;

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


import org.adempiere.model.POWrapper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Location;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import de.metas.adempiere.service.ILocationBL;

/**
 * Location Validator
 * 
 * @author tsa
 * @see http ://dewiki908/mediawiki/index.php/US786:_Postleitzahlenfehler_bei_Kommisionnierung_%282010100610000094%29
 */
public class CLocationValidator implements ModelValidator
{
	private int m_AD_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	@Override
	public void initialize(ModelValidationEngine engine, MClient client)
	{
		if (client != null)
			m_AD_Client_ID = client.getAD_Client_ID();

		Services.registerService(ILocationBL.class, new de.metas.adempiere.service.impl.LocationBL());
		engine.addModelChange(I_C_Location.Table_Name, this);
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(PO po, int type) throws Exception
	{
		if (I_C_Location.Table_Name.equals(po.get_TableName()))
		{
			if (po.isReplication())
			{
				// don't validate the postal if we got the record from replication
				return null;
			}
			I_C_Location location = POWrapper.create(po, I_C_Location.class);
			if (type == TYPE_AFTER_NEW
					|| (type == TYPE_AFTER_CHANGE && po.is_ValueChanged(I_C_Location.COLUMNNAME_Postal))
					|| !location.isPostalValidated())
			{
				// TODO: add to SysConfig to be configurable if we want to deactivate it or not
				// Services.get(ILocationBL.class).validatePostal(location);
			}
		}

		return null;
	}

	@Override
	public String docValidate(PO po, int timing)
	{
		return null;
	}
}

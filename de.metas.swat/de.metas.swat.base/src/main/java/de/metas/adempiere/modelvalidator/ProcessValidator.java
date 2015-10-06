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


import org.adempiere.util.Constants;
import org.adempiere.util.CustomColNames;
import org.compiere.model.I_AD_Process;
import org.compiere.model.MClient;
import org.compiere.model.MProcess;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import de.metas.adempiere.process.ExecuteUpdateSQL;

/**
 * This model validator checks for each new invoice line if there needs to be an additional invoice line for freight
 * cost.
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Versandkostenermittlung/_-berechnung_(2009_0027_G28)'>DV-Konzept (2009_0027_G28)</a>"
 * 
 */
public class ProcessValidator implements ModelValidator
{

	private int ad_Client_ID = -1;

	public int getAD_Client_ID()
	{
		return ad_Client_ID;
	}

	public final void initialize(final ModelValidationEngine engine,
			final MClient client)
	{

		if (client != null)
		{
			ad_Client_ID = client.getAD_Client_ID();
		}

		engine.addModelChange(I_AD_Process.Table_Name, this);
	}

	public String login(final int AD_Org_ID, final int AD_Role_ID,
			final int AD_User_ID)
	{
		// nothing to do
		return null;
	}

	public String docValidate(final PO po, final int timing)
	{

		// nothing to do
		return null;
	}

	public String modelChange(final PO po, final int type) throws Exception
	{
		final MProcess process = (MProcess)po;

		if (type == ModelValidator.TYPE_BEFORE_NEW || type == ModelValidator.TYPE_BEFORE_CHANGE)
		{

			if (process.is_ValueChanged(CustomColNames.AD_Process_TYPE))
			{
				final String processType = process.get_ValueAsString(CustomColNames.AD_Process_TYPE);

				if (Constants.AD_Process_TYPE_SQL.equals(processType))
				{
					process.setClassname(ExecuteUpdateSQL.class.getName());
				}
			}
		}
		return null;
	}
}

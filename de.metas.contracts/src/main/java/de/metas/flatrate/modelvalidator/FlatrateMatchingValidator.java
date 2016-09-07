package de.metas.flatrate.modelvalidator;

/*
 * #%L
 * de.metas.contracts
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


import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.Msg;

import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.flatrate.model.I_C_Flatrate_Matching;
import de.metas.flatrate.model.X_C_Flatrate_Conditions;

public class FlatrateMatchingValidator implements ModelValidator
{

	private int m_AD_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	@Override
	public void initialize(final ModelValidationEngine engine, final MClient client)
	{
		if (client != null)
			m_AD_Client_ID = client.getAD_Client_ID();

		engine.addModelChange(I_C_Flatrate_Matching.Table_Name, this);
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(final PO po, final int type) throws Exception
	{
		if (type == TYPE_BEFORE_NEW || type == TYPE_BEFORE_CHANGE)
		{
			final I_C_Flatrate_Matching matching = InterfaceWrapperHelper.create(po, I_C_Flatrate_Matching.class);

			final I_C_Flatrate_Conditions fc = matching.getC_Flatrate_Conditions();

			if (matching.getM_Product_Category_Matching_ID() <= 0
					&& X_C_Flatrate_Conditions.TYPE_CONDITIONS_Depotgebuehr.equals(fc.getType_Conditions()))
			{
				throw new FillMandatoryException(I_C_Flatrate_Matching.COLUMNNAME_M_Product_Category_Matching_ID);
			}
		}
		return null;
	}

	@Override
	public String docValidate(PO po, int timing)
	{
		if (TIMING_BEFORE_VOID == timing || TIMING_BEFORE_CLOSE == timing)
		{
			return Msg.getMsg(po.getCtx(), MainValidator.MSG_FLATRATE_DOC_ACTION_NOT_SUPPORTED_0P);
		}

		// nothing to do
		return null;
	}
}

package de.metas.commission.modelvalidator;

/*
 * #%L
 * de.metas.commission.base
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


import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.Env;

import de.metas.adempiere.form.IClientUI;
import de.metas.commission.interfaces.I_C_BPartner_Location;

public class C_BPartner_Location implements ModelValidator
{
	public static final String MSG_NoCommissionToError = "NoCommissionToError";

	private int ad_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return ad_Client_ID;
	}

	@Override
	public final void initialize(final ModelValidationEngine engine, final MClient client)
	{
		if (client != null)
		{
			ad_Client_ID = client.getAD_Client_ID();
		}

		engine.addModelChange(org.compiere.model.I_C_BPartner_Location.Table_Name, this);
	}

	@Override
	public String modelChange(final PO po, final int type) throws Exception
	{
		final I_C_BPartner_Location address = InterfaceWrapperHelper.create(po, I_C_BPartner_Location.class);
		checkDefaults(address, po, type);
		return null;
	}

	private void checkDefaults(final I_C_BPartner_Location address, final PO po, final int type)
	{
		if (type == ModelValidator.TYPE_BEFORE_NEW)
		{
			// Solved by Index
			return;
		}

		if (type == ModelValidator.TYPE_BEFORE_DELETE)
		{
			if (address.isActive())
			{
				beforeDeleteCheck(address);
			}

		}
		if (type == ModelValidator.TYPE_BEFORE_CHANGE)
		{
			if (po.is_ValueChanged(org.compiere.model.I_C_BPartner_Location.COLUMNNAME_IsActive))
			{
				if (!address.isActive())
				{
					beforeDeleteCheck(address);
				}
				else
				{
					// Solved by index
					return;
				}
			}

			if (po.is_ValueChanged(I_C_BPartner_Location.COLUMNNAME_IsCommissionTo))
			{
				if (!address.isCommissionTo())
				{
					throw new AdempiereException("@" + C_BPartner_Location.MSG_NoCommissionToError + "@");
				}
			}
		}

		// If there is no IsCommission flag checked for any address, do not save.
		if (ModelValidator.TYPE_BEFORE_SAVE_TRX == type && !address.isCommissionTo())
		{
			final String whereClause = I_C_BPartner_Location.COLUMNNAME_IsCommissionTo + " = ? AND "
					+ org.compiere.model.I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID + " = ?";
			final int rows = new Query(Env.getCtx(), org.compiere.model.I_C_BPartner_Location.Table_Name, whereClause, po.get_TrxName())
					.setOnlyActiveRecords(true)
					.setParameters(true, address.getC_BPartner_ID())
					.setClient_ID()
					.count();

			if (0 == rows)
			{
				address.setIsCommissionTo(true);
				Services.get(IClientUI.class).error(0, C_BPartner_Location.MSG_NoCommissionToError);
			}
		}
	}

	private void beforeDeleteCheck(final I_C_BPartner_Location address)
	{
		if (address.isCommissionTo())
		{
			throw new AdempiereException("@" + C_BPartner_Location.MSG_NoCommissionToError + "@");
		}
		return;
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		return null;
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		return null;
	}
}

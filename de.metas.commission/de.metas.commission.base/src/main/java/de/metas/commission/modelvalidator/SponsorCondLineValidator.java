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


import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import de.metas.commission.model.I_C_Sponsor_CondLine;

public class SponsorCondLineValidator implements ModelValidator
{
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
		engine.addDocValidate(I_C_Sponsor_CondLine.Table_Name, this);
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(final PO po, final int type) throws Exception
	{
		if (type == ModelValidator.TYPE_BEFORE_NEW || type == ModelValidator.TYPE_BEFORE_CHANGE)
		{
			if (type == ModelValidator.TYPE_BEFORE_NEW
					|| po.is_ValueChanged(I_C_Sponsor_CondLine.COLUMNNAME_C_BPartner_ID)
					|| po.is_ValueChanged(I_C_Sponsor_CondLine.COLUMNNAME_C_AdvCommissionCondition_ID))
			{
				final I_C_Sponsor_CondLine condLine = InterfaceWrapperHelper.create(po, I_C_Sponsor_CondLine.class);

				if (condLine.getC_BPartner_ID() > 0)
				{
					if (condLine.getC_AdvCommissionCondition_ID() <= 0)
					{
						throw new FillMandatoryException(I_C_Sponsor_CondLine.COLUMNNAME_C_AdvCommissionCondition_ID);
					}
				}
				else
				{
					condLine.setC_AdvCommissionCondition_ID(0);
				}
			}
		}
		return null;
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		return null; // nothing to do
	}
}

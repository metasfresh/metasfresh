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


import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import de.metas.commission.interfaces.I_M_ProductPrice;
import de.metas.commission.interfaces.I_M_ProductScalePrice;
import de.metas.product.IProductPA;

public class MProductPriceValidator implements ModelValidator
{
	private int ad_Client_ID = -1;

	@Override
	public String docValidate(final PO po, final int timing)
	{
		return null;
	}

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
		engine.addModelChange(org.compiere.model.I_M_ProductPrice.Table_Name, this);
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(final PO po, final int type) throws Exception
	{
		if (type != ModelValidator.TYPE_BEFORE_CHANGE && type != ModelValidator.TYPE_BEFORE_NEW)
		{
			return null;
		}

		if (!po.is_ValueChanged(de.metas.adempiere.model.I_M_ProductPrice.COLUMNNAME_UseScalePrice))
		{
			return null;
		}

		final I_M_ProductPrice productPrice = InterfaceWrapperHelper.create(po, I_M_ProductPrice.class);
		if (!productPrice.isUseScalePrice())
		{
			return null;
		}
		final IProductPA productPA = Services.get(IProductPA.class);

		// if the scalePrice doesn't exist yet, create a new one
		final I_M_ProductScalePrice productScalePrice =
				InterfaceWrapperHelper.create(productPA.retrieveOrCreateScalePrices(po.get_ID(), BigDecimal.ONE, true, po.get_TrxName()),
						I_M_ProductScalePrice.class);

		// copy the price from the productPrice
		productScalePrice.setCommissionPoints(productPrice.getCommissionPoints());

		InterfaceWrapperHelper.save(productScalePrice);

		return null;
	}
}

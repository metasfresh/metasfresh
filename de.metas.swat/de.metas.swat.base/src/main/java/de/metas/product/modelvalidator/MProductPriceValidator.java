package de.metas.product.modelvalidator;

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


import java.math.BigDecimal;

import org.adempiere.model.I_M_ProductScalePrice;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import de.metas.product.IProductPA;

public class MProductPriceValidator implements ModelValidator
{
	private int ad_Client_ID = -1;

	@Override
	public String docValidate(PO po, int timing)
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
		engine.addModelChange(I_M_ProductPrice.Table_Name, this);
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(final PO po, final int type) throws Exception
	{
		if (type != ModelValidator.TYPE_AFTER_CHANGE
				&& type != ModelValidator.TYPE_AFTER_NEW
				&& type != ModelValidator.TYPE_BEFORE_DELETE)
		{

			return null;
		}

		final I_M_ProductPrice productPrice = InterfaceWrapperHelper.create(po, I_M_ProductPrice.class);
		if (!productPrice.isUseScalePrice())
		{
			return null;
		}

		if (type == ModelValidator.TYPE_BEFORE_DELETE)
		{
			productPriceDelete(productPrice);
		}
		else
		{
			final IProductPA productPA = Services.get(IProductPA.class);

			if (productPrice.isUseScalePrice())
			{
				final String trxName = InterfaceWrapperHelper.getTrxName(productPrice);
				final I_M_ProductScalePrice productScalePrice = productPA.retrieveOrCreateScalePrices(
						productPrice.getM_ProductPrice_ID(),
						BigDecimal.ONE, // Qty
						true, // createNew=true => if the scalePrice doesn't exist yet, create a new one
						trxName);

				// copy the price from the productPrice
				productScalePrice.setM_ProductPrice_ID(productPrice.getM_ProductPrice_ID());
				productScalePrice.setPriceLimit(productPrice.getPriceLimit());
				productScalePrice.setPriceList(productPrice.getPriceList());
				productScalePrice.setPriceStd(productPrice.getPriceStd());

				InterfaceWrapperHelper.save(productScalePrice);
			}
		}
		
		return null;
	}

	private void productPriceDelete(final I_M_ProductPrice productPrice)
	{
		final IProductPA productPA = Services.get(IProductPA.class);

		if (productPrice.getM_ProductPrice_ID() <= 0)
		{
			return;
		}

		final String trxName = InterfaceWrapperHelper.getTrxName(productPrice);
		for (final I_M_ProductScalePrice psp : productPA.retrieveScalePrices(productPrice.getM_ProductPrice_ID(), trxName))
		{

			if (psp.getM_ProductPrice_ID() != productPrice.getM_ProductPrice_ID())
			{
				// the psp comes from cache and is stale
				// NOTE: i think this problem does not apply anymore, so we can safely delete this check
				continue;
			}
			
			
			InterfaceWrapperHelper.delete(psp);
		}

	}
}

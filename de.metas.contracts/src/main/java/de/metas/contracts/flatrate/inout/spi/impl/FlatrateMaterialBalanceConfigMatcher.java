package de.metas.contracts.flatrate.inout.spi.impl;

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


import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;

import de.metas.contracts.flatrate.api.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.inout.spi.IMaterialBalanceConfigMatcher;

public class FlatrateMaterialBalanceConfigMatcher implements IMaterialBalanceConfigMatcher
{

	public FlatrateMaterialBalanceConfigMatcher()
	{
	}
	
	@Override
	public boolean matches(final I_M_InOutLine inoutLine)
	{
		// Services
		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);
		
		//header
		final I_M_InOut inout = inoutLine.getM_InOut();
		
		// order
		final I_C_Order order = inout.getC_Order();
		
		final Properties ctx = InterfaceWrapperHelper.getCtx(inoutLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(inoutLine);
		
		final I_C_BPartner partner;
		if(order != null && order.getC_Order_ID() > 0)
		{
			partner = order.getBill_BPartner();
		}
		else
		{
			partner = inout.getC_BPartner();
		}
		
		final I_M_Product product = inoutLine.getM_Product();
		
		final List<I_C_Flatrate_Term> terms = flatrateDB.retrieveTerms(ctx, partner.getC_BPartner_ID(), inout.getDateOrdered(), product.getM_Product_Category_ID(), product.getM_Product_ID(), inoutLine.getC_Charge_ID(), trxName);
		
		if(terms.isEmpty())
		{
			return false;
		}
		
		return true;	
	}

}

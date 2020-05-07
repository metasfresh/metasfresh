package de.metas.contracts.flatrate.inout.spi.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

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
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.inout.spi.IMaterialBalanceConfigMatcher;
import de.metas.util.Services;

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

		final BPartnerId partnerId;
		if(order != null && order.getC_Order_ID() > 0)
		{
			partnerId = BPartnerId.ofRepoId(order.getBill_BPartner_ID());
		}
		else
		{
			partnerId = BPartnerId.ofRepoId(inout.getC_BPartner_ID());
		}

		final I_M_Product product = loadOutOfTrx(inoutLine.getM_Product_ID(), I_M_Product.class);

		final List<I_C_Flatrate_Term> terms = flatrateDB.retrieveTerms(
				ctx,
				partnerId.getRepoId(),
				inout.getDateOrdered(),
				product.getM_Product_Category_ID(),
				product.getM_Product_ID(),
				inoutLine.getC_Charge_ID(),
				trxName);

		if(terms.isEmpty())
		{
			return false;
		}

		return true;
	}

}

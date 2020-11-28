package de.metas.edi.rpl.requesthandler;

/*
 * #%L
 * de.metas.edi
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


import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerBL;
import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerCtx;
import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerResult;
import org.adempiere.process.rpl.requesthandler.spi.ReplRequestHandlerAdapter;
import org.compiere.model.PO;

import de.metas.util.Services;

public class XLS_C_OLCand_RequestHandler extends ReplRequestHandlerAdapter
{

	@Override
	public IReplRequestHandlerResult handleRequest(final PO po, final IReplRequestHandlerCtx ctx)
	{
		final IReplRequestHandlerResult result = Services.get(IReplRequestHandlerBL.class).createInitialRequestHandlerResult();

		// NOTE: atm we do nothing here because we expect everything to be solved by existing attribute pricing rules
		//@formatter:off
//		final I_C_OLCand olcand = InterfaceWrapperHelper.create(po, I_C_OLCand.class);
//		//
//		// Create and set ASI from given M_ProductPrice_Attribute_ID
//		final I_M_ProductPrice_Attribute productPriceAttribute = olcand.getM_ProductPrice_Attribute();
//		if (productPriceAttribute != null)
//		{
//			final I_M_AttributeSetInstance asi = Services.get(IAttributePricingBL.class).generateASI(productPriceAttribute);
//			olcand.setM_AttributeSetInstance(asi);
//			OLCandPriceValidator.DYNATTR_OLCAND_PRICEVALIDATOR_PRICING_RESULT.setValue(olcand, null); // reset
//		}
//		InterfaceWrapperHelper.save(olcand);
		//@formatter:on

		return result;
	}

}

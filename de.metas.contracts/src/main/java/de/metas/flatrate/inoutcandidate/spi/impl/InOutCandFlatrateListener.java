package de.metas.flatrate.inoutcandidate.spi.impl;

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
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.adempiere.model.I_C_Order;
import de.metas.flatrate.api.IFlatrateDB;
import de.metas.flatrate.interfaces.I_C_OrderLine;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.inoutcandidate.spi.IInOutCandHandler;
import de.metas.inoutcandidate.spi.IInOutCandHandlerListener;

/**
 * This implementation vetoes the creation of shipment schedule records for {@link I_C_OrderLine}s if those order lines
 * are handled by a flatrate contract.
 * 
 * @author ts
 * 
 */
public class InOutCandFlatrateListener implements IInOutCandHandlerListener
{

	/**
	 * @param model
	 *            the object for this the given <code>handler</code> wants to create a shipment schedule. The method
	 *            assumes that it can obtain a {@link I_C_OrderLine} instance for model by using the
	 *            {@link InterfaceWrapperHelper}.
	 * @param handler
	 *            the handler that wants to create a shipment schedule for the given <code>model</code>. The method
	 *            assume that this handler's {@link IInOutCandHandler#getSourceTable()} invocation returns
	 *            <code>"C_OrderLine"</code>.
	 */
	@Override
	public OnMissingCandidate foundModelWithoutInOutCandidate(Object model, IInOutCandHandler handler)
	{
		Check.assume(I_C_OrderLine.Table_Name.equals(handler.getSourceTable()), "Param 'handler'=" + handler + " has SourceTable=" + I_C_OrderLine.Table_Name);

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(model, I_C_OrderLine.class);

		return isSubscription(ol) || hasAtLreastOneFlatrateContract(ol) ? OnMissingCandidate.SKIP_CREATION : OnMissingCandidate.I_DONT_CARE;
	}

	public boolean hasAtLreastOneFlatrateContract(final I_C_OrderLine ol)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(ol);
		final String trxName = InterfaceWrapperHelper.getTrxName(ol);

		final I_C_Order o = InterfaceWrapperHelper.create(ol.getC_Order(), I_C_Order.class);

		final IFlatrateDB flatrateDB = Services.get(IFlatrateDB.class);
		final List<I_C_Flatrate_Term> termsForOl =
				flatrateDB.retrieveTerms(ctx, o.getBill_BPartner_ID(), o.getDateOrdered(), ol.getM_Product().getM_Product_Category_ID(), ol.getM_Product_ID(), ol.getC_Charge_ID(), trxName);

		// if there are terms for 'ol', then we ask the handler not to create a shipment schedule for 'ol'
		final boolean atLeastOneFlatrateContract = !termsForOl.isEmpty();
		return atLeastOneFlatrateContract;
	}

	private boolean isSubscription(final I_C_OrderLine ol)
	{
		return ol.getC_Flatrate_Conditions_ID() > 0;
	}
}

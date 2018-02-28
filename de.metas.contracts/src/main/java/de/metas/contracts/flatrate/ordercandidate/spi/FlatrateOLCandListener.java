package de.metas.contracts.flatrate.ordercandidate.spi;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.spi.IOLCandListener;

@Component
public class FlatrateOLCandListener implements IOLCandListener
{

	/**
	 * This method sets the {@link de.metas.contracts.subscription.model.I_C_OrderLine#COLUMNNAME_C_Flatrate_Conditions_ID} value
	 * from the given <code>olCand</code>.
	 * 
	 * If the given order line already has a <code>C_Flatrate_Conditions_ID</code>, then the method assumes that the
	 * value is the same as that of <code>olCand</code>.
	 * 
	 * @param olCand
	 * @param orderLine
	 * 
	 * @see FlatrateGroupingProvider
	 */
	@Override
	public void onOrderLineCreated(final OLCand olCand, final I_C_OrderLine orderLine)
	{
		de.metas.contracts.subscription.model.I_C_OrderLine flatrateOrderLine = InterfaceWrapperHelper.create(orderLine, de.metas.contracts.subscription.model.I_C_OrderLine.class);

		final int flatrateConditionsId = olCand.getFlatrateConditionsId();
		if (flatrateConditionsId <= 0)
		{
			flatrateOrderLine.setC_Flatrate_Conditions_ID(flatrateConditionsId);
		}
		else
		{
			final int orderLineFlatrateConditionsId = flatrateOrderLine.getC_Flatrate_Conditions_ID();
			Check.assume(orderLineFlatrateConditionsId == flatrateConditionsId,
					"{} with C_Flatrate_Conditions_ID={} can't be aggregated into {} with C_Flatrate_Conditions_ID={}",
					olCand, flatrateConditionsId, orderLine, orderLineFlatrateConditionsId);
		}
	}
}

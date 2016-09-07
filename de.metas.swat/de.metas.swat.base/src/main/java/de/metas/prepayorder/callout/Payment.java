package de.metas.prepayorder.callout;

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
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_Payment;

import de.metas.prepayorder.service.IPrepayOrderBL;

public class Payment extends CalloutEngine
{
	public String order(final Properties ctx, final int WindowNo, final GridTab mTab,
			final GridField mField, final Object value)
	{
		if (isCalloutActive())
		{
			return "";
		}
		final I_C_Payment payment = InterfaceWrapperHelper.create(mTab, I_C_Payment.class);

		final int orderId = payment.getC_Order_ID();
		
		if (orderId <= 0)
		{
			// nothing to do
			return "";
		}

		final IPrepayOrderBL prepayOrderBL = Services.get(IPrepayOrderBL.class);
		if (!prepayOrderBL.isPrepayOrder(ctx, orderId, null))
		{
			// nothing to do
			return "";
		}

		final BigDecimal allocated = prepayOrderBL.retrieveAllocatedAmt(ctx, orderId, null);
		final BigDecimal openAmt = payment.getC_Order().getGrandTotal().subtract(allocated);
		payment.setPayAmt(openAmt);

		return "";
	}
	
	
}

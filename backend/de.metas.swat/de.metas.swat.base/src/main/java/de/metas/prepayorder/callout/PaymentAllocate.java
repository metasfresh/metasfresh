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

import de.metas.prepayorder.interfaces.I_C_PaymentAllocate;
import de.metas.prepayorder.service.IPrepayOrderBL;

public class PaymentAllocate extends CalloutEngine
{
	
	public String prepayOrder(final Properties ctx, final int WindowNo, final GridTab mTab, final GridField mField, final Object value)
	{
		if (isCalloutActive())
		{
			return "";
		}
		final I_C_PaymentAllocate pa = InterfaceWrapperHelper.create(mTab, I_C_PaymentAllocate.class);

		if (pa.getC_PrepayOrder_ID() <= 0)
		{
			pa.setInvoiceAmt(BigDecimal.ZERO);
		}
		else
		{
			final IPrepayOrderBL prepayOrderBL = Services.get(IPrepayOrderBL.class);
			final BigDecimal allocated = prepayOrderBL.retrieveAllocatedAmt(ctx, pa.getC_PrepayOrder_ID(), null);

			final BigDecimal openAmt = pa.getC_PrepayOrder().getGrandTotal().subtract(allocated);
			pa.setInvoiceAmt(openAmt);
			pa.setAmount(openAmt);
			pa.setDiscountAmt(BigDecimal.ZERO);
		}
		return "";
	}

}

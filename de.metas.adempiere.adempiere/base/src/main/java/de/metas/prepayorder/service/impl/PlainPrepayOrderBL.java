package de.metas.prepayorder.service.impl;

/*
 * #%L
 * ADempiere ERP - Base
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

import de.metas.prepayorder.service.IPrepayOrderBL;

public class PlainPrepayOrderBL implements IPrepayOrderBL
{

	@Override
	public boolean isPrepayOrder(Properties ctx, int orderId, String trxName)
	{
		return isPrepayOrder(InterfaceWrapperHelper.create(ctx, orderId, de.metas.adempiere.model.I_C_Order.class, trxName));
	}

	@Override
	public BigDecimal retrieveAllocatedAmt(Properties ctx, int orderId, String trxName)
	{
		return BigDecimal.ZERO;
	}

	@Override
	public boolean isPrepayOrder(de.metas.adempiere.model.I_C_Order order)
	{
		// Partial implementation. Only what was needed for PaymentBLTest.
		
		if ((null == order) || (order.getC_Order_ID() <= 0))
		{
			return false;
		}
		if (!order.isSOTrx())
		{
			return false;
		}
		
		return true;
	}

}

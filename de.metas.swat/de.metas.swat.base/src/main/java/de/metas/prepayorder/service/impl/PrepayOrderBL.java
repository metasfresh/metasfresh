package de.metas.prepayorder.service.impl;

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
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.Query;
import org.compiere.util.CLogger;

import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.prepayorder.modelvalidator.PrepayOrderValidator;
import de.metas.prepayorder.service.IPrepayOrderBL;

public class PrepayOrderBL implements IPrepayOrderBL
{
	private static final CLogger logger = CLogger.getCLogger(PrepayOrderBL.class);

	@Override
	@Cached
	public boolean isPrepayOrder(
			final @CacheCtx Properties ctx,
			final int orderId,
			final @CacheTrx String trxName)
	{
		if (orderId <= 0)
		{
			logger.fine("orderId=" + orderId + "; returning false");
			return false;
		}

		return isPrepayOrder(InterfaceWrapperHelper.create(ctx, orderId, I_C_Order.class, trxName));
	}

	@Override
	public boolean isPrepayOrder(final I_C_Order order)
	{
		if (!order.isSOTrx())
		{
			logger.fine(order + " is not a sales order; returning false");
			return false;
		}

		final String soDocSubType;
		if (order.getC_DocType_ID() > 0)
		{
			soDocSubType = order.getC_DocType().getDocSubType();
		}
		else
		{
			logger.fine(order + " has no doc type yet");
			if (order.getC_DocTypeTarget_ID() > 0)
			{
				logger.fine("using C_DocTypeTarget_ID= " + order.getC_DocTypeTarget_ID() + " for " + order);
				soDocSubType = order.getC_DocTypeTarget().getDocSubType();
			}
			else
			{
				logger.fine(order + " has no doc type and no docsubtype; returning false");
				return false;
			}
		}

		return de.metas.prepayorder.model.I_C_DocType.DOCSUBTYPE_PrepayOrder_metas.equals(soDocSubType);
	}

	public BigDecimal retrieveAllocatedAmt(final Properties ctx, final int orderId, final String trxName)
	{
		final String wc = I_C_AllocationLine.COLUMNNAME_C_Order_ID + "=?";
		final String sumExpr =
				I_C_AllocationLine.COLUMNNAME_Amount + "+" + I_C_AllocationLine.COLUMNNAME_DiscountAmt + "+" + I_C_AllocationLine.COLUMNNAME_WriteOffAmt;

		final BigDecimal allocated =
				new Query(ctx, I_C_AllocationLine.Table_Name, wc, trxName)
						.setParameters(orderId)
						.setOnlyActiveRecords(true)
						.sum(sumExpr);
		return allocated;
	}

}

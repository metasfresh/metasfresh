package org.compiere.acct;

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchPO;
import org.compiere.model.MAcctSchema;
import org.compiere.model.ProductCost;
import org.compiere.model.X_M_InOut;

import de.metas.costing.CostAmount;
import de.metas.costing.CostingMethod;
import de.metas.currency.ICurrencyBL;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderLineBL;
import de.metas.quantity.Quantity;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

final class DocLine_MatchPO extends DocLine<Doc_MatchPO>
{
	private final transient ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);

	public DocLine_MatchPO(final I_M_MatchPO matchPO, final Doc_MatchPO doc)
	{
		super(InterfaceWrapperHelper.getPO(matchPO), doc);
		setDateDoc(matchPO.getDateTrx());

		final Quantity qty = Quantity.of(matchPO.getQty(), getProductStockingUOM());
		final boolean isSOTrx = false;
		setQty(qty, isSOTrx);
	}

	public CostAmount getStandardCosts(final I_C_AcctSchema as)
	{
		final ProductCost pc = getProductCost();
		final BigDecimal costs = pc.getProductCosts(as, getAD_Org_ID(), CostingMethod.StandardCosting, getC_OrderLine_ID(), false);	// non-zero costs
		return CostAmount.of(costs, as.getC_Currency_ID());
	}

	public CostAmount getPOCosts(final MAcctSchema as)
	{
		I_C_OrderLine orderLine = getOrderLine();
		final CostAmount poCostPrice = Services.get(IOrderLineBL.class).getCostPrice(orderLine);

		CostAmount poCost = poCostPrice.multiply(getQty());

		if (poCost.getCurrencyId() != as.getC_Currency_ID())
		{
			final I_C_Order order = orderLine.getC_Order();
			final BigDecimal rate = currencyConversionBL.getRate(
					poCost.getCurrencyId(), as.getC_Currency_ID(),
					order.getDateAcct(),
					order.getC_ConversionType_ID(),
					orderLine.getAD_Client_ID(),
					orderLine.getAD_Org_ID());
			if (rate == null)
			{
				throw newPostingException()
						.setC_AcctSchema(as)
						.setDetailMessage("Purchase Order not convertible");
			}
			poCost = poCost
					.multiply(rate)
					.roundToPrecisionIfNeeded(as.getCostingPrecision());
		}

		return poCost;
	}

	public I_C_OrderLine getOrderLine()
	{
		return InterfaceWrapperHelper.create(getModel(I_M_MatchPO.class).getC_OrderLine(), I_C_OrderLine.class);
	}

	public int getM_InOutLine_ID()
	{
		return getModel(I_M_MatchPO.class).getM_InOutLine_ID();
	}

	public I_M_InOutLine getReceiptLine()
	{
		return getModel(I_M_MatchPO.class).getM_InOutLine();
	}

	public boolean isReturnTrx()
	{
		final I_M_InOutLine receiptLine = getReceiptLine();
		final I_M_InOut inOut = receiptLine.getM_InOut();
		return X_M_InOut.MOVEMENTTYPE_VendorReturns.equals(inOut.getMovementType());
	}
}

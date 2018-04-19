package org.compiere.acct;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchPO;
import org.compiere.model.MAcctSchema;
import org.compiere.model.X_M_InOut;
import org.compiere.util.TimeUtil;

import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostResult;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingMethod;
import de.metas.costing.ICostingService;
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

	/** @return PO cost amount in accounting schema currency */
	public CostAmount getPOCostAmount(final MAcctSchema as)
	{
		I_C_OrderLine orderLine = getOrderLine();
		final CostAmount poCostPrice = Services.get(IOrderLineBL.class).getCostPrice(orderLine);

		final CostAmount poCost = poCostPrice.multiply(getQty());
		if (poCost.getCurrencyId() == as.getC_Currency_ID())
		{
			return poCost;
		}

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
		return poCost
				.multiply(rate)
				.roundToPrecisionIfNeeded(as.getCostingPrecision());
	}

	public CostAmount getStandardCosts(final I_C_AcctSchema as)
	{
		final ICostingService costDetailService = Adempiere.getBean(ICostingService.class);

		final CostSegment costSegment = CostSegment.builder()
				.costingLevel(getProductCostingLevel(as))
				.acctSchemaId(as.getC_AcctSchema_ID())
				.costTypeId(as.getM_CostType_ID())
				.clientId(getAD_Client_ID())
				.orgId(getAD_Org_ID())
				.productId(getM_Product_ID())
				.attributeSetInstanceId(getM_AttributeSetInstance_ID())
				.build();
		final CostAmount costPrice = costDetailService.getCurrentCosts(costSegment, CostingMethod.StandardCosting).getTotalAmount();
		return costPrice.multiply(getQty());
	}

	public CostResult createCostDetails(final I_C_AcctSchema as)
	{
		final I_M_InOutLine receiptLine = getReceiptLine();
		Check.assumeNotNull(receiptLine, "Parameter receiptLine is not null");

		final ICostingService costDetailService = Adempiere.getBean(ICostingService.class);

		final I_C_OrderLine orderLine = getOrderLine();
		final int currencyConversionTypeId = orderLine.getC_Order().getC_ConversionType_ID();
		final Timestamp receiptDateAcct = receiptLine.getM_InOut().getDateAcct();

		final Quantity qty = isReturnTrx() ? getQty().negate() : getQty();

		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
		final CostAmount costPrice = orderLineBL.getCostPrice(orderLine);
		final CostAmount amt = costPrice.multiply(qty);

		return costDetailService.createCostDetail(
				CostDetailCreateRequest.builder()
						.acctSchemaId(as.getC_AcctSchema_ID())
						.clientId(orderLine.getAD_Client_ID())
						.orgId(orderLine.getAD_Org_ID())
						.productId(getM_Product_ID())
						.attributeSetInstanceId(getM_AttributeSetInstance_ID())
						.documentRef(CostingDocumentRef.ofMatchPOId(getM_MatchPO_ID()))
						.qty(qty)
						.amt(amt)
						.currencyConversionTypeId(currencyConversionTypeId)
						.date(TimeUtil.asLocalDate(receiptDateAcct))
						.description(orderLine.getDescription())
						.build());
	}

	public I_C_OrderLine getOrderLine()
	{
		return InterfaceWrapperHelper.create(getModel(I_M_MatchPO.class).getC_OrderLine(), I_C_OrderLine.class);
	}

	public int getReceipt_InOutLine_ID()
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

	public int getM_MatchPO_ID()
	{
		return get_ID();
	}
}

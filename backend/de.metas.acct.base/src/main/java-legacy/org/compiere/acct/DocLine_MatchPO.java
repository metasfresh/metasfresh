package org.compiere.acct;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.AggregatedCostAmount;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingMethod;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRate;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.ICurrencyDAO;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.organization.OrgId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchPO;
import org.compiere.model.X_M_InOut;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;

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

	private final I_C_OrderLine orderLine;

	public DocLine_MatchPO(final I_M_MatchPO matchPO, final Doc_MatchPO doc)
	{
		super(InterfaceWrapperHelper.getPO(matchPO), doc);

		final int orderLineId = matchPO.getC_OrderLine_ID();
		orderLine = Services.get(IOrderDAO.class).getOrderLineById(orderLineId);

		setDateDoc(TimeUtil.asLocalDate(matchPO.getDateTrx()));

		final Quantity qty = Quantity.of(matchPO.getQty(), getProductStockingUOM());
		final boolean isSOTrx = false;
		setQty(qty, isSOTrx);
	}

	/** @return PO cost amount in accounting schema currency */
	CostAmount getPOCostAmount(final AcctSchema as)
	{
		final I_C_OrderLine orderLine = getOrderLine();
		final ProductPrice poCostPrice = getOrderLineCostPrice();
		final CostAmount poCost = CostAmount.multiply(poCostPrice, getQty());
		if (poCost.getCurrencyId().equals(as.getCurrencyId()))
		{
			return poCost;
		}

		final I_C_Order order = orderLine.getC_Order();
		final CurrencyRate conversionRate = currencyConversionBL.getCurrencyRate(
				poCost.getCurrencyId(),
				as.getCurrencyId(),
				TimeUtil.asLocalDate(order.getDateAcct()),
				CurrencyConversionTypeId.ofRepoIdOrNull(order.getC_ConversionType_ID()),
				ClientId.ofRepoId(orderLine.getAD_Client_ID()),
				OrgId.ofRepoId(orderLine.getAD_Org_ID()));

		return poCost
				.multiply(conversionRate.getConversionRate())
				.roundToPrecisionIfNeeded(as.getCosting().getCostingPrecision());
	}

	CostAmount getStandardCosts(final AcctSchema acctSchema)
	{
		final CostSegment costSegment = CostSegment.builder()
				.costingLevel(getProductCostingLevel(acctSchema))
				.acctSchemaId(acctSchema.getId())
				.costTypeId(acctSchema.getCosting().getCostTypeId())
				.clientId(getClientId())
				.orgId(getOrgId())
				.productId(getProductId())
				.attributeSetInstanceId(getAttributeSetInstanceId())
				.build();

		final CostPrice costPrice = services.getCurrentCostPrice(costSegment, CostingMethod.StandardCosting)
				.orElseThrow(() -> newPostingException()
						.setAcctSchema(acctSchema)
						.setDetailMessage("No standard costs found for " + costSegment));

		return costPrice.multiply(getQty());
	}

	AggregatedCostAmount createCostDetails(final AcctSchema as)
	{
		final I_M_InOutLine receiptLine = getReceiptLine();
		Check.assumeNotNull(receiptLine, "Parameter receiptLine is not null");

		final I_C_OrderLine orderLine = getOrderLine();
		final CurrencyConversionTypeId currencyConversionTypeId = CurrencyConversionTypeId.ofRepoIdOrNull(orderLine.getC_Order().getC_ConversionType_ID());
		final Timestamp receiptDateAcct = receiptLine.getM_InOut().getDateAcct();

		final Quantity qty = isReturnTrx() ? getQty().negate() : getQty();

		final ProductPrice costPrice = getOrderLineCostPrice();
		final CostAmount amt = CostAmount.multiply(costPrice, qty);

		final AcctSchemaId acctSchemaId = as.getId();

		return services.createCostDetail(
				CostDetailCreateRequest.builder()
						.acctSchemaId(acctSchemaId)
						.clientId(ClientId.ofRepoId(orderLine.getAD_Client_ID()))
						.orgId(OrgId.ofRepoId(orderLine.getAD_Org_ID()))
						.productId(getProductId())
						.attributeSetInstanceId(getAttributeSetInstanceId())
						.documentRef(CostingDocumentRef.ofMatchPOId(getM_MatchPO_ID()))
						.qty(qty)
						.amt(amt)
						.currencyConversionTypeId(currencyConversionTypeId)
						.date(TimeUtil.asLocalDate(receiptDateAcct))
						.description(orderLine.getDescription())
						.build());
	}

	I_C_OrderLine getOrderLine()
	{
		return orderLine;
	}

	private ProductPrice getOrderLineCostPrice()
	{
		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
		final ICurrencyDAO currenciesRepo = Services.get(ICurrencyDAO.class);
		final IUOMConversionBL uomConversionsBL = Services.get(IUOMConversionBL.class);

		final I_C_OrderLine orderLine = getOrderLine();
		final ProductPrice costPrice = orderLineBL.getCostPrice(orderLine);

		final CurrencyPrecision precision = currenciesRepo.getCostingPrecision(costPrice.getCurrencyId());
		return uomConversionsBL.convertProductPriceToUom(costPrice, getProductStockingUOMId(), precision);
	}

	int getReceipt_InOutLine_ID()
	{
		return getModel(I_M_MatchPO.class).getM_InOutLine_ID();
	}

	I_M_InOutLine getReceiptLine()
	{
		return getModel(I_M_MatchPO.class).getM_InOutLine();
	}

	boolean isReturnTrx()
	{
		final I_M_InOutLine receiptLine = getReceiptLine();
		final I_M_InOut inOut = receiptLine.getM_InOut();
		return X_M_InOut.MOVEMENTTYPE_VendorReturns.equals(inOut.getMovementType());
	}

	private int getM_MatchPO_ID()
	{
		return get_ID();
	}
}

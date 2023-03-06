package org.compiere.acct;

import de.metas.acct.api.AcctSchema;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingMethod;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyConversionResult;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyBL;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLineId;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchPO;
import org.compiere.model.X_M_InOut;

import java.sql.Timestamp;
import java.time.Instant;

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
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IUOMConversionBL uomConversionsBL = Services.get(IUOMConversionBL.class);

	private final I_C_OrderLine orderLine;
	private final I_M_InOutLine _receiptLine;
	private final I_M_InOut _receipt;
	private final CurrencyConversionContext _currencyConversionContext;

	DocLine_MatchPO(final I_M_MatchPO matchPO, final Doc_MatchPO doc)
	{
		super(InterfaceWrapperHelper.getPO(matchPO), doc);

		IOrderDAO orderDAO = Services.get(IOrderDAO.class);
		final OrderLineId orderLineId = OrderLineId.ofRepoId(matchPO.getC_OrderLine_ID());
		this.orderLine = orderDAO.getOrderLineById(orderLineId);

		IInOutBL inoutBL = Services.get(IInOutBL.class);
		final InOutLineId receiptLineId = InOutLineId.ofRepoId(matchPO.getM_InOutLine_ID());
		this._receiptLine = inoutBL.getLineByIdInTrx(receiptLineId);
		this._receipt = inoutBL.getById(InOutId.ofRepoId(_receiptLine.getM_InOut_ID()));
		this._currencyConversionContext = inoutBL.getCurrencyConversionContext(_receipt);

		setDateDoc(LocalDateAndOrgId.ofTimestamp(
				matchPO.getDateTrx(),
				OrgId.ofRepoId(matchPO.getAD_Org_ID()),
				doc.getServices()::getTimeZone));

		final Quantity qty = Quantity.of(matchPO.getQty(), getProductStockingUOM());
		final boolean isSOTrx = false;
		setQty(qty, isSOTrx);
	}

	CostAmount getPOCostAmountInAcctCurrency(final AcctSchema acctSchema)
	{
		final CostAmount poCostAmt = CostAmount.multiply(getOrderLineCostPriceInStockingUOM(), getQty());
		return convertToAcctCurrency(poCostAmt, acctSchema);
	}

	private CostAmount convertToAcctCurrency(final CostAmount amt, final AcctSchema acctSchema)
	{
		if (amt.getCurrencyId().equals(acctSchema.getCurrencyId()))
		{
			return amt;
		}

		final CurrencyConversionResult poCostConv = currencyBL.convert(
				getCurrencyConversionContext().withPrecision(acctSchema.getCosting().getCostingPrecision()),
				amt.toMoney(),
				acctSchema.getCurrencyId());

		return CostAmount.of(poCostConv.getAmount(), acctSchema.getCurrencyId());
	}

	private ProductPrice getOrderLineCostPriceInStockingUOM()
	{
		final I_C_OrderLine orderLine = getOrderLine();
		final ProductPrice costPrice = orderLineBL.getCostPrice(orderLine);

		final CurrencyPrecision precision = currencyBL.getCostingPrecision(costPrice.getCurrencyId());
		return uomConversionsBL.convertProductPriceToUom(costPrice, getProductStockingUOMId(), precision);
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

	void createCostDetails(final AcctSchema as)
	{
		final I_M_InOut receipt = getReceipt();
		final Quantity qty = isReturnTrx() ? getQty().negate() : getQty();
		final CostAmount amt = CostAmount.multiply(getOrderLineCostPriceInStockingUOM(), qty);

		// NOTE: there is no need to fail if no cost details were created because:
		// * not all costing methods are creating cost details for MatchPO
		// * we are not using the result of cost details
		services.createCostDetailOrEmpty(
				CostDetailCreateRequest.builder()
						.acctSchemaId(as.getId())
						.clientId(ClientId.ofRepoId(receipt.getAD_Client_ID()))
						.orgId(OrgId.ofRepoId(receipt.getAD_Org_ID()))
						.productId(getProductId())
						.attributeSetInstanceId(getAttributeSetInstanceId())
						.documentRef(CostingDocumentRef.ofMatchPOId(getM_MatchPO_ID()))
						.qty(qty)
						.amt(amt)
						.currencyConversionContext(getCurrencyConversionContext())
						.date(getReceiptDateAcct())
						.description(getOrderLine().getDescription())
						.build());
	}

	I_C_OrderLine getOrderLine()
	{
		return orderLine;
	}

	I_M_InOutLine getReceiptLine()
	{
		return this._receiptLine;
	}

	I_M_InOut getReceipt()
	{
		return this._receipt;
	}

	private CurrencyConversionContext getCurrencyConversionContext()
	{
		return this._currencyConversionContext;
	}

	Instant getReceiptDateAcct()
	{
		final I_M_InOut receipt = getReceipt();
		final Timestamp receiptDateAcct = receipt.getDateAcct();
		return receiptDateAcct.toInstant();
	}

	boolean isReturnTrx()
	{
		final I_M_InOut receipt = getReceipt();
		return X_M_InOut.MOVEMENTTYPE_VendorReturns.equals(receipt.getMovementType());
	}

	private int getM_MatchPO_ID()
	{
		return get_ID();
	}
}

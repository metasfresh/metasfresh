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
import de.metas.i18n.ExplainedOptional;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.material.MovementType;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.MatchPOId;
import de.metas.order.OrderLineId;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchPO;

import javax.annotation.Nullable;
import java.util.Optional;

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

	@NonNull private final I_C_OrderLine orderLine;

	@Nullable private final I_M_InOutLine _receiptLine;
	@Nullable private final I_M_InOut _receipt;
	@NonNull private final ExplainedOptional<CurrencyConversionContext> _currencyConversionContext;

	DocLine_MatchPO(@NonNull final I_M_MatchPO matchPO, @NonNull final Doc_MatchPO doc)
	{
		super(InterfaceWrapperHelper.getPO(matchPO), doc);

		final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
		final OrderLineId orderLineId = OrderLineId.ofRepoId(matchPO.getC_OrderLine_ID());
		this.orderLine = orderDAO.getOrderLineById(orderLineId);

		final InOutLineId receiptLineId = InOutLineId.ofRepoIdOrNull(matchPO.getM_InOutLine_ID());
		if (receiptLineId == null)
		{
			// throw newPostingException()
			// 		.setDetailMessage("MatchPO cannot be posted because receipt line is not set yet")
			// 		.setPreserveDocumentPostedStatus();
			this._receiptLine = null;
			this._receipt = null;
			this._currencyConversionContext = ExplainedOptional.emptyBecause("No currency conversion context because receipt line is not set yet");
		}
		else
		{
			final IInOutBL inoutBL = Services.get(IInOutBL.class);
			this._receiptLine = inoutBL.getLineByIdInTrx(receiptLineId);
			this._receipt = inoutBL.getById(InOutId.ofRepoId(_receiptLine.getM_InOut_ID()));
			this._currencyConversionContext = ExplainedOptional.of(inoutBL.getCurrencyConversionContext(_receipt));
		}

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
		final I_M_InOut receipt = getReceipt().orElse(null);
		if (receipt == null)
		{
			// in case we are dealing with M_MatchPO without receipt line (only with invoice line set),
			// there no costs (nor facts) to be created
			return;
		}

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
						.date(receipt.getDateAcct().toInstant())
						.description(getOrderLine().getDescription())
						.build());
	}

	boolean isReceivingInDifferentOrgThanPurchaseOrder()
	{
		final I_M_InOutLine receiptLine = getReceiptLine().orElse(null);
		final I_C_OrderLine orderLine = getOrderLine();
		return receiptLine != null
				&& receiptLine.getAD_Org_ID() != orderLine.getAD_Org_ID();
	}

	@NonNull
	I_C_OrderLine getOrderLine()
	{
		return orderLine;
	}

	boolean isNoReceiptLineSet() {return !getReceiptLine().isPresent();}

	@NonNull
	private Optional<I_M_InOutLine> getReceiptLine() {return Optional.ofNullable(this._receiptLine);}

	@NonNull
	private Optional<I_M_InOut> getReceipt() {return Optional.ofNullable(this._receipt);}

	@NonNull
	private CurrencyConversionContext getCurrencyConversionContext()
	{
		return _currencyConversionContext.orElseThrow();
	}

	boolean isReturnTrx()
	{
		final I_M_InOut receipt = getReceipt().orElse(null);
		return receipt != null && MovementType.ofCode(receipt.getMovementType()).isMaterialReturn();
	}

	private MatchPOId getM_MatchPO_ID()
	{
		return MatchPOId.ofRepoId(get_ID());
	}
}

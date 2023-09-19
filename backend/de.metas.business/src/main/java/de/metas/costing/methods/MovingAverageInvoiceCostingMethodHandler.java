/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.costing.methods;

import de.metas.acct.api.AcctSchemaId;
import de.metas.common.util.Check;
import de.metas.costing.AggregatedCostAmount;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostDetailPreviousAmounts;
import de.metas.costing.CostDetailVoidRequest;
import de.metas.costing.CostElement;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.MoveCostsRequest;
import de.metas.costing.MoveCostsResult;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyPrecision;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.matchinv.MatchInv;
import de.metas.invoice.matchinv.MatchInvId;
import de.metas.invoice.matchinv.MatchInvType;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.Money;
import de.metas.order.IOrderLineBL;
import de.metas.order.costs.OrderCostService;
import de.metas.order.costs.inout.InOutCost;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MovingAverageInvoiceCostingMethodHandler extends CostingMethodHandlerTemplate
{
	private final MatchInvoiceService matchInvoiceService;
	private final OrderCostService orderCostService;
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IInOutBL inoutBL = Services.get(IInOutBL.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

	public MovingAverageInvoiceCostingMethodHandler(
			@NonNull final CostingMethodHandlerUtils utils,
			@NonNull final MatchInvoiceService matchInvoiceService,
			@NonNull final OrderCostService orderCostService)
	{
		super(utils);
		this.matchInvoiceService = matchInvoiceService;
		this.orderCostService = orderCostService;
	}

	@Override
	public CostingMethod getCostingMethod()
	{
		return CostingMethod.MovingAverageInvoice;
	}

	@Override
	protected CostDetailCreateResult createCostForMatchPO(final CostDetailCreateRequest request)
	{
		return createCostDetailAndAdjustCurrentCosts(request);
	}

	@Override
	protected CostDetailCreateResult createCostForMatchInvoice_MaterialCosts(final CostDetailCreateRequest request) {return createCostForMatchInvoice(request);}

	@Override
	protected CostDetailCreateResult createCostForMatchInvoice_NonMaterialCosts(final CostDetailCreateRequest request) {return createCostForMatchInvoice(request);}

	private CostDetailCreateResult createCostForMatchInvoice(final CostDetailCreateRequest request)
	{
		final CurrentCost currentCost = utils.getCurrentCost(request);

		final CostAmountDetailed costAmountDetailed = computeCostAmountDetailedForMatchInv(request);

		final CostDetailCreateResult mainResult = utils.createCostDetailRecordNoCostsChanged(
				request.withAmountAndType(costAmountDetailed.getMainAmt(), CostAmountType.MAIN),
				CostDetailPreviousAmounts.of(currentCost));

		CostAmountDetailed amtDetailed = mainResult.getAmt();

		if (!costAmountDetailed.getCostAdjustmentAmt().isZero())
		{
			final CostDetailCreateResult adjustmentResult = utils.createCostDetailRecordWithChangedCosts(
					request.withAmountAndType(costAmountDetailed.getCostAdjustmentAmt(), CostAmountType.ADJUSTMENT).withQtyZero(),
					CostDetailPreviousAmounts.of(currentCost));

			currentCost.addWeightedAverage(costAmountDetailed.getCostAdjustmentAmt(), request.getQty().toZero(), utils.getQuantityUOMConverter());
			utils.saveCurrentCost(currentCost);

			amtDetailed = amtDetailed.add(adjustmentResult.getAmt());
		}

		if (!costAmountDetailed.getAlreadyShippedAmt().isZero())
		{
			final CostDetailCreateResult alreadyShippedResult = utils.createCostDetailRecordNoCostsChanged(
					request.withAmountAndType(costAmountDetailed.getAlreadyShippedAmt(), CostAmountType.ALREADY_SHIPPED).withQtyZero(),
					CostDetailPreviousAmounts.of(currentCost));
			amtDetailed = amtDetailed.add(alreadyShippedResult.getAmt());
		}

		return mainResult.withAmt(amtDetailed);
	}

	@Override
	protected CostDetailCreateResult createCostForMaterialReceipt(final CostDetailCreateRequest request)
	{
		final CurrentCost currentCost = utils.getCurrentCost(request);

		final InOutLineId receipLineId = request.getDocumentRef().getId(InOutLineId.class);
		final I_M_InOutLine receiptLine = inoutBL.getLineByIdInTrx(receipLineId);
		final I_C_OrderLine orderLine = receiptLine.getC_OrderLine();
		final CostAmount amtConv;
		if (orderLine != null)
		{
			final InOutId receiptId = InOutId.ofRepoId(receiptLine.getM_InOut_ID());
			final CurrencyConversionContext currencyConversionContext = inoutBL.getCurrencyConversionContext(receiptId);
			amtConv = getCostAmountInAcctCurrency(orderLine, request.getQty(), request.getAcctSchemaId(), currencyConversionContext);
		}
		else
		{
			final CostAmount currentCostPrice = currentCost.getCostPrice().toCostAmount();
			final CostAmount amt = currentCostPrice.multiply(request.getQty());
			// NOTE: expect conversion to do nothing because the current cost price shall already be in accounting currency
			amtConv = utils.convertToAcctSchemaCurrency(amt, request);
		}

		return utils.createCostDetailRecordNoCostsChanged(
				request.withAmount(amtConv),
				CostDetailPreviousAmounts.of(currentCost));
	}

	@Override
	protected CostDetailCreateResult createCostForMaterialReceipt_NonMaterialCosts(final CostDetailCreateRequest request)
	{
		final CurrentCost currentCost = utils.getCurrentCost(request);

		currentCost.addWeightedAverage(request.getAmt(), request.getQty(), utils.getQuantityUOMConverter());

		final CostDetailCreateResult result = utils.createCostDetailRecordWithChangedCosts(
				request,
				CostDetailPreviousAmounts.of(currentCost));

		utils.saveCurrentCost(currentCost);

		return result;
	}

	@Override
	protected CostDetailCreateResult createOutboundCostDefaultImpl(final CostDetailCreateRequest request)
	{
		return createCostDetailAndAdjustCurrentCosts(request);
	}

	private CostDetailCreateResult createCostDetailAndAdjustCurrentCosts(final CostDetailCreateRequest request)
	{
		final CostAmount explicitCostPrice = request.getExplicitCostPrice();

		final CurrentCost currentCosts = utils.getCurrentCost(request);
		final CostDetailPreviousAmounts previousCosts = CostDetailPreviousAmounts.of(currentCosts);
		final CostPrice currentCostPrice = currentCosts.getCostPrice();

		final Quantity requestQty = request.getQty();
		final Quantity qty = utils.convertToUOM(requestQty, currentCostPrice.getUomId(), request.getProductId());

		final boolean isInboundTrx = requestQty.signum() >= 0;

		final CostDetailCreateRequest requestEffective;

		//
		// Inbound transactions (qty >= 0)
		// or Reversals
		if (isInboundTrx || request.isReversal())
		{
			// Seed/initial costs import
			final CostAmount requestAmt = request.getAmt();

			if (request.getDocumentRef().isInventoryLine())
			{
				final CostAmount effectiveAmt = explicitCostPrice != null
						? explicitCostPrice.multiply(qty).roundToPrecisionIfNeeded(currentCosts.getPrecision())
						: currentCosts.getCostPrice().multiply(qty).roundToPrecisionIfNeeded(currentCosts.getPrecision());

				requestEffective = request.withAmount(effectiveAmt);

				if (explicitCostPrice != null && currentCosts.getCurrentQty().isZero())
				{
					currentCosts.setOwnCostPrice(explicitCostPrice);
				}
				else
				{
					// Do not change an existing positive cost price if there is also a positive qty
				}
			}

			// In case the amount was not provided but there is a positive qty incoming
			// use the current cost price to calculate the amount.
			// In case of reversals, always consider the Amt.

			else
			{
				if (requestAmt.isZero() && !request.isReversal())
				{
					final CostAmount amt = currentCostPrice.multiply(qty).roundToPrecisionIfNeeded(currentCosts.getPrecision());
					requestEffective = request.withAmountAndQty(amt, qty);
				}
				else
				{
					requestEffective = request.withQty(qty);
				}
			}

			currentCosts.addWeightedAverage(requestEffective.getAmt(), requestEffective.getQty(), utils.getQuantityUOMConverter());
		}

		//
		// Outbound transactions (qty < 0)
		else
		{
			final CostPrice price = currentCosts.getCostPrice();
			final CostAmount amt = price.multiply(qty).roundToPrecisionIfNeeded(currentCosts.getPrecision());
			requestEffective = request.withAmountAndQty(amt, qty);

			currentCosts.addToCurrentQtyAndCumulate(qty, amt);
		}

		final CostDetailCreateResult result = utils.createCostDetailRecordWithChangedCosts(
				requestEffective,
				previousCosts);

		utils.saveCurrentCost(currentCosts);

		return result;
	}

	@Override
	public MoveCostsResult createMovementCosts(@NonNull final MoveCostsRequest request)
	{
		final CostElement costElement = request.getCostElement();
		if (costElement == null)
		{
			throw new AdempiereException("No cost element: " + request);
		}

		final CostSegmentAndElement outboundSegmentAndElement = utils.extractOutboundCostSegmentAndElement(request);
		final CostSegmentAndElement inboundSegmentAndElement = utils.extractInboundCostSegmentAndElement(request);

		final CurrentCost outboundCurrentCosts = utils.getCurrentCost(outboundSegmentAndElement);
		final CostDetailPreviousAmounts outboundPreviousCosts = CostDetailPreviousAmounts.of(outboundCurrentCosts);
		final CostPrice currentCostPrice = outboundCurrentCosts.getCostPrice();
		final Quantity outboundQty = utils.convertToUOM(
				request.getQtyToMove().negate(),
				currentCostPrice.getUomId(),
				request.getProductId());
		final CostAmount outboundAmt = currentCostPrice.multiply(outboundQty).roundToPrecisionIfNeeded(outboundCurrentCosts.getPrecision());

		final CostDetailCreateRequest outboundCostDetailRequest = CostDetailCreateRequest.builder()
				.acctSchemaId(request.getAcctSchemaId())
				.clientId(request.getClientId())
				.orgId(request.getOutboundOrgId())
				.productId(request.getProductId())
				.attributeSetInstanceId(request.getAttributeSetInstanceId())
				.documentRef(request.getOutboundDocumentRef())
				.costElement(costElement)
				.amt(outboundAmt)
				.qty(outboundQty)
				.date(request.getDate())
				.build();
		final CostDetailCreateRequest inboundCostDetailRequest = CostDetailCreateRequest.builder()
				.acctSchemaId(request.getAcctSchemaId())
				.clientId(request.getClientId())
				.orgId(request.getInboundOrgId())
				.productId(request.getProductId())
				.attributeSetInstanceId(request.getAttributeSetInstanceId())
				.documentRef(request.getInboundDocumentRef())
				.costElement(costElement)
				.amt(outboundAmt.negate())
				.qty(outboundQty.negate())
				.date(request.getDate())
				.build();

		//
		// Moving costs inside costing segment
		// => no changes, just record the cost details
		final CostDetailCreateResult outboundResult;
		final CostDetailCreateResult inboundResult;
		if (Objects.equals(outboundSegmentAndElement, inboundSegmentAndElement))
		{
			final CostDetailPreviousAmounts inboundPreviousCosts = outboundPreviousCosts;

			outboundResult = utils.createCostDetailRecordNoCostsChanged(outboundCostDetailRequest, outboundPreviousCosts);
			inboundResult = utils.createCostDetailRecordNoCostsChanged(inboundCostDetailRequest, inboundPreviousCosts);
		}
		//
		// Moving costs between costing segments
		// => change current costs, record the cost details
		else
		{
			outboundResult = utils.createCostDetailRecordWithChangedCosts(
					outboundCostDetailRequest,
					outboundPreviousCosts);

			outboundCurrentCosts.addToCurrentQtyAndCumulate(outboundQty, outboundAmt, utils.getQuantityUOMConverter());
			utils.saveCurrentCost(outboundCurrentCosts);

			// Inbound cost
			final CurrentCost inboundCurrentCosts = utils.getCurrentCost(inboundSegmentAndElement);
			final CostDetailPreviousAmounts inboundPreviousCosts = CostDetailPreviousAmounts.of(inboundCurrentCosts);
			inboundResult = utils.createCostDetailRecordWithChangedCosts(
					inboundCostDetailRequest,
					inboundPreviousCosts);

			inboundCurrentCosts.addWeightedAverage(
					inboundCostDetailRequest.getAmt(),
					inboundCostDetailRequest.getQty(),
					utils.getQuantityUOMConverter());
			utils.saveCurrentCost(inboundCurrentCosts);
		}

		return MoveCostsResult.builder()
				.outboundCosts(AggregatedCostAmount.builder()
						.costSegment(outboundSegmentAndElement.toCostSegment())
						.amount(costElement, outboundResult.getAmt())
						.build())
				.inboundCosts(AggregatedCostAmount.builder()
						.costSegment(inboundSegmentAndElement.toCostSegment())
						.amount(costElement, inboundResult.getAmt())
						.build())
				.build();
	}

	private CostAmountDetailed computeCostAmountDetailedForMatchInv(final CostDetailCreateRequest request)
	{
		final MatchInv matchInv = matchInvoiceService.getById(request.getDocumentRef().getId(MatchInvId.class));

		final CurrentCost currentCost = utils.getCurrentCost(request);

		final InvoiceId invoiceId = matchInv.getInvoiceId();
		final boolean isReversal = invoiceBL.isReversal(invoiceId);

		final Quantity receiptQty = request.getQty().negateIf(isReversal); // i.e. qty matched
		final CostAmount receiptAmt = getReceiptAmount(matchInv, receiptQty, request.getCostElement(), request.getAcctSchemaId(), currentCost.getPrecision());
		final CostAmount invoicedAmt = request.getAmt().negateIf(isReversal);
		final CostAmount amtDifference = invoicedAmt.subtract(receiptAmt);

		final CostAmount costAdjustmentAmt;
		final CostAmount alreadyShippedAmt;

		final Quantity qtyStillInStock = currentCost.getCurrentQty().min(receiptQty);
		if (amtDifference.isZero())
		{
			costAdjustmentAmt = CostAmount.zero(currentCost.getCurrencyId());
			alreadyShippedAmt = CostAmount.zero(currentCost.getCurrencyId());
		}
		else if (receiptQty.isZero())
		{
			costAdjustmentAmt = CostAmount.zero(currentCost.getCurrencyId());
			alreadyShippedAmt = amtDifference;
		}
		else if (receiptQty.equalsIgnoreSource(qtyStillInStock))
		{
			costAdjustmentAmt = amtDifference;
			alreadyShippedAmt = CostAmount.zero(currentCost.getCurrencyId());
		}
		else
		{
			final CostAmount priceDifference = amtDifference.isZero() || receiptQty.isZero()
					? CostAmount.zero(currentCost.getCurrencyId())
					: amtDifference.divide(receiptQty, currentCost.getPrecision());

			costAdjustmentAmt = priceDifference.multiply(qtyStillInStock);
			alreadyShippedAmt = amtDifference.subtract(costAdjustmentAmt);
		}

		return CostAmountDetailed.builder()
				.mainAmt(invoicedAmt)
				.costAdjustmentAmt(costAdjustmentAmt)
				.alreadyShippedAmt(alreadyShippedAmt)
				.build()
				.negateIf(isReversal);
	}

	private CostAmount getReceiptAmount(
			@NonNull final MatchInv matchInv,
			@NonNull final Quantity receiptQty,
			@NonNull final CostElement costElement,
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CurrencyPrecision precision)
	{
		final CurrencyConversionContext currencyConversionContext = inoutBL.getCurrencyConversionContext(matchInv.getInOutId());

		final MatchInvType type = matchInv.getType();
		if (type.isMaterial())
		{
			Check.assume(costElement.isMaterialElement(), "Cost Element shall be material: {}", costElement);

			final I_C_OrderLine orderLine = matchInvoiceService.getOrderLineId(matchInv)
					.map(orderLineBL::getOrderLineById)
					.orElseThrow(() -> new AdempiereException("Cannot determine order line for " + matchInv));

			return getCostAmountInAcctCurrency(orderLine, receiptQty, acctSchemaId, currencyConversionContext);
		}
		else if (type.isCost())
		{
			final InOutCost inoutCost = orderCostService.getInOutCostsById(matchInv.getCostPartNotNull().getInoutCostId());
			Check.assumeEquals(inoutCost.getCostElementId(), costElement.getId(), "Cost Element shall match: {}, {}", inoutCost, costElement);

			final Money receiptAmount = inoutCost.getCostAmountForQty(receiptQty, precision);

			return utils.convertToAcctSchemaCurrency(
					CostAmount.ofMoney(receiptAmount),
					() -> currencyConversionContext,
					acctSchemaId);
		}
		else
		{
			throw new AdempiereException("Unknown type: " + type);
		}
	}

	@Override
	public void voidCosts(final CostDetailVoidRequest request)
	{
		final Quantity qty = request.getQty();
		final boolean isInboundTrx = qty.signum() > 0;
		final CurrentCost currentCosts = utils.getCurrentCost(request.getCostSegmentAndElement());

		final CostAmount negateAmount = request.getAmt().negate();
		if (isInboundTrx)
		{
			currentCosts.addWeightedAverage(negateAmount, qty.negate(), utils.getQuantityUOMConverter());
		}
		else
		{
			currentCosts.addToCurrentQtyAndCumulate(qty.negate(), negateAmount, utils.getQuantityUOMConverter());
		}

		utils.saveCurrentCost(currentCosts);
	}

	@NonNull
	private CostAmount getCostAmountInAcctCurrency(
			@NonNull final I_C_OrderLine orderLine,
			@NonNull final Quantity qty,
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CurrencyConversionContext currencyConversionContext)
	{
		final CostAmount amt = getCostAmountInSourceCurrency(orderLine, qty);

		return utils.convertToAcctSchemaCurrency(
				amt,
				() -> currencyConversionContext,
				acctSchemaId);
	}

	@NonNull
	private CostAmount getCostAmountInSourceCurrency(@NonNull final I_C_OrderLine orderLine, @NonNull final Quantity qty)
	{
		final ProductPrice costPriceConv = utils.convertToUOM(
				orderLineBL.getCostPrice(orderLine),
				qty.getUomId());

		return CostAmount.ofProductPrice(costPriceConv).multiply(qty);
	}
}
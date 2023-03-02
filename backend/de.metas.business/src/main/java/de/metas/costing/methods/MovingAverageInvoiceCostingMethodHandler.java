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
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.invoice.matchinv.MatchInv;
import de.metas.invoice.matchinv.MatchInvId;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLineId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class MovingAverageInvoiceCostingMethodHandler extends CostingMethodHandlerTemplate
{
	private final MatchInvoiceService matchInvoiceService;
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IInOutBL inoutBL = Services.get(IInOutBL.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

	public MovingAverageInvoiceCostingMethodHandler(
			@NonNull final CostingMethodHandlerUtils utils,
			@NonNull final MatchInvoiceService matchInvoiceService)
	{
		super(utils);
		this.matchInvoiceService = matchInvoiceService;
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
	protected CostDetailCreateResult createCostForMatchInvoice(final CostDetailCreateRequest request)
	{
		final CurrentCost currentCost = utils.getCurrentCost(request);

		final CostAmountDetailed costAmountDetailed = computeCostAmountDetailedForMatchInv(request);

		return utils.createCostDetailRecordWithChangedCosts(
				request.withAmount(costAmountDetailed),
				CostDetailPreviousAmounts.of(currentCost));

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
			CurrencyConversionContext currencyConversionContext = inoutBL.getCurrencyConversionContext(receiptId);
			amtConv = getCostAmountInAcctCurrency(orderLine, request.getQty(), request.getAcctSchemaId(), currencyConversionContext);
		}
		else
		{
			final CostAmount currentCostPrice = currentCost.getCostPrice().toCostAmount();
			final CostAmount amt = currentCostPrice.multiply(request.getQty());
			// NOTE: expect conversion to do nothing because the current cost price shall already be in accounting currency
			amtConv = utils.convertToAcctSchemaCurrency(amt, request);
		}

		final CostAmountDetailed costAmountDetailed = CostAmountDetailed.builder()
				.mainAmt(amtConv)
				.build();

		return utils.createCostDetailRecordNoCostsChanged(
				request.withAmount(costAmountDetailed),
				CostDetailPreviousAmounts.of(currentCost));
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
			final CostAmountDetailed requestAmt = request.getAmt();

			if (request.getDocumentRef().isInventoryLine())
			{
				final CostAmount effectiveAmt = explicitCostPrice != null
						? explicitCostPrice.multiply(qty).roundToPrecisionIfNeeded(currentCosts.getPrecision())
						: currentCosts.getCostPrice().multiply(qty).roundToPrecisionIfNeeded(currentCosts.getPrecision());

				final CostAmountDetailed effectiveAmountDetailed = CostAmountDetailed.builder()
						.mainAmt(effectiveAmt)
						.build();

				requestEffective = request.withAmount(effectiveAmountDetailed);

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
				if (requestAmt.getMainAmt().isZero() && !request.isReversal())
				{
					final CostAmount amt = currentCostPrice.multiply(qty).roundToPrecisionIfNeeded(currentCosts.getPrecision());
					requestEffective = request.withAmountAndQty(CostAmountDetailed.builder().mainAmt(amt).build(), qty);
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
			final CostAmountDetailed costAmountDetailed = CostAmountDetailed.builder().mainAmt(amt).build();
			requestEffective = request.withAmountAndQty(costAmountDetailed, qty);

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
				.amt(CostAmountDetailed.builder().mainAmt(outboundAmt).build())
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
				.amt(CostAmountDetailed.builder().mainAmt(outboundAmt.negate()).build())
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

		final de.metas.adempiere.model.I_C_InvoiceLine invoiceLine = invoiceBL.getLineById(matchInv.getInvoiceLineId());

		final I_C_OrderLine orderLine = Optional.of(invoiceLine.getC_OrderLine())
				.orElseThrow(() -> new AdempiereException("Cannot determine order line for " + matchInv.getId()));

		final Quantity receiptQty = inoutBL.retrieveCompleteOrClosedLinesForOrderLine(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()))
				.stream()
				.map(line -> Quantitys.create(line.getMovementQty(), UomId.ofRepoId(line.getC_UOM_ID())))
				.reduce(Quantity::add)
				.orElse(Quantitys.createZero(UomId.ofRepoId(orderLine.getC_UOM_ID())));

		final ProductPrice purchaseOrderPrice = orderLineBL.getCostPrice(orderLine);

		final CostAmount merchandiseStock = CostAmount.ofProductPrice(purchaseOrderPrice).multiply(receiptQty.toBigDecimal()).roundToPrecisionIfNeeded(currentCost.getPrecision());

		final CostAmount invoicedAmt = request.getAmt().getMainAmt();

		final CostAmount amtDifference = merchandiseStock.subtract(invoicedAmt);

		final CostAmount adjustmentProportion = amtDifference.isZero() || receiptQty.isZero()
				? CostAmount.zero(currentCost.getCurrencyId())
				: amtDifference.divide(receiptQty.toBigDecimal(), currentCost.getPrecision())
				.roundToPrecisionIfNeeded(currentCost.getPrecision());

		final Quantity qtyToDistribute = currentCost.getCumulatedQty().min(receiptQty);

		final CostAmount costAdjustmentAmt = adjustmentProportion.multiply(qtyToDistribute.toBigDecimal());

		final CostAmount alreadyShippedAmt = amtDifference.subtract(costAdjustmentAmt);

		return CostAmountDetailed.builder()
				.mainAmt(invoicedAmt)
				.costAdjustmentAmt(costAdjustmentAmt)
				.alreadyShippedAmt(alreadyShippedAmt)
				.build();
	}

	@Override
	public void voidCosts(final CostDetailVoidRequest request)
	{
		final Quantity qty = request.getQty();
		final boolean isInboundTrx = qty.signum() > 0;
		final CurrentCost currentCosts = utils.getCurrentCost(request.getCostSegmentAndElement());

		final CostAmountDetailed negateAmount = CostAmountDetailed.builder().mainAmt(request.getAmt().negate()).build();
		if (isInboundTrx)
		{
			currentCosts.addWeightedAverage(negateAmount, qty.negate(), utils.getQuantityUOMConverter());
		}
		else
		{
			currentCosts.addToCurrentQtyAndCumulate(qty.negate(), negateAmount.getMainAmt(), utils.getQuantityUOMConverter());
		}

		utils.saveCurrentCost(currentCosts);
	}

	private CostAmount getCostAmountInAcctCurrency(
			@NonNull final I_C_OrderLine orderLine,
			@NonNull final Quantity qty,
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CurrencyConversionContext currencyConversionContext)
	{
		final ProductPrice costPriceConv = utils.convertToUOM(
				orderLineBL.getCostPrice(orderLine),
				qty.getUomId());

		final CostAmount amt = CostAmount.ofProductPrice(costPriceConv).multiply(qty);

		return utils.convertToAcctSchemaCurrency(
				amt,
				() -> currencyConversionContext,
				acctSchemaId);
	}
}
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
import de.metas.common.util.CoalesceUtil;
import de.metas.costing.AggregatedCostAmount;
import de.metas.costing.CostAmount;
import de.metas.costing.CostAmountAndQty;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostDetailCreateResultsList;
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
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderLineBL;
import de.metas.order.costs.OrderCostService;
import de.metas.order.costs.inout.InOutCost;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
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

		CostAmountAndQtyDetailed amtAndQty = mainResult.getAmtAndQty();

		if (!costAmountDetailed.getCostAdjustmentAmt().isZero())
		{
			final CostDetailCreateResult adjustmentResult = utils.createCostDetailRecordWithChangedCosts(
					request.withAmountAndType(costAmountDetailed.getCostAdjustmentAmt(), CostAmountType.ADJUSTMENT).withQtyZero(),
					CostDetailPreviousAmounts.of(currentCost));

			currentCost.addWeightedAverage(costAmountDetailed.getCostAdjustmentAmt(), request.getQty().toZero(), utils.getQuantityUOMConverter());
			utils.saveCurrentCost(currentCost);

			amtAndQty = amtAndQty.add(adjustmentResult.getAmtAndQty());
		}

		if (!costAmountDetailed.getAlreadyShippedAmt().isZero())
		{
			final CostDetailCreateResult alreadyShippedResult = utils.createCostDetailRecordNoCostsChanged(
					request.withAmountAndType(costAmountDetailed.getAlreadyShippedAmt(), CostAmountType.ALREADY_SHIPPED).withQtyZero(),
					CostDetailPreviousAmounts.of(currentCost));
			amtAndQty = amtAndQty.add(alreadyShippedResult.getAmtAndQty());
		}

		return mainResult.withAmtAndQty(amtAndQty);
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
	protected CostDetailCreateResultsList createCostForMaterialShipment(final CostDetailCreateRequest request)
	{
		final CurrentCost currentCosts = utils.getCurrentCost(request);
		final CostPrice currentCostPrice = currentCosts.getCostPrice();

		final Quantity qty = utils.convertToUOM(request.getQty(), currentCostPrice.getUomId(), request.getProductId());
		final boolean isInboundTrx = qty.signum() >= 0;

		final CostDetailCreateResultsList result;

		//
		// Reversal
		if (request.isReversal())
		{
			// In case of reversals, always consider the provided Amt
			final CostDetailCreateRequest requestEffective = request.withQty(qty);
			currentCosts.addWeightedAverage(requestEffective.getAmt(), requestEffective.getQty(), utils.getQuantityUOMConverter());
			final CostDetailCreateResult singleResult = utils.createCostDetailRecordWithChangedCosts(requestEffective, CostDetailPreviousAmounts.of(currentCosts));
			result = CostDetailCreateResultsList.of(singleResult);
		}
		//
		// Inbound transactions (qty >= 0)
		else if (isInboundTrx)
		{
			final CostDetailCreateRequest requestEffective;

			// In case the amount was not provided but there is a positive qty incoming
			// use the current cost price to calculate the amount.
			if (request.getAmt().isZero())
			{
				final CostAmount amt = currentCostPrice.multiply(qty).roundToPrecisionIfNeeded(currentCosts.getPrecision());
				requestEffective = request.withAmountAndQty(amt, qty);
			}
			else
			{
				requestEffective = request.withQty(qty);
			}

			currentCosts.addWeightedAverage(requestEffective.getAmt(), requestEffective.getQty(), utils.getQuantityUOMConverter());
			final CostDetailCreateResult singleResult = utils.createCostDetailRecordWithChangedCosts(requestEffective, CostDetailPreviousAmounts.of(currentCosts));
			result = CostDetailCreateResultsList.of(singleResult);
		}
		//
		// Outbound transactions (qty < 0)
		else
		{
			final ShipmentCostAmountDetailed shipmentCostAmountDetailed = computeShipmentCostAmountDetailed(request, currentCosts);
			final ArrayList<CostDetailCreateResult> resultsList = new ArrayList<>();

			//
			// Shipped but not notified
			{
				final CostDetailCreateResult shippedButNotNotifiedResult = utils.createCostDetailRecordWithChangedCosts(
						request.withAmountAndTypeAndQty(shipmentCostAmountDetailed.getShippedButNotNotified().negate(), CostAmountType.MAIN),
						CostDetailPreviousAmounts.of(currentCosts));
				currentCosts.addToCurrentQtyAndCumulate(shippedButNotNotifiedResult.getAmtAndQty(CostAmountType.MAIN));
				resultsList.add(shippedButNotNotifiedResult);
			}

			if (!shipmentCostAmountDetailed.getShippedAndNotified().isZero())
			{
				final CostDetailCreateResult alreadyShippedResult = utils.createCostDetailRecordNoCostsChanged(
						request.withAmountAndTypeAndQty(shipmentCostAmountDetailed.getShippedAndNotified().negate(), CostAmountType.ALREADY_SHIPPED),
						CostDetailPreviousAmounts.of(currentCosts));
				resultsList.add(alreadyShippedResult);
			}

			if (!shipmentCostAmountDetailed.getNotifiedButNotShipped().isZero())
			{
				final CostDetailCreateResult adjustmentResult = utils.createCostDetailRecordWithChangedCosts(
						request.withAmountAndTypeAndQty(shipmentCostAmountDetailed.getNotifiedButNotShipped().negate(), CostAmountType.ADJUSTMENT),
						CostDetailPreviousAmounts.of(currentCosts));
				currentCosts.addToCurrentQtyAndCumulate(adjustmentResult.getAmtAndQty(CostAmountType.ADJUSTMENT));
				resultsList.add(adjustmentResult);
			}

			result = CostDetailCreateResultsList.ofList(resultsList);
		}

		utils.saveCurrentCost(currentCosts);

		return result;
	}

	@Value
	private static class ShipmentCostAmountDetailed
	{
		//
		// P_ExternallyOwnedStock -> P_COGS
		@NonNull CostAmountAndQty shippedAndNotified;

		//
		// P_ExternallyOwnedStock -> P_Asset
		@NonNull CostAmountAndQty notifiedButNotShipped;

		//
		// P_Asset -> P_COGS
		@NonNull CostAmountAndQty shippedButNotNotified;

		@Builder
		private ShipmentCostAmountDetailed(
				@NonNull CostAmountAndQty shippedAndNotified,
				@Nullable CostAmountAndQty notifiedButNotShipped,
				@Nullable CostAmountAndQty shippedButNotNotified)
		{
			final CostAmountAndQty ZERO = shippedAndNotified.toZero();
			this.shippedAndNotified = shippedAndNotified;
			this.notifiedButNotShipped = CoalesceUtil.coalesceNotNull(notifiedButNotShipped, ZERO);
			this.shippedButNotNotified = CoalesceUtil.coalesceNotNull(shippedButNotNotified, ZERO);
		}
	}

	@SuppressWarnings("UnnecessaryLocalVariable")
	private ShipmentCostAmountDetailed computeShipmentCostAmountDetailed(
			@NonNull final CostDetailCreateRequest request,
			@NonNull final CurrentCost currentCosts)
	{
		final CurrencyPrecision precision = currentCosts.getPrecision();
		final CostAmount currentCostPrice = currentCosts.getCostPrice().toCostAmount();
		final CurrencyId currencyId = currentCostPrice.getCurrencyId();
		final UomId uomId = currentCosts.getUomId();
		final ProductId productId = request.getProductId();

		@NonNull final Quantity qtyShipped = utils.convertToUOM(request.getQty(), uomId, productId).negate(); // negate to get positive

		@NonNull final CostAmountAndQty costAndQtyNotified = request.getExternallyOwned()
				.map(CostAmountAndQty::negate) // negate to get positive
				.map(amtAndQty -> amtAndQty.mapQty(utils.convertQuantityToUOM(uomId, productId)))
				.orElseGet(() -> CostAmountAndQty.zero(currencyId, uomId));
		@NonNull final CostAmount costNotified = costAndQtyNotified.getAmt();
		@NonNull final Quantity qtyNotified = costAndQtyNotified.getQty();
		@NonNull final CostAmount costPriceNotified = costNotified.divideIfNotZero(qtyNotified, precision).orElse(currentCostPrice);

		@NonNull final Quantity qtyShippedButNotNotified = qtyShipped.subtract(qtyNotified);

		//
		// Shipped less than notified
		// => P_ExternallyOwnedStock -> P_COGS (cost of qty shipped and notified)
		// => P_ExternallyOwnedStock -> P_Asset (cost of qty notified but not shipped)
		if (qtyShippedButNotNotified.signum() < 0)
		{
			final Quantity qtyShippedAndNotified = qtyShipped;
			final CostAmount costShippedAndNotified = costPriceNotified.multiply(qtyShippedAndNotified).roundToPrecisionIfNeeded(precision);

			final Quantity qtyNotifiedButNotShipped = qtyNotified.subtract(qtyShipped);
			final CostAmount costNotifiedButNotShipped = costNotified.subtract(costShippedAndNotified);

			return ShipmentCostAmountDetailed.builder()
					.shippedAndNotified(CostAmountAndQty.of(costShippedAndNotified, qtyShippedAndNotified))
					.notifiedButNotShipped(CostAmountAndQty.of(costNotifiedButNotShipped, qtyNotifiedButNotShipped))
					.build();
		}
		//
		// Shipped exactly as much as notified
		// => P_ExternallyOwnedStock -> P_COGS (cost of qty shipped and notified)
		else if (qtyShippedButNotNotified.signum() == 0)
		{
			final Quantity qtyShippedAndNotified = qtyNotified;
			final CostAmount costShippedAndNotified = costNotified;

			return ShipmentCostAmountDetailed.builder()
					.shippedAndNotified(CostAmountAndQty.of(costShippedAndNotified, qtyShippedAndNotified))
					.build();
		}
		//
		// Shipped more than notified
		// NOTE: that's also the case when we don't use a shipping notification
		// => P_ExternallyOwnedStock -> P_COGS (cost of qty shipped and notified)
		// => P_Asset -> P_COGS (cost of qty shipped but not notified)
		else // qtyShippedButNotNotified.signum() > 0
		{
			final Quantity qtyShippedAndNotified = qtyNotified;
			final CostAmount costShippedAndNotified = costNotified;

			final CostAmount costShippedButNotNotified = currentCostPrice.multiply(qtyShippedButNotNotified);

			return ShipmentCostAmountDetailed.builder()
					.shippedAndNotified(CostAmountAndQty.of(costShippedAndNotified, qtyShippedAndNotified))
					.shippedButNotNotified(CostAmountAndQty.of(costShippedButNotNotified, qtyShippedButNotNotified))
					.build();
		}
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

		final Quantity qty = utils.convertToUOM(request.getQty(), currentCostPrice.getUomId(), request.getProductId());

		final boolean isInboundTrx = qty.signum() >= 0;

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

		final CostDetailCreateResult result = utils.createCostDetailRecordWithChangedCosts(requestEffective, previousCosts);

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
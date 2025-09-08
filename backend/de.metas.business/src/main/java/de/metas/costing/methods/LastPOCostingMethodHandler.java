package de.metas.costing.methods;

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
import de.metas.quantity.Quantity;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

import java.util.Objects;

/*
 * #%L
 * de.metas.business
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

@Component
public class LastPOCostingMethodHandler extends CostingMethodHandlerTemplate
{
	public LastPOCostingMethodHandler(@NonNull final CostingMethodHandlerUtils utils)
	{
		super(utils);
	}

	@Override
	public CostingMethod getCostingMethod()
	{
		return CostingMethod.LastPOPrice;
	}

	@Override
	protected CostDetailCreateResult createCostForMatchPO(final CostDetailCreateRequest request)
	{
		final CurrentCost currentCosts = utils.getCurrentCost(request);
		final CostDetailPreviousAmounts previousCosts = CostDetailPreviousAmounts.of(currentCosts);

		final CostDetailCreateResult result = utils.createCostDetailRecordWithChangedCosts(request, previousCosts);

		final CostAmount amt = request.getAmt();
		final Quantity qty = request.getQty();
		final boolean isReturnTrx = qty.signum() < 0;

		if (!isReturnTrx)
		{
			if (qty.signum() != 0)
			{
				final CostAmount price = amt.divide(qty, currentCosts.getPrecision());
				currentCosts.setCostPrice(CostPrice.ownCostPrice(price, qty.getUomId()));
			}
			else
			{
				currentCosts.addToOwnCostPrice(amt);
			}
		}

		currentCosts.addToCurrentQtyAndCumulate(qty, amt, utils.getQuantityUOMConverter());

		utils.saveCurrentCost(currentCosts);

		return result;
	}

	@Override
	protected CostDetailCreateResult createOutboundCostDefaultImpl(final CostDetailCreateRequest request)
	{
		final CurrentCost currentCosts = utils.getCurrentCost(request);
		final CostDetailPreviousAmounts previousCosts = CostDetailPreviousAmounts.of(currentCosts);

		final CostDetailCreateResult result = utils.createCostDetailRecordWithChangedCosts(request, previousCosts);

		currentCosts.addToCurrentQtyAndCumulate(request.getQty(), request.getAmt(), utils.getQuantityUOMConverter());

		utils.saveCurrentCost(currentCosts);

		return result;
	}

	@Override
	public void voidCosts(final CostDetailVoidRequest request)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public MoveCostsResult createMovementCosts(@NonNull final MoveCostsRequest request)
	{
		// TODO: do it right! It's copied from de.metas.costing.methods.AveragePOCostingMethodHandler.createMovementCosts

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
}

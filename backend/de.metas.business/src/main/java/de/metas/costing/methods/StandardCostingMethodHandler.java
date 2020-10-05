package de.metas.costing.methods;

import java.util.Objects;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

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
public class StandardCostingMethodHandler extends CostingMethodHandlerTemplate
{
	public StandardCostingMethodHandler(final CostingMethodHandlerUtils utils)
	{
		super(utils);
	}

	@Override
	public CostingMethod getCostingMethod()
	{
		return CostingMethod.StandardCosting;
	}

	@Override
	protected CostDetailCreateResult createCostForMatchInvoice(final CostDetailCreateRequest request)
	{
		final CurrentCost currentCosts = utils.getCurrentCost(request);
		final CostDetailPreviousAmounts previousCosts = CostDetailPreviousAmounts.of(currentCosts);

		final Quantity qty = request.getQty();
		final CostAmount amt = currentCosts.getCostPrice().multiply(qty);

		final CostDetailCreateResult result = utils.createCostDetailRecordWithChangedCosts(request.withAmount(amt), previousCosts);

		currentCosts.addToCurrentQtyAndCumulate(qty, amt, utils.getQuantityUOMConverter());
		utils.saveCurrentCost(currentCosts);

		return result;
	}

	@Override
	protected CostDetailCreateResult createCostForMaterialReceipt(final CostDetailCreateRequest request)
	{
		final CurrentCost currentCosts = utils.getCurrentCost(request);
		final CostDetailPreviousAmounts previousCosts = CostDetailPreviousAmounts.of(currentCosts);

		final Quantity qty = request.getQty();
		final CostAmount amt = currentCosts.getCostPrice().multiply(qty);
		return utils.createCostDetailRecordNoCostsChanged(
				request.withAmount(amt),
				previousCosts);
	}

	@Override
	protected CostDetailCreateResult createOutboundCostDefaultImpl(final CostDetailCreateRequest request)
	{
		final CurrentCost currentCosts = utils.getCurrentCost(request);
		final CostDetailPreviousAmounts previousCosts = CostDetailPreviousAmounts.of(currentCosts);

		final Quantity qty = request.getQty();
		final CostAmount amt = currentCosts.getCostPrice().multiply(qty);

		final CostDetailCreateResult result = utils.createCostDetailRecordWithChangedCosts(request.withAmount(amt), previousCosts);

		currentCosts.addToCurrentQtyAndCumulate(qty, amt, utils.getQuantityUOMConverter());

		utils.saveCurrentCost(currentCosts);

		return result;
	}

	@Override
	public void voidCosts(final CostDetailVoidRequest request)
	{
		final CurrentCost currentCosts = utils.getCurrentCost(request.getCostSegmentAndElement());

		currentCosts.addToCurrentQtyAndCumulate(request.getQty().negate(), request.getAmt().negate(), utils.getQuantityUOMConverter());

		utils.saveCurrentCost(currentCosts);
	}

	@Override
	public MoveCostsResult createMovementCosts(@NonNull MoveCostsRequest request)
	{
		final CostElement costElement = request.getCostElement();
		if (costElement == null)
		{
			throw new AdempiereException("No cost element: " + request);
		}

		final CostSegmentAndElement outboundSegmentAndElement = utils.extractOutboundCostSegmentAndElement(request);

		//
		final CurrentCost outboundCurrentCosts = utils.getCurrentCost(outboundSegmentAndElement);
		final CostDetailPreviousAmounts outboundPreviousCosts = CostDetailPreviousAmounts.of(outboundCurrentCosts);
		final CostPrice outboundCurrentCostPrice = outboundCurrentCosts.getCostPrice();
		final Quantity outboundQty = request.getQtyToMove().negate();
		final CostAmount outboundAmt = outboundCurrentCostPrice.multiply(outboundQty).roundToPrecisionIfNeeded(outboundCurrentCosts.getPrecision());
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

		//
		final CostSegmentAndElement inboundSegmentAndElement = utils.extractInboundCostSegmentAndElement(request);
		final CurrentCost inboundCurrentCosts = Objects.equals(outboundSegmentAndElement, inboundSegmentAndElement)
				? outboundCurrentCosts
				: utils.getCurrentCost(inboundSegmentAndElement);
		final CostDetailPreviousAmounts inboundPreviousCosts = CostDetailPreviousAmounts.of(inboundCurrentCosts);

		final CostPrice inboundCurrentCostPrice = outboundCurrentCosts.getCostPrice();
		final Quantity inboundQty = outboundQty.negate();
		final CostAmount inboundAmt = inboundCurrentCostPrice.multiply(inboundQty).roundToPrecisionIfNeeded(inboundCurrentCosts.getPrecision());
		final CostDetailCreateRequest inboundCostDetailRequest = CostDetailCreateRequest.builder()
				.acctSchemaId(request.getAcctSchemaId())
				.clientId(request.getClientId())
				.orgId(request.getInboundOrgId())
				.productId(request.getProductId())
				.attributeSetInstanceId(request.getAttributeSetInstanceId())
				.documentRef(request.getInboundDocumentRef())
				.costElement(costElement)
				.amt(inboundAmt)
				.qty(inboundQty)
				.date(request.getDate())
				.build();

		//
		// Moving costs inside costing segment
		// => no changes, just record the cost details
		final CostDetailCreateResult outboundResult;
		final CostDetailCreateResult inboundResult;
		if (Objects.equals(outboundSegmentAndElement, inboundSegmentAndElement))
		{
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

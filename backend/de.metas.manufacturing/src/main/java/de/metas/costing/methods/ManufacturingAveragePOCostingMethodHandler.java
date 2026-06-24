package de.metas.costing.methods;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailAdjustment;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostDetailCreateResultsList;
import de.metas.costing.CostDetailPreviousAmounts;
import de.metas.costing.CostDetailVoidRequest;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.MoveCostsRequest;
import de.metas.costing.MoveCostsResult;
import de.metas.currency.CurrencyPrecision;
import de.metas.material.planning.IResourceProductService;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.CostCollectorType;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderCost;
import org.eevolution.api.PPOrderCosts;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Cost_Collector;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Set;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
public class ManufacturingAveragePOCostingMethodHandler implements CostingMethodHandler
{
	// services
	private final IPPCostCollectorBL costCollectorsService = Services.get(IPPCostCollectorBL.class);
	private final IResourceProductService resourceProductService = Services.get(IResourceProductService.class);
	private final IPPOrderCostBL ppOrderCostsService = Services.get(IPPOrderCostBL.class);
	private final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);
	//
	private final CostingMethodHandlerUtils utils;

	private final AveragePOCostingMethodHandler averagePOCostingMethodHandler;

	private static final ImmutableSet<String> HANDLED_TABLE_NAMES = ImmutableSet.<String>builder()
			.add(CostingDocumentRef.TABLE_NAME_PP_Cost_Collector)
			.build();

	public ManufacturingAveragePOCostingMethodHandler(
			@NonNull final CostingMethodHandlerUtils utils,
			@NonNull final AveragePOCostingMethodHandler averagePOCostingMethodHandler)
	{
		this.utils = utils;
		this.averagePOCostingMethodHandler = averagePOCostingMethodHandler;
	}

	@Override
	public CostingMethod getCostingMethod()
	{
		return CostingMethod.AveragePO;
	}

	@Override
	public Set<String> getHandledTableNames()
	{
		return HANDLED_TABLE_NAMES;
	}

	@Override
	public CostDetailCreateResultsList createOrUpdateCost(final CostDetailCreateRequest request)
	{
		final List<CostDetail> existingCostDetails = utils.getExistingCostDetails(request);
		if (!existingCostDetails.isEmpty())
		{
			// make sure DateAcct is up-to-date
			final List<CostDetail> existingCostDetailsUpdated = utils.updateDateAcct(existingCostDetails, request.getDate());
			return utils.toCostDetailCreateResultsList(existingCostDetailsUpdated);
		}
		else
		{
			return createCost(request);
		}
	}

	private CostDetailCreateResultsList createCost(final CostDetailCreateRequest request)
	{
		final PPCostCollectorId costCollectorId = request.getDocumentRef().getCostCollectorId();
		final I_PP_Cost_Collector cc = costCollectorsService.getById(costCollectorId);
		final CostCollectorType costCollectorType = CostCollectorType.ofCode(cc.getCostCollectorType());
		final PPOrderId orderId = PPOrderId.ofRepoId(cc.getPP_Order_ID());
		final PPOrderBOMLineId orderBOMLineId = PPOrderBOMLineId.ofRepoIdOrNull(cc.getPP_Order_BOMLine_ID());

		final PPOrderCosts orderCosts;
		final CurrentCost currentCost;

		final CostDetailCreateResult result;
		if (costCollectorType.isMaterialReceiptOrCoProduct())
		{
			orderCosts = ppOrderCostsService.getByOrderId(orderId);
			currentCost = utils.getCurrentCost(request);
			result = createMainProductOrCoProductReceipt(request, currentCost, orderCosts);
		}
		else if (costCollectorType.isAnyComponentIssue(orderBOMLineId))
		{
			orderCosts = ppOrderCostsService.getByOrderId(orderId);
			currentCost = utils.getCurrentCost(request);
			result = createComponentIssue(request, currentCost, orderCosts);
		}
		else if (costCollectorType.isActivityControl())
		{
			final ResourceId actualResourceId = ResourceId.ofRepoId(cc.getS_Resource_ID());
			if (actualResourceId.isNoResource())
			{
				return null;
			}

			final ProductId actualResourceProductId = resourceProductService.getProductIdByResourceId(actualResourceId);
			final Duration totalDuration = costCollectorsService.getTotalDurationReported(cc);

			orderCosts = null;
			currentCost = null;
			result = createActivityControl(request.withProductId(actualResourceProductId), totalDuration);
		}
		else if (costCollectorType.isUsageVariance()
				|| costCollectorType.isMethodChangeVariance()
				|| costCollectorType.isRateVariance())
		{
			// those cost collectors are specific to standard costs,
			// so we are ignoring them
			orderCosts = null;
			currentCost = null;
			result = null;
		}
		else
		{
			orderCosts = null;
			currentCost = null;
			result = null;
		}

		//
		if (orderCosts != null)
		{
			orderCosts.updatePostCalculationAmountsForCostElement(getCostingPrecision(request), request.getCostElementId());
			ppOrderCostsService.save(orderCosts);
		}

		//
		if (currentCost != null)
		{
			utils.saveCurrentCost(currentCost);
		}

		return CostDetailCreateResultsList.ofNullable(result);
	}

	private CurrencyPrecision getCostingPrecision(final CostDetailCreateRequest request)
	{
		final AcctSchemaId acctSchemaId = request.getAcctSchemaId();
		return acctSchemasRepo.getById(acctSchemaId)
				.getCosting()
				.getCostingPrecision();
	}

	private CostDetailCreateResult createMainProductOrCoProductReceipt(
			@NonNull final CostDetailCreateRequest request,
			@NonNull final CurrentCost currentCost,
			@NonNull final PPOrderCosts orderCosts)
	{
		final CostSegmentAndElement costSegmentAndElement = utils.extractCostSegmentAndElement(request);

		final CostDetailCreateRequest requestEffective;
		if (!request.isReversal())
		{
			final PPOrderCost mainProductCost = orderCosts.getByCostSegmentAndElement(costSegmentAndElement)
					.orElseThrow(() -> new AdempiereException("No order cost found for " + costSegmentAndElement + " in " + orderCosts));

			final Quantity qty = utils.convertToUOM(request.getQty(), mainProductCost.getUomId(), costSegmentAndElement.getProductId());

			// Recover the order's actual production cost into the finished good instead of the frozen
			// planned BOM-rollup price, so the work-in-process account clears once the order is closed.
			// The post-calculation amount is the total actual inbound (component) cost accumulated so far;
			// this receipt absorbs whatever part of it earlier receipts have not yet recovered.
			final CostAmount amt = mainProductCost.getPostCalculationAmount()
					.subtract(mainProductCost.getAccumulatedAmount())
					.roundToPrecisionIfNeeded(currentCost.getPrecision());
			requestEffective = request.withAmountAndQty(amt, qty);
		}
		else
		{
			requestEffective = request;
		}

		final CostDetailPreviousAmounts previousCosts = CostDetailPreviousAmounts.of(currentCost);
		final CostDetailCreateResult result = utils.createCostDetailRecordWithChangedCosts(requestEffective, previousCosts);
		currentCost.addWeightedAverage(requestEffective.getAmt(), requestEffective.getQty(), utils.getQuantityUOMConverter());

		// Accumulate to order costs
		// NOTE: outbound amounts are negative, so we have to negate it here in order to get a positive value
		orderCosts.accumulateOutboundCostAmount(
				costSegmentAndElement,
				requestEffective.getAmt().negate(),
				requestEffective.getQty().negate(),
				utils.getQuantityUOMConverter());

		return result;
	}

	private CostDetailCreateResult createComponentIssue(
			@NonNull final CostDetailCreateRequest request,
			@NonNull final CurrentCost currentCosts,
			@NonNull final PPOrderCosts orderCosts)
	{
		final CostDetailPreviousAmounts previousCosts = CostDetailPreviousAmounts.of(currentCosts);

		final CostDetailCreateRequest requestEffective;
		final CostDetailCreateResult result;
		if (request.isReversal())
		{
			requestEffective = request;
			result = utils.createCostDetailRecordWithChangedCosts(requestEffective, previousCosts);
			currentCosts.addWeightedAverage(requestEffective.getAmt(), requestEffective.getQty(), utils.getQuantityUOMConverter());
		}
		else
		{
			final CostPrice price = currentCosts.getCostPrice();
			final Quantity qty = utils.convertToUOM(request.getQty(), price.getUomId(), request.getProductId());
			final CostAmount amt = price.multiply(qty).roundToPrecisionIfNeeded(currentCosts.getPrecision());
			requestEffective = request.withAmountAndQty(amt, qty);
			result = utils.createCostDetailRecordWithChangedCosts(requestEffective, previousCosts);

			currentCosts.addToCurrentQtyAndCumulate(requestEffective.getQty(), requestEffective.getAmt());
		}

		// Accumulate the ACTUAL issued cost (from the current cost price) to the order costs, so that
		// the order's post-calculation reflects the real inbound component cost.
		// NOTE: issue amounts/quantities are negative, so we negate them here to accumulate a positive
		// inbound value (mirroring the outbound negation in createMainProductOrCoProductReceipt).
		orderCosts.accumulateInboundCostAmount(
				utils.extractCostSegmentAndElement(request),
				requestEffective.getAmt().negate(),
				requestEffective.getQty().negate(),
				utils.getQuantityUOMConverter());

		return result;
	}

	private CostDetailCreateResult createActivityControl(
			final CostDetailCreateRequest ignoredRequest,
			final Duration ignoredTotalDuration)
	{
		// TODO Auto-generated method stub
		throw new AdempiereException("Computing activity costs is not yet supported");
	}

	@Override
	public void voidCosts(final CostDetailVoidRequest request)
	{
		// TODO
		throw new AdempiereException("Voiding costs is not yet supported");
	}

	@Override
	public MoveCostsResult createMovementCosts(@NonNull final MoveCostsRequest request)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public CostDetailAdjustment recalculateCostDetailAmountAndUpdateCurrentCost(final CostDetail costDetail, final CurrentCost currentCost)
	{
		return averagePOCostingMethodHandler.recalculateCostDetailAmountAndUpdateCurrentCost(costDetail, currentCost);
	}
}

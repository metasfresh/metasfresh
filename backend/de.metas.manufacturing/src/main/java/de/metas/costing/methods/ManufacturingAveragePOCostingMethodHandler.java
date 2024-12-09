package de.metas.costing.methods;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
<<<<<<< HEAD
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostDetailPreviousAmounts;
import de.metas.costing.CostDetailVoidRequest;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegment;
=======
import de.metas.costing.CostDetailAdjustment;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostDetailCreateResultsList;
import de.metas.costing.CostDetailPreviousAmounts;
import de.metas.costing.CostDetailVoidRequest;
import de.metas.costing.CostPrice;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.MoveCostsRequest;
import de.metas.costing.MoveCostsResult;
import de.metas.currency.CurrencyPrecision;
import de.metas.material.planning.IResourceProductService;
<<<<<<< HEAD
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import de.metas.order.OrderLineId;
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
import org.eevolution.api.PPOrderCosts;
=======
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderCosts;
import org.eevolution.api.PPOrderId;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.eevolution.model.I_PP_Cost_Collector;
import org.springframework.stereotype.Component;

import java.time.Duration;
<<<<<<< HEAD
import java.util.Optional;
=======
import java.util.List;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
=======
	private final AveragePOCostingMethodHandler averagePOCostingMethodHandler;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	private static final ImmutableSet<String> HANDLED_TABLE_NAMES = ImmutableSet.<String>builder()
			.add(CostingDocumentRef.TABLE_NAME_PP_Cost_Collector)
			.build();

	public ManufacturingAveragePOCostingMethodHandler(
<<<<<<< HEAD
			@NonNull final CostingMethodHandlerUtils utils)
	{
		this.utils = utils;
=======
			@NonNull final CostingMethodHandlerUtils utils,
			@NonNull final AveragePOCostingMethodHandler averagePOCostingMethodHandler)
	{
		this.utils = utils;
		this.averagePOCostingMethodHandler = averagePOCostingMethodHandler;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	public Optional<CostDetailCreateResult> createOrUpdateCost(final CostDetailCreateRequest request)
	{
		final CostDetail existingCostDetail = utils.getExistingCostDetail(request).orElse(null);
		if (existingCostDetail != null)
		{
			return Optional.of(utils.toCostDetailCreateResult(existingCostDetail));
=======
	public CostDetailCreateResultsList createOrUpdateCost(final CostDetailCreateRequest request)
	{
		final List<CostDetail> existingCostDetails = utils.getExistingCostDetails(request);
		if (!existingCostDetails.isEmpty())
		{
			// make sure DateAcct is up-to-date
			final List<CostDetail> existingCostDetailsUpdated = utils.updateDateAcct(existingCostDetails, request.getDate());
			return utils.toCostDetailCreateResultsList(existingCostDetailsUpdated);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
		else
		{
			return createCost(request);
		}
	}

<<<<<<< HEAD
	private Optional<CostDetailCreateResult> createCost(final CostDetailCreateRequest request)
	{
		final PPCostCollectorId costCollectorId = request.getDocumentRef().getCostCollectorId(PPCostCollectorId::ofRepoId);
=======
	private CostDetailCreateResultsList createCost(final CostDetailCreateRequest request)
	{
		final PPCostCollectorId costCollectorId = request.getDocumentRef().getCostCollectorId();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
				return Optional.empty();
=======
				return null;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
		return Optional.ofNullable(result);
=======
		return CostDetailCreateResultsList.ofNullable(result);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
			final CostPrice price = orderCosts.getPriceByCostSegmentAndElement(costSegmentAndElement)
					.orElseThrow(() -> new AdempiereException("No cost price found for " + costSegmentAndElement + " in " + orderCosts));

			final Quantity qty = utils.convertToUOM(request.getQty(), price.getUomId(), costSegmentAndElement.getProductId());
			final CostAmount amt = price.multiply(qty).roundToPrecisionIfNeeded(currentCost.getPrecision());
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

		final CostDetailCreateResult result;
		if (request.isReversal())
		{
			result = utils.createCostDetailRecordWithChangedCosts(request, previousCosts);
			currentCosts.addWeightedAverage(request.getAmt(), request.getQty(), utils.getQuantityUOMConverter());
		}
		else
		{
			final CostPrice price = currentCosts.getCostPrice();
			final Quantity qty = utils.convertToUOM(request.getQty(), price.getUomId(), request.getProductId());
			final CostAmount amt = price.multiply(qty).roundToPrecisionIfNeeded(currentCosts.getPrecision());
			final CostDetailCreateRequest requestEffective = request.withAmountAndQty(amt, qty);
			result = utils.createCostDetailRecordWithChangedCosts(requestEffective, previousCosts);

			currentCosts.addToCurrentQtyAndCumulate(requestEffective.getQty(), requestEffective.getAmt());
		}

		// Accumulate to order costs
		orderCosts.accumulateInboundCostAmount(
				utils.extractCostSegmentAndElement(request),
				request.getAmt(),
				request.getQty(),
				utils.getQuantityUOMConverter());

		return result;
	}

	private CostDetailCreateResult createActivityControl(
<<<<<<< HEAD
			final CostDetailCreateRequest request,
			final Duration totalDuration)
=======
			final CostDetailCreateRequest ignoredRequest,
			final Duration ignoredTotalDuration)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	public Optional<CostAmount> calculateSeedCosts(
			final CostSegment costSegment,
			final OrderLineId orderLineId)
	{
		return Optional.empty();
	}

	@Override
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public MoveCostsResult createMovementCosts(@NonNull final MoveCostsRequest request)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
<<<<<<< HEAD
=======

	@Override
	public CostDetailAdjustment recalculateCostDetailAmountAndUpdateCurrentCost(final CostDetail costDetail, final CurrentCost currentCost)
	{
		return averagePOCostingMethodHandler.recalculateCostDetailAmountAndUpdateCurrentCost(costDetail, currentCost);
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}

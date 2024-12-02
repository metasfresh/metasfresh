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
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.CostCollectorType;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderCosts;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Cost_Collector;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class ManufacturingMovingAverageInvoiceCostingMethodHandler implements CostingMethodHandler
{
	// services
	@NonNull private final IPPCostCollectorBL costCollectorsService = Services.get(IPPCostCollectorBL.class);
	@NonNull private final IResourceProductService resourceProductService = Services.get(IResourceProductService.class);
	@NonNull private final IPPOrderCostBL ppOrderCostsService = Services.get(IPPOrderCostBL.class);
	@NonNull private final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);
	//
	@NonNull private final CostingMethodHandlerUtils utils;
	@NonNull private final MovingAverageInvoiceCostingMethodHandler movingAverageInvoiceCostingMethodHandler;

	@NonNull @Getter private final CostingMethod costingMethod = CostingMethod.MovingAverageInvoice;
	@NonNull @Getter private final ImmutableSet<String> handledTableNames = ImmutableSet.of(CostingDocumentRef.TABLE_NAME_PP_Cost_Collector);

	@Override
	public CostDetailCreateResultsList createOrUpdateCost(final CostDetailCreateRequest request)
	{
		final PPCostCollectorId costCollectorId = request.getDocumentRef().getCostCollectorId();
		final I_PP_Cost_Collector cc = costCollectorsService.getById(costCollectorId);
		final CostCollectorType costCollectorType = CostCollectorType.ofCode(cc.getCostCollectorType());
		final PPOrderId orderId = PPOrderId.ofRepoId(cc.getPP_Order_ID());
		final PPOrderBOMLineId orderBOMLineId = PPOrderBOMLineId.ofRepoIdOrNull(cc.getPP_Order_BOMLine_ID());

		final PPOrderCosts orderCosts;
		final CurrentCost currentCost;

		final CostDetailCreateResult result;
		if (costCollectorType.isMaterialReceipt())
		{
			orderCosts = ppOrderCostsService.getByOrderId(orderId);
			currentCost = utils.getCurrentCost(request);
			result = createMainProductOrCoProductReceipt(request, currentCost, orderCosts);
		}
		else if (costCollectorType.isCoOrByProductReceipt())
		{
			// CO/BY product quantities are negative, so we are negating them here to get a positive "received" qty
			final CostDetailCreateRequest requestEffective = request.withQty(request.getQty().negate());
			
			orderCosts = ppOrderCostsService.getByOrderId(orderId);
			currentCost = utils.getCurrentCost(requestEffective);
			result = createMainProductOrCoProductReceipt(requestEffective, currentCost, orderCosts);
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
		return movingAverageInvoiceCostingMethodHandler.recalculateCostDetailAmountAndUpdateCurrentCost(costDetail, currentCost);
	}
}

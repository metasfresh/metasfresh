package de.metas.costing.methods;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostDetailPreviousAmounts;
import de.metas.costing.CostDetailVoidRequest;
import de.metas.costing.CostElement;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegment;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICostDetailService;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costing.MoveCostsRequest;
import de.metas.costing.MoveCostsResult;
import de.metas.material.planning.IResourceProductService;
import de.metas.order.OrderLineId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.uom.UOMUtil;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.time.DurationUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.CostCollectorType;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.model.I_PP_Cost_Collector;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Optional;
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
public class ManufacturingStandardCostingMethodHandler implements CostingMethodHandler
{
	// services
	private final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);
	private final IPPCostCollectorBL costCollectorsService = Services.get(IPPCostCollectorBL.class);
	private final IProductBL productsService = Services.get(IProductBL.class);
	private final IResourceProductService resourceProductService = Services.get(IResourceProductService.class);
	//
	private final ICurrentCostsRepository currentCostsRepo;
	private final ICostDetailService costDetailsService;
	private final CostingMethodHandlerUtils utils;

	private static final ImmutableSet<String> HANDLED_TABLE_NAMES = ImmutableSet.<String>builder()
			.add(CostingDocumentRef.TABLE_NAME_PP_Cost_Collector)
			.build();

	public ManufacturingStandardCostingMethodHandler(
			@NonNull final ICurrentCostsRepository currentCostsRepo,
			@NonNull final ICostDetailService costDetailsService,
			@NonNull final CostingMethodHandlerUtils utils)
	{
		this.currentCostsRepo = currentCostsRepo;
		this.costDetailsService = costDetailsService;
		this.utils = utils;
	}

	@Override
	public CostingMethod getCostingMethod()
	{
		return CostingMethod.StandardCosting;
	}

	@Override
	public Set<String> getHandledTableNames()
	{
		return HANDLED_TABLE_NAMES;
	}

	@Override
	public Optional<CostDetailCreateResult> createOrUpdateCost(final CostDetailCreateRequest request)
	{
		final PPCostCollectorId costCollectorId = request.getDocumentRef().getCostCollectorId(PPCostCollectorId::ofRepoId);
		final I_PP_Cost_Collector cc = costCollectorsService.getById(costCollectorId);
		final CostCollectorType costCollectorType = CostCollectorType.ofCode(cc.getCostCollectorType());
		final PPOrderBOMLineId orderBOMLineId = PPOrderBOMLineId.ofRepoIdOrNull(cc.getPP_Order_BOMLine_ID());

		if (costCollectorType.isMaterial(orderBOMLineId))
		{
			return Optional.of(createIssueOrReceipt(request));
		}
		else if (costCollectorType.isActivityControl())
		{
			final ResourceId actualResourceId = ResourceId.ofRepoId(cc.getS_Resource_ID());
			if (actualResourceId.isNoResource())
			{
				return Optional.empty();
			}

			final ProductId actualResourceProductId = resourceProductService.getProductIdByResourceId(actualResourceId);

			final Duration totalDuration = costCollectorsService.getTotalDurationReported(cc);

			return Optional.ofNullable(createActivityControl(request.withProductId(actualResourceProductId), totalDuration));
		}
		else if (costCollectorType.isUsageVariance())
		{
			if (cc.getPP_Order_BOMLine_ID() > 0)
			{
				return Optional.of(createUsageVariance(request));
			}
			else
			{
				final ResourceId actualResourceId = ResourceId.ofRepoId(cc.getS_Resource_ID());
				if (actualResourceId.isNoResource())
				{
					return Optional.empty();
				}

				final ProductId actualResourceProductId = resourceProductService.getProductIdByResourceId(actualResourceId);

				final Duration totalDurationReported = costCollectorsService.getTotalDurationReported(cc);
				final Quantity qty = convertDurationToQuantity(totalDurationReported, actualResourceProductId);

				return Optional.of(createUsageVariance(request.withProductIdAndQty(actualResourceProductId, qty)));
			}
		}
		else
		{
			throw new AdempiereException("Unknown cost collector type: " + costCollectorType);
		}
	}

	@Override
	public void voidCosts(final CostDetailVoidRequest request)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<CostAmount> calculateSeedCosts(
			final CostSegment costSegment,
			final OrderLineId orderLineId)
	{
		return Optional.empty();
	}

	private CurrentCost getCurrentCost(final CostDetailCreateRequest request)
	{
		final CostSegmentAndElement costSegmentAndElement = utils.extractCostSegmentAndElement(request);
		return currentCostsRepo.getOrCreate(costSegmentAndElement);
	}

	private CostDetailCreateResult createIssueOrReceipt(final CostDetailCreateRequest request)
	{
		final AcctSchema acctSchema = acctSchemasRepo.getById(request.getAcctSchemaId());

		final CurrentCost currentCosts = getCurrentCost(request);
		final CostPrice price = currentCosts.getCostPrice();
		final Quantity qty = utils.convertToUOM(request.getQty(), price.getUomId(), request.getProductId());
		final CostAmount amt = price.multiply(qty).roundToCostingPrecisionIfNeeded(acctSchema);
		final CostDetail costDetail = costDetailsService.create(request.toCostDetailBuilder()
				.amt(amt)
				.qty(qty)
				.changingCosts(true)
				.previousAmounts(CostDetailPreviousAmounts.of(currentCosts)));

		final CostDetailCreateResult result = utils.toCostDetailCreateResult(costDetail);

		currentCosts.addToCurrentQtyAndCumulate(qty, amt);
		currentCostsRepo.save(currentCosts);

		return result;
	}

	@Nullable
	private CostDetailCreateResult createActivityControl(
			@NonNull final CostDetailCreateRequest request,
			@NonNull final Duration duration)
	{
		final CostElement costElement = request.getCostElement();
		if (!costElement.isActivityControlElement())
		{
			return null;
		}

		final AcctSchema acctSchema = acctSchemasRepo.getById(request.getAcctSchemaId());

		final CostSegmentAndElement costSegmentAndElement = utils.extractCostSegmentAndElement(request);
		final CostPrice price = getProductActualCostPrice(costSegmentAndElement);
		final Quantity qty = convertDurationToQuantity(duration, request.getProductId(), price.getUomId());
		final CostAmount amt = price.multiply(qty).roundToCostingPrecisionIfNeeded(acctSchema);

		final CurrentCost currentCosts = getCurrentCost(request);
		final CostDetail costDetail = costDetailsService.create(request.toCostDetailBuilder()
				.amt(amt)
				.changingCosts(true)
				.previousAmounts(CostDetailPreviousAmounts.of(currentCosts)));

		final CostDetailCreateResult result = utils.toCostDetailCreateResult(costDetail);

		currentCosts.addToCurrentQtyAndCumulate(qty, amt, utils.getQuantityUOMConverter());
		currentCostsRepo.save(currentCosts);

		return result;
	}

	private CostDetailCreateResult createUsageVariance(@NonNull final CostDetailCreateRequest request)
	{
		final AcctSchema acctSchema = acctSchemasRepo.getById(request.getAcctSchemaId());
		final CostSegmentAndElement costSegmentAndElement = utils.extractCostSegmentAndElement(request);
		final CostPrice price = getProductActualCostPrice(costSegmentAndElement);
		final Quantity qty = utils.convertToUOM(request.getQty(), price.getUomId(), request.getProductId());
		final CostAmount amt = price.multiply(qty).roundToCostingPrecisionIfNeeded(acctSchema);

		final CurrentCost currentCosts = getCurrentCost(request);
		final CostDetail costDetail = costDetailsService.create(request.toCostDetailBuilder()
				.amt(amt)
				.changingCosts(true)
				.previousAmounts(CostDetailPreviousAmounts.of(currentCosts)));

		final CostDetailCreateResult result = utils.toCostDetailCreateResult(costDetail);

		currentCosts.addToCurrentQtyAndCumulate(qty, amt, utils.getQuantityUOMConverter());
		currentCostsRepo.save(currentCosts);

		return result;
	}

	// private void createRateVariances(@NonNull CostDetailCreateRequest request)
	// {
	// final I_M_Product product;
	// final CostCollectorType costCollectorType = CostCollectorType.ofCode(cc.getCostCollectorType());
	// if (costCollectorType.isActivityControl())
	// {
	// final ResourceId stdResourceId = getStandardResourceId(cc);
	// product = resourceProductService.getProductByResourceId(stdResourceId);
	// }
	// else if (costCollectorType.isComponentIssue())
	// {
	// final I_PP_Order_BOMLine bomLine = cc.getPP_Order_BOMLine();
	// product = productsRepo.getById(bomLine.getM_Product_ID());
	// }
	// else
	// {
	// return;
	// }
	//
	// final PPOrderId ppOrderId = PPOrderId.ofRepoId(cc.getPP_Order_ID());
	// final PPOrderCosts ppOrderCosts = Services.get(IPPOrderCostBL.class).getByOrderId(ppOrderId);
	//
	// I_PP_Cost_Collector ccrv = null; // Cost Collector - Rate Variance
	//
	// final AcctSchema acctSchema = acctSchemasRepo.getById(request.getAcctSchemaId());
	// final CostSegment costSegment = utils.extractCostSegment(request);
	// final CostElement costElement = request.getCostElement();
	// final CostElementId costElementId = costElement.getId();
	//
	// final CostDetail cd = getCostDetail(cc, costElementId);
	// if (cd == null)
	// {
	// continue;
	// }
	//
	// //
	// final Quantity qty = cd.getQty();
	//
	// final CostAmount priceStd = ppOrderCosts
	// .getByCostSegmentAndElement(costSegment, costElementId)
	// .orElseGet(() -> CostAmount.zero(acctSchema.getCurrencyId()));
	// final CostAmount amtStd = roundCost(priceStd.multiply(qty), acctSchema.getId());
	//
	// final CostAmount priceActual = getProductActualCostPrice(costSegment, costElementId);
	// final CostAmount amtActual = roundCost(priceActual.multiply(qty), acctSchema.getId());
	//
	// if (amtStd.subtract(amtActual).signum() == 0)
	// {
	// continue;
	// }
	//
	// //
	// if (ccrv == null)
	// {
	// ccrv = createVarianceCostCollector(cc, CostCollectorType.RateVariance);
	// }
	// //
	// createVarianceCostDetail(
	// ccrv,
	// amtActual.negate(),
	// qty.negate(),
	// cd,
	// null,
	// acctSchema.getId(),
	// costElement);
	// createVarianceCostDetail(
	// ccrv,
	// amtStd,
	// qty,
	// cd,
	// null,
	// acctSchema.getId(),
	// costElement);
	//
	// //
	// if (ccrv != null)
	// {
	// completeCostCollector(ccrv);
	// }
	// }

	// public void createMethodVariances(final I_PP_Cost_Collector cc)
	// {
	// final CostCollectorType costCollectorType = CostCollectorType.ofCode(cc.getCostCollectorType());
	// if (!costCollectorType.isActivityControl())
	// {
	// return;
	// }
	//
	// //
	// final ResourceId stdResourceId = getStandardResourceId(cc);
	// final ResourceId actualResourceId = ResourceId.ofRepoId(cc.getS_Resource_ID());
	// if (ResourceId.equals(actualResourceId, stdResourceId))
	// {
	// return;
	// }
	//
	// final I_M_Product stdResourceProduct = resourceProductService.getProductByResourceId(stdResourceId);
	// final I_M_Product actualResourceProduct = resourceProductService.getProductByResourceId(actualResourceId);
	//
	// //
	// I_PP_Cost_Collector ccmv = null; // Cost Collector - Method Change Variance
	// final RoutingService routingService = RoutingServiceFactory.get().getRoutingService();
	// for (final AcctSchema as : getAcctSchema(cc))
	// {
	// final CostSegment stdResourceCostSegment = createCostSegment(cc, as, stdResourceProduct);
	// final CostSegment actualResourceCostSegment = createCostSegment(cc, as, actualResourceProduct);
	//
	// for (final CostElement element : getCostElements())
	// {
	// final CostElementId costElementId = element.getId();
	//
	// final CostAmount priceStd = getProductActualCostPrice(stdResourceCostSegment, costElementId);
	// final CostAmount priceActual = getProductActualCostPrice(actualResourceCostSegment, costElementId);
	// if (priceStd.subtract(priceActual).signum() == 0)
	// {
	// continue;
	// }
	//
	// //
	// if (ccmv == null)
	// {
	// ccmv = createVarianceCostCollector(cc, CostCollectorType.MethodChangeVariance);
	// }
	//
	// //
	// final Quantity qty = routingService.getResourceBaseValue(actualResourceId, cc);
	// final CostAmount amtStd = priceStd.multiply(qty);
	// final CostAmount amtActual = priceActual.multiply(qty);
	// //
	// createVarianceCostDetail(
	// ccmv,
	// amtActual,
	// qty,
	// (CostDetail)null,
	// actualResourceProduct,
	// as.getId(),
	// element);
	// createVarianceCostDetail(
	// ccmv,
	// amtStd.negate(),
	// qty.negate(),
	// (CostDetail)null,
	// stdResourceProduct,
	// as.getId(),
	// element);
	// }
	// }
	// //
	// if (ccmv != null)
	// {
	// completeCostCollector(ccmv);
	// }
	// }

	// private CostDetail createVarianceCostDetail(
	// final I_PP_Cost_Collector cc,
	// final CostAmount amt,
	// final Quantity qty,
	// final CostDetail cd,
	// final I_M_Product product,
	// final AcctSchemaId acctSchemaId,
	// final CostElement element)
	// {
	// final I_M_CostDetail cdv = newInstance(I_M_CostDetail.class, cc);
	// if (cd != null)
	// {
	// copyValues(cd, cdv);
	// cdv.setProcessed(false);
	// }
	// if (product != null)
	// {
	// cdv.setM_Product_ID(product.getM_Product_ID());
	// cdv.setM_AttributeSetInstance_ID(AttributeConstants.M_AttributeSetInstance_ID_None);
	// }
	// if (acctSchemaId != null)
	// {
	// cdv.setC_AcctSchema_ID(acctSchemaId.getRepoId());
	// }
	// if (element != null)
	// {
	// cdv.setM_CostElement_ID(element.getId().getRepoId());
	// }
	// //
	// cdv.setPP_Cost_Collector_ID(cc.getPP_Cost_Collector_ID());
	// cdv.setAmt(amt.getValue());
	// cdv.setQty(qty);
	// cdv.setIsSOTrx(false);
	// saveRecord(cdv);
	// processCostDetail(cdv);
	// return cdv;
	// }

	// private ResourceId getStandardResourceId(final I_PP_Cost_Collector cc)
	// {
	// final I_AD_WF_Node node = cc.getPP_Order_Node().getAD_WF_Node();
	// final ResourceId resourceId = ResourceId.ofRepoId(node.getS_Resource_ID());
	// return resourceId;
	// }

	private Quantity convertDurationToQuantity(
			final Duration duration,
			final ProductId resourceProductId,
			final UomId targetUomId)
	{
		final Quantity qty = convertDurationToQuantity(duration, resourceProductId);
		return utils.convertToUOM(qty, targetUomId, resourceProductId);
	}

	private Quantity convertDurationToQuantity(
			final Duration duration,
			final ProductId resourceProductId)
	{
		final I_C_UOM durationUOM = productsService.getStockUOM(resourceProductId);
		final TemporalUnit durationUnit = UOMUtil.toTemporalUnit(durationUOM);

		final BigDecimal durationBD = DurationUtils.toBigDecimal(duration, durationUnit);
		return Quantity.of(durationBD, durationUOM);
	}

	private CostPrice getProductActualCostPrice(@NonNull final CostSegmentAndElement costSegmentAndElement)
	{
		return currentCostsRepo.getOrCreate(costSegmentAndElement)
				.getCostPrice();
	}

	@Override
	public MoveCostsResult createMovementCosts(@NonNull final MoveCostsRequest request)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}

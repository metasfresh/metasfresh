package de.metas.costing.methods;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.CostCollectorType;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.PPOrderCosts;
import org.eevolution.model.I_PP_Cost_Collector;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableSet;

import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostDetailVoidRequest;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegment;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICostDetailRepository;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.material.planning.IResourceProductService;
import de.metas.material.planning.pporder.PPOrderBOMLineId;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.order.OrderLineId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import lombok.NonNull;

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
	private final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);
	private final IPPOrderRoutingRepository orderRoutingsRepo = Services.get(IPPOrderRoutingRepository.class);
	private final IPPCostCollectorBL costCollectorsService = Services.get(IPPCostCollectorBL.class);
	private final IProductBL productsService = Services.get(IProductBL.class);
	private final IResourceProductService resourceProductService = Services.get(IResourceProductService.class);
	private final IPPOrderCostBL ppOrderCostsService = Services.get(IPPOrderCostBL.class);
	//
	private final ICurrentCostsRepository currentCostsRepo;
	private final ICostDetailRepository costDetailsRepo;
	private final CostingMethodHandlerUtils utils;

	private static final ImmutableSet<String> HANDLED_TABLE_NAMES = ImmutableSet.<String> builder()
			.add(CostingDocumentRef.TABLE_NAME_PP_Cost_Collector)
			.build();

	public ManufacturingAveragePOCostingMethodHandler(
			@NonNull final ICurrentCostsRepository currentCostsRepo,
			@NonNull final ICostDetailRepository costDetailsRepo,
			@NonNull final CostingMethodHandlerUtils utils)
	{
		this.currentCostsRepo = currentCostsRepo;
		this.costDetailsRepo = costDetailsRepo;
		this.utils = utils;
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
	public Optional<CostDetailCreateResult> createOrUpdateCost(final CostDetailCreateRequest request)
	{
		final I_PP_Cost_Collector cc = costCollectorsService.getById(request.getDocumentRef().getRecordId());
		final CostCollectorType costCollectorType = CostCollectorType.ofCode(cc.getCostCollectorType());
		final PPOrderId orderId = PPOrderId.ofRepoId(cc.getPP_Order_ID());
		final PPOrderBOMLineId orderBOMLineId = PPOrderBOMLineId.ofRepoIdOrNull(cc.getPP_Order_BOMLine_ID());

		if (costCollectorType.isMaterialReceiptOrCoProduct())
		{
			return Optional.of(createMainProductOrCoProductReceipt(request, orderId));
		}
		if (costCollectorType.isAnyComponentIssue(orderBOMLineId))
		{
			return Optional.of(createComponentIssue(request, orderId));
		}
		else if (costCollectorType.isActivityControl())
		{
			final ResourceId actualResourceId = ResourceId.ofRepoId(cc.getS_Resource_ID());
			final ProductId actualResourceProductId = resourceProductService.getProductIdByResourceId(actualResourceId);

			final Duration totalDuration = costCollectorsService.getTotalDurationReported(cc);

			return Optional.of(createActivityControl(request.withProductId(actualResourceProductId), totalDuration));
		}
		else
		{
			return Optional.empty();
		}
	}

	private CostDetailCreateResult createMainProductOrCoProductReceipt(final CostDetailCreateRequest request, final PPOrderId orderId)
	{
		final CurrentCost currentCosts = utils.getCurrentCost(request);

		CostDetailCreateRequest requestEffective;

		if (!request.isReversal())
		{
			final PPOrderCosts orderCosts = ppOrderCostsService.getByOrderId(orderId);
			final CostSegmentAndElement costSegmentAndElement = utils.extractCostSegmentAndElement(request);

			final CostPrice price = orderCosts.getPriceByCostSegmentAndElement(costSegmentAndElement)
					.orElseThrow(() -> new AdempiereException("No cost price found for " + costSegmentAndElement + " in " + orderCosts));

			final CostAmount amt = price.multiply(request.getQty()).roundToPrecisionIfNeeded(currentCosts.getPrecision());

			requestEffective = request.withAmount(amt);
		}
		else
		{
			requestEffective = request;
		}

		final CostDetailCreateResult result = utils.createCostDetailRecordWithChangedCosts(requestEffective, currentCosts);
		currentCosts.addWeightedAverage(requestEffective.getAmt(), requestEffective.getQty());

		return result;
	}

	private CostDetailCreateResult createComponentIssue(
			@NonNull final CostDetailCreateRequest request,
			@NonNull final PPOrderId orderId)
	{
		// TODO Auto-generated method stub
		final PPOrderCosts orderCosts = ppOrderCostsService.getByOrderId(orderId);
		throw new UnsupportedOperationException();
	}

	private CostDetailCreateResult createActivityControl(final CostDetailCreateRequest withProductId, final Duration totalDuration)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void voidCosts(final CostDetailVoidRequest request)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<CostAmount> calculateSeedCosts(final CostSegment costSegment, final OrderLineId orderLineId)
	{
		return Optional.empty();
	}

}

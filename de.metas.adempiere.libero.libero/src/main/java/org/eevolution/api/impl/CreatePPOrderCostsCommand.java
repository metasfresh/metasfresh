package org.eevolution.api.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.adempiere.service.OrgId;
import org.compiere.Adempiere;
import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.PPOrderCost;
import org.eevolution.api.PPOrderCosts;
import org.eevolution.api.PPOrderRoutingActivity;
import org.eevolution.costing.BOM;
import org.eevolution.costing.OrderBOMCostCalculatorRepository;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegment;
import de.metas.costing.CostSegment.CostSegmentBuilder;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costing.IProductCostingBL;
import de.metas.material.planning.IResourceProductService;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.GuavaCollectors;
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

final class CreatePPOrderCostsCommand
{
	// services
	private final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);
	private final IResourceProductService resourceProductService = Services.get(IResourceProductService.class);
	//
	private final IPPOrderBOMDAO orderBOMsRepo = Services.get(IPPOrderBOMDAO.class);
	private final IPPOrderRoutingRepository orderRoutingRepo = Services.get(IPPOrderRoutingRepository.class);
	private final IPPOrderCostBL orderCostsService = Services.get(IPPOrderCostBL.class);
	//
	private final ICurrentCostsRepository currentCostsRepository = Adempiere.getBean(ICurrentCostsRepository.class);
	private final ICostElementRepository costElementsRepo = Adempiere.getBean(ICostElementRepository.class);

	// parameters
	private final PPOrderId ppOrderId;
	private final ClientId clientId;
	private final OrgId orgId;
	private final ProductId mainProductId;
	private final AttributeSetInstanceId mainProductAsiId;
	private final AcctSchema acctSchema;

	public CreatePPOrderCostsCommand(@NonNull final I_PP_Order ppOrder)
	{
		ppOrderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());
		clientId = ClientId.ofRepoId(ppOrder.getAD_Client_ID());
		orgId = OrgId.ofRepoId(ppOrder.getAD_Org_ID());
		mainProductId = ProductId.ofRepoId(ppOrder.getM_Product_ID());
		mainProductAsiId = AttributeSetInstanceId.ofRepoIdOrNone(ppOrder.getM_AttributeSetInstance_ID());

		final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);
		acctSchema = acctSchemasRepo.getByCliendAndOrg(clientId, orgId);
	}

	public PPOrderCosts execute()
	{
		PPOrderCosts orderCosts = createNewOrderCosts();

		for (final CostingMethod costingMethod : getCostingMethodsWhichRequiredBOMRollup())
		{
			orderCosts = rollupForCostingMethod(orderCosts, costingMethod);
		}

		orderCostsService.save(orderCosts);

		return orderCosts;
	}

	private PPOrderCosts rollupForCostingMethod(final PPOrderCosts orderCosts, final CostingMethod costingMethod)
	{
		final Set<CostElementId> costElementIds = costElementsRepo.getIdsByCostingMethod(costingMethod);
		if (costElementIds.isEmpty())
		{
			return orderCosts;
		}

		final OrderBOMCostCalculatorRepository costingBOMRepo = OrderBOMCostCalculatorRepository.builder()
				.orderId(ppOrderId)
				.mainProductId(mainProductId)
				.mainProductAsiId(mainProductAsiId)
				.clientId(clientId)
				.orgId(orgId)
				.acctSchema(acctSchema)
				.costElementIds(costElementIds)
				.build();

		final BOM bom = costingBOMRepo.getBOM(orderCosts);
		bom.rollupCosts();

		// TODO: instead of clearing the BOM's own cost price, we shall rollout the routing costs!
		costElementIds.forEach(bom::clearBOMOwnCostPrice);

		return costingBOMRepo.changeOrderCostsFromBOM(orderCosts, bom);
	}

	private PPOrderCosts createNewOrderCosts()
	{
		final List<PPOrderCost> orderCostsList = extractCostSegments()
				.stream()
				.flatMap(this::createPPOrderCostsAndStream)
				.collect(ImmutableList.toImmutableList());

		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.costs(orderCostsList)
				.build();
		return orderCosts;
	}

	private Set<CostSegment> extractCostSegments()
	{
		return ImmutableSet.<CostSegment> builder()
				.add(createCostSegmentForMainProduct())
				.addAll(createCostSegmentsForBOMLines())
				.addAll(createCostSegmentsForWorkflowNodes())
				.build();
	}

	private CostSegment createCostSegmentForMainProduct()
	{
		return prepareCostSegment(mainProductId)
				.attributeSetInstanceId(mainProductAsiId)
				.build();
	}

	private CostSegmentBuilder prepareCostSegment(final ProductId productId)
	{
		return CostSegment.builder()
				.costingLevel(productCostingBL.getCostingLevel(productId, acctSchema))
				.acctSchemaId(acctSchema.getId())
				.costTypeId(acctSchema.getCosting().getCostTypeId())
				.productId(productId)
				.clientId(clientId)
				.orgId(orgId);
	}

	private ImmutableSet<CostSegment> createCostSegmentsForBOMLines()
	{
		return orderBOMsRepo.retrieveOrderBOMLines(ppOrderId)
				.stream()
				.map(this::createCostSegmentForBOMLine)
				.collect(ImmutableSet.toImmutableSet());
	}

	private CostSegment createCostSegmentForBOMLine(final I_PP_Order_BOMLine bomLine)
	{
		final ProductId productId = ProductId.ofRepoId(bomLine.getM_Product_ID());
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(bomLine.getM_AttributeSetInstance_ID());

		return prepareCostSegment(productId)
				.attributeSetInstanceId(asiId)
				.build();
	}

	private ImmutableSet<CostSegment> createCostSegmentsForWorkflowNodes()
	{
		return orderRoutingRepo.getByOrderId(ppOrderId)
				.getActivities()
				.stream()
				.map(this::createCostSegmentForOrderActivityOrNull)
				.filter(Predicates.notNull())
				.collect(ImmutableSet.toImmutableSet());
	}

	private CostSegment createCostSegmentForOrderActivityOrNull(final PPOrderRoutingActivity activity)
	{
		final ResourceId resourceId = activity.getResourceId();
		if (resourceId == null)
		{
			return null;
		}

		final ProductId resourceProductId = resourceProductService.getProductIdByResourceId(resourceId);

		return prepareCostSegment(resourceProductId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.build();
	}

	private Stream<PPOrderCost> createPPOrderCostsAndStream(final CostSegment costSegment)
	{
		final Set<CostElementId> costElementIds = costElementsRepo.getActiveCostElementIds();
		if (costElementIds.isEmpty())
		{
			// shall not happen!
			return Stream.empty();
		}

		final Map<CostElementId, PPOrderCost> orderCostsByCostElementId = currentCostsRepository.getByCostSegmentAndCostElements(costSegment, costElementIds)
				.stream()
				.map(currentCost -> createPPOrderCost(costSegment, currentCost))
				.collect(GuavaCollectors.toImmutableMapByKey(PPOrderCost::getCostElementId));

		//
		final CostPrice zero = CostPrice.zero(acctSchema.getCurrencyId());
		final Stream<PPOrderCost> zeroOrderCosts = Sets.difference(costElementIds, orderCostsByCostElementId.keySet())
				.stream()
				.map(costElementId -> PPOrderCost.builder()
						.costSegmentAndElement(costSegment.withCostElementId(costElementId))
						.price(zero)
						.build());

		return Stream.concat(
				orderCostsByCostElementId.values().stream(),
				zeroOrderCosts);
	}

	private PPOrderCost createPPOrderCost(final CostSegment costSegment, final CurrentCost currentCost)
	{
		return PPOrderCost.builder()
				.costSegmentAndElement(costSegment.withCostElementId(currentCost.getCostElementId()))
				.price(currentCost.getCostPrice())
				.build();
	}

	private Set<CostingMethod> getCostingMethodsWhichRequiredBOMRollup()
	{
		return ImmutableSet.of(
				CostingMethod.AverageInvoice,
				CostingMethod.AveragePO);
	}
}

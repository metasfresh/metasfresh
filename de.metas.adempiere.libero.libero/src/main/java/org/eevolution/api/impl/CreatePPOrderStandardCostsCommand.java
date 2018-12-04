package org.eevolution.api.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.adempiere.service.OrgId;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Product;
import org.eevolution.api.IPPOrderCostDAO;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.PPOrderCost;
import org.eevolution.api.PPOrderCosts;
import org.eevolution.api.PPOrderRoutingActivity;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.AggregatedCostPrice;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingMethod;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costing.IProductCostingBL;
import de.metas.material.planning.IResourceProductService;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.PPOrderId;
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

final class CreatePPOrderStandardCostsCommand
{
	// services
	private final IPPOrderCostDAO ppOrderCostsRepo = Services.get(IPPOrderCostDAO.class);
	private final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);
	private final IPPOrderBOMDAO orderBOMsRepo = Services.get(IPPOrderBOMDAO.class);
	private final IPPOrderRoutingRepository orderWorkflowsRepo = Services.get(IPPOrderRoutingRepository.class);
	private final IResourceProductService resourceProductService = Services.get(IResourceProductService.class);
	private final ICurrentCostsRepository currentCostsRepository = Adempiere.getBean(ICurrentCostsRepository.class);

	// parameters
	private final PPOrderId ppOrderId;
	private final ClientId clientId;
	private final OrgId orgId;
	private final ProductId mainProductId;
	private final AttributeSetInstanceId mainProductAsiId;
	private final AcctSchema acctSchema;

	public CreatePPOrderStandardCostsCommand(@NonNull final I_PP_Order ppOrder)
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
		final List<PPOrderCost> orderCostsList = extractCostSegments()
				.stream()
				.flatMap(this::createPPOrderCostsAndStream)
				.collect(ImmutableList.toImmutableList());

		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.costs(orderCostsList)
				.build();

		ppOrderCostsRepo.save(orderCosts);

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
		return CostSegment.builder()
				.costingLevel(productCostingBL.getCostingLevel(mainProductId, acctSchema))
				.acctSchemaId(acctSchema.getId())
				.costTypeId(acctSchema.getCosting().getCostTypeId())
				.productId(mainProductId)
				.clientId(clientId)
				.orgId(orgId)
				.attributeSetInstanceId(mainProductAsiId)
				.build();
	}

	private ImmutableSet<CostSegment> createCostSegmentsForBOMLines()
	{
		return orderBOMsRepo.retrieveOrderBOMLines(ppOrderId, I_PP_Order_BOMLine.class)
				.stream()
				.map(this::createCostSegmentForBOMLine)
				.collect(ImmutableSet.toImmutableSet());
	}

	private CostSegment createCostSegmentForBOMLine(final I_PP_Order_BOMLine bomLine)
	{
		final ProductId productId = ProductId.ofRepoId(bomLine.getM_Product_ID());

		return CostSegment.builder()
				.costingLevel(productCostingBL.getCostingLevel(productId, acctSchema))
				.acctSchemaId(acctSchema.getId())
				.costTypeId(acctSchema.getCosting().getCostTypeId())
				.productId(productId)
				.clientId(ClientId.ofRepoId(bomLine.getAD_Client_ID()))
				.orgId(OrgId.ofRepoId(bomLine.getAD_Org_ID()))
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(bomLine.getM_AttributeSetInstance_ID()))
				.build();
	}

	private ImmutableSet<CostSegment> createCostSegmentsForWorkflowNodes()
	{
		return orderWorkflowsRepo.getByOrderId(ppOrderId)
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

		final I_M_Product resourceProduct = resourceProductService.getProductByResourceId(resourceId);
		if (resourceProduct == null)
		{
			// shall not happen, but we can skip it for now
			return null;
		}
		final ProductId resourceProductId = ProductId.ofRepoId(resourceProduct.getM_Product_ID());

		return CostSegment.builder()
				.costingLevel(productCostingBL.getCostingLevel(resourceProduct, acctSchema))
				.acctSchemaId(acctSchema.getId())
				.costTypeId(acctSchema.getCosting().getCostTypeId())
				.productId(resourceProductId)
				.clientId(clientId)
				.orgId(orgId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.build();

	}

	private Stream<PPOrderCost> createPPOrderCostsAndStream(final CostSegment costSegment)
	{
		final AggregatedCostPrice price = currentCostsRepository.getAggregatedCostPriceByCostSegmentAndCostingMethod(costSegment, CostingMethod.StandardCosting)
				.orElse(null);
		if (price == null)
		{
			return Stream.empty();
		}

		return price.getCostElements()
				.stream()
				.map(costElement -> PPOrderCost.builder()
						.costSegmentAndElement(costSegment.withCostElementId(costElement.getId()))
						.price(price.getCostPriceForCostElement(costElement))
						.build());
	}
}

package org.eevolution.api.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.compiere.Adempiere;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.PPOrderCost;
import org.eevolution.api.PPOrderCostTrxType;
import org.eevolution.api.PPOrderCosts;
import org.eevolution.api.PPOrderRoutingActivity;
import org.eevolution.costing.BOM;
import org.eevolution.costing.OrderBOMCostCalculatorRepository;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import java.util.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegment;
import de.metas.costing.CostSegment.CostSegmentBuilder;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costing.IProductCostingBL;
import de.metas.material.planning.IResourceProductService;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
	private final Quantity mainProductQty;
	private final AcctSchema acctSchema;

	public CreatePPOrderCostsCommand(@NonNull final I_PP_Order ppOrder)
	{
		ppOrderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());
		clientId = ClientId.ofRepoId(ppOrder.getAD_Client_ID());
		orgId = OrgId.ofRepoId(ppOrder.getAD_Org_ID());
		mainProductId = ProductId.ofRepoId(ppOrder.getM_Product_ID());
		mainProductAsiId = AttributeSetInstanceId.ofRepoIdOrNone(ppOrder.getM_AttributeSetInstance_ID());

		final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
		mainProductQty = ppOrderBL.getQtyOrdered(ppOrder);

		final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);
		acctSchema = acctSchemasRepo.getByCliendAndOrg(clientId, orgId);
	}

	public PPOrderCosts execute()
	{
		PPOrderCosts orderCosts = createNewOrderCosts();

		for (final CostingMethod costingMethod : getCostingMethodsWhichRequiredBOMRollup())
		{
			rollupForCostingMethod(orderCosts, costingMethod);
		}

		orderCostsService.save(orderCosts);

		return orderCosts;
	}

	private void rollupForCostingMethod(final PPOrderCosts orderCosts, final CostingMethod costingMethod)
	{
		final Set<CostElementId> costElementIds = costElementsRepo.getIdsByCostingMethod(costingMethod);
		if (costElementIds.isEmpty())
		{
			return;
		}

		final OrderBOMCostCalculatorRepository costingBOMRepo = OrderBOMCostCalculatorRepository.builder()
				.orderId(ppOrderId)
				.mainProductId(mainProductId)
				.mainProductAsiId(mainProductAsiId)
				.mainProductQty(mainProductQty)
				.clientId(clientId)
				.orgId(orgId)
				.acctSchema(acctSchema)
				.costElementIds(costElementIds)
				.build();

		final BOM bom = costingBOMRepo.getBOM(orderCosts);
		bom.rollupCosts();

		// TODO: instead of clearing the BOM's own cost price, we shall rollout the routing costs!
		costElementIds.forEach(bom::clearBOMOwnCostPrice);

		costingBOMRepo.changeOrderCostsFromBOM(orderCosts, bom);
	}

	private PPOrderCosts createNewOrderCosts()
	{
		final List<PPOrderCost> orderCostsList = extractCandidates()
				.stream()
				.flatMap(this::createPPOrderCostsAndStream)
				.collect(ImmutableList.toImmutableList());

		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.costs(orderCostsList)
				.build();
		return orderCosts;
	}

	private Set<PPOrderCostCandidate> extractCandidates()
	{
		return ImmutableSet.<PPOrderCostCandidate> builder()
				.add(createCandidateFromMainProduct())
				.addAll(createCandidatesFromBOMLines())
				.addAll(createCandidatesFromRouting())
				.build();
	}

	private PPOrderCostCandidate createCandidateFromMainProduct()
	{
		return PPOrderCostCandidate.builder()
				.trxType(PPOrderCostTrxType.MainProduct)
				.costSegment(prepareCostSegment(mainProductId)
						.attributeSetInstanceId(mainProductAsiId)
						.build())
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

	private ImmutableSet<PPOrderCostCandidate> createCandidatesFromBOMLines()
	{
		return orderBOMsRepo.retrieveOrderBOMLines(ppOrderId)
				.stream()
				.map(this::createCandidateFromBOMLine)
				.collect(ImmutableSet.toImmutableSet());
	}

	private PPOrderCostCandidate createCandidateFromBOMLine(final I_PP_Order_BOMLine bomLine)
	{
		final ProductId productId = ProductId.ofRepoId(bomLine.getM_Product_ID());
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(bomLine.getM_AttributeSetInstance_ID());
		final BOMComponentType bomComponentType = BOMComponentType.ofCode(bomLine.getComponentType());
		final PPOrderCostTrxType trxType = PPOrderCostTrxType.ofBOMComponentType(bomComponentType);
		final Percent coProductCostDistributionPercent = trxType.isCoProduct()
				? Percent.of("0.01") // TODO : FIXME see https://github.com/metasfresh/metasfresh/issues/4947
				: null;

		return PPOrderCostCandidate.builder()
				.trxType(trxType)
				.costSegment(prepareCostSegment(productId)
						.attributeSetInstanceId(asiId)
						.build())
				.coProductCostDistributionPercent(coProductCostDistributionPercent)
				.build();
	}

	private ImmutableSet<PPOrderCostCandidate> createCandidatesFromRouting()
	{
		return orderRoutingRepo.getByOrderId(ppOrderId)
				.getActivities()
				.stream()
				.map(this::createCostSegmentForOrderActivityOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	private PPOrderCostCandidate createCostSegmentForOrderActivityOrNull(final PPOrderRoutingActivity activity)
	{
		final ResourceId resourceId = activity.getResourceId();
		if (resourceId == null)
		{
			return null;
		}

		final ProductId resourceProductId = resourceProductService.getProductIdByResourceId(resourceId);

		return PPOrderCostCandidate.builder()
				.trxType(PPOrderCostTrxType.ResourceUtilization)
				.costSegment(prepareCostSegment(resourceProductId)
						.attributeSetInstanceId(AttributeSetInstanceId.NONE)
						.build())
				.build();
	}

	private Stream<PPOrderCost> createPPOrderCostsAndStream(final PPOrderCostCandidate candidate)
	{
		final Set<CostElementId> costElementIds = costElementsRepo.getActiveCostElementIds();
		if (costElementIds.isEmpty())
		{
			// shall not happen!
			return Stream.empty();
		}

		final Map<CostElementId, PPOrderCost> orderCostsByCostElementId = currentCostsRepository.getByCostSegmentAndCostElements(candidate.getCostSegment(), costElementIds)
				.stream()
				.map(currentCost -> createPPOrderCost(candidate, currentCost))
				.collect(GuavaCollectors.toImmutableMapByKey(PPOrderCost::getCostElementId));

		//
		final CurrencyId currencyId = acctSchema.getCurrencyId();
		final Stream<PPOrderCost> zeroOrderCosts = Sets.difference(costElementIds, orderCostsByCostElementId.keySet())
				.stream()
				.map(costElementId -> createZeroPPOrderCost(candidate, costElementId, currencyId));

		return Stream.concat(
				orderCostsByCostElementId.values().stream(),
				zeroOrderCosts);
	}

	private static PPOrderCost createPPOrderCost(final PPOrderCostCandidate candidate, final CurrentCost currentCost)
	{
		final PPOrderCostTrxType trxType = candidate.getTrxType();
		final CostSegment costSegment = candidate.getCostSegment();

		return PPOrderCost.builder()
				.trxType(trxType)
				.costSegmentAndElement(costSegment.withCostElementId(currentCost.getCostElementId()))
				.price(currentCost.getCostPrice())
				.build();
	}

	private static PPOrderCost createZeroPPOrderCost(final PPOrderCostCandidate candidate, final CostElementId costElementId, final CurrencyId currencyId)
	{
		final PPOrderCostTrxType trxType = candidate.getTrxType();
		final CostSegmentAndElement costSegmentAndElement = candidate.getCostSegment().withCostElementId(costElementId);
		final CostPrice zeroPrice = CostPrice.zero(currencyId);
		final Percent coProductCostDistributionPercent = candidate.getCoProductCostDistributionPercent();
		return PPOrderCost.builder()
				.trxType(trxType)
				.coProductCostDistributionPercent(coProductCostDistributionPercent)
				.costSegmentAndElement(costSegmentAndElement)
				.price(zeroPrice)
				.build();
	}

	private Set<CostingMethod> getCostingMethodsWhichRequiredBOMRollup()
	{
		return ImmutableSet.of(
				CostingMethod.AverageInvoice,
				CostingMethod.AveragePO);
	}

	@Value
	@Builder
	private static class PPOrderCostCandidate
	{
		@NonNull
		CostSegment costSegment;
		@NonNull
		PPOrderCostTrxType trxType;
		@Nullable
		Percent coProductCostDistributionPercent;
	}
}

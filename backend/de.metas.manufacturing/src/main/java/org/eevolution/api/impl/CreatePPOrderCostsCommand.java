package org.eevolution.api.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostAmount;
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
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.logging.LogManager;
import de.metas.material.planning.IResourceProductService;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.PPOrderCost;
import org.eevolution.api.PPOrderCostTrxType;
import org.eevolution.api.PPOrderCosts;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRoutingActivity;
import org.eevolution.costing.BOM;
import org.eevolution.costing.OrderBOMCostCalculatorRepository;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

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
	private static final Logger logger = LogManager.getLogger(CreatePPOrderCostsCommand.class);
	private final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);
	private final IResourceProductService resourceProductService = Services.get(IResourceProductService.class);
	//
	private final IPPOrderBOMDAO orderBOMsRepo = Services.get(IPPOrderBOMDAO.class);
	private final IPPOrderRoutingRepository orderRoutingRepo = Services.get(IPPOrderRoutingRepository.class);
	private final IPPOrderCostBL orderCostsService = Services.get(IPPOrderCostBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	//
	private final CurrencyRepository currencyRepository = SpringContextHolder.instance.getBean(CurrencyRepository.class);
	private final ICurrentCostsRepository currentCostsRepository = SpringContextHolder.instance.getBean(ICurrentCostsRepository.class);
	private final ICostElementRepository costElementsRepo = SpringContextHolder.instance.getBean(ICostElementRepository.class);

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

		final IPPOrderBOMBL orderBOMBL = Services.get(IPPOrderBOMBL.class);
		mainProductQty = orderBOMBL.getQuantities(ppOrder).getQtyRequiredToProduce();
		if (mainProductQty.signum() <= 0)
		{
			throw new AdempiereException("Qty of finished goods to produce shall be greater than zero for " + ppOrder);
		}

		final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);
		acctSchema = acctSchemasRepo.getByClientAndOrg(clientId, orgId);
	}

	public PPOrderCosts execute()
	{
		final PPOrderCosts orderCosts = createNewOrderCosts();

		for (final CostingMethod costingMethod : getCostingMethodsWhichRequiredBOMRollup())
		{
			rollupForCostingMethod(orderCosts, costingMethod);
		}

		orderCostsService.save(orderCosts);

		return orderCosts;
	}

	private void rollupForCostingMethod(
			final PPOrderCosts orderCosts,
			final CostingMethod costingMethod)
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

		if (orderCostsList.isEmpty())
		{
			throw new AdempiereException("No order costs found");
		}

		return PPOrderCosts.builder()
				.orderId(ppOrderId)
				.costs(orderCostsList)
				.build();
	}

	private Set<PPOrderCostCandidate> extractCandidates()
	{
		return ImmutableSet.<PPOrderCostCandidate>builder()
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
				.uomId(mainProductQty.getUomId())
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
				.collect(GuavaCollectors.distinctBy(PPOrderCostCandidate::getCostSegment))
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
				.uomId(UomId.ofRepoId(bomLine.getC_UOM_ID()))
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

	@Nullable
	private PPOrderCostCandidate createCostSegmentForOrderActivityOrNull(final PPOrderRoutingActivity activity)
	{
		final ResourceId resourceId = activity.getResourceId();
		if (resourceId.isNoResource())
		{
			return null;
		}

		final ProductId resourceProductId = resourceProductService.getProductIdByResourceId(resourceId);

		final UomId durationUomId = uomDAO.getUomIdByTemporalUnit(activity.getDurationUnit().getTemporalUnit());
		return PPOrderCostCandidate.builder()
				.trxType(PPOrderCostTrxType.ResourceUtilization)
				.costSegment(prepareCostSegment(resourceProductId)
						.attributeSetInstanceId(AttributeSetInstanceId.NONE)
						.build())
				.uomId(durationUomId)
				.build();
	}

	private Stream<PPOrderCost> createPPOrderCostsAndStream(final PPOrderCostCandidate candidate)
	{
		final Set<CostElementId> costElementIds = costElementsRepo.getIdsByClientId(candidate.getCostSegment().getClientId());
		if (costElementIds.isEmpty())
		{
			// shall not happen!
			logger.warn("No active costs elements found. Returning empty for {}", candidate);
			return Stream.empty();
		}

		final List<CurrentCost> currentCosts = currentCostsRepository.getByCostSegmentAndCostElements(candidate.getCostSegment(), costElementIds);
		if (currentCosts.isEmpty())
		{
			logger.debug("No current costs found {} and {}", candidate.getCostSegment(), costElementIds);
		}

		final Map<CostElementId, PPOrderCost> orderCostsByCostElementId = currentCosts
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

	private PPOrderCost createPPOrderCost(
			final PPOrderCostCandidate candidate,
			final CurrentCost currentCost)
	{
		final PPOrderCostTrxType trxType = candidate.getTrxType();
		final CostSegment costSegment = candidate.getCostSegment();

		final UomId uomId = candidate.getUomId();
		final I_C_UOM uom = uomDAO.getById(uomId);

		final CostPrice costPrice = convertCostPrice(currentCost.getCostPrice(), costSegment.getProductId(), uomId);

		return PPOrderCost.builder()
				.trxType(trxType)
				.costSegmentAndElement(costSegment.withCostElementId(currentCost.getCostElementId()))
				.price(costPrice)
				.accumulatedQty(Quantity.zero(uom))
				.build();
	}

	private CostPrice convertCostPrice(
			@NonNull final CostPrice costPrice,
			@NonNull final ProductId productId,
			@NonNull final UomId targetUomId)
	{
		final UomId uomId = costPrice.getUomId();
		final CurrencyPrecision costingPrecision = currencyRepository.getCostingPrecision(costPrice.getCurrencyId());

		return costPrice.convertAmounts(targetUomId, costAmount -> {
			final ProductPrice productPrice = ProductPrice.builder()
					.productId(productId)
					.uomId(uomId)
					.money(costAmount.toMoney())
					.build();
			final ProductPrice productPriceConv = uomConversionBL.convertProductPriceToUom(productPrice, targetUomId, costingPrecision);
			return CostAmount.ofProductPrice(productPriceConv);
		});
	}

	private PPOrderCost createZeroPPOrderCost(
			final PPOrderCostCandidate candidate,
			final CostElementId costElementId,
			final CurrencyId currencyId)
	{
		final PPOrderCostTrxType trxType = candidate.getTrxType();
		final CostSegmentAndElement costSegmentAndElement = candidate.getCostSegment().withCostElementId(costElementId);
		final Percent coProductCostDistributionPercent = candidate.getCoProductCostDistributionPercent();
		final I_C_UOM uom = uomDAO.getById(candidate.getUomId());

		return PPOrderCost.builder()
				.trxType(trxType)
				.coProductCostDistributionPercent(coProductCostDistributionPercent)
				.costSegmentAndElement(costSegmentAndElement)
				.price(CostPrice.zero(currencyId, candidate.getUomId()))
				.accumulatedQty(Quantity.zero(uom))
				.build();
	}

	private Set<CostingMethod> getCostingMethodsWhichRequiredBOMRollup()
	{
		return ImmutableSet.of(
				CostingMethod.AverageInvoice,
				CostingMethod.MovingAverageInvoice,
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
		@NonNull
		UomId uomId;
	}
}

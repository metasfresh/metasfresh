package org.eevolution.costing;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.api.PPOrderCost;
import org.eevolution.api.PPOrderCostTrxType;
import org.eevolution.api.PPOrderCosts;
import org.eevolution.model.I_PP_Order_BOMLine;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.acct.api.AcctSchema;
import de.metas.costing.CostElementId;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostingLevel;
import de.metas.costing.IProductCostingBL;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.Builder;
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

public class OrderBOMCostCalculatorRepository implements BOMCostCalculatorRepository
{
	// services
	private final IPPOrderBOMDAO orderBOMsRepo = Services.get(IPPOrderBOMDAO.class);
	private final IPPOrderBOMBL orderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final IPPOrderCostBL orderCostBL = Services.get(IPPOrderCostBL.class);
	private final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);

	private final PPOrderId orderId;
	private final ProductId mainProductId;
	private final AttributeSetInstanceId mainProductAsiId;
	private final Quantity mainProductQty;

	private final ImmutableSet<CostElementId> costElementIds;

	private final ClientId clientId;
	private final OrgId orgId;
	private final AcctSchema acctSchema;

	@Builder
	public OrderBOMCostCalculatorRepository(
			@NonNull final PPOrderId orderId,
			@NonNull final ProductId mainProductId,
			@NonNull final AttributeSetInstanceId mainProductAsiId,
			@NonNull final Quantity mainProductQty,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final AcctSchema acctSchema,
			@NonNull final Set<CostElementId> costElementIds)
	{
		Check.assumeNotEmpty(costElementIds, "costElementIds is not empty");

		this.orderId = orderId;
		this.mainProductId = mainProductId;
		this.mainProductAsiId = mainProductAsiId;
		this.mainProductQty = mainProductQty;
		this.clientId = clientId;
		this.orgId = orgId;
		this.acctSchema = acctSchema;
		this.costElementIds = ImmutableSet.copyOf(costElementIds);
	}

	private void assertExpectedOrderId(final PPOrderId orderId)
	{
		Check.assumeEquals(this.orderId, orderId, "orderId");
	}

	@Override
	public Optional<BOM> getBOM(@NonNull final ProductId productId)
	{
		Check.assumeEquals(productId, mainProductId, "productId");

		final PPOrderCosts orderCosts = orderCostBL.getByOrderId(orderId);

		final BOM bom = getBOM(orderCosts);
		return Optional.of(bom);
	}

	public BOM getBOM(final PPOrderCosts orderCosts)
	{
		assertExpectedOrderId(orderCosts.getOrderId());

		final ImmutableList<BOMLine> bomLines = orderBOMsRepo.retrieveOrderBOMLines(orderId)
				.stream()
				.map(orderBOMLineRecord -> toCostingBOMLine(orderBOMLineRecord, orderCosts))
				.collect(ImmutableList.toImmutableList());

		return BOM.builder()
				.productId(mainProductId)
				.asiId(mainProductAsiId)
				.qty(mainProductQty.toBigDecimal())
				.lines(bomLines)
				.costPrice(getProductCostPrice(mainProductId, orderCosts))
				.build();
	}

	private BOMLine toCostingBOMLine(final I_PP_Order_BOMLine orderBOMLineRecord, final PPOrderCosts orderCosts)
	{
		final ProductId productId = ProductId.ofRepoId(orderBOMLineRecord.getM_Product_ID());
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(orderBOMLineRecord.getM_AttributeSetInstance_ID());
		final BOMComponentType componentType = BOMComponentType.ofCode(orderBOMLineRecord.getComponentType());
		final Percent scrapPercent = Percent.of(orderBOMLineRecord.getScrap());

		final Quantity qty;
		if (componentType.isIssue())
		{
			qty = orderBOMBL.getQtyRequiredToIssue(orderBOMLineRecord);
		}
		else if (componentType.isReceipt())
		{
			qty = orderBOMBL.getQtyRequiredToReceive(orderBOMLineRecord);
		}
		else
		{
			throw new AdempiereException("Component type not supported: " + componentType);
		}

		final Percent coProductCostDistributionPercent = componentType.isCoProduct()
				? orderBOMBL.getCoProductCostDistributionPercent(orderBOMLineRecord)
				: null;

		return BOMLine.builder()
				.componentType(componentType)
				.componentId(productId)
				.asiId(asiId)
				.qty(qty)
				.scrapPercent(scrapPercent)
				.costPrice(getProductCostPrice(productId, orderCosts))
				.coProductCostDistributionPercent(coProductCostDistributionPercent)
				.build();

	}

	private BOMCostPrice getProductCostPrice(final ProductId productId, final PPOrderCosts orderCosts)
	{
		final List<BOMCostElementPrice> costElementPrices = orderCosts.getByProductAndCostElements(productId, costElementIds)
				.stream()
				.map(this::toBOMCostElementPrice)
				.collect(ImmutableList.toImmutableList());

		return BOMCostPrice.builder()
				.productId(productId)
				.costElementPrices(costElementPrices)
				.build();
	}

	private BOMCostElementPrice toBOMCostElementPrice(final PPOrderCost cost)
	{
		return BOMCostElementPrice.builder()
				.id(cost.getId())
				.costElementId(cost.getCostElementId())
				.costPrice(cost.getPrice())
				.build();
	}

	@Override
	public void save(@NonNull final BOM bom)
	{
		final PPOrderCosts orderCosts = orderCostBL.getByOrderId(orderId);
		changeOrderCostsFromBOM(orderCosts, bom);
		orderCostBL.save(orderCosts);
	}

	public void changeOrderCostsFromBOM(final PPOrderCosts orderCosts, final BOM bom)
	{
		final LinkedHashMap<CostSegmentAndElement, PPOrderCost> newOrderCosts = new LinkedHashMap<>();

		//
		// BOM Header Product Cost
		for (final BOMCostElementPrice elementCostPrice : bom.getCostPrice().getElementPrices())
		{
			final CostSegmentAndElement costSegmentAndElement = createCostSegmentAndElement(bom.getProductId(), bom.getAsiId(), elementCostPrice.getCostElementId());

			PPOrderCost elementOrderCost = newOrderCosts.get(costSegmentAndElement);
			if (elementOrderCost == null)
			{
				elementOrderCost = PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MainProduct)
						.costSegmentAndElement(costSegmentAndElement)
						.price(elementCostPrice.getCostPrice())
						.build();
			}
			else
			{
				elementOrderCost = elementOrderCost.withPrice(elementCostPrice.getCostPrice());
			}

			newOrderCosts.put(elementOrderCost.getCostSegmentAndElement(), elementOrderCost);
		}

		//
		// BOM Line Product Cost
		for (final BOMLine bomLine : bom.getLines())
		{
			final PPOrderCostTrxType trxType = PPOrderCostTrxType.ofBOMComponentType(bomLine.getComponentType());
			final Percent coProductCostDistributionPercent = trxType.isCoProduct()
					? bomLine.getCoProductCostDistributionPercent()
					: null;

			for (final BOMCostElementPrice elementCostPrice : bomLine.getCostPrice().getElementPrices())
			{
				final CostSegmentAndElement costSegmentAndElement = createCostSegmentAndElement(bomLine.getComponentId(), bomLine.getAsiId(), elementCostPrice.getCostElementId());

				PPOrderCost elementOrderCost = newOrderCosts.get(costSegmentAndElement);
				if (elementOrderCost == null)
				{
					elementOrderCost = PPOrderCost.builder()
							.trxType(trxType)
							.costSegmentAndElement(costSegmentAndElement)
							.price(elementCostPrice.getCostPrice())
							.coProductCostDistributionPercent(coProductCostDistributionPercent)
							.build();
				}
				else
				{
					elementOrderCost = elementOrderCost.withPrice(elementCostPrice.getCostPrice());
				}

				newOrderCosts.put(elementOrderCost.getCostSegmentAndElement(), elementOrderCost);
			}
		}

		//
		orderCosts.removeByProductsAndCostElements(bom.getProductIds(), costElementIds);
		orderCosts.addCosts(newOrderCosts.values());
	}

	private CostSegmentAndElement createCostSegmentAndElement(
			final ProductId productId,
			final AttributeSetInstanceId asiId,
			final CostElementId costElementId)
	{
		final CostingLevel costingLevel = productCostingBL.getCostingLevel(productId, acctSchema);

		return CostSegmentAndElement.builder()
				.costingLevel(costingLevel)
				.acctSchemaId(acctSchema.getId())
				.costTypeId(acctSchema.getCosting().getCostTypeId())
				.clientId(clientId)
				.orgId(orgId)
				.productId(productId)
				.attributeSetInstanceId(asiId)
				.costElementId(costElementId)
				.build();
	}

	@Override
	public void resetComponentsCostPrices(final ProductId productId)
	{
		throw new UnsupportedOperationException();
	}

}

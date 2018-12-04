package org.eevolution.costing;

import java.util.List;
import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.api.PPOrderCost;
import org.eevolution.api.PPOrderCosts;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostingMethod;
import de.metas.costing.ICostElementRepository;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
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

	private final PPOrderId orderId;
	private final ProductId mainProductId;

	private final ImmutableSet<CostElementId> costElementIds;
	private final PPOrderCosts orderCosts;

	public OrderBOMCostCalculatorRepository(
			@NonNull final I_PP_Order ppOrder,
			@NonNull final CostingMethod costingMethod)
	{
		orderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());
		mainProductId = ProductId.ofRepoId(ppOrder.getM_Product_ID());

		final ICostElementRepository costElementsRepo = Adempiere.getBean(ICostElementRepository.class);
		costElementIds = costElementsRepo.getByCostingMethod(costingMethod)
				.stream()
				.map(CostElement::getId)
				.collect(ImmutableSet.toImmutableSet());
		if (costElementIds.isEmpty())
		{
			throw new AdempiereException("No cost elements found for " + costingMethod);
		}

		final IPPOrderCostBL orderCostBL = Services.get(IPPOrderCostBL.class);
		orderCosts = orderCostBL.getByOrderId(orderId);
	}

	@Override
	public Optional<BOM> getBOM(@NonNull final ProductId productId)
	{
		Check.assumeEquals(productId, mainProductId, "productId");

		final ImmutableList<BOMLine> bomLines = orderBOMsRepo.retrieveOrderBOMLines(orderId, I_PP_Order_BOMLine.class)
				.stream()
				.map(this::toCostingBOMLine)
				.collect(ImmutableList.toImmutableList());

		final BOM bom = BOM.builder()
				.productId(productId)
				.lines(bomLines)
				.costPrice(BOMCostPrice.empty(productId))
				.build();

		return Optional.of(bom);
	}

	private BOMLine toCostingBOMLine(final I_PP_Order_BOMLine orderBOMLineRecord)
	{
		final ProductId productId = ProductId.ofRepoId(orderBOMLineRecord.getM_Product_ID());
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

		return BOMLine.builder()
				.componentType(componentType)
				.componentId(productId)
				.qty(qty)
				.scrapPercent(scrapPercent)
				.costPrice(getBOMLineCostPrice(productId))
				.build();

	}

	private BOMCostPrice getBOMLineCostPrice(final ProductId productId)
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
				.repoId(cost.getRepoId())
				.costElementId(cost.getCostSegmentAndElement().getCostElementId())
				.costPrice(cost.getPrice())
				.build();
	}

	@Override
	public void save(final BOM bom)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public void resetComponentsCostPrices(final ProductId productId)
	{
		throw new UnsupportedOperationException();
	}

}

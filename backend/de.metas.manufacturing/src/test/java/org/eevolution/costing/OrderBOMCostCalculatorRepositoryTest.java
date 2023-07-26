/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2020 metas GmbH
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

package org.eevolution.costing;

import com.google.common.collect.ImmutableSet;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostAmount;
import de.metas.costing.CostPrice;
import org.eevolution.api.PPOrderId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.api.impl.PPOrderCostsTestHelper;
import org.eevolution.model.I_PP_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class OrderBOMCostCalculatorRepositoryTest
{
	private PPOrderCostsTestHelper helper;
	private IPPOrderCostBL ppOrderCostBL;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		helper = new PPOrderCostsTestHelper();
		ppOrderCostBL = Services.get(IPPOrderCostBL.class);
	}

	@Test
	public void differentStockingAndBomUOMs()
	{
		final ProductId finishedGoodsProductId = BusinessTestHelper.createProductId("finished goods", helper.uomBag);
		final ProductId componentId = BusinessTestHelper.createProductId("component", helper.uomBag);

		// finished good: 1 Bag = 20 Each
		helper.uomConversionDAO.createUOMConversion(CreateUOMConversionRequest.builder()
				.productId(finishedGoodsProductId)
				.fromUomId(helper.uomBagId)
				.toUomId(helper.uomEachId)
				.fromToMultiplier(new BigDecimal("20"))
				.build());

		// component: 1 Bag = 15 Kg
		helper.uomConversionDAO.createUOMConversion(CreateUOMConversionRequest.builder()
				.productId(componentId)
				.fromUomId(helper.uomBagId)
				.toUomId(helper.uomKgId)
				.fromToMultiplier(new BigDecimal("15"))
				.build());

		// component current cost price: 1 EUR/Bag
		helper.currentCost().productId(componentId).currentCostPrice("1").uom(helper.uomBag).build();
		helper.currentCost().productId(finishedGoodsProductId).currentCostPrice("1").uom(helper.uomBag).build();

		// Manufacturing order
		// IMPORTANT: remark that the finished good and component UOMs are different from their stocking UOM!
		final I_PP_Order ppOrder = helper.order()
				.finishedGoodsProductId(finishedGoodsProductId).finishedGoodsQty("100").finishedGoodsUOM(helper.uomEach)
				.componentId(componentId).componentQtyRequired("200").componentUOM(helper.uomKg) // i.e. we need 2 Kg for one finished good
				.build();
		ppOrderCostBL.createOrderCosts(ppOrder);

		//
		// Create the repository, i.e. the class under test
		final OrderBOMCostCalculatorRepository repo = OrderBOMCostCalculatorRepository.builder()
				.orderId(PPOrderId.ofRepoId(ppOrder.getPP_Order_ID()))
				.mainProductId(finishedGoodsProductId)
				.mainProductAsiId(AttributeSetInstanceId.NONE)
				.mainProductQty(Quantity.of("100", helper.uomEach))
				.clientId(helper.clientId)
				.orgId(OrgId.ANY)
				.acctSchema(helper.getAcctSchema())
				.costElementIds(ImmutableSet.of(helper.costElement.getId()))
				.build();

		final BOM bom = repo.getBOM(finishedGoodsProductId).get();
		Assertions.assertThat(bom)
				.usingRecursiveComparison()
				.isEqualTo(BOM.builder()
						.productId(finishedGoodsProductId)
						.asiId(AttributeSetInstanceId.NONE)
						.qty(Quantity.of("100", helper.uomEach))
						.costPrice(BOMCostPrice.builder()
								.productId(finishedGoodsProductId)
								.uomId(helper.uomEachId)
								.costElementPrice(BOMCostElementPrice.builder()
										.id(bom.getCostPrice().getElementPrices().iterator().next().getId()) // copy from BOM
										.costElementId(helper.costElement.getId())
										.costPrice(CostPrice.builder()
												.uomId(helper.uomEachId)
												.ownCostPrice(CostAmount.zero(helper.currencyId))
												.componentsCostPrice(CostAmount.of("0.1334", helper.currencyId)) // = component cost price x 2(=200/100)
												.build())
										.build())
								.build())
						.line(BOMLine.builder()
								.componentType(BOMComponentType.Component)
								.componentId(componentId)
								.qty(Quantity.of("200", helper.uomKg))
								.costPrice(BOMCostPrice.builder()
										.productId(componentId)
										.uomId(helper.uomKgId)
										.costElementPrice(BOMCostElementPrice.builder()
												.id(bom.getLines().get(0).getCostPrice().getElementPrices().get(0).getId()) // copy
												.costElementId(helper.costElement.getId())
												.costPrice(CostPrice.builder()
														.uomId(helper.uomKgId)
														.ownCostPrice(CostAmount.of("0.0667", helper.currencyId))
														.componentsCostPrice(CostAmount.zero(helper.currencyId))
														.build())
												.build())
										.build())
								.build())
						.build());
	}

}
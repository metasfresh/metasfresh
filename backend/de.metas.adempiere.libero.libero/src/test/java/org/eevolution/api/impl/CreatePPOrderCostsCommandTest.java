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

package org.eevolution.api.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostAmount;
import de.metas.costing.CostPrice;
import de.metas.product.ProductId;
import de.metas.uom.CreateUOMConversionRequest;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.eevolution.api.PPOrderCost;
import org.eevolution.api.PPOrderCosts;
import org.eevolution.model.I_PP_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
public class CreatePPOrderCostsCommandTest
{
	protected PPOrderCostsTestHelper helper;

	@BeforeEach
	public void setUp()
	{
		AdempiereTestHelper.get().init();
		helper = new PPOrderCostsTestHelper();

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

		final PPOrderCosts ppOrderCosts = new CreatePPOrderCostsCommand(ppOrder).execute();
		ppOrderCosts.toCollection().forEach(System.out::println);

		//
		// Check order cost prices for component
		{
			final List<PPOrderCost> componentCostsList = ppOrderCosts.getByProductAndCostElements(componentId, ImmutableSet.of(helper.costElement.getId()));
			assertThat(componentCostsList).hasSize(1);
			assertThat(componentCostsList.get(0).getPrice()).usingRecursiveComparison().isEqualTo(CostPrice.builder()
					.ownCostPrice(CostAmount.of("0.0667", helper.currencyId)) // = 1 EUR/Bag divided 15Kg
					.componentsCostPrice(CostAmount.zero(helper.currencyId))
					.uomId(helper.uomKgId)
					.build());
		}

		//
		// Check order cost prices for finished good
		{
			final List<PPOrderCost> finishedGoodCostsList = ppOrderCosts.getByProductAndCostElements(finishedGoodsProductId, ImmutableSet.of(helper.costElement.getId()));
			assertThat(finishedGoodCostsList).hasSize(1);
			assertThat(finishedGoodCostsList.get(0).getPrice()).usingRecursiveComparison().isEqualTo(CostPrice.builder()
					.ownCostPrice(CostAmount.zero(helper.currencyId))
					.componentsCostPrice(CostAmount.of("0.1334", helper.currencyId)) // = component cost price x 2(=200/100)
					.uomId(helper.uomEachId)
					.build());
		}
	}
}
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
import org.adempiere.model.InterfaceWrapperHelper;
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
			assertThat(componentCostsList.get(0).getPrice()).usingRecursiveComparison()
					.isEqualTo(CostPrice.builder()
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
			assertThat(finishedGoodCostsList.get(0).getPrice()).usingRecursiveComparison()
					.isEqualTo(CostPrice.builder()
							.ownCostPrice(CostAmount.zero(helper.currencyId))
							.componentsCostPrice(CostAmount.of("0.1334", helper.currencyId)) // = component cost price x 2(=200/100)
							.uomId(helper.uomEachId)
							.build());
		}
	}

	@Test
	public void sameProductTwice()
	{
		final ProductId finishedGoodsProductId = BusinessTestHelper.createProductId("finished goods", helper.uomBag);
		final ProductId componentId = BusinessTestHelper.createProductId("component", helper.uomBag);

		helper.currentCost().productId(componentId).currentCostPrice("1").uom(helper.uomBag).build();

		helper.uomConversionDAO.createUOMConversion(CreateUOMConversionRequest.builder()
				.productId(componentId)
				.fromUomId(helper.uomBagId)
				.toUomId(helper.uomKgId)
				.fromToMultiplier(new BigDecimal("15"))
				.build());

		// Manufacturing order
		// IMPORTANT: remark that we have the same product twice, with 2 different UOMs
		final I_PP_Order ppOrder = helper.order()
				.finishedGoodsProductId(finishedGoodsProductId).finishedGoodsQty("100").finishedGoodsUOM(helper.uomEach)
				.componentId(componentId).componentQtyRequired("200").componentUOM(helper.uomBag) // i.e. we need 2 Bags for 1 Ea of finished goods
				.componentId2(componentId).componentQtyRequired2("300").componentUOM2(helper.uomKg) // i.e. ... and another 3 Kg
				.build();

		final PPOrderCosts ppOrderCosts = new CreatePPOrderCostsCommand(ppOrder).execute();
		ppOrderCosts.toCollection().forEach(System.out::println);

		//
		// Check order cost prices for component
		{
			final List<PPOrderCost> componentCostsList = ppOrderCosts.getByProductAndCostElements(componentId, ImmutableSet.of(helper.costElement.getId()));
			assertThat(componentCostsList).hasSize(1);
			assertThat(componentCostsList.get(0).getPrice()).usingRecursiveComparison()
					.isEqualTo(CostPrice.ownCostPrice(CostAmount.of("1", helper.currencyId), helper.uomBagId)); // = 1 EUR/Bag
		}

		//
		// Check order cost prices for finished good
		// Basically, for 100 Ea of finished goods we need
		// * 200 bags of component1 which costs 1 EUR/bag => 200 EUR
		// * 300 kg of component1.
		//		1bag costs 1EUR
		//		1bag = 15kg => 1Kg=1/15bags=0.0667bags
		//      => 1Kg costs 0.0667bags x 1 EUR/bag = 0.0667 EUR
		//      => 300Kg costs = 300 x 0.0667 EUR/kg = 20.01 EUR
		// So, 100 Ea of finished goods costs 200 EUR + 20.01 EUR = 220.01 EUR
		// => 1 Ea of finished goods cost 220.01 EUR / 100 = 2.2001 EUR
		{
			final List<PPOrderCost> finishedGoodCostsList = ppOrderCosts.getByProductAndCostElements(finishedGoodsProductId, ImmutableSet.of(helper.costElement.getId()));
			assertThat(finishedGoodCostsList).hasSize(1);
			assertThat(finishedGoodCostsList).element(0).extracting(PPOrderCost::getPrice).usingRecursiveComparison()
					.isEqualTo(CostPrice.builder()
							.ownCostPrice(CostAmount.zero(helper.currencyId))
							.componentsCostPrice(CostAmount.of("2.2001", helper.currencyId))
							.uomId(helper.uomEachId)
							.build());
		}
	}

	@Test
	public void bom_with_OneRawProduct_and_OneCoProduct()
	{
		helper.uomKg.setStdPrecision(2);
		helper.uomKg.setStdPrecision(4);
		InterfaceWrapperHelper.save(helper.uomKg);

		final ProductId finishedGoodsProductId = BusinessTestHelper.createProductId("finished goods", helper.uomKg);
		final ProductId rawProductId = BusinessTestHelper.createProductId("raw-product", helper.uomKg);
		final ProductId coProductId = BusinessTestHelper.createProductId("co-product", helper.uomKg);

		helper.currentCost().productId(rawProductId).currentCostPrice("0.70").uom(helper.uomKg).build();

		final I_PP_Order ppOrder = helper.order()
				.finishedGoodsProductId(finishedGoodsProductId).finishedGoodsQty("75").finishedGoodsUOM(helper.uomKg)
				.componentId(rawProductId).componentQtyRequired("100.005").componentUOM(helper.uomKg)
				.coProductId(coProductId).coProductQtyRequired("-25.005").coProductUOM(helper.uomKg)
				.build();

		final PPOrderCosts ppOrderCosts = new CreatePPOrderCostsCommand(ppOrder).execute();
		ppOrderCosts.toCollection().stream().map(CreatePPOrderCostsCommandTest::toString).forEach(System.out::println);

		//
		// Finished good
		{
			final List<PPOrderCost> finishedGoodCostsList = ppOrderCosts.getByProductAndCostElements(finishedGoodsProductId, ImmutableSet.of(helper.costElement.getId()));
			assertThat(finishedGoodCostsList).hasSize(1);
			assertThat(finishedGoodCostsList).element(0).extracting(PPOrderCost::getPrice).usingRecursiveComparison()
					.isEqualTo(CostPrice.builder()
							.ownCostPrice(CostAmount.zero(helper.currencyId))
							.componentsCostPrice(CostAmount.of("0.8296", helper.currencyId))
							.uomId(helper.uomKgId)
							.build());
		}

		//
		// Co-Product
		{
			final List<PPOrderCost> finishedGoodCostsList = ppOrderCosts.getByProductAndCostElements(coProductId, ImmutableSet.of(helper.costElement.getId()));
			assertThat(finishedGoodCostsList).hasSize(1);
			assertThat(finishedGoodCostsList).element(0).extracting(PPOrderCost::getPrice).usingRecursiveComparison()
					.isEqualTo(CostPrice.builder()
							.ownCostPrice(CostAmount.zero(helper.currencyId))
							.componentsCostPrice(CostAmount.of("0.3112", helper.currencyId))
							.uomId(helper.uomKgId)
							.build());
		}
	}

	private static String toString(PPOrderCost ppOrderCost)
	{
		return ppOrderCost.getTrxType()
				+ " " + ppOrderCost.getPrice();
	}

	@Test
	public void bom_with_OneRawProduct()
	{
		helper.uomKg.setStdPrecision(2);
		helper.uomKg.setStdPrecision(4);
		InterfaceWrapperHelper.save(helper.uomKg);

		final ProductId finishedGoodsProductId = BusinessTestHelper.createProductId("finished goods", helper.uomKg);
		final ProductId rawProductId = BusinessTestHelper.createProductId("raw-product", helper.uomKg);

		helper.currentCost().productId(rawProductId).currentCostPrice("100").uom(helper.uomKg).build();

		final I_PP_Order ppOrder = helper.order()
				.finishedGoodsProductId(finishedGoodsProductId).finishedGoodsQty("533.09").finishedGoodsUOM(helper.uomKg)
				.componentId(rawProductId).componentQtyRequired("716.55").componentUOM(helper.uomKg)
				.build();

		final PPOrderCosts ppOrderCosts = new CreatePPOrderCostsCommand(ppOrder).execute();
		ppOrderCosts.toCollection().stream().map(CreatePPOrderCostsCommandTest::toString).forEach(System.out::println);

		//
		// Finished good
		{
			final List<PPOrderCost> finishedGoodCostsList = ppOrderCosts.getByProductAndCostElements(finishedGoodsProductId, ImmutableSet.of(helper.costElement.getId()));
			assertThat(finishedGoodCostsList).hasSize(1);
			assertThat(finishedGoodCostsList).element(0).extracting(PPOrderCost::getPrice).usingRecursiveComparison()
					.isEqualTo(CostPrice.builder()
									   .ownCostPrice(CostAmount.zero(helper.currencyId))
									   .componentsCostPrice(CostAmount.of("134.4145", helper.currencyId))
									   .uomId(helper.uomKgId)
									   .build());
		}


	}
}
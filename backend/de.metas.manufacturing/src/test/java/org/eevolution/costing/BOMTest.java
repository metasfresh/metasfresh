package org.eevolution.costing;

import static org.eevolution.costing.BOMAssertUtils.assertComponentsCostPrice;
import static org.eevolution.costing.BOMAssertUtils.assertOwnCostPrice;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.BOMComponentType;
import org.junit.Before;
import org.junit.Test;

import de.metas.costing.CostAmount;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.money.CurrencyId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.impl.UOMTestHelper;
import de.metas.util.lang.Percent;

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

public class BOMTest
{
	private final ProductId bomProductId = ProductId.ofRepoId(100);
	private final ProductId componentId1 = ProductId.ofRepoId(101);
	// private final ProductId componentId2 = ProductId.ofRepoId(102);
	private final ProductId coProductId = ProductId.ofRepoId(110);
	// private final ProductId byProductId = ProductId.ofRepoId(120);

	private final CostElementId costElementId1 = CostElementId.ofRepoId(1);
	// private final CostElementId costElementId2 = CostElementId.ofRepoId(2);
	// private final CostElementId costElementId3 = CostElementId.ofRepoId(3);
	// private final CostElementId costElementId4 = CostElementId.ofRepoId(4);

	private final CurrencyId currencyId = CurrencyId.ofRepoId(1);

	private I_C_UOM uom_Each;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final UOMTestHelper uomHelper = new UOMTestHelper();
		uom_Each = uomHelper.createUOM("Each", 1);
	}

	@Test
	public void test_SingleComponent()
	{
		final BOM bom = BOM.builder()
				.productId(bomProductId)
				.costPrice(BOMCostPrice.builder()
						.productId(bomProductId)
						.build())
				.line(BOMLine.builder()
						.componentId(componentId1)
						.componentType(BOMComponentType.Component)
						.qty(Quantity.of(5, uom_Each))
						.costPrice(BOMCostPrice.builder()
								.productId(componentId1)
								.costElementPrice(BOMCostElementPrice.builder()
										.costElementId(costElementId1)
										.costPrice(CostPrice.builder()
												.ownCostPrice(CostAmount.of(5, currencyId))
												.componentsCostPrice(CostAmount.of(55, currencyId))
												.build())
										.build())
								.build())
						.build())
				.build();

		assertOwnCostPrice(bom, costElementId1, 0);
		assertComponentsCostPrice(bom, costElementId1, 0);

		bom.rollupCosts();

		assertOwnCostPrice(bom, costElementId1, "0");
		assertComponentsCostPrice(bom, costElementId1, (55 + 5) * 5);
	}

	@Test
	public void test_CoProductComponent()
	{
		final BOM bom = BOM.builder()
				.productId(bomProductId)
				.costPrice(BOMCostPrice.builder()
						.productId(bomProductId)
						.build())
				.line(BOMLine.builder()
						.componentId(componentId1)
						.componentType(BOMComponentType.Component)
						.qty(Quantity.of(5, uom_Each))
						.costPrice(BOMCostPrice.builder()
								.productId(componentId1)
								.costElementPrice(BOMCostElementPrice.builder()
										.costElementId(costElementId1)
										.costPrice(CostPrice.builder()
												.ownCostPrice(CostAmount.of(5, currencyId))
												.componentsCostPrice(CostAmount.of(55, currencyId))
												.build())
										.build())
								.build())
						.build())
				.line(BOMLine.builder()
						.componentId(coProductId)
						.componentType(BOMComponentType.CoProduct)
						.qty(Quantity.of(-2, uom_Each))
						.coProductCostDistributionPercent(Percent.of(50))
						.costPrice(BOMCostPrice.builder()
								.productId(componentId1)
								.costElementPrice(BOMCostElementPrice.builder()
										.costElementId(costElementId1)
										.costPrice(CostPrice.zero(currencyId))
										.build())
								.build())
						.build())
				.build();

		assertOwnCostPrice(bom, costElementId1, 0);
		assertComponentsCostPrice(bom, costElementId1, 0);

		bom.rollupCosts();

		assertOwnCostPrice(bom, costElementId1, "0");
		assertComponentsCostPrice(bom, costElementId1, "150"); // (55 + 5) * 5 * 50%)
	}

}
